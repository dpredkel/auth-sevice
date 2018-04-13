package com.pda.auth.config;

import com.pda.auth.config.additional.CustomTokenConverter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.Resource;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.config.annotation.configurers.ClientDetailsServiceConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configuration.AuthorizationServerConfigurerAdapter;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableAuthorizationServer;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableResourceServer;
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerEndpointsConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerSecurityConfigurer;
import org.springframework.security.oauth2.provider.token.store.JwtAccessTokenConverter;
import org.springframework.security.oauth2.provider.token.store.KeyStoreKeyFactory;

import java.security.KeyPair;
import java.security.SecureRandom;

@Configuration
@EnableResourceServer
@EnableAuthorizationServer
public class AuthorizationServerConfigurer extends AuthorizationServerConfigurerAdapter {
    // TODO: 1/5/2018 Exception handling
    private static final String CLIENT_SCOPE = "client";
    private static final String SERVER_SCOPE = "server";
    private static final String[] clientGrantTypes = {"password", "refresh_token"};
    private static final String[] serverGrantTypes = {"client_credentials", "refresh_token"};

    @Value("${security.encoder.strength}")
    private Integer strength;

    @Value("${security.client.public.name}")
    private String publicName;
    @Value("${security.client.private.name}")
    private String privateName;
    @Value("${security.client.private.password}")
    private String privatePass;

    @Value("${jwt.certificate.store.file}")
    private Resource keystore;
    @Value("${jwt.certificate.store.password}")
    private String keystorePassword;
    @Value("${jwt.certificate.key.alias}")
    private String keyAlias;
    @Value("${jwt.certificate.key.password}")
    private String keyPassword;

    @Autowired
    private AuthenticationManager authenticationManager;

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder(strength, new SecureRandom());
    }

    @Override
    public void configure(ClientDetailsServiceConfigurer clients) throws Exception {
        clients.inMemory()
                .withClient(publicName)
                .authorizedGrantTypes(clientGrantTypes)
                .scopes(CLIENT_SCOPE)
                .and()
                .withClient(privateName)
                .secret(privatePass)
                .authorizedGrantTypes(serverGrantTypes)
                .scopes(SERVER_SCOPE)
                // Auto approve the required scopes.
                // If we do not auto approve, the user is asked upon login if (s)he approves
                .autoApprove(true);
    }

    @Override
    public void configure(AuthorizationServerEndpointsConfigurer endpoints) {
        endpoints
                .reuseRefreshTokens(false)
                .accessTokenConverter(jwtAccessTokenConverter())
                .authenticationManager(authenticationManager);
    }

    @Bean
    public JwtAccessTokenConverter jwtAccessTokenConverter() {
        KeyStoreKeyFactory keyStoreKeyFactory = new KeyStoreKeyFactory(keystore, keystorePassword.toCharArray());
        KeyPair keyPair = keyStoreKeyFactory.getKeyPair(keyAlias, keyPassword.toCharArray());
        JwtAccessTokenConverter converter = new CustomTokenConverter();
        converter.setKeyPair(keyPair);
        return converter;
    }

    @Override
    public void configure(AuthorizationServerSecurityConfigurer oauthServer) {
        oauthServer
                .tokenKeyAccess("permitAll()")
                .checkTokenAccess("denyAll()");
    }

}

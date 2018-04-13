package com.pda.auth.service.security;

import com.pda.auth.exception.AuthServiceException;
import com.pda.auth.exception.ErrorCode;
import com.pda.auth.model.UserVO;
import com.pda.auth.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Service;

@Service
public class CustomUserDetailsService implements UserDetailsService {
    private static final String PREFIX = "ROLE_";

    @Autowired
    private UserService userService;

    @Override
    public UserDetails loadUserByUsername(String email) {
        UserVO user = this.userService.findByEmail(email);

        if (user == null)
            throw new AuthServiceException("couldn't find " + email + "!", ErrorCode.USERNAME_NOT_FOUND.getValue());

        return new User(user.getUuid(),
                user.getPassword(),
                user.isActive(), user.isActive(), user.isActive(), user.isActive(),
                AuthorityUtils.createAuthorityList(PREFIX + user.getAuthority()));

    }

}

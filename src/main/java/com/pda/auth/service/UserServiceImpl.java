package com.pda.auth.service;

import com.pda.auth.converter.UserConverter;
import com.pda.auth.db.entity.User;
import com.pda.auth.db.entity.Authority;
import com.pda.auth.db.repository.UserRepository;
import com.pda.auth.exception.AuthServiceException;
import com.pda.auth.exception.ErrorCode;
import com.pda.auth.model.UserVO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Slf4j
@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserRepository repository;

    @Autowired
    private UserConverter converter;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Override
    public UserVO findByEmail(String email) {
        return converter.toVO(repository.findByEmail(email));
    }

    @Override
    public void save(String email, String password) {
        User existing = repository.findByEmail(email);
        if (existing != null)
            throw new AuthServiceException("User already exist", ErrorCode.USER_ALREADY_EXIST.getValue());

        String hash = passwordEncoder.encode(password);

        User user = new User();
        user.setUuid(UUID.randomUUID().toString());
        user.setEmail(email);
        user.setPassword(hash);
        user.setActive(true);
        user.setAuthority(Authority.USER);

        repository.save(user);

        log.info("Created new user | username: {} , uuid: {}", email, user.getUuid());
    }

}

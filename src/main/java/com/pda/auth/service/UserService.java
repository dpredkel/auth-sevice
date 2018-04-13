package com.pda.auth.service;

import com.pda.auth.model.UserVO;

public interface UserService {

    UserVO findByEmail(String email);

    void save(String email, String password);
}

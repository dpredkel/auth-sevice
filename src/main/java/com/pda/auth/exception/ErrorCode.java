package com.pda.auth.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum ErrorCode {

    ERROR_VALIDATION(400_0),
    USER_ALREADY_EXIST(400_1),
    INCORRECT_EMAIL(400_2),
    INCORRECT_PASSWORD(400_3),
    USERNAME_NOT_FOUND(400_4);

    private int value;

}

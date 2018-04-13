package com.pda.auth.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@AllArgsConstructor
public class AuthServiceException extends RuntimeException {

    @Getter
    private int status;

    public AuthServiceException(String message) {
        super(message);
    }

    public AuthServiceException(String message, Throwable cause) {
        super(message, cause);
    }

    public AuthServiceException(String message, int status) {
        super(message);
        this.status = status;
    }

    public AuthServiceException(String s, Throwable cause, int status) {
        super(s, cause);
        this.status = status;
    }
}

package com.pda.auth.controller;

import com.pda.auth.exception.AuthServiceException;
import com.pda.auth.exception.ErrorCode;
import com.pda.auth.model.RegistrationDTO;
import com.pda.auth.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.oauth2.provider.OAuth2Authentication;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping("/user")
public class UserController {

    @Autowired
    private UserService userService;

    @PostMapping
    @PreAuthorize("#oauth2.hasScope('server')")
    public ResponseEntity<HttpStatus> create(@Valid @RequestBody RegistrationDTO dto) {
        userService.save(dto.getEmail(), dto.getPassword());
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @GetMapping("/exc")
    public ResponseEntity<HttpStatus> exc() {
        throw new AuthServiceException("User already exist", ErrorCode.USER_ALREADY_EXIST.getValue());
    }

    @GetMapping("/ok")
    public ResponseEntity<HttpStatus> ok() {
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @GetMapping("/err")
    public ResponseEntity<HttpStatus> err() throws Exception {
        throw new Exception("User already exist");
    }

}

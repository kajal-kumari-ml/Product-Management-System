package com.management.project.controller;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.management.project.dto.RefreshTokenRequest;
import com.management.project.dto.SignInRequest;
import com.management.project.dto.SignUpRequest;
import com.management.project.entity.User;
import com.management.project.exception.UserNotFoundException;
import com.management.project.service.AuthenticationService;



@RestController
@RequestMapping("api/auth")
public class AuthenticationContoller {

    @Autowired
    private AuthenticationService userService;

    @PostMapping("/signup")
    public ResponseEntity<?> signUp(@RequestBody SignUpRequest request)  {
       User user= userService.registerUser(request);
         if(user==null){
              return new ResponseEntity<>("User already exists", HttpStatus.BAD_REQUEST);
            }
        return new ResponseEntity<>("User registered successfully", HttpStatus.OK);
    }

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody SignInRequest user) throws UserNotFoundException {
        return new ResponseEntity<>(userService.loginInUser(user), HttpStatus.OK);
    }

    @PostMapping("/refresh")
    public ResponseEntity<?> refreshToken(@RequestBody  RefreshTokenRequest refreshToken) {
        return new ResponseEntity<>(userService.refreshToken(refreshToken), HttpStatus.OK);
    }
    
}

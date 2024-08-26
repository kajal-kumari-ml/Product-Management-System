package com.management.project.service;


import java.util.HashMap;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.management.project.dto.JwtResponse;
import com.management.project.dto.RefreshTokenRequest;
import com.management.project.dto.SignInRequest;
import com.management.project.dto.SignUpRequest;
import com.management.project.entity.User;
import com.management.project.enums.Role;
import com.management.project.exception.UserNotFoundException;
import com.management.project.repository.UserRepository;



@Service
public class AuthenticationService {

    @Autowired
    UserRepository userRepository;

    private final PasswordEncoder passwordEncoder;


    private final JwtService jwtService;

    public AuthenticationService(PasswordEncoder passwordEncoder,JwtService jwtService) {
        this.passwordEncoder = passwordEncoder;
        this.jwtService = jwtService;
    }

    public User registerUser(SignUpRequest user)  {
      try{
      User exist=  userRepository.findByEmail(user.getEmail());
        if(exist!=null){
            return null;
        }
        User newUser = new User();
        newUser.setEmail(user.getEmail());
        newUser.setName(user.getFirstName() + " " + user.getLastName());
        newUser.setPassword(passwordEncoder.encode(user.getPassword()));
        if(user.getRole()!=null){
            newUser.setRole(user.getRole().equals("ADMIN") ? Role.ADMIN : Role.VENDOR);
        }
        userRepository.save(newUser);
        return newUser;
    }catch(Exception e){
        return null;
    }
    }


    public JwtResponse loginInUser(SignInRequest user) throws UserNotFoundException{
            User userOptional = userRepository.findByEmail(user.getUsername());
            if (!passwordEncoder.matches(user.getPassword(), userOptional.getPassword())) {
                throw new UsernameNotFoundException("User not found");
            }
            if (userOptional == null) {
                throw new UserNotFoundException("User not found");
            }
            String token = jwtService.generateToken(userOptional);
            String refreshToken = jwtService.generateRefreshToken(new HashMap<>(), userOptional);

            JwtResponse jwtResponse = new JwtResponse();
            jwtResponse.setToken(token);
            jwtResponse.setRefreshToken(refreshToken);
            return jwtResponse;

    }

    public JwtResponse refreshToken(RefreshTokenRequest refreshTokenRequest){
        String username = jwtService.extractUsername(refreshTokenRequest.getToken());
        User user = userRepository.findByEmail(username);
        if(jwtService.isTokenValis(refreshTokenRequest.getToken(),user)){
            String newToken = jwtService.generateToken(user);

            JwtResponse jwtResponse = new JwtResponse();
            jwtResponse.setToken(newToken);
            jwtResponse.setRefreshToken(refreshTokenRequest.getToken());
            return jwtResponse;
        }
        throw new UsernameNotFoundException("User not found");
    }

    
}

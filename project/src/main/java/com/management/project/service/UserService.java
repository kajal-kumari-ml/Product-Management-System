package com.management.project.service;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Service;

import com.management.project.repository.UserRepository;



@Service
public class  UserService {

    @Autowired
    private UserRepository userRepository;

    public UserDetailsService userDetailsService(){
        return new  UserDetailsService(){
            @Override
            public UserDetails loadUserByUsername(String username) {
            return userRepository.findByEmail(username);
            
        }
    };
}
    
}

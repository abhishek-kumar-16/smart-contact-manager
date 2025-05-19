package com.smartmanager.services.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.smartmanager.repositories.userRepo;

@Service
public class SecurityCustomUserDetailServices implements UserDetailsService{
     @Autowired
     private userRepo userRepo;
     
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
         // need to load user from database
        return userRepo.findByEmail(username).orElseThrow(() -> new UsernameNotFoundException("User not found with email: " + username)); 
    }

}

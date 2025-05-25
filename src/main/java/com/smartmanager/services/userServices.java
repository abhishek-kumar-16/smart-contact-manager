package com.smartmanager.services;

import java.util.List;
import java.util.Optional;

import com.smartmanager.entities.user;

public interface userServices {
//  here are the standard methods that we will be using
//  for the user services
     user saveUser(user user);   
        Optional<user> getUserById(String id); // optional is used to for the case when the user is not found, no need of if else
        Optional<user> updatUser(user user);
        void deleteUserById(String id);
        boolean isUserPresent(String userId);
        boolean isUserPresentByEmail(String email);
        List<user> getAllUsers();
        user getUserByEmail(String email);
        // more methods can be added as per the requirements
}

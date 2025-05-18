package com.smartmanager.services.impl;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.smartmanager.entities.user;
import com.smartmanager.helpers.ResourceNotFoundException;
import com.smartmanager.repositories.userRepo;
import com.smartmanager.services.userServices;

@Service
public class userServiceImpl implements userServices{
//  interface are abstract classes that are used to define the methods that will be used in the service

    @Autowired
    private userRepo userRepo;
    // here we are using the userRepo to access the database
    private Logger logger=  LoggerFactory.getLogger(userServiceImpl.class);

  

    @Override
    public user saveUser(user user) {
        String userID=UUID.randomUUID().toString();
        user.setUserId(userID);
        // simillarly passwords can be encoded here
        logger.info("user saved successfully");
        return userRepo.save(user);
    }

    @Override
    public Optional<user> getUserById(String id) {
       return userRepo.findById(id);
        // TODO Auto-generated method stub
        // throw new UnsupportedOperationException("Unimplemented method 'getUserById'");
    }

    @Override
    public Optional<user> updatUser(user user) {
       user old_user=userRepo.findById(user.getUserId()).orElseThrow(() -> new ResourceNotFoundException("User not found"));
    //     now this old user will be updated with the new user
       old_user.setUserId(user.getUserId());
       old_user.setEmail(user.getEmail());
       old_user.setName(user.getName());
       old_user.setPassword(user.getPassword());
       old_user.setPhone(user.getPhone());
       old_user.setProfilePic(user.getProfilePic());
       old_user.setEmailVerified(user.isEmailVerified());
       old_user.setPhoneVerified(user.isPhoneVerified());
      old_user.setProvider(user.getProvider());
      old_user.setProviderId(user.getProviderId());


       user save=userRepo.save(old_user);
       logger.info("user updated successfully");
       return Optional.ofNullable(save);
        // TODO Auto-generated method stub
        // throw new UnsupportedOperationException("Unimplemented method 'updatUser'");
    }

    @Override
    public void deleteUserById(String id) {
        user old_user=userRepo.findById(id).orElseThrow(() -> new ResourceNotFoundException("User not found"));
        userRepo.delete(old_user);
        logger.info("user deleted successfully");
    }

    @Override
    public boolean isUserPresent(String userId) {
        user old_user=userRepo.findById(userId).orElse(null);
        if(old_user==null){
            return false;
        }
        return true;
    }

    @Override
    public boolean isUserPresentByEmail(String email) {
        user old_user=userRepo.findByEmail(email).orElse(null);
        if(old_user==null){
            return false;
        }
        return true;
        // TODO Auto-generated method stub
        // throw new UnsupportedOperationException("Unimplemented method 'isUserPresentByEmail'");
    }

    @Override
    public List<user> getAllUsers() {
       return userRepo.findAll();
    }


}

package com.smartmanager.services.impl;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.smartmanager.entities.user;
import com.smartmanager.helpers.AppConstants;
import com.smartmanager.helpers.Helper;
import com.smartmanager.helpers.ResourceNotFoundException;
import com.smartmanager.repositories.userRepo;
import com.smartmanager.services.EmailService;
import com.smartmanager.services.userServices;

@Service
public class userServiceImpl implements userServices {
    // interface are abstract classes that are used to define the methods that will
    // be used in the service

    @Autowired
    private userRepo userRepo;
    // here we are using the userRepo to access the database
    private Logger logger = LoggerFactory.getLogger(userServiceImpl.class);

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private EmailService emailService;

    @Override
    public user saveUser(user user) {
        String userID = UUID.randomUUID().toString();
        user.setUserId(userID);
        // simillarly passwords can be encoded here
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        // here we are using the password encoder to encode the password

        // setting the default role of user
        user.setRoleList(List.of(AppConstants.ROLE_USER));
        // here we are setting the default role of user to ROLE_USER
        logger.info("user saved successfully");
      
        String emailToken = UUID.randomUUID().toString();
        user.setEmailToken(emailToken);
        user Saveduser = userRepo.save(user);
        String verificationLink = Helper.getVerificationLink(emailToken);
        String subject = "Verify your Smart Manager account";
        String message = """
                    Dear %s,

                    Thank you for registering with Smart Manager.

                    Please verify your email address by clicking the link below:
                    %s

                    If you did not request this, please ignore this email.

                    Regards,
                    Abhishek Kumar
                    Smart Contact Manager Team
                """.formatted(Saveduser.getName(), verificationLink);

        emailService.sendEmail(Saveduser.getEmail(), subject, message);
                 return Saveduser;
    } 

    @Override
    public Optional<user> getUserById(String id) {
        return userRepo.findById(id);
        // TODO Auto-generated method stub
        // throw new UnsupportedOperationException("Unimplemented method
        // 'getUserById'");
    }

    @Override
    public Optional<user> updatUser(user user) {
        user old_user = userRepo.findById(user.getUserId())
                .orElseThrow(() -> new ResourceNotFoundException("User not found"));
        // now this old user will be updated with the new user
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

        user save = userRepo.save(old_user);
        logger.info("user updated successfully");
        return Optional.ofNullable(save);
        // TODO Auto-generated method stub
        // throw new UnsupportedOperationException("Unimplemented method 'updatUser'");
    }

    @Override
    public void deleteUserById(String id) {
        user old_user = userRepo.findById(id).orElseThrow(() -> new ResourceNotFoundException("User not found"));
        userRepo.delete(old_user);
        // logger.info("user deleted successfully");
    }

    @Override
    public boolean isUserPresent(String userId) {
        user old_user = userRepo.findById(userId).orElse(null);
        if (old_user == null) {
            return false;
        }
        return true;
    }

    @Override
    public boolean isUserPresentByEmail(String email) {
        user old_user = userRepo.findByEmail(email).orElse(null);
        if (old_user == null) {
            return false;
        }
        return true;
        // TODO Auto-generated method stub
        // throw new UnsupportedOperationException("Unimplemented method
        // 'isUserPresentByEmail'");
    }

    @Override
    public List<user> getAllUsers() {
        return userRepo.findAll();
    }

    @Override
    public user getUserByEmail(String email) {
        user old_user = userRepo.findByEmail(email).orElse(null);
        return old_user;

    }

    @Override
    public user findByEmailToken(String emailToken) {
       return userRepo.findByEmailToken(emailToken);
        // TODO Auto-generated method stub
        // throw new UnsupportedOperationException("Unimplemented method
        // 'findByEmailToken'");
    }

}

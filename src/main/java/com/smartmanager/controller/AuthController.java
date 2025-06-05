package com.smartmanager.controller;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.smartmanager.entities.user;
import com.smartmanager.repositories.userRepo;
import com.smartmanager.services.userServices;

@Controller
@RequestMapping("/auth")
public class AuthController {
     @Autowired
     userServices userService;

     @Autowired
     userRepo userRepo;

    // verify email
    @RequestMapping("/verify-email")
    public String verifyEmail(@RequestParam("emailToken") String emailToken) {
        // This method will handle the email verification
        // It will be called when the user clicks on the verification link
       user user = userRepo.findByEmailToken(emailToken);
    //    System.out.println("Email token: " + emailToken);
    //    System.out.println("User found: " + user.getEmail());
System.out.println("Email token: " + emailToken);
 // Redirect to success page after verification
    if(user != null) {
        // If user is found, set emailVerified to true
        user.setEmailVerified(true);
        
        user.setEnabled(true);
        userRepo.save(user);
        // Redirect to success page
        return "success_page";
    } else {
        // If user is not found, redirect to error page
        return "error_page";
    }
    

  
    }

}

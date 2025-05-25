package com.smartmanager.controller;

import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;

import com.smartmanager.entities.user;
import com.smartmanager.helpers.Helper;
import com.smartmanager.services.userServices;

import org.springframework.ui.Model;

@Controller
@RequestMapping("/user")
public class UserController {
//    user dashboard
     @Autowired 
     private userServices userService;
    Logger logger = org.slf4j.LoggerFactory.getLogger(UserController.class);
    



    // This is the user dashboard page
    @RequestMapping(value="/dashboard")
    public String dashboard() {
        return "user/dashboard";  // inside user folder, place dashboard
    }





// profile page
  @RequestMapping(value="/profile")
    public String profile(Model model, Authentication authentication) {
        // You can use the Principal object to get user details if needed
        // For example, you can access the username with principal.getName()
        // You can also add user details to the model if needed
        

      
     
        return "user/profile";  // inside user folder, place dashboard
    }






}

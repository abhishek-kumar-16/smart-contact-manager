package com.smartmanager.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.slf4j.Logger;
import com.smartmanager.entities.user;
import com.smartmanager.helpers.Helper;
import com.smartmanager.services.userServices;

@ControllerAdvice // this is used to apply the same model attribute to all controllers
public class RootController {
     Logger logger = org.slf4j.LoggerFactory.getLogger(UserController.class);

     @Autowired
     private userServices userService;

     @ModelAttribute
     public void setCurrentUserInfo(Model model, Authentication authentication) {
          if(authentication ==null) {
               // logger.info("No user is logged in");
               return; // if no user is logged in, we don't need to fetch user info
          }
          String userEmail = Helper.getEmailOfUser(authentication); // This will give you the username of the logged-in
                                                                    // user
          // now userinfo for the current logged-in user can be fetched from the database
          // using this email
          // logger.info("User email: " + userEmail);

          user currentUser = userService.getUserByEmail(userEmail);

          model.addAttribute("user", currentUser); // this is in key pair format, so we can access it in the
                                                   // profile.html file using ${user.name} or ${user.email} etc.

     }

}

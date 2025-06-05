package com.smartmanager.controller;

import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.smartmanager.entities.user;
import com.smartmanager.helpers.Helper;
import com.smartmanager.helpers.notification;
import com.smartmanager.helpers.notificationType;
import com.smartmanager.services.EmailService;
import com.smartmanager.services.userServices;

import jakarta.servlet.http.HttpSession;

import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequestMapping("/user")
public class UserController {
    // user dashboard
    @Autowired
    private userServices userService;
    Logger logger = org.slf4j.LoggerFactory.getLogger(UserController.class);
    
    @Autowired
    private EmailService emailService;

    // This is the user dashboard page
    @RequestMapping(value = "/dashboard")
    public String dashboard() {
        return "user/dashboard"; // inside user folder, place dashboard
    }

    // profile page
    @RequestMapping(value = "/profile")
    public String profile(Model model, Authentication authentication) {
        // You can use the Principal object to get user details if needed
        // For example, you can access the username with principal.getName()
        // You can also add user details to the model if needed

        return "user/profile"; // inside user folder, place dashboard
    }

    @PostMapping("/submit-feedback")
    public String sendFeedback(@RequestParam("comments") String comments,  @RequestParam("rating") String rating, HttpSession session, Authentication authentication) {


         String userName = Helper.getEmailOfUser(authentication);
        user user = userService.getUserByEmail(userName);
        // Send the feedback via email
        String to = "kumarabhishekk16dec@gmail.com"; // Replace with recipient email
        String subject = "New User Feedback";
        String body = "User Feedback Received\n\n"
                + "Name: " + user.getName() + "\n"
                + "Rating: " + rating + " \n\n"
                + "Email: " + userName+ "\n\n"
                + "Comment:\n" + comments;


        emailService.sendEmail(to, subject, body); // Your email sending service

        notification message = notification.builder().msg("Feedback submitted ! Thanks for your valuable feedback !").type(notificationType.green)
                .build();
        session.setAttribute("message", message); // can't pass message directly, need to build it

      return "redirect:/user/send-feedback"; // Redirect after sending

    }

    @RequestMapping(value = "/send-feedback")
    public String requestMethodName() {
        return "user/feedback_form";
    }
    

}

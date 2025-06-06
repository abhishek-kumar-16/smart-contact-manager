package com.smartmanager.controller;

import java.util.Optional;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.smartmanager.entities.user;
import com.smartmanager.forms.ResetPasswordForm;
import com.smartmanager.helpers.Helper;
import com.smartmanager.helpers.notification;
import com.smartmanager.helpers.notificationType;
import com.smartmanager.repositories.userRepo;
import com.smartmanager.services.EmailService;
import com.smartmanager.services.userServices;

import jakarta.servlet.http.HttpSession;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
@RequestMapping("/auth")
public class AuthController {
    @Autowired
    userServices userService;

    @Autowired
    userRepo userRepo;

    @Autowired
    EmailService emailService;

    // verify email
    @RequestMapping("/verify-email")
    public String verifyEmail(@RequestParam("emailToken") String emailToken) {
        // This method will handle the email verification
        // It will be called when the user clicks on the verification link
        user user = userRepo.findByEmailToken(emailToken);
        // System.out.println("Email token: " + emailToken);
        // System.out.println("User found: " + user.getEmail());
        System.out.println("Email token: " + emailToken);
        // Redirect to success page after verification
        if (user != null) {
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

    @PostMapping("/resend-verification")
    public String resendVerification(@RequestParam("email") String email, HttpSession session) {
        // Find user by email
        Optional<user> userOpt = userRepo.findByEmail(email);
        if (userOpt.isPresent()) {
            user user = userOpt.get();
            if (!user.isEnabled()) {
                String newToken = UUID.randomUUID().toString();
                user.setEmailToken(newToken);
                userRepo.save(user);

                String verificationLink = Helper.getVerificationLink(newToken);
                String subject = "Verify your Smart Manager account";
                String message = String.format("""
                        Dear %s,

                        Please verify your email address by clicking the link below:
                        %s

                        Regards,
                        Abhishek Kumar
                        Smart Contact Manager Team
                        """, user.getName(), verificationLink);

                emailService.sendEmail(email, subject, message);

                notification popupMessage = notification.builder()
                        .msg("Verification link resent, check your inbox inlcuding spam folder !")
                        .type(notificationType.green).build();

                session.setAttribute("message", popupMessage);
            } else {
                notification popupMessage = notification.builder().msg("Account already verified! Please login !")
                        .type(notificationType.green).build();

                session.setAttribute("message", popupMessage);
            }
        } else {
            notification popupMessage = notification.builder().msg("Kindly register first to get verification link !")
                    .type(notificationType.red).build();

            session.setAttribute("message", popupMessage);
        }

        return "redirect:/login"; // or show a message page
    }

    @RequestMapping(value = "/send-reset-password-link")
    public String sendResetPasswordLink(@RequestParam("username") String username, HttpSession session) {

        Optional<user> userOpt = userRepo.findByEmail(username);

        if (userOpt.isPresent()) {
            // make a reset password link and send it to user
            user user = userOpt.get();
            String newToken = UUID.randomUUID().toString();
            user.setPasswordResetToken(newToken);
            userRepo.save(user);
            String verificationLink = Helper.getResetPasswordLink(newToken);
            String subject = "Reset Password Link";
            String message = String.format("""
                    Dear %s,

                    Please reset your password by clicking the link below:
                    %s

                    Regards,
                    Abhishek Kumar
                    Smart Contact Manager Team
                    """, user.getName(), verificationLink);

            emailService.sendEmail(username, subject, message);
             notification popupMessage = notification.builder()
                    .msg("Password reset link has been sent to your email !")
                    .type(notificationType.green).build();

            session.setAttribute("message", popupMessage);
        }

        else {
            // when no user with the given email exists
            notification popupMessage = notification.builder()
                    .msg("There does not exist any account with the give email !")
                    .type(notificationType.red).build();

            session.setAttribute("message", popupMessage);
        }

        return "redirect:/login";
    }

    @RequestMapping(value = "/reset-password")
    public String requestMethodName(@RequestParam("passwordResetToken") String passwordResetToken, Model model, HttpSession session) {
       user user = userRepo.findByPasswordResetToken(passwordResetToken);
       
      
       ResetPasswordForm resetPasswordForm = new ResetPasswordForm();
        if(user != null){
            resetPasswordForm.setToken(passwordResetToken);
            model.addAttribute("resetPasswordForm", resetPasswordForm);
             return "forgot_password";
        }
       
         notification popupMessage = notification.builder()
                    .msg("Something went wrong ! Please try again !")
                    .type(notificationType.red).build();

            session.setAttribute("message", popupMessage);
        return "redirect:/login";
    }
    

}

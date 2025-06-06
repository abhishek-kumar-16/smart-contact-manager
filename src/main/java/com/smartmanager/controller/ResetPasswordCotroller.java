package com.smartmanager.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import com.smartmanager.entities.user;
import com.smartmanager.forms.ResetPasswordForm;
import com.smartmanager.forms.signupForm;
import com.smartmanager.helpers.notification;
import com.smartmanager.helpers.notificationType;
import com.smartmanager.repositories.userRepo;

import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;

@Controller
@RequestMapping("/reset")
public class ResetPasswordCotroller {

    @Autowired
    userRepo userRepo;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @RequestMapping(value = "/reset-password", method = RequestMethod.POST)
    public String requestMethodName(@Valid @ModelAttribute ResetPasswordForm resetPasswordForm, HttpSession session) {

        System.out.println("reset password token: " + resetPasswordForm.getToken());

        user user = userRepo.findByPasswordResetToken(resetPasswordForm.getToken());
        user.setPassword(passwordEncoder.encode(resetPasswordForm.getNewPassword()));

        user.setPasswordResetToken(null); // Invalidate token
        userRepo.save(user);

         notification popupMessage = notification.builder()
                    .msg("Password changed successfully ! Login")
                    .type(notificationType.green).build();

            session.setAttribute("message", popupMessage);

        return "/login";
    }

}

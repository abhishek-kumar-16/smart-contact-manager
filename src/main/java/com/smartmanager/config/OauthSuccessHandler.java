package com.smartmanager.config;

import java.io.IOException;
import java.util.List;
import java.util.UUID;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.oauth2.core.user.DefaultOAuth2User;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

import com.smartmanager.entities.Providers;
import com.smartmanager.entities.user;
import com.smartmanager.helpers.AppConstants;
import com.smartmanager.repositories.userRepo;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@Component
public class OauthSuccessHandler implements AuthenticationSuccessHandler {

    Logger logger = LoggerFactory.getLogger(OauthSuccessHandler.class);

    @Autowired
    private userRepo userRepo;

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response,
            Authentication authentication) throws IOException, ServletException {
        logger.info("Oauth login success");
        //  here saving data from google sign in to databasefor user

      DefaultOAuth2User user = (DefaultOAuth2User)authentication.getPrincipal();

//    logger.getLogger().info("User details: {}", user.getAttributes()); 
// simillarly here other user details can be fetched
      String email = user.getAttribute("email").toString();
      String name = user.getAttribute("name").toString();
      String picture = user.getAttribute("picture").toString();       
      
      user OauthUser = new user();
        OauthUser.setEmail(email);
        OauthUser.setName(name);
        OauthUser.setProfilePic(picture);
        OauthUser.setPassword("DefaultPassword");
        OauthUser.setUserId(UUID.randomUUID().toString());
        OauthUser.setProvider(Providers.GOOGLE);
        OauthUser.setEnabled(true);
        OauthUser.setEmailVerified(true);
        OauthUser.setProviderId(user.getName());
        OauthUser.setRoleList(List.of(AppConstants.ROLE_USER));
        OauthUser.setAbout("this account is created using google sign in");
        
        // check if user already exists
        user existingUser = userRepo.findByEmail(email).orElse(null);
        if(existingUser == null) {
            // save the user to the database
            userRepo.save(OauthUser);
            logger.info("user created");
        } else {
            // update the existing user with new information
            existingUser.setProfilePic(picture);
            existingUser.setName(name);
            userRepo.save(existingUser);
            logger.info("user updated");
        }


        response.sendRedirect("/user/profile");
    }

}

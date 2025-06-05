package com.smartmanager.helpers;



import org.springframework.security.core.Authentication;
import org.springframework.security.oauth2.client.authentication.OAuth2AuthenticationToken;
import org.springframework.security.oauth2.core.user.OAuth2User;

public class Helper {




  public static String  getEmailOfUser(Authentication authentication) {
    //  above authentication is passed which will  help to know which signin method, google, github , facebook etc was used
    // AuthenticationPrincipal principal = (AuthenticationPrincipal) authentication.getPrincipal();


//     for the current project , there are three // types of users: login with google, github and username-password
//  so three different ways to get email of the user that is username here in this project
   
// case-1 + 2 
if(authentication instanceof OAuth2AuthenticationToken){

    var oauth2AuthenticationToken = (OAuth2AuthenticationToken)authentication;
    var clientId = oauth2AuthenticationToken.getAuthorizedClientRegistrationId();
    var Oauth2User= (OAuth2User) oauth2AuthenticationToken.getPrincipal();
     String UserEmail="";
    if(clientId.equalsIgnoreCase("google")){
     System.out.println("google sign in");
    UserEmail= Oauth2User.getAttribute("email").toString();
    }
    else if(clientId.equalsIgnoreCase("github")){
    UserEmail= Oauth2User.getAttribute("email")!= null ? Oauth2User.getAttribute("email").toString() : Oauth2User.getAttribute("login").toString() + "@gmail.com";
        System.out.println("github sign in");
    }
 
 return UserEmail;
}
else{
    System.out.println("username-password sign in");
    return authentication.getName();
}

    
   
  }

  public static String getVerificationLink(String emailToken) {
    // This method will return the verification link for the user
    // It will be used to send the verification link to the user
    // It will be used in the email service
    return "http://localhost:8081/auth/verify-email?emailToken=" + emailToken;
  }

}

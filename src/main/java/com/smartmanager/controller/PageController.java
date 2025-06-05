package com.smartmanager.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import com.smartmanager.entities.user;
import com.smartmanager.forms.signupForm;
import com.smartmanager.helpers.notification;
import com.smartmanager.helpers.notificationType;
import com.smartmanager.services.userServices;

import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;


@Controller
public class PageController {

//   injecting userservices so that it can be used here
@Autowired
userServices userServices;


   @RequestMapping("home")
   public String home() {
    System.out.println("home page handler");
       return "home";
   }

   @RequestMapping("about")
   public String about() {
       System.out.println("about page handler");
       return "about";
   }

   @RequestMapping("products")
   public String products() {
       System.out.println("products page handler");
       return "user/products";
   }

   @RequestMapping("services")
   public String services() {
       System.out.println("services page handler");
       return "services";
   }
   @RequestMapping("contact")
   public String contact() {
       System.out.println("services page handler");
       return "contact";
   }
   @RequestMapping("login")
   public String login() {
       System.out.println("services page handler");
       return "login";
   }
   @RequestMapping("signup")
   public String signup(Model model) {
         // model.addAttribute("signupForm", new signupForm());
       System.out.println("services page handler");
       signupForm signupForm = new signupForm();
        // signupForm.setName("John Doe"); 
       model.addAttribute("signupForm", signupForm);
       
    //     now the above object is passed to the signup page
    //     so that we can use it in the form
       return "signup";
   }


//    handling the request for signup for user

 @RequestMapping(value = "register", method=RequestMethod.POST)
 public String  ProcessRegister(@Valid @ModelAttribute signupForm signupForm,BindingResult rBindingResult, HttpSession session) {
    // when the user clicks on the signup button, this method will be called
    System.out.println("signup page handler");
    // System.out.println(signupForm.getAbout());
    if(rBindingResult.hasErrors()){
        System.out.println("Error in form");
    
        notification message=notification.builder().msg("Please fill all the fields").type(notificationType.red).build();
        session.setAttribute("message", message);
        return "signup";
    }

//  now do the validation of the data
   

//  save form to database
//  extract the data from the form and made a user object so that it can be saved in the database
//  user newUser = user.builder() builder is not taking default data
//                   .name(signupForm.getName())
//                   .email(signupForm.getEmail())
//                   .password(signupForm.getPassword())
//                   .phone(signupForm.getPhone())
//                   .profilePic("logo-design.png")
//                   .build();

    user newUser = new user();
    newUser.setName(signupForm.getName());
    newUser.setEmail(signupForm.getEmail());
    newUser.setPassword(signupForm.getPassword());
    newUser.setPhone(signupForm.getPhone());
    newUser.setProfilePic("logo-design.png");
    newUser.setAbout(signupForm.getAbout());



     user SavedUser= userServices.saveUser(newUser);              
   System.out.println("User saved successfully");
//   send message here of success

  notification message=notification.builder().msg("Registration successful ! Please verify the link sent to your to email to activate account !").type(notificationType.green).build();

session.setAttribute("message", message);
    // now we need to redirect to the signup page
    // so that the user can see the message
    // but we need to use redirect attribute to send the message
    // so we will use redirect:/signup
    // but we need to add the message in the session
    // so that it can be used in the signup page

// after saving the data in the database, redirect to the signup page
    return "redirect:/signup";
 }


 // saving userserservice 

          
 
 
   
}

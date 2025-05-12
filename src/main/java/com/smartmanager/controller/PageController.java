package com.smartmanager.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;


@Controller
public class PageController {
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
   public String signup() {
       System.out.println("services page handler");
       return "signup";
   }
 
   
}

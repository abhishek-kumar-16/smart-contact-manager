package com.smartmanager.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
@RequestMapping("/user")
public class UserController {
//    user dashboard

    @RequestMapping(value="/dashboard")
    public String dashboard() {
        return "user/dashboard";  // inside user folder, place dashboard
    }
// profile page
  @RequestMapping(value="/profile")
    public String profile() {
        return "user/profile";  // inside user folder, place dashboard
    }


}

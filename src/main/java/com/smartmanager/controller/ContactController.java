package com.smartmanager.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/user/contact")
public class ContactController {

    //  add contact page handler
    @RequestMapping("/add")
    public String addContact() {
        System.out.println("Add contact page handler");
        return "user/add_contact";
    }

}

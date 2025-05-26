package com.smartmanager.controller;

import java.net.Authenticator;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;

import com.smartmanager.entities.contact;
import com.smartmanager.entities.user;
import com.smartmanager.forms.ContactForms;
import com.smartmanager.helpers.Helper;
import com.smartmanager.repositories.ContactRepo;
import com.smartmanager.services.ContactServices;
import com.smartmanager.services.userServices;

import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMethod;


@Controller
@RequestMapping("/user/contact")
public class ContactController {

    private final ContactRepo contactRepo;

    //  add contact page handler
  // for production use
    // private ContactServices contactServices = new ContactServices();

    //  for testing use
   @Autowired
   private ContactServices contactServices;
   
   @Autowired
   private userServices userServices;

    ContactController(ContactRepo contactRepo) {
        this.contactRepo = contactRepo;
    }

    @RequestMapping("/add")
    public String addContact(Model model) {
        //  this method will handle the request to show the add contact page
        ContactForms contactForms = new ContactForms();
        // sending empty contact form to the view
        model.addAttribute("contactForms", contactForms);
        
        System.out.println("Add contact page handler");
        return "user/add_contact";
    }
   
    @RequestMapping(value = "/add", method=RequestMethod.POST)
    public String saveContact(@ModelAttribute ContactForms contactForms, Authentication authentication) {
        //  this method will handle the request to save the contact
        System.out.println("inside processing add contact form");
        System.out.println(contactForms);

        String userName= Helper.getEmailOfUser(authentication);
        user user = userServices.getUserByEmail(userName);
        // convert ContactForms to contact entity
       contact newContact = new contact();
        newContact.setName(contactForms.getName());
        newContact.setEmail(contactForms.getEmail());
        newContact.setPhone(contactForms.getPhone());
        newContact.setAddress(contactForms.getAddress());
        newContact.setDescription(contactForms.getDescription());
        newContact.setFavorite(contactForms.isFavorite());
        newContact.setSocialMedia(contactForms.getSocialMedia());
        newContact.setUser(user);
        
       contactServices.saveContact(newContact);

        return "redirect:/user/contact/add";
    }
    
    

}

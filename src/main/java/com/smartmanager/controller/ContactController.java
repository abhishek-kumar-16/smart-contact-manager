package com.smartmanager.controller;

import java.net.Authenticator;
import java.util.List;

import javax.management.Notification;

import org.aspectj.bridge.Message;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;

import com.smartmanager.entities.contact;
import com.smartmanager.entities.user;
import com.smartmanager.forms.ContactForms;
import com.smartmanager.helpers.Helper;
import com.smartmanager.helpers.notification;
import com.smartmanager.helpers.notificationType;
import com.smartmanager.repositories.ContactRepo;
import com.smartmanager.services.ContactServices;
import com.smartmanager.services.imageServices;
import com.smartmanager.services.userServices;

import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;

import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
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

    @Autowired
    private imageServices imageServices;

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
    public String saveContact(@Valid @ModelAttribute ContactForms contactForms, BindingResult result, Authentication authentication, HttpSession session) {
        
        if( result.hasErrors()) {
            //  if there are validation errors, return to the add contact page
            System.out.println("Validation errors occurred");
            return "user/add_contact";
        }
        
        
        //  this method will handle the request to save the contact
        System.out.println("inside processing add contact form");
        System.out.println(contactForms);

        String userName= Helper.getEmailOfUser(authentication);
        user user = userServices.getUserByEmail(userName);
 
         // uploading image to cloud and using the url in the contact entity
         String imageURL= imageServices.uploadImage(contactForms.getContactImage());


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
        newContact.setProfilePic(imageURL);
        
       contactServices.saveContact(newContact);
    
       notification message=notification.builder().msg("Contact Added Successfully").type(notificationType.green).build();
        session.setAttribute("message", message); //  can't pass message directly,  need to build it

        return "redirect:/user/contact/add";
    }

    // this method will handle the request to show the contact list page
    @RequestMapping("")
    public String contactList(Model model, Authentication authentication) {


        String userName= Helper.getEmailOfUser(authentication);
        user user = userServices.getUserByEmail(userName);
    List<contact> contacts=    contactServices.getContactsByUser(user);

        model.addAttribute("contacts", contacts);
        return "user/contact_list";
    }
    
    

}

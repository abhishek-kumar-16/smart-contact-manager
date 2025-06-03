package com.smartmanager.controller;

import java.net.Authenticator;
import java.util.List;

import javax.management.Notification;

import org.aspectj.bridge.Message;
import org.springframework.data.domain.Page;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import com.smartmanager.entities.contact;
import com.smartmanager.entities.user;
import com.smartmanager.forms.ContactForms;
import com.smartmanager.forms.ContactSearchForm;
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
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

@Controller
@RequestMapping("/user/contact")
public class ContactController {

    private final ContactRepo contactRepo;

    // add contact page handler
    // for production use
    // private ContactServices contactServices = new ContactServices();

    // for testing use
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
        // this method will handle the request to show the add contact page
        ContactForms contactForms = new ContactForms();
        // sending empty contact form to the view
        model.addAttribute("contactForms", contactForms);

        System.out.println("Add contact page handler");
        return "user/add_contact";
    }

    @RequestMapping(value = "/add", method = RequestMethod.POST)
    public String saveContact(@Valid @ModelAttribute ContactForms contactForms, BindingResult result,
            Authentication authentication, HttpSession session) {

        // above there is model attribute binding, so we can use @ModelAttribute to get
        // the contactForms object, this is for getting and sending the form data
        // this method will handle the request to save the contact

        if (result.hasErrors()) {
            // if there are validation errors, return to the add contact page
            System.out.println("Validation errors occurred");
            return "user/add_contact";
        }

        // this method will handle the request to save the contact
        System.out.println("inside processing add contact form");
        System.out.println(contactForms);

        String userName = Helper.getEmailOfUser(authentication);
        user user = userServices.getUserByEmail(userName);

        // uploading image to cloud and using the url in the contact entity
        MultipartFile imageFile = contactForms.getContactImage();
        String imageURL = (imageFile != null && !imageFile.isEmpty())
                ? imageServices.uploadImage(imageFile)
                : "";

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

        notification message = notification.builder().msg("Contact Added Successfully").type(notificationType.green)
                .build();
        session.setAttribute("message", message); // can't pass message directly, need to build it

        return "redirect:/user/contact/add";
    }

    // this method will handle the request to show the contact list page
    @RequestMapping("")
    public String contactList(
            @RequestParam(value = "page", defaultValue = "0") int page,
            @RequestParam(value = "size", defaultValue = "5") int size,
            @RequestParam(value = "direction", defaultValue = "asc") String direction,
            @RequestParam(value = "sortBy", defaultValue = "name") String sortBy,
            Model model, Authentication authentication) {

        String userName = Helper.getEmailOfUser(authentication);
        user user = userServices.getUserByEmail(userName);
        Page<contact> contacts = contactServices.getContactsByUser(user, page, size, sortBy, direction);

        model.addAttribute("contacts", contacts);

        // need to send the form to that page first where i will be getting datas from
        // client in the form
        model.addAttribute("searchForm", new ContactSearchForm());
        return "user/contact_list";
    }

    // search handler
    @RequestMapping("/search")
    public String searchContacts(
            @ModelAttribute ContactSearchForm searchForm,
            @RequestParam(value = "page", defaultValue = "0") int page,
            @RequestParam(value = "size", defaultValue = "5") int size,
            @RequestParam(value = "direction", defaultValue = "asc") String direction,
            @RequestParam(value = "sortBy", defaultValue = "name") String sortBy,
            Model model,
            Authentication authentication) {
        // here already recieved data from search form from the client
        // this method will handle the request to search contacts
        // for now, we will just return the contact list page

        user user = userServices.getUserByEmail(Helper.getEmailOfUser(authentication));

        // depending on the field, we will call the appropriate method in the
        // contactServices

        if (searchForm.getField().equalsIgnoreCase("name")) {
            // search by name
            Page<contact> contacts = contactServices.searchContactByName(user, searchForm.getKeyword(), page, size,
                    sortBy, direction);
            model.addAttribute("contacts", contacts);
            model.addAttribute("searchForm", searchForm);
        } else if (searchForm.getField().equalsIgnoreCase("email")) {
            // search by email
            Page<contact> contacts = contactServices.searchContactByEmail(user, searchForm.getKeyword(), page, size,
                    sortBy, direction);
            model.addAttribute("contacts", contacts);
            model.addAttribute("searchForm", searchForm);
        } else if (searchForm.getField().equalsIgnoreCase("phone")) {
            // search by phone
            Page<contact> contacts = contactServices.searchContactByPhone(user, searchForm.getKeyword(), page, size,
                    sortBy, direction);
            model.addAttribute("contacts", contacts);
            model.addAttribute("searchForm", searchForm);
        }

        return "user/search_contact";
    }


    @RequestMapping("/delete/{contactId}")
    public String deleteContact(
        @PathVariable("contactId") String contactId)
        {
            contactServices.deleteContact(contactId);
         
        return "redirect:/user/contact";
    }


    // update contact view handler
    @RequestMapping("/view/{contactId}")
    public String updateViewContact(
            @PathVariable("contactId") String contactId,
            Model model, HttpSession session) {

        // this method will handle the request to show the update contact page
        contact contact = contactServices.geContactById(contactId);
        if (contact == null) {
            notification message = notification.builder().msg("Contact Not Found").type(notificationType.red)
                    .build();
            session.setAttribute("message", message);
            return "redirect:/user/contact";
        }

        ContactForms contactForms = new ContactForms();
        contactForms.setName(contact.getName());
        contactForms.setEmail(contact.getEmail());
        contactForms.setPhone(contact.getPhone());
        contactForms.setAddress(contact.getAddress());
        contactForms.setDescription(contact.getDescription());
        contactForms.setFavorite(contact.isFavorite());
        contactForms.setSocialMedia(contact.getSocialMedia());
       contactForms.setProfilePicURL(contact.getProfilePic());

        model.addAttribute("contactForms", contactForms);
        model.addAttribute("contactId", contactId);

        return "user/update_contact_view";
    }

    @RequestMapping(value="/update/{contactId}", method=RequestMethod.POST)
    public String updateContact(
        @PathVariable("contactId") String contactId,
        @Valid @ModelAttribute ContactForms contactForms, 
        BindingResult result, 
        Authentication authentication, 
        HttpSession session) {

        // this method will handle the request to update the contact
        if (result.hasErrors()) {
            // if there are validation errors, return to the update contact page
            System.out.println("Validation errors occurred");
            return "user/update_contact_view";
        }

        // get the existing contact
        contact existingContact = contactServices.geContactById(contactId);
        if (existingContact == null) {
            notification message = notification.builder().msg("Contact Not Found").type(notificationType.red)
                    .build();
            session.setAttribute("message", message);
            return "redirect:/user/contact";
        }

        // uploading image to cloud and using the url in the contact entity
        MultipartFile imageFile = contactForms.getContactImage();
        String imageURL = (imageFile != null && !imageFile.isEmpty())
                ? imageServices.uploadImage(imageFile)
                : existingContact.getProfilePic();

        // update the existing contact with new data
        existingContact.setName(contactForms.getName());
        existingContact.setEmail(contactForms.getEmail());
        existingContact.setPhone(contactForms.getPhone());
        existingContact.setAddress(contactForms.getAddress());
        existingContact.setDescription(contactForms.getDescription());
        existingContact.setFavorite(contactForms.isFavorite());
        existingContact.setSocialMedia(contactForms.getSocialMedia());
        existingContact.setProfilePic(imageURL);

        contactServices.updateContact(existingContact);

        notification message = notification.builder().msg("Contact Updated Successfully").type(notificationType.green)
                .build();
        session.setAttribute("message", message); // can't pass message directly, need to build it

        return "redirect:/user/contact/view/"+ contactId; 
    }

} 

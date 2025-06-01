package com.smartmanager.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.smartmanager.entities.contact;
import com.smartmanager.services.ContactServices;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;


@RestController 
@RequestMapping("/api")
public class ApiController {
     
    @Autowired
     private ContactServices contactServices;

    @GetMapping("contact/{contactId}")
    public contact getContact(@PathVariable String contactId) {
        // This method will return the contact with the given contact id
        return contactServices.geContactById(contactId);
    }
   


}

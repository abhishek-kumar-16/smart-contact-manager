package com.smartmanager.services.impl;

import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.stereotype.Service;

import com.smartmanager.entities.contact;
import com.smartmanager.helpers.ResourceNotFoundException;
import com.smartmanager.repositories.ContactRepo;
import com.smartmanager.services.ContactServices;


@Service
public class ContactServicesImpl  implements ContactServices{

    private final AuthenticationProvider authenticationProvider;

    @Autowired
    ContactRepo contactRepo;

    ContactServicesImpl(AuthenticationProvider authenticationProvider) {
        this.authenticationProvider = authenticationProvider;
    }

    @Override
    public contact saveContact(contact contact) {
        // TODO Auto-generated method stub
        String contactId= UUID.randomUUID().toString();
        contact.setContactId(contactId);
        return contactRepo.save(contact);
    }

    @Override
    public contact updateContact(contact contact) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'updateContact'");
    }

    @Override
    public List<contact> getAll() {
      return contactRepo.findAll();
        
    }

    @Override
    public contact geContactById(String id) {
       return contactRepo.findById(id).orElseThrow(() -> new ResourceNotFoundException("Contact not found with id: " + id));
        // This will return the contact with the given id or null if not found
    }

    @Override
    public void deleteContact(String id) {
        contact existingContact = geContactById(id);
        if (existingContact != null) {
            contactRepo.delete(existingContact);
        } else {
            throw new ResourceNotFoundException("Contact not found with id: " + id);
        }
        // This will delete the contact with the given id if it exists
    }

    @Override
    public List<contact> search(String name, String email, String phone) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'search'");
    }

    @Override
    public List<contact> getContactsByUserId(String id) {
       return contactRepo.findByUserID(id);
        // This will return the contacts with the given user id
    }



}

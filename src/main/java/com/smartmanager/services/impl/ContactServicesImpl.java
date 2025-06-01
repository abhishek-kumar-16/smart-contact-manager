package com.smartmanager.services.impl;

import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.stereotype.Service;

import com.smartmanager.entities.contact;
import com.smartmanager.entities.user;
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
    public Page<contact> getContactsByUser(user user,int page, int size, String sortBy, String sortDirection) {
        // TODO Auto-generated method stub
        // This will return the contacts for the given user with pagination
        // The pageable parameter allows for pagination of results
        var pageable =PageRequest.of(page, size)
                .withSort(sortDirection.equalsIgnoreCase("asc") ? Sort.by(sortBy).ascending() : Sort.by(sortBy).descending());
       return contactRepo.findByuser(user, pageable);
        // This will return the contacts with the given user id
    }

    @Override
    public Page<contact> searchContactByName(user user, String name, int page, int size, String sortBy, String sortDirection) {
       var pageable = PageRequest.of(page, size)
                .withSort(sortDirection.equalsIgnoreCase("asc") ? Sort.by(sortBy).ascending() : Sort.by(sortBy).descending());
        // This will return the contacts with the given name
        return  contactRepo.findByUserAndNameContainingIgnoreCase(user,name, pageable);
        // The getContent() method returns the list of contacts from the page
    }

    @Override
    public Page<contact> searchContactByEmail(user user, String email, int page, int size, String sortBy, String sortDirection) {
        var pageable = PageRequest.of(page, size)
                .withSort(sortDirection.equalsIgnoreCase("asc") ? Sort.by(sortBy).ascending() : Sort.by(sortBy).descending());
        // This will return the contacts with the given email
        return contactRepo.findByuserAndEmailContainingIgnoreCase(user,email, pageable);
        // The getContent() method returns the list of contacts from the page
    }

    @Override
    public Page<contact> searchContactByPhone(user user, String phone, int page, int size, String sortBy, String sortDirection) {
        var pageable = PageRequest.of(page, size)
                .withSort(sortDirection.equalsIgnoreCase("asc") ? Sort.by(sortBy).ascending() : Sort.by(sortBy).descending());
        // This will return the contacts with the given phone
        return contactRepo.findByuserAndPhoneContainingIgnoreCase(user, phone, pageable);
        // This will return the contacts with the given phone as a Page<contact>
    }

   

    // @Override
    // public List<contact> getContactsByUserId(String userId) {
    //     // TODO Auto-generated method stub
    //    return contactRepo.findByUserId(userId);
    // }

   



}

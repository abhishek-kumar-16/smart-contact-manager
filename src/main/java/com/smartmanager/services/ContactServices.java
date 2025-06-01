package com.smartmanager.services;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.access.method.P;

import com.smartmanager.entities.contact;
import com.smartmanager.entities.user;

public interface ContactServices {

    //  save contact
contact saveContact(contact contact);

// update contact
contact updateContact(contact contact);

// get contacts
List<contact> getAll();

// get contact by id
contact geContactById(String id);

// delete contact

void deleteContact(String id);

// search contact
Page<contact> searchContactByName(user user, String name, int page, int size, String sortBy, String sortDirection);
Page<contact> searchContactByEmail(user user, String email,int page, int size, String sortBy, String sortDirection);
Page<contact> searchContactByPhone(user user, String phone,int page, int size, String sortBy, String sortDirection);

// get contacts  by userid
// List<contact> getContactsByUserId(String userId);

// get contacts by user

Page<contact> getContactsByUser(user user,int page, int size, String sortBy, String sortDirection);

}

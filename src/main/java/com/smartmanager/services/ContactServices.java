package com.smartmanager.services;

import java.util.List;

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

List<contact> search(String name, String email, String phone);

// get contacts  by userid
// List<contact> getContactsByUserId(String userId);

// get contacts by user

List<contact> getContactsByUser(user user);

}

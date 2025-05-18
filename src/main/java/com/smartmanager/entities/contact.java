package com.smartmanager.entities;

import org.springframework.boot.autoconfigure.security.SecurityProperties.User;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;

@Entity
public class contact {
    @Id
    private String contactId;
    private String name;
    private String email;
    private String phone;
    private String address;
    private String profilePic;
    private String description;
    private boolean isFavorite=false;
    private String socialMedia;
    @ManyToOne
    private user user;

}

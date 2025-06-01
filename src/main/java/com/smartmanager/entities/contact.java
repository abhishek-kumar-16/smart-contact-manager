package com.smartmanager.entities;



import com.fasterxml.jackson.annotation.JsonIgnore;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
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
    @JsonIgnore
    private user user;

}

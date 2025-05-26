package com.smartmanager.forms;

import org.springframework.web.multipart.MultipartFile;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class ContactForms {

        
    private String name;
    private String email;
    private String phone;
    private String address;
   
    private String description;
    private boolean isFavorite=false;
    private String socialMedia;
    private MultipartFile profilePic;
}

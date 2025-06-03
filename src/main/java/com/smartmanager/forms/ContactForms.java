package com.smartmanager.forms;

import org.springframework.web.multipart.MultipartFile;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
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

    @NotBlank(message = "Name is required")    
    private String name;

    @Email(message = "Invalid email format")
    private String email;

    @NotBlank(message = "Phone number is required")
    @jakarta.validation.constraints.Pattern(regexp = "^(\\+\\d{1,3}[- ]?)?\\d{10}$", message = "Invalid phone number format")
    private String phone;

    @NotBlank(message = "Address is required")
    private String address;
   
    
    private String description;
    private boolean isFavorite=false;
    private String socialMedia;
    
    // Maximum allowed file size is 10MB (validate in service/controller if needed)
    private MultipartFile ContactImage;
    private String ProfilePicURL;
}

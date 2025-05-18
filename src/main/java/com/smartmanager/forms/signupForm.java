package com.smartmanager.forms;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class signupForm {
//   name of all entitiy should remain same to name in fronend point

@NotBlank(message = "Name is required")
private String name;

@Email(message = "Invalid email format")
@NotBlank(message = "Email is required")
private String email;

@NotBlank(message = "Password is required")
private String password;    

@NotBlank(message = "Phone is required")
private String phone;

@NotBlank(message = "About is required")
private String about;

}

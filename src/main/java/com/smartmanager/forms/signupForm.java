package com.smartmanager.forms;

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
private String name;
private String email;
private String password;    
private String phone;
private String about;

}

package com.smartmanager.forms;

import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter

public class ContactUsForm {
    @NotBlank
 private String name;
 @NotBlank
    private String email;
    @NotBlank
    private String message;
}

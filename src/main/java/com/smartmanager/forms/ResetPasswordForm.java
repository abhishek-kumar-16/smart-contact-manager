package com.smartmanager.forms;

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
public class ResetPasswordForm {
    @NotBlank
    private String newPassword;
    @NotBlank
    private String confirmNewPassword;
    @NotBlank
    private String token;
   


}

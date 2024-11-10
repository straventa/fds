package com.yokke.usermanagement.auth.request;

import com.yokke.usermanagement.auth.RegistrationRoleType;
import com.yokke.usermanagement.auth.validation.ValidPassword;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import lombok.*;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class RegisterRequest {

    @NotEmpty
    String username;

    @Email
    @NotEmpty
    String email;

    @ValidPassword
    String password;

    RegistrationRoleType registrationRole;

}

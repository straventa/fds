package com.yokke.usermanagement.auth.request;

import com.yokke.usermanagement.auth.validation.NoWhitespace;
import com.yokke.usermanagement.auth.validation.PasswordMatches;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.*;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@PasswordMatches

public class ChangePasswordRequest {
    @NoWhitespace
    @Email
    @NotNull
    @NotBlank
    private String oldPassword;
    @NoWhitespace
    @Email
    @NotNull
    @NotBlank
    private String password;
    @NoWhitespace
    @Email
    @NotNull
    @NotBlank
    private String confirmPassword;
}

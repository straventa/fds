package com.yokke.usermanagement.auth.request;

import com.yokke.usermanagement.auth.validation.NoWhitespace;
import com.yokke.usermanagement.auth.validation.PasswordMatches;
import com.yokke.usermanagement.auth.validation.ValidPassword;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.*;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@PasswordMatches

public class ResetPasswordRequest {
    @ValidPassword
    @NoWhitespace
    @NotBlank
    @NotNull
    private String password;
    @ValidPassword
    @NoWhitespace
    @NotBlank
    @NotNull
    private String confirmPassword;
    private String token;

}

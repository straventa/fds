package com.yokke.usermanagement.auth.request;


import com.yokke.usermanagement.auth.validation.NoWhitespace;
import jakarta.validation.constraints.Email;
import lombok.*;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ForgotPasswordRequest {
    @NoWhitespace
    @Email
    private String email;
}

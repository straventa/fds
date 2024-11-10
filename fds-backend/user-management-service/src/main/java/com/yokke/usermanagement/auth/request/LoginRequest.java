package com.yokke.usermanagement.auth.request;

import com.yokke.usermanagement.auth.validation.Lowercase;
import com.yokke.usermanagement.auth.validation.NoWhitespace;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.*;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class LoginRequest {

    String password;
    private String email;
    @Size(max = 50)
    @NotNull
    @NotBlank
    @Lowercase
    @NoWhitespace
    private String username;
}

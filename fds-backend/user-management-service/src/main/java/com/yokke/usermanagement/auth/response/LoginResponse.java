package com.yokke.usermanagement.auth.response;

import com.yokke.usermanagement.user_account.UserAccountDTO;
import lombok.*;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class LoginResponse {
    private String accessToken;
    private String refreshToken;
    private UserAccountDTO userAccount;
}

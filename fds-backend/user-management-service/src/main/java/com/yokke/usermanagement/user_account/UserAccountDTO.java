package com.yokke.usermanagement.user_account;

import com.yokke.base.response.BaseResponse;
import com.yokke.usermanagement.auth.validation.Lowercase;
import com.yokke.usermanagement.auth.validation.NoWhitespace;
import com.yokke.usermanagement.role.RoleDTO;
import com.yokke.usermanagement.user_account_rule.UserAccountRuleDto;
import jakarta.validation.constraints.*;
import lombok.*;

import java.time.LocalDateTime;
import java.util.List;


@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class UserAccountDTO extends BaseResponse {

    private String userAccountId;

    @Size(max = 50)
    @NotNull
    @NotBlank
    @NoWhitespace
    @Lowercase
    private String username;

    @Size(max = 100)
    @Email
    @NotNull
    @NotBlank
    @NoWhitespace
    @Lowercase
    private String email;

    @Size(max = 100)
    private String password;

    private Integer organization;

    private Integer team;

    private Integer userData;
    private LocalDateTime passwordDateChanged;
    private Boolean isPasswordSetup;
    @NotNull(message = "Role list cannot be null.")
    @NotEmpty(message = "Role list cannot be empty.")
    private List<RoleDTO> role;

    private List<UserAccountRuleDto> userAccountRule;
    private Boolean isLocked;

    void setUserAccountRule(List<UserAccountRuleDto> userAccountRule) {
        if (userAccountRule == null) {
            this.userAccountRule = null;
            return;
        }
        this.userAccountRule = userAccountRule.stream().sorted((o1, o2) -> o1.getRule().getRiskLevelNumber().compareTo(o2.getRule().getRiskLevelNumber())).toList();
    }


}

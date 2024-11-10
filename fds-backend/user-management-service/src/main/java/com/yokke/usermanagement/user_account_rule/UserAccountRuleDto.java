package com.yokke.usermanagement.user_account_rule;

import com.yokke.base.dto.BaseDto;
import com.yokke.usermanagement.rule.RuleDto;
import com.yokke.usermanagement.user_account.UserAccountDTO;
import lombok.*;

import java.io.Serializable;
import java.time.OffsetDateTime;

/**
 * DTO for {@link UserAccountRule}
 */
@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class UserAccountRuleDto extends BaseDto implements Serializable {
    String userRuleId;
    Boolean isActive;
    Integer priority;
    UserAccountDTO userAccount;
    RuleDto rule;



}
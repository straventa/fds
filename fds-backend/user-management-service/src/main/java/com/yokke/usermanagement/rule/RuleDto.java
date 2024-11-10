package com.yokke.usermanagement.rule;

import com.yokke.base.dto.BaseDto;
import com.yokke.usermanagement.user_account_rule.UserAccountRuleDto;
import lombok.*;

import java.io.Serializable;
import java.time.OffsetDateTime;
import java.util.List;
import java.util.stream.Collectors;

/**
 * DTO for {@link Rule}
 */
@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class RuleDto extends BaseDto implements Serializable {
    String ruleId;
    String ruleName;
    String ruleDescription;
    String sourceData;
    String riskLevel;
     Integer riskLevelNumber;

    List<UserAccountRuleDto> userAccountRule;

    void setUserAccountRule(List<UserAccountRuleDto> userAccountRule) {
        if (userAccountRule == null) {
            this.userAccountRule = null;
            return;
        }
        this.userAccountRule = userAccountRule.stream().sorted(
                (o1, o2) -> {
                    if (o1.getRule() == null || o2.getRule() == null) {
                        return 0;
                    }else{
                        return o1.getRule().getRiskLevelNumber().compareTo(o2.getRule().getRiskLevelNumber());
                    }
                }
        ).toList();
    }
}
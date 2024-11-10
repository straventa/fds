package com.yokke.usermanagement.user_account_rule;

import com.yokke.base.model.BaseModel;
import com.yokke.usermanagement.rule.Rule;
import com.yokke.usermanagement.user_account.UserAccount;
import jakarta.persistence.*;
import lombok.*;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

@Table(name="FDS_USER_ACCOUNT_RULE")
@EntityListeners(AuditingEntityListener.class)
@Entity
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UserAccountRule extends BaseModel {
    @Id
    @Column(name = "USER_RULE_ID", nullable = false, updatable = false)
    @GeneratedValue(strategy = GenerationType.UUID)
    private String userRuleId;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "USER_ACCOUNT_ID")
    private UserAccount userAccount;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "RULE_ID")
    @OrderBy(value = "riskLevelNumber")
    private Rule rule;

    @Column(name = "IS_ACTIVE")
    private Boolean isActive;

    @Column(name="PRIORITY")
    private Integer priority;

}

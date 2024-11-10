package com.yokke.usermanagement.rule;

import com.yokke.base.model.BaseModel;
import com.yokke.usermanagement.transactions.fds_transactions.FdsTransactions;
import com.yokke.usermanagement.user_account_rule.UserAccountRule;
import jakarta.persistence.*;
import lombok.*;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.util.List;

@Table(name = "FDS_RULE")
@EntityListeners(AuditingEntityListener.class)
@Entity
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Rule extends BaseModel {

    @Id
    @Column(name = "RULE_ID", nullable = false, updatable = false)
    @GeneratedValue(strategy = GenerationType.UUID)
    private String ruleId;

    @Column(name = "RULE_NAME")
    private String ruleName;

    @Column(name = "RULE_DESCRIPTION", length = 500)
    private String ruleDescription;

    @Column(name = "SOURCE_DATA")
    private String sourceData;

    @Column(name = "RISK_LEVEL")
    private String riskLevel;

    @Column(name = "RISK_LEVEL_NUMBER")
    private Integer riskLevelNumber;
    @OneToMany(mappedBy = "rule", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private List<UserAccountRule> userAccountRule;
    @ManyToMany(mappedBy = "rule")
    private List<FdsTransactions> fdsTransactions;

    @PrePersist
    @PreUpdate
    private void setRiskLevelOrder() {
        this.riskLevelNumber = switch (this.riskLevel) {
            case "High Risk" -> 3;
            case "Medium Risk" -> 2;
            case "Low Risk" -> 1;
            default -> 3;
        };
    }
}

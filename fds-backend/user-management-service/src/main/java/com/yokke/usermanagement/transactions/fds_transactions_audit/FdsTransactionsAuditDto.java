package com.yokke.usermanagement.transactions.fds_transactions_audit;

import com.yokke.usermanagement.user_account.UserAccountDTO;
import lombok.*;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * DTO for {@link com.yokke.usermanagement.transactions.fds_transactions_audit.FdsTransactionsAudit}
 */
@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class FdsTransactionsAuditDto implements Serializable {
    String fdsTransactionsAuditId;
    String activity;
    String activityNotes;
    LocalDateTime createdDateTime;
    UserAccountDTO userAccount;
}
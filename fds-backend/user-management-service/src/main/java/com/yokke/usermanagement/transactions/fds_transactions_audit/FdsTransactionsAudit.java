package com.yokke.usermanagement.transactions.fds_transactions_audit;

import com.yokke.usermanagement.transactions.fds_transactions.FdsTransactions;
import com.yokke.usermanagement.user_account.UserAccount;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Entity
@Table(name = "FDS_TRANSACTIONS_AUDIT")
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class FdsTransactionsAudit {
    @Id
    @Column(name="FDS_TRANSACTIONS_AUDIT_ID",nullable = false)
    @GeneratedValue(strategy = GenerationType.UUID)
    private String fdsTransactionsAuditId;

    @Column(name="ACTIVITY", length = 200)
    private String activity;

    @Column(name="ACTIVITY_NOTES", length = 200)
    private String activityNotes;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="USER_ACCOUNT_ID")
    private UserAccount userAccount;

    @Column(name="CREATED_DATE_TIME")
    private LocalDateTime createdDateTime;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "FDS_TRANSACTIONS_ID")
    private FdsTransactions fdsTransactions;
    

}

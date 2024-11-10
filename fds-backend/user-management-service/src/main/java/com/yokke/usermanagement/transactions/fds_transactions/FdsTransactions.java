package com.yokke.usermanagement.transactions.fds_transactions;

import com.yokke.usermanagement.rule.Rule;
import com.yokke.usermanagement.transactions.fds_transactions_audit.FdsTransactionsAudit;
import com.yokke.usermanagement.user_account.UserAccount;
import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

@Entity
@Table(name = "FDS_TRANSACTIONS", indexes = {
        @Index(name = "idx_fds_trans_action_type", columnList = "ACTION_TYPE"),
        @Index(name = "idx_fds_trans_id", columnList = "FDS_TRANSACTION_ID")
})
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class
FdsTransactions {

    @Column(name = "AUTH_SEQ_NO", length = 8, nullable = false)
    private String authSeqNo;

    @Column(name = "CARD_NO", length = 19, nullable = false)
    private String cardNo;

    @Column(name = "MEMBER_BANK_ACQ", length = 1000)
    private String memberBankAcq;

    @Column(name = "MER_NM", length = 100)
    private String merchantName;

    @Column(name = "MID", length = 11)
    private String mid;

    @Column(name = "TID", length = 8)
    private String tid;

    @Column(name = "RRN", length = 12)
    private String rrn;

    @Column(name = "ISSUER", length = 1000)
    private String issuer;

    @Column(name = "AUTH_DATE", length = 8, nullable = false)
    private String authDate;

    @Column(name = "AUTH_TIME", length = 6)
    private String authTime;

    @Column(name = "AUTH_AMT", precision = 18, scale = 2)
    private BigDecimal authAmount;

    @Column(name = "TRACE_NO", length = 30)
    private String traceNo;

    @Column(name = "MSG_TP_ID", length = 4)
    private String messageTypeId;

    @Column(name = "AUTH_SALE_TYPE", length = 1000)
    private String authSaleType;

    @Column(name = "AUTH_INTN_RSPN_CD", length = 4)
    private String authIntnRspnCd;

    @Column(name = "RSON_CTNTS", length = 1000)
    private String reasonContents;

    @Column(name = "INS_MCNT", precision = 5)
    private Integer installmentCount;

    @Column(name = "SWITCH_BRAND", length = 1000)
    private String switchBrand;

    @Column(name = "POS_ENTRY_MODE_DTL", length = 15)
    private String posEntryModeDetail;

    @Column(name = "CARD_TP_CD", length = 7)
    private String cardTypeCode;

    @Column(name = "ONUS_CD", length = 1000)
    private String onusCode;

    @Column(name = "ECI_VAL", length = 1)
    private String eciValue;

    @Column(name = "APPR_CODE", length = 6)
    private String approvalCode;

    @Column(name = "PG_NAME", length = 1000)
    private String pgName;

    @Column(name = "PG_TYPE", length = 1000)
    private String pgType;

    @Column(name = "ISSUR_MB_NO", length = 3)
    private String issuerMemberNo;

    @Column(name = "BUSINESS_TYPE", length = 1000)
    private String businessType;

    @Column(name = "CHANNEL", length = 30)
    private String channel;

    @Column(name = "ISSUER_COUNTRY", length = 128)
    private String issuerCountry;

    @Column(name = "PARAMETER_VALUES", length = 4000)
    private String parameterValues;

    @Column(name = "ACTION_TYPE", length = 200)
    private String actionType;

    @Column(name = "FRAUD_TYPE", length = 200)
    private String fraudType;

    @Column(name = "FRAUD_POD_TYPE", length = 200)
    private String fraudPodType;

    @Column(name = "FRAUD_REMARK", length = 200)
    private String fraudRemark;

    @Column(name = "FRAUD_NOTE", length = 4000)
    private String fraudNote;


    @Column(name = "REMIND_NOTE", length = 200)
    private String remindNote;

    @Column(name = "REMIND_DATE")
    private LocalDateTime remindDate;
    @ManyToOne(fetch = FetchType.EAGER)

    @JoinColumn(name = "REMIND_USER_ACCOUNT_ID")
    private UserAccount remindUserAccount;

    @Column(name = "REMIND_CREATED_DATE_TIME")
    private LocalDateTime remindCreatedDateTime;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "REVIEWED_USER_ACCOUNT_ID")
    private UserAccount reviewedUserAccount;

    @Column(name = "REVIEWED_CREATED_DATE_TIME")
    private LocalDateTime reviewedDateTime;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "ASSIGNED_USER_ACCOUNT_ID")
    private UserAccount assignedUserAccount;

    @Column(name = "ASSIGNED_DATE_TIME")
    private LocalDateTime assignedDateTime;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "CONFIRMED_USER_ACCOUNT_ID")
    private UserAccount confirmedUserAccount;

    @Column(name = "CONFIRMED_DATE_TIME")
    private LocalDateTime confirmedDateTime;

    @Id
    @Column(name = "FDS_TRANSACTION_ID", unique = true)
    @GeneratedValue(strategy = GenerationType.UUID)
    private String fdsTransactionId;

    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(
            name = "FDS_TRANSACTIONS_RULE",
            joinColumns = @JoinColumn(name = "FDS_TRANSACTION_ID"),
            inverseJoinColumns = @JoinColumn(name = "RULE_ID"), indexes = {
            @Index(name = "idx_trans_rule_fk", columnList = "FDS_TRANSACTION_ID"),
            @Index(name = "idx_rule_trans_fk", columnList = "RULE_ID")
    }
    )
    private List<Rule> rule;


    @OneToMany(mappedBy = "fdsTransactions", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private List<FdsTransactionsAudit> fdsTransactionsAudit;

}


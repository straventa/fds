package com.yokke.usermanagement.transactions.fds_transactions;

import com.yokke.usermanagement.rule.RuleDto;
import com.yokke.usermanagement.transactions.fds_transactions_audit.FdsTransactionsAuditDto;
import com.yokke.usermanagement.user_account.UserAccountDTO;
import lombok.*;

import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

/**
 * DTO for {@link FdsTransactions}
 */
@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class FdsTransactionsDto implements Serializable {
    String authSeqNo;
    String cardNo;
    String memberBankAcq;
    String merchantName;
    String mid;
    String tid;
    String rrn;
    String issuer;
    String authDate;
    String authTime;
    BigDecimal authAmount;
    String traceNo;
    String messageTypeId;
    String authSaleType;
    String authIntnRspnCd;
    String reasonContents;
    Integer installmentCount;
    String switchBrand;
    String posEntryModeDetail;
    String cardTypeCode;
    String onusCode;
    String eciValue;
    String approvalCode;
    String pgName;
    String pgType;
    String issuerMemberNo;
    String businessType;
    String channel;
    String issuerCountry;
    String parameterValues;
    String actionType;
    String fraudType;
    String fraudPodType;
    String fraudNote;
    String fraudRemark;
    String remindNote;
    LocalDateTime remindDate;
    LocalDateTime assignedDateTime;
    LocalDateTime confirmedDateTime;
    String fdsTransactionId;
    List<RuleDto> rule;
    List<FdsTransactionsAuditDto> fdsTransactionsAudit;
    UserAccountDTO confirmedUserAccount;
    UserAccountDTO assignedUserAccount;


}
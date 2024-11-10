package com.yokke.usermanagement.transactions.fds_transactions.report.dto;

import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class FdsTransactionsRuleNameReportsDto {
    String ruleName;
    String riskLevel;
    String genuine;
    String investigation;
    String watchlist;
    String fraud;
    String remind;
    String pending;
    String total;
    String falsePositive;

}

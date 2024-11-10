package com.yokke.usermanagement.transactions.fds_transactions.report.dto;

import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class FdsTransactionsMerchantReportDto {
    String mid;
    String merchantName;
    String memberBank;
    String channel;
    String genuine;
    String investigation;
    String watchlist;
    String fraud;
    String remind;
    String pending;
    String total;
    String falsePositive;
}

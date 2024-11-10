package com.yokke.usermanagement.transactions.fds_transactions.report.dto;

import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class FdsTransationsAnalystReportDto {
    String riskLevel;
    String analyst;
    String genuine;
    String investigation;
    String watchlist;
    String fraud;
    String remind;
    String total;
}



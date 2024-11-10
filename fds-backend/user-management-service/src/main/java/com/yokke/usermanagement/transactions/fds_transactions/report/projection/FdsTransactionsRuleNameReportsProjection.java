package com.yokke.usermanagement.transactions.fds_transactions.report.projection;

public interface FdsTransactionsRuleNameReportsProjection {
    String getRuleName();

    String getRiskLevel();

    String getGenuine();

    String getInvestigation();

    String getWatchlist();

    String getFraud();

    String getRemind();

    String getPending();

    String getTotal();

    String getFalsePositive();
}

package com.yokke.usermanagement.transactions.fds_transactions.report.projection;

public interface FdsTransactionsMerchantReportsProjection {
    String getMID();

    String getMerchantName();

    String getMemberBank();

    String getChannel();

    String getGenuine();

    String getInvestigation();

    String getWatchlist();

    String getFraud();

    String getRemind();

    String getPending();

    String getTotalAlert();

    String getFalsePositive();
}

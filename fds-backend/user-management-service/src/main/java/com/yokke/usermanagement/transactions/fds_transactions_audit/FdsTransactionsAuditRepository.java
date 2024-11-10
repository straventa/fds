package com.yokke.usermanagement.transactions.fds_transactions_audit;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface FdsTransactionsAuditRepository extends JpaRepository<FdsTransactionsAudit, String> {
    @Query("select f from FdsTransactionsAudit f where f.fdsTransactions.fdsTransactionId = :fdsTransactionId")
    List<FdsTransactionsAudit> findByFdsTransactions_FdsTransactionId(@Param("fdsTransactionId") String fdsTransactionId);
}
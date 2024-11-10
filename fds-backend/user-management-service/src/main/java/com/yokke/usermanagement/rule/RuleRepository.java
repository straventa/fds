package com.yokke.usermanagement.rule;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDateTime;
import java.util.Collection;
import java.util.List;
import java.util.Optional;

public interface RuleRepository extends JpaRepository<Rule, String> {
    @Query("""
                select r from Rule r 
                inner join r.fdsTransactions fds 
                where fds.fdsTransactionId = :fdsTransactionId
            """)
    List<Rule> findByFdsTransactions_FdsTransactionId(@Param("fdsTransactionId") String fdsTransactionId);

    @Query("""
            select r from Rule r inner join r.userAccountRule userAccountRule
            where userAccountRule.userAccount.userAccountId = ?1""")
    Page<Rule> findByUserAccountRule_UserAccount_UserAccountId(String userAccountId, Pageable pageable);

    @Query("select r from Rule r inner join r.userAccountRule userAccountRule where userAccountRule.userRuleId = ?1")
    Optional<Rule> findByUserAccountRule_UserRuleId(String userRuleId);

    //    @Query("""
//            select r from Rule r  join r.userAccountRule userAccountRule
//            where (:riskLevel is null or r.riskLevel like concat('%', :riskLevel, '%'))
//            and (:ruleDescription is null or r.ruleDescription like concat('%', :ruleDescription, '%'))
//            and (:ruleName is null or r.ruleName like concat('%', :ruleName, '%'))
//            and (:sourceData is null or r.sourceData like concat('%', :sourceData, '%'))
//            and (:userAccountIds is null or userAccountRule.userAccount.userAccountId in :userAccountIds)
//            """)
//    Page<Rule> findAll(
//            @Param("riskLevel") String riskLevel,
//            @Param("ruleDescription") String ruleDescription,
//            @Param("ruleName") String ruleName,
//            @Param("sourceData") String sourceData,
//            @Param("userAccountIds") Collection<String> userAccountIds,
//            Pageable pageable
//    );
    @Query("""
            select distinct r from Rule r
            left join r.userAccountRule userAccountRule
            where (:riskLevel is null or r.riskLevel like concat('%', :riskLevel, '%'))
            and (:ruleDescription is null or r.ruleDescription like concat('%', :ruleDescription, '%'))
            and (:ruleName is null or r.ruleName like concat('%', :ruleName, '%'))
            and (:sourceData is null or r.sourceData like concat('%', :sourceData, '%'))
            and (:userAccountIds is null or userAccountRule.userAccount.userAccountId in :userAccountIds)
            """)
    Page<Rule> findAll(
            @Param("riskLevel") String riskLevel,
            @Param("ruleDescription") String ruleDescription,
            @Param("ruleName") String ruleName,
            @Param("sourceData") String sourceData,
            @Param("userAccountIds") Collection<String> userAccountIds,
            Pageable pageable
    );

    @Query("select r from Rule r where r.ruleId = :ruleId")
    Optional<Rule> findByRuleId(@Param("ruleId") String ruleId);

    @Query("select r from Rule r where r.ruleId = :ruleId")
    Optional<Rule> findRuleByRuleId(@Param("ruleId") String ruleId);

    @Query(
            value = """
                    
                                        SELECT\s
                        fr.RULE_ID,
                        COUNT(ft.FDS_TRANSACTION_ID) AS total_transactions,
                        COUNT(CASE WHEN ft.ACTION_TYPE IS NULL THEN 1 END) AS null_action_type_count,
                        COUNT(CASE WHEN ft.ACTION_TYPE IS NOT NULL THEN 1 END) AS non_null_action_type_count
                    FROM\s
                        FDS_RULE fr
                    JOIN\s
                        FDS_TRANSACTIONS_RULE ftr ON fr.RULE_ID = ftr.RULE_ID
                    JOIN\s
                        FDS_TRANSACTIONS ft ON ftr.FDS_TRANSACTION_ID = ft.FDS_TRANSACTION_ID
                    WHERE\s
                        fr.RULE_ID = :ruleId
                    AND ft.ASSIGNED_DATE_TIME between :startDate and :endDate
                    GROUP BY\s
                        fr.RULE_ID
                    """, nativeQuery = true
    )
    Object[][] countByRuleId(
            @Param("ruleId") String ruleId,
            @Param("startDate") LocalDateTime startDate,
            @Param("endDate") LocalDateTime endDate
    );

}
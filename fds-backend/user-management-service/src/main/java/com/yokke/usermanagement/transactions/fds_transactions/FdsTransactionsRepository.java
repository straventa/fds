package com.yokke.usermanagement.transactions.fds_transactions;

import com.yokke.usermanagement.transactions.fds_transactions.report.projection.FdsTransactionsMerchantReportsProjection;
import com.yokke.usermanagement.transactions.fds_transactions.report.projection.FdsTransactionsRuleNameReportsProjection;
import com.yokke.usermanagement.user_account.UserAccount;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDateTime;
import java.util.Collection;
import java.util.List;
import java.util.Optional;

public interface FdsTransactionsRepository extends JpaRepository<FdsTransactions, String> {
    @Query("select f from FdsTransactions f where upper(f.mid) like upper(concat('%', ?1, '%'))")
    List<FdsTransactions> findByMidContainsIgnoreCase(String mid);


//    @Query("""
//            select distinct f from FdsTransactions f
//                left join f.rule r
//
//            where f.assignedDateTime
//             between :assignedDateTimeStart and :assignedDateTimeEnd
//             and (:mid is null or f.mid = :mid)
//             and (:tid is null or f.tid = :tid)
//             and (:userAccountId is null or r.ruleId in :userAccountId )
//             and (:actionType is null or f.actionType = :actionType)
//             and (:actionTypeIsNull is null or f.actionType is null)
//                and (:actionTypeIsNotNull is null or f.actionType is not null)
//            """)
//    Page<FdsTransactions> findAll(
//            @Param("assignedDateTimeStart") LocalDateTime assignedDateTimeStart,
//            @Param("assignedDateTimeEnd") LocalDateTime assignedDateTimeEnd,
//            @Param("mid") String mid,
//            @Param("tid") String tid,
//            @Param("userAccountId") List<String> userAccountId,
//            @Param("actionType") String actionType,
//            @Param("actionTypeIsNull") Boolean actionTypeIsNull,
//            @Param("actionTypeIsNotNull") Boolean actionTypeIsNotNull,
//            Pageable pageable
//    );

    @Query("""
            select distinct f from FdsTransactions f
                left join f.rule r
            
            where f.assignedDateTime
             between :assignedDateTimeStart and :assignedDateTimeEnd
             and (:mid is null or upper(f.mid) like upper(concat('%', :mid, '%')))
             and (:tid is null or upper(f.tid) like upper(concat('%', :tid, '%')))
             and (:actionType is null or f.actionType in :actionType)
             and (:ruleId is null or r.ruleId in :ruleId)
            """)
    Page<FdsTransactions> findAllV2(
            @Param("assignedDateTimeStart") LocalDateTime assignedDateTimeStart,
            @Param("assignedDateTimeEnd") LocalDateTime assignedDateTimeEnd,
            @Param("mid") String mid,
            @Param("tid") String tid,
            @Param("actionType") List<String> actionType,
            @Param("ruleId") List<String> ruleId,
            Pageable pageable
    );


//    @Query("""
//            select distinct f from FdsTransactions f
//                left join f.rule r
//
//            where f.assignedDateTime
//             between :assignedDateTimeStart and :assignedDateTimeEnd
//             and (:mid is null or f.mid = :mid)
//             and (:tid is null or f.tid = :tid)
//             and (:userAccountId is null or r.ruleId in :userAccountId )
//             and (:actionType is null or f.actionType = :actionType)
//             and (:actionTypeIsNull is null or f.actionType is null)
//                and (:actionTypeIsNotNull is null or f.actionType is not null)
//                and f.memberBankAcq='BANK MANDIRI'
//                order by  r.riskLevelNumber desc
//            """)
//    Page<FdsTransactions> findAllDesc(
//            @Param("assignedDateTimeStart") LocalDateTime assignedDateTimeStart,
//            @Param("assignedDateTimeEnd") LocalDateTime assignedDateTimeEnd,
//            @Param("mid") String mid,
//            @Param("tid") String tid,
//            @Param("userAccountId") List<String> userAccountId,
//            @Param("actionType") String actionType,
//            @Param("actionTypeIsNull") Boolean actionTypeIsNull,
//            @Param("actionTypeIsNotNull") Boolean actionTypeIsNotNull,
//            Pageable pageable
//    );

    @Query("""
              select distinct f from FdsTransactions f
                  left join f.rule r
            
              where f.assignedDateTime
               between :assignedDateTimeStart and :assignedDateTimeEnd
            and (:mid is null or upper(f.mid) like upper(concat('%', :mid, '%')))
               and (:tid is null or upper(f.tid) like upper(concat('%', :tid, '%')))
               and (:actionType is null or f.actionType in :actionType)
               and (:ruleId is null or r.ruleId in :ruleId)
                      order by  r.riskLevelNumber desc 
            """)
    Page<FdsTransactions> findAllDescV2(
            @Param("assignedDateTimeStart") LocalDateTime assignedDateTimeStart,
            @Param("assignedDateTimeEnd") LocalDateTime assignedDateTimeEnd,
            @Param("mid") String mid,
            @Param("tid") String tid,
            @Param("actionType") List<String> actionType,
            @Param("ruleId") List<String> ruleId,
            Pageable pageable
    );

//    @Query("""
//            select distinct f from FdsTransactions f
//                left join f.rule r
//
//            where f.assignedDateTime
//             between :assignedDateTimeStart and :assignedDateTimeEnd
//             and (:mid is null or f.mid = :mid)
//             and (:tid is null or f.tid = :tid)
//             and (:actionType is null or f.actionType = :actionType)
//             and (:ruleId is null or r.ruleId in :ruleId)
//                order by  r.riskLevelNumber desc
//            """)
//    Page<FdsTransactions> findAllAsc(
//            @Param("assignedDateTimeStart") LocalDateTime assignedDateTimeStart,
//            @Param("assignedDateTimeEnd") LocalDateTime assignedDateTimeEnd,
//            @Param("mid") String mid,
//            @Param("tid") String tid,
//            @Param("userAccountId") List<String> userAccountId,
//            @Param("actionType") String actionType,
//            @Param("actionTypeIsNull") Boolean actionTypeIsNull,
//            @Param("actionTypeIsNotNull") Boolean actionTypeIsNotNull,
//            Pageable pageable
//    );

    @Query("""
              select distinct f from FdsTransactions f
                  left join f.rule r
            
              where f.assignedDateTime
               between :assignedDateTimeStart and :assignedDateTimeEnd
            and (:mid is null or upper(f.mid) like upper(concat('%', :mid, '%')))
               and (:tid is null or upper(f.tid) like upper(concat('%', :tid, '%')))
               and (:actionType is null or f.actionType in :actionType)
               and (:ruleId is null or r.ruleId in :ruleId)
                  order by  r.riskLevelNumber asc
            """)
    Page<FdsTransactions> findAllAscV2(
            @Param("assignedDateTimeStart") LocalDateTime assignedDateTimeStart,
            @Param("assignedDateTimeEnd") LocalDateTime assignedDateTimeEnd,
            @Param("mid") String mid,
            @Param("tid") String tid,
            @Param("actionType") List<String> actionType,
            @Param("ruleId") List<String> ruleId,
            Pageable pageable
    );

    @Query(nativeQuery = true, value = """
            
               SELECT * FROM (
                SELECT DISTINCT ft.*
                FROM FDS_TRANSACTIONS ft
                INNER JOIN FDS_TRANSACTIONS_RULE ftr ON ft.FDS_TRANSACTION_ID = ftr.FDS_TRANSACTION_ID
                INNER JOIN FDS_RULE fr ON ftr.RULE_ID = fr.RULE_ID
                WHERE ft.FDS_TRANSACTION_ID != :fdsTransactionId
                AND ft.ACTION_TYPE IS NULL
                AND fr.RULE_ID IN (:ruleIds)
                ORDER BY ft.AUTH_DATE DESC
            ) WHERE ROWNUM = 1
            """)
    Optional<FdsTransactions> findFirst(
            @Param("fdsTransactionId") String fdsTransactionId,
            @Param("ruleIds") List<String> ruleIds
    );

    //    @Query("""
//            select f from FdsTransactions f
//             where (:mid is null or f.mid = :mid)
//             and (:tid is null or f.tid = :tid)
//             and (:userAccountId is null or f.assignedUserAccount.userAccountId = :userAccountId )
//             and (:actionType is null or f.actionType = :actionType)
//             and (:isMarked is null or f.actionType is not null)
//             """)
//    Page<FdsTransactions> findAll(
//            @Param("mid") String mid,
//            @Param("tid") String tid,
//            @Param("userAccountId") String userAccountId,
//            @Param("actionType") String actionType,
//            @Param("isMarked") Boolean isMakrked,
//            Pageable pageable
//    );
//    @Query(value = """
//            WITH categorized_data AS (
//                                      SELECT
//                                          NVL(UA.USERNAME, 'Unassigned') AS ASSIGNED_TO,
//                                          CASE
//                                              WHEN FT.ACTION_TYPE IS NULL THEN 'Incoming'
//                                              WHEN FT.ACTION_TYPE = 'NORMAL' THEN 'Genuine'
//                                              WHEN FT.ACTION_TYPE = 'WATCH_LIST' THEN 'Watchlist'
//                                              WHEN FT.ACTION_TYPE = 'FRAUD' THEN 'Fraud'
//                                              ELSE 'Investigation'
//                                          END AS category,
//                                          COUNT(*) AS count
//                                      FROM FDS_TRANSACTIONS FT
//
//                                      LEFT JOIN FDS_USER_ACCOUNT UA ON FT.ASSIGNED_USER_ACCOUNT_ID = UA.USER_ACCOUNT_ID
//                                      WHERE FT.ASSIGNED_DATE_TIME BETWEEN :startDate AND :endDate
//                                                                                           AND FT.MEMBER_BANK_ACQ='BANK MANDIRI'
//
//                                      GROUP BY
//                                          NVL(UA.USERNAME, 'Unassigned'),
//                                          CASE
//                                              WHEN FT.ACTION_TYPE IS NULL THEN 'Incoming'
//                                              WHEN FT.ACTION_TYPE = 'NORMAL' THEN 'Genuine'
//                                              WHEN FT.ACTION_TYPE = 'WATCH_LIST' THEN 'Watchlist'
//                                              WHEN FT.ACTION_TYPE = 'FRAUD' THEN 'Fraud'
//                                              ELSE 'Investigation'
//                                          END
//                                  )
//                                  SELECT
//                                      Analyst,
//                                      SUM(Incoming) AS Incoming,
//                                      SUM(Genuine) AS Genuine,
//                                      SUM(Watchlist) AS Watchlist,
//                                      SUM(Fraud) AS Fraud,
//                                      SUM(Investigation) AS Investigation,
//                                      (SUM(Incoming) + SUM(Genuine) + SUM(Watchlist) + SUM(Fraud) + SUM(Investigation)) AS Pending
//                                  FROM (
//                                      SELECT
//                                          ASSIGNED_TO AS Analyst,
//                                          CASE WHEN category = 'Incoming' THEN count ELSE 0 END AS Incoming,
//                                          CASE WHEN category = 'Genuine' THEN count ELSE 0 END AS Genuine,
//                                          CASE WHEN category = 'Watchlist' THEN count ELSE 0 END AS Watchlist,
//                                          CASE WHEN category = 'Fraud' THEN count ELSE 0 END AS Fraud,
//                                          CASE WHEN category = 'Investigation' THEN count ELSE 0 END AS Investigation
//                                      FROM categorized_data
//                                  )
//                                  GROUP BY ROLLUP(Analyst)
//                                  ORDER BY
//                                      CASE WHEN Analyst IS NULL THEN 1 ELSE 0 END,
//                                      Analyst NULLS LAST
//            """, nativeQuery = true)
//    List<List<Object>> countByAnalyst(
//            @Param("startDate") LocalDateTime startDate,
//            @Param("endDate") LocalDateTime endDate);
    @Query(value = """
            
              WITH categorized_data AS
              (SELECT NVL(UA.USERNAME, 'Unassigned') AS ASSIGNED_TO,
                      CASE
                          WHEN FR.RISK_LEVEL = 'High Risk' THEN 'High Risk'
                          WHEN FR.RISK_LEVEL = 'Low Risk' THEN 'Low Risk'
                          ELSE 'Medium Risk'
                      END AS risk_level,
                      CASE
                          WHEN FT.ACTION_TYPE IS NULL THEN 'Incoming'
                          WHEN FT.ACTION_TYPE = 'NORMAL' THEN 'Genuine'
                          WHEN FT.ACTION_TYPE = 'INVESTIGATION' THEN 'Investigation'
                          WHEN FT.ACTION_TYPE = 'WATCH_LIST' THEN 'Watchlist'
                          WHEN FT.ACTION_TYPE = 'FRAUD' THEN 'Fraud'
                          ELSE 'Remind'
                      END AS category,
                      COUNT(*) AS COUNT
               FROM FDS_TRANSACTIONS FT
               LEFT JOIN FDS_TRANSACTIONS_RULE FTR ON FT.FDS_TRANSACTION_ID = FTR.FDS_TRANSACTION_ID
               LEFT JOIN FDS_RULE FR ON FTR.RULE_ID = FR.RULE_ID
               LEFT JOIN FDS_USER_ACCOUNT UA ON FT.ASSIGNED_USER_ACCOUNT_ID = UA.USER_ACCOUNT_ID
               WHERE FT.ASSIGNED_DATE_TIME BETWEEN :startDate AND :endDate
                 AND FT.MEMBER_BANK_ACQ = 'BANK MANDIRI'
               GROUP BY NVL(UA.USERNAME, 'Unassigned'),
                        CASE
                            WHEN FR.RISK_LEVEL = 'High Risk' THEN 'High Risk'
                            WHEN FR.RISK_LEVEL = 'Low Risk' THEN 'Low Risk'
                            ELSE 'Medium Risk'
                        END,
                        CASE
                            WHEN FT.ACTION_TYPE IS NULL THEN 'Incoming'
                            WHEN FT.ACTION_TYPE = 'NORMAL' THEN 'Genuine'
                            WHEN FT.ACTION_TYPE = 'INVESTIGATION' THEN 'Investigation'
                            WHEN FT.ACTION_TYPE = 'WATCH_LIST' THEN 'Watchlist'
                            WHEN FT.ACTION_TYPE = 'FRAUD' THEN 'Fraud'
                            ELSE 'Remind'
                        END),
                 mockup_report AS
              (SELECT risk_level,
                      ASSIGNED_TO AS analyst,
                      SUM(CASE
                              WHEN category = 'Genuine' THEN COUNT
                              ELSE 0
                          END) AS genuine,
                      SUM(CASE
                              WHEN category = 'Investigation' THEN COUNT
                              ELSE 0
                          END) AS investigation,
                      SUM(CASE
                              WHEN category = 'Watchlist' THEN COUNT
                              ELSE 0
                          END) AS watchlist,
                      SUM(CASE
                              WHEN category = 'Fraud' THEN COUNT
                              ELSE 0
                          END) AS fraud,
                      SUM(CASE
                              WHEN category = 'Remind' THEN COUNT
                              ELSE 0
                          END) AS remind, --         SUM(count) AS total
             (SUM(CASE
                      WHEN category = 'Genuine' THEN COUNT
                      ELSE 0
                  END)+ SUM(CASE
                                WHEN category = 'Investigation' THEN COUNT
                                ELSE 0
                            END) + SUM(CASE
                                           WHEN category = 'Watchlist' THEN COUNT
                                           ELSE 0
                                       END) + SUM(CASE
                                                      WHEN category = 'Fraud' THEN COUNT
                                                      ELSE 0
                                                  END) + SUM(CASE
                                                                 WHEN category = 'Remind' THEN COUNT
                                                                 ELSE 0
                                                             END)) AS total
               FROM categorized_data
               GROUP BY risk_level,
                        ASSIGNED_TO
               ORDER BY CASE risk_level
                            WHEN 'High Risk' THEN 1
                            WHEN 'Medium Risk' THEN 2
                            ELSE 3
                        END,
                        analyst)
            SELECT risk_level,
                   analyst,
                   genuine,
                   investigation,
                   watchlist,
                   fraud,
                   remind,
                   total
            FROM mockup_report
            """, nativeQuery = true)
    List<List<Object>> countByAnalyst(
            @Param("startDate") LocalDateTime startDate,
            @Param("endDate") LocalDateTime endDate);

    //    @Query(value = """
//               SELECT
//                fr.RULE_NAME AS "Rule name",
//                SUM(CASE WHEN ft.ACTION_TYPE = 'NORMAL' THEN 1 ELSE 0 END) AS "Genuine",
//                SUM(CASE WHEN ft.ACTION_TYPE = 'WATCHLIST' THEN 1 ELSE 0 END) AS "Watchlist",
//                SUM(CASE WHEN ft.ACTION_TYPE = 'FRAUD' THEN 1 ELSE 0 END) AS "Fraud",
//                SUM(CASE WHEN ft.ACTION_TYPE = 'REMIND' THEN 1 ELSE 0 END) AS "Investigation",
//                SUM(CASE WHEN ft.ACTION_TYPE is null THEN 1 ELSE 0 END) AS "Incoming",
//                COUNT(ft.FDS_TRANSACTION_ID) AS "Total alert",
//                ROUND(
//                    SUM(CASE WHEN ft.ACTION_TYPE = 'FRAUD' THEN 1 ELSE 0 END) * 100.0 /
//                    NULLIF(COUNT(ft.FDS_TRANSACTION_ID), 0),
//                    2
//                ) AS "False positive"
//            FROM
//                FDS_RULE fr
//            LEFT JOIN
//                FDS_TRANSACTIONS_RULE ftr ON fr.RULE_ID = ftr.RULE_ID
//            LEFT JOIN
//                FDS_TRANSACTIONS ft ON ftr.FDS_TRANSACTION_ID = ft.FDS_TRANSACTION_ID
//                AND ft.ASSIGNED_DATE_TIME BETWEEN :startDate AND :endDate
//            GROUP BY
//                fr.RULE_NAME
//            ORDER BY
//                "Total alert" DESC
//
//            """, nativeQuery = true)
//    List<List<Object>> countByRuleName(
//            @Param("startDate") LocalDateTime startDate,
//            @Param("endDate") LocalDateTime endDate);
    @Query(value = """
            
              SELECT fr.RULE_NAME AS "ruleName",
                   fr.
              RISK_LEVEL AS "riskLevel",
                   SUM(CASE
                           WHEN ft.ACTION_TYPE = 'NORMAL' THEN 1
                           ELSE 0
                       END) AS "genuine",
                   SUM(CASE
                           WHEN ft.ACTION_TYPE = 'INVESTIGATION' THEN 1
                           ELSE 0
                       END) AS "investigation",
                   SUM(CASE
                           WHEN ft.ACTION_TYPE = 'WATCHLIST' THEN 1
                           ELSE 0
                       END) AS "watchlist",
                   SUM(CASE
                           WHEN ft.ACTION_TYPE = 'FRAUD' THEN 1
                           ELSE 0
                       END) AS "fraud",
                   SUM(CASE
                           WHEN ft.ACTION_TYPE = 'REMIND' THEN 1
                           ELSE 0
                       END) AS "remind",
                   SUM(CASE
                           WHEN ft.ACTION_TYPE IS NULL THEN 1
                           ELSE 0
                       END) AS "pending",
                   COUNT(*) AS "total",
                   COALESCE(ROUND(SUM(CASE
                                          WHEN ft.ACTION_TYPE = 'NORMAL' THEN 1
                                          ELSE 0
                                      END) * 100.0 / NULLIF(SUM(CASE
                                                                    WHEN ft.ACTION_TYPE IS NOT NULL THEN 1
                                                                    ELSE 0
                                                                END), 0), 2), 0) AS "falsePositive"
            FROM FDS_RULE fr
            LEFT JOIN FDS_TRANSACTIONS_RULE ftr ON fr.RULE_ID = ftr.RULE_ID
            LEFT JOIN FDS_TRANSACTIONS ft ON ftr.FDS_TRANSACTION_ID = ft.FDS_TRANSACTION_ID
            AND FT.MEMBER_BANK_ACQ='BANK MANDIRI'
            AND ft.ASSIGNED_DATE_TIME BETWEEN :startDate AND :endDate
            GROUP BY fr.RULE_NAME,
                     fr.RISK_LEVEL
            ORDER BY CASE risk_level
                         WHEN 'High Risk' THEN 1
                         WHEN 'Medium Risk' THEN 2
                         ELSE 3
                     END
            """,
            nativeQuery = true)
    List<FdsTransactionsRuleNameReportsProjection> countByRuleName(
            @Param("startDate") LocalDateTime startDate,
            @Param("endDate") LocalDateTime endDate);

    @Query(value = """
            
              SELECT ft.MID AS mid,
                   ft.
              MER_NM AS merchantName,
                   ft.MEMBER_BANK_ACQ AS memberBank,
                   ft.CHANNEL AS channel,
                   SUM(CASE
                           WHEN ft.ACTION_TYPE = 'NORMAL' THEN 1
                           ELSE 0
                       END) AS genuine,
                   SUM(CASE
                           WHEN ft.ACTION_TYPE = 'INVESTIGATION' THEN 1
                           ELSE 0
                       END) AS investigation,
                   SUM(CASE
                           WHEN ft.ACTION_TYPE = 'WATCHLIST' THEN 1
                           ELSE 0
                       END) AS watchlist,
                   SUM(CASE
                           WHEN ft.ACTION_TYPE = 'FRAUD' THEN 1
                           ELSE 0
                       END) AS fraud,
                   SUM(CASE
                           WHEN ft.ACTION_TYPE = 'REMIND' THEN 1
                           ELSE 0
                       END) AS remind,
                   SUM(CASE
                           WHEN ft.ACTION_TYPE IS NULL THEN 1
                           ELSE 0
                       END) AS pending,
                   COUNT(*) AS totalAlert,
                   COALESCE(ROUND(SUM(CASE
                                          WHEN ft.ACTION_TYPE = 'NORMAL' THEN 1
                                          ELSE 0
                                      END) * 100.0 / NULLIF(SUM(CASE
                                                                    WHEN ft.ACTION_TYPE IS NOT NULL THEN 1
                                                                    ELSE 0
                                                                END), 0), 2), 0) AS "falsePositive"
            FROM FDS_TRANSACTIONS ft
            LEFT JOIN FDS_TRANSACTIONS_RULE ftr ON ft.FDS_TRANSACTION_ID = ftr.FDS_TRANSACTION_ID
            LEFT JOIN FDS_RULE fr ON ftr.RULE_ID = fr.RULE_ID
            WHERE ft.ASSIGNED_DATE_TIME BETWEEN :startDate AND :endDate
              AND FT.MEMBER_BANK_ACQ='BANK MANDIRI'
            GROUP BY ft.MID,
                     ft.MER_NM,
                     ft.MEMBER_BANK_ACQ,
                     ft.CHANNEL
            ORDER BY ft.MID
            """, nativeQuery = true)
    List<FdsTransactionsMerchantReportsProjection> countByMerchant(
            @Param("startDate") LocalDateTime startDate,
            @Param("endDate") LocalDateTime endDate);

    @Query(value = """
            
                                            SELECT * FROM FDS_TRANSACTIONS F WHERE F.ASSIGNED_USER_ACCOUNT_ID IS NULL
                                        AND ROWNUM=1
            """, nativeQuery = true)
    Optional<FdsTransactions> findByAssignedUserAccountNull();

    @Query("select f from FdsTransactions f where f.assignedUserAccount = :assignedUserAccount")
    List<FdsTransactions> findByAssignedUserAccount(@Param("assignedUserAccount") UserAccount assignedUserAccount);

    @Query(value = """
            SELECT *
            FROM FDS_TRANSACTIONS
            WHERE ASSIGNED_USER_ACCOUNT_ID IS NULL
            """, nativeQuery = true)
    Page<FdsTransactions> findFdsTransactionsByAssignedUserAccountNull(Pageable pageable);

    @Query(value = "SELECT * FROM FDS_TRANSACTIONS e " +
            "WHERE TRUNC(e.REMIND_DATE) = TRUNC(:date) " +
            "AND TO_CHAR(e.REMIND_DATE, 'HH24') = TO_CHAR(:date, 'HH24') " +
            "AND TO_CHAR(e.REMIND_DATE, 'MI') = TO_CHAR(:date, 'MI')", nativeQuery = true)
    List<FdsTransactions> findByDateTimeIgnoringSeconds(@Param("date") LocalDateTime date);

    @Query("select f from FdsTransactions f where f.fdsTransactionId in :fdsTransactionIds")
    List<FdsTransactions> findByFdsTransactionIdIn(@Param("fdsTransactionIds") Collection<String> fdsTransactionIds);
}
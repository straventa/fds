package com.yokke.fdsservice.alert_fds_parameter;

import lombok.*;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.web.bind.annotation.RequestParam;

import java.time.OffsetDateTime;
import java.util.List;
import java.util.Optional;


public interface AlertFdsParameterRepository extends JpaRepository<AlertFdsParameter, String> {
    @Query("select a from AlertFdsParameter a where a.authSeqNo = ?1 and a.authDate = ?2 and a.cardNo = ?3")
    Optional<AlertFdsParameter> findByAuthSeqNoAndAuthDateAndCardNo(String authSeqNo, String authDate, String cardNo);

//    @Query(value = """
//SELECT *
//FROM ALERT_FDS_PARAMETER
//WHERE TO_DATE(ALERT_FDS_PARAMETER.AUTH_DATE || ALERT_FDS_PARAMETER.AUTH_TIME, 'YYYYMMDDHH24MISS')
//      BETWEEN TO_DATE(:startDate, 'YYYYMMDD HH24MISS')
//      AND TO_DATE(:endDate, 'YYYYMMDD HH24MISS')
//""",
//            nativeQuery = true)
//    Page<AlertFdsParameter> findAll(@Param("startDate") String startDate,
//                                             @Param("endDate") String endDate,
//                                             Pageable pageable);
    @Query(value = """
    SELECT *
    FROM ALERT_FDS_PARAMETER
    WHERE TO_DATE(ALERT_FDS_PARAMETER.AUTH_DATE || ALERT_FDS_PARAMETER.AUTH_TIME, 'YYYYMMDDHH24MISS')
          BETWEEN TO_DATE(:startDate, 'YYYYMMDD HH24MISS')
          AND TO_DATE(:endDate, 'YYYYMMDD HH24MISS')
            AND (:mid IS NULL OR LOWER(ALERT_FDS_PARAMETER.MID) LIKE LOWER('%' || :mid || '%'))
            AND (:tid IS NULL OR LOWER(ALERT_FDS_PARAMETER.TID) LIKE LOWER('%' || :tid || '%'))
            AND (:assignedTo IS NULL OR ALERT_FDS_PARAMETER.ASSIGNED_TO = :assignedTo)
            AND (:actionType IS NULL OR ALERT_FDS_PARAMETER.ACTION_TYPE = :actionType)
    AND (:isMarked IS NULL OR ALERT_FDS_PARAMETER.ACTION_TYPE IS NOT NULL)

    """,
            nativeQuery = true)
    Page<AlertFdsParameter> findAll(@Param("startDate") String startDate,
                                    @Param("endDate") String endDate,
                                    @Param("mid") String mid,
                                    @Param("tid") String tid,
                                    @Param("assignedTo") String assignedTo,
                                    @Param("actionType") String actionType,
                                    @Param("isMarked") Boolean isMarked,
                                    Pageable pageable);

    @Query(
            value = """
select * from ALERT_FDS_PARAMETER where CONFIRMED_FRAUD is null and rownum=1
""",nativeQuery = true
    )
    Optional<AlertFdsParameter> findByConfirmedFraudNull();

    @Query(value = """
select * from ALERT_FDS_PARAMETER where ASSIGNED_TO = :assignedTo and rownum=1 and  ACTION_TYPE IS NULL
""",nativeQuery = true)
    Optional<AlertFdsParameter> findByAssignedTo( @Param("assignedTo") String assignedTo);
    @Query(value = """
select * from ALERT_FDS_PARAMETER 
         where ASSIGNED_TO = :assignedTo 
           and rownum=1 
           and  ACTION_TYPE IS NULL
              and AUTH_SEQ_NO != :authSeqNo
""",nativeQuery = true)
    Optional<AlertFdsParameter> findOne(
            @Param("assignedTo") String assignedTo,
            @Param("authSeqNo") String authSeqNo
            );

    @Query("select count(a) from AlertFdsParameter a where a.actionType is null")
    long countByActionTypeNull();

    @Query("select count(a) from AlertFdsParameter a where a.actionType is null and a.assignedTo = :assignedTo")
    long countByActionTypeNullAndAssignedTo(@Param("assignedTo") String assignedTo);

    @Query(value = """
        WITH categorized_data AS (
            SELECT 
                NVL(ASSIGNED_TO, 'Unassigned') AS ASSIGNED_TO,
                CASE 
                    WHEN ACTION_TYPE IS NULL THEN 'Incoming'
                    WHEN ACTION_TYPE = 'NORMAL' THEN 'Genuine'
                    WHEN ACTION_TYPE = 'WATCH_LIST' THEN 'Watchlist'
                    WHEN ACTION_TYPE = 'FRAUD' THEN 'Fraud'
                    ELSE 'Investigation'
                END AS category,
                COUNT(*) AS count
            FROM ALERT_FDS_PARAMETER
            WHERE TO_DATE(AUTH_DATE, 'YYYYMMDD') BETWEEN TO_DATE(:start_date, 'YYYY-MM-DD') AND TO_DATE(:end_date, 'YYYY-MM-DD')
            GROUP BY 
                NVL(ASSIGNED_TO, 'Unassigned'),
                CASE 
                    WHEN ACTION_TYPE IS NULL THEN 'Incoming'
                    WHEN ACTION_TYPE = 'NORMAL' THEN 'Genuine'
                    WHEN ACTION_TYPE = 'WATCH_LIST' THEN 'Watchlist'
                    WHEN ACTION_TYPE = 'FRAUD' THEN 'Fraud'
                    ELSE 'Investigation'
                END
        )
        SELECT 
            Analyst,
            SUM(Incoming) AS Incoming,
            SUM(Genuine) AS Genuine,
            SUM(Watchlist) AS Watchlist,
            SUM(Fraud) AS Fraud,
            SUM(Investigation) AS Investigation,
            (SUM(Incoming) + SUM(Genuine) + SUM(Watchlist) + SUM(Fraud) + SUM(Investigation)) AS Pending
        FROM (
            SELECT 
                ASSIGNED_TO AS Analyst,
                CASE WHEN category = 'Incoming' THEN count ELSE 0 END AS Incoming,
                CASE WHEN category = 'Genuine' THEN count ELSE 0 END AS Genuine,
                CASE WHEN category = 'Watchlist' THEN count ELSE 0 END AS Watchlist,
                CASE WHEN category = 'Fraud' THEN count ELSE 0 END AS Fraud,
                CASE WHEN category = 'Investigation' THEN count ELSE 0 END AS Investigation
            FROM categorized_data
        )
        GROUP BY ROLLUP(Analyst)
        ORDER BY 
            CASE WHEN Analyst IS NULL THEN 1 ELSE 0 END,
            Analyst NULLS LAST
""", nativeQuery = true)
    List<List<Object>> countByAnalyst(
            @Param("start_date") String startDate,
            @Param("end_date") String endDate
    );

}
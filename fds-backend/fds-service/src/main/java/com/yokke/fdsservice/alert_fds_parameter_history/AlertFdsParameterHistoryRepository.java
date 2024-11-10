package com.yokke.fdsservice.alert_fds_parameter_history;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface AlertFdsParameterHistoryRepository extends JpaRepository<AlertFdsParameterHistory, String> {
    @Query("select a from AlertFdsParameterHistory a where a.alertFdsParameterId = ?1")
    List<AlertFdsParameterHistory> findByAlertFdsParameterId(String alertFdsParameterId);
}
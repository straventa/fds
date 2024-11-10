package com.yokke.fdsservice.alert_fds_parameter_history;

import com.yokke.fdsservice.alert_fds_parameter.AlertFdsParameterHistoryMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.time.OffsetDateTime;
import java.util.List;


@Transactional
@Service
@RequiredArgsConstructor
@EnableTransactionManagement
public class AlertFdsParameterHistoryService {
    private final AlertFdsParameterHistoryRepository alertFdsParameterRepository;
    private final AlertFdsParameterHistoryMapper alertFdsParameterHistoryMapper;
    public List<AlertFdsParameterHistoryDto> readByAlertFdsParameterId(String alertFdsParameterId) {
        return alertFdsParameterRepository.findByAlertFdsParameterId(alertFdsParameterId)
                .stream()
                .map(alertFdsParameterHistoryMapper::mapToDto)
                .toList();
    }
    public AlertFdsParameterHistoryDto create(AlertFdsParameterHistoryDto alertFdsParameterHistoryDto) {
        return alertFdsParameterHistoryMapper.mapToDto(
                alertFdsParameterRepository.save(
                        alertFdsParameterHistoryMapper.mapToEntity(alertFdsParameterHistoryDto)
                )
        );
    }
    public AlertFdsParameterHistoryDto create(String alertFdsParameterId,String activity,String notes) {
//        String createdBy = SecurityContextHolder.getContext().getAuthentication().getName();
        String createdBy = "admin";
        LocalDateTime createdDate = LocalDateTime.now();
        return alertFdsParameterHistoryMapper.mapToDto(
                alertFdsParameterRepository.save(
                        AlertFdsParameterHistory.builder()
                                .alertFdsParameterId(alertFdsParameterId)
                                .activity(activity)
                                .notes(notes)
                                .createdBy(createdBy)
                                .createdDate(createdDate)
                                .build()
                )
        );

    }

}

package com.yokke.fdsservice.alert_fds_parameter;


import com.yokke.base.mapper.BaseMapper;
import com.yokke.fdsservice.alert_fds_parameter_history.AlertFdsParameterHistory;
import com.yokke.fdsservice.alert_fds_parameter_history.AlertFdsParameterHistoryDto;
import org.springframework.stereotype.Service;

@Service
public class AlertFdsParameterHistoryMapper extends BaseMapper {
    public AlertFdsParameterHistoryDto mapToDto(AlertFdsParameterHistory alertFdsParameterHistory) {
        return AlertFdsParameterHistoryDto.builder()
                .id(alertFdsParameterHistory.getId())
                .alertFdsParameterId(alertFdsParameterHistory.getAlertFdsParameterId())
                .activity(alertFdsParameterHistory.getActivity())
                .createdBy(alertFdsParameterHistory.getCreatedBy())
                .createdDate(alertFdsParameterHistory.getCreatedDate())
                .notes(alertFdsParameterHistory.getNotes())
                .build();
    }
    public AlertFdsParameterHistory mapToEntity(AlertFdsParameterHistoryDto alertFdsParameterHistoryDto) {
        return AlertFdsParameterHistory.builder()
                .id(alertFdsParameterHistoryDto.getId())
                .alertFdsParameterId(alertFdsParameterHistoryDto.getAlertFdsParameterId())
                .activity(alertFdsParameterHistoryDto.getActivity())
                .createdBy(alertFdsParameterHistoryDto.getCreatedBy())
                .createdDate(alertFdsParameterHistoryDto.getCreatedDate())
                .notes(alertFdsParameterHistoryDto.getNotes())
                .build();
    }
}

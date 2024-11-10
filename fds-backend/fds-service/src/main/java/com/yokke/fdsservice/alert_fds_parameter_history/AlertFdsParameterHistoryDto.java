package com.yokke.fdsservice.alert_fds_parameter_history;

import lombok.*;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * DTO for {@link AlertFdsParameterHistory}
 */
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class AlertFdsParameterHistoryDto implements Serializable {
    String id;
    String alertFdsParameterId;
    String activity;
    String createdBy;
    LocalDateTime createdDate;
    String notes;
}
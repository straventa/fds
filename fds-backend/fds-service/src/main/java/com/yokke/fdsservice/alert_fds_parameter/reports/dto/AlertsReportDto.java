package com.yokke.fdsservice.alert_fds_parameter.reports.dto;

import lombok.*;

import java.time.OffsetDateTime;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class AlertsReportDto {
    OffsetDateTime dateTime;
    String label;
    String value;
}

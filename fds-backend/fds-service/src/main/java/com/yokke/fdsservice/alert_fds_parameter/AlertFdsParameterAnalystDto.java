package com.yokke.fdsservice.alert_fds_parameter;

import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class AlertFdsParameterAnalystDto {
    String analyst;
    String incoming;
    String genuine;
    String watchlist;
    String fraud;
    String investigation;
    String pending;
}

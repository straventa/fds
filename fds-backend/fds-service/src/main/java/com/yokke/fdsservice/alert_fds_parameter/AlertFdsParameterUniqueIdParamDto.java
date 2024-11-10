package com.yokke.fdsservice.alert_fds_parameter;

import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class AlertFdsParameterUniqueIdParamDto {
    private String authSeqNo;
    private String authDate;
    private String cardNo;
}

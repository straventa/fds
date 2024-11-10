package com.yokke.fdsservice.alert_fds_parameter_history;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Entity
@Table(name = "ALERT_FDS_PARAMETER_HISTORY")
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class AlertFdsParameterHistory {
    @Id
    @Column(name="ID", length = 36, nullable = false)
    @GeneratedValue(strategy = GenerationType.UUID)
    private String id;

    @Column(name="ALERT_FDS_PARAMETER_ID", length = 200, nullable = false)
    private String alertFdsParameterId;

    @Column(name="ACTIVITY", length = 200)
    private String activity;

    @Column(name="CREATED_BY", length = 200)
    private String createdBy;

    @Column(name="CREATED_DATE_TIME")
    private LocalDateTime createdDate;

    @Column(name="NOTES", length = 200)
    private String notes;


}

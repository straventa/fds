package com.yokke.usermanagement.transactions.fds_parameter;


import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "FDS_PARAMETER")
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class FdsParameter {

    @Id
    @Column(name = "FDS_PARAMETER_ID", nullable = false)
    @GeneratedValue(strategy = GenerationType.UUID)
    private String fdsParameterId;


    @Column(name = "FDS_PARAMETER_KEY", length = 1000)
    private String fdsParameterKey;


    @Column(name = "FDS_PARAMETER_VALUE", length = 1000)
    private String fdsParameterValue;


    @Column(name = "FDS_PARAMETER_CATEGORY", length = 1000)
    private String fdsParameterCategory;
}

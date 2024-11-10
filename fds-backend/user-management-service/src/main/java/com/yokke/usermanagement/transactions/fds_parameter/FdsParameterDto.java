package com.yokke.usermanagement.transactions.fds_parameter;

import lombok.*;

import java.io.Serializable;

/**
 * DTO for {@link FdsParameter}
 */
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class FdsParameterDto implements Serializable {
    String fdsParameterId;
    String fdsParameterKey;
    String fdsParameterValue;
    String fdsParameterCategory;
}
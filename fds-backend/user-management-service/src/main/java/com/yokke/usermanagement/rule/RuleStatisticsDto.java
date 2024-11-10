package com.yokke.usermanagement.rule;

import lombok.*;

import java.math.BigDecimal;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class RuleStatisticsDto {
    BigDecimal alertTotal;
    BigDecimal alertReviewed;
    BigDecimal alertWaiting;
    String alertPercentage;

}

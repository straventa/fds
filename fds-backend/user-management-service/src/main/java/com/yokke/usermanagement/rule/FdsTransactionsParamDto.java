package com.yokke.usermanagement.rule;

import lombok.*;

import java.io.Serializable;
import java.time.OffsetDateTime;
import java.util.List;


@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class FdsTransactionsParamDto implements Serializable {
    Integer page;
    Integer size;
    String sort;
    String order;
    Integer totalItems;


    OffsetDateTime startDate;
    OffsetDateTime endDate;
    String mid;
    String tid;

    List<String> actionType;
    Boolean isConfirmed;
    List<String> ruleId;
    Boolean isAll;
}

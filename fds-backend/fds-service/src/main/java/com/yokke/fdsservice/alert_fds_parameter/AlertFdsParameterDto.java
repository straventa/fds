package com.yokke.fdsservice.alert_fds_parameter;

import com.yokke.fdsservice.alert_fds_parameter_history.AlertFdsParameterHistoryDto;
import jakarta.persistence.Column;
import lombok.*;

import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.time.OffsetDateTime;
import java.util.List;

/**
 * DTO for {@link AlertFdsParameter}
 */
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class AlertFdsParameterDto implements Serializable {
    String authSeqNo;
    String cardNo;
    String memberBankAcq;
    String merchantName;
    String mid;
    String tid;
    String rrn;
    String issuer;
    String authDate;
    String authTime;
    BigDecimal authAmount;
    String traceNo;
    String messageTypeId;
    String authSaleType;
    String authIntnRspnCd;
    String reasonContents;
    Integer installmentCount;
    String switchBrand;
    String posEntryModeDetail;
    String cardTypeCode;
    String onusCode;
    String eciValue;
    String approvalCode;
    String pgName;
    String pgType;
    String issuerMemberNo;
    String businessType;
    String channel;
    String issuerCountry;
    String parameterValues;
    String actionType;
     String fraudType;

    String fraudPodType;
    String fraudNote;
    String confirmedBy;
    LocalDateTime confirmedDateTime;
    String remindNote;
    LocalDateTime remindDate;
    String assignedTo;
    LocalDateTime assignedDateTime;


    OffsetDateTime authDateTime;
    String uniqueId;
    String notes;

     String activity;
     String id;


    List<AlertFdsParameterHistoryDto> alertFdsParameterHistory;
}
package com.yokke.fdsservice.alert_fds_parameter;

import com.yokke.base.mapper.BaseMapper;
import com.yokke.fdsservice.alert_fds_parameter_history.AlertFdsParameterHistoryDto;
import org.springframework.stereotype.Service;

import java.time.*;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.UUID;

@Service
public class AlertFdsParameterMapper extends BaseMapper {
    public AlertFdsParameterDto mapToDto(AlertFdsParameter alertFdsParameter) {
        return AlertFdsParameterDto.builder()
                .authSeqNo(alertFdsParameter.getAuthSeqNo())
                .cardNo(alertFdsParameter.getCardNo())
                .memberBankAcq(alertFdsParameter.getMemberBankAcq())
                .merchantName(alertFdsParameter.getMerchantName())
                .mid(alertFdsParameter.getMid())
                .tid(alertFdsParameter.getTid())
                .rrn(alertFdsParameter.getRrn())
                .issuer(alertFdsParameter.getIssuer())
                .authDate(alertFdsParameter.getAuthDate())
                .authTime(alertFdsParameter.getAuthTime())
                .authAmount(alertFdsParameter.getAuthAmount())
                .traceNo(alertFdsParameter.getTraceNo())
                .messageTypeId(alertFdsParameter.getMessageTypeId())
                .authSaleType(alertFdsParameter.getAuthSaleType())
                .authIntnRspnCd(alertFdsParameter.getAuthIntnRspnCd())
                .reasonContents(alertFdsParameter.getReasonContents())
                .installmentCount(alertFdsParameter.getInstallmentCount())
                .switchBrand(alertFdsParameter.getSwitchBrand())
                .posEntryModeDetail(alertFdsParameter.getPosEntryModeDetail())
                .cardTypeCode(alertFdsParameter.getCardTypeCode())
                .onusCode(alertFdsParameter.getOnusCode())
                .eciValue(alertFdsParameter.getEciValue())
                .approvalCode(alertFdsParameter.getApprovalCode())
                .pgName(alertFdsParameter.getPgName())
                .pgType(alertFdsParameter.getPgType())
                .issuerMemberNo(alertFdsParameter.getIssuerMemberNo())
                .businessType(alertFdsParameter.getBusinessType())
                .channel(alertFdsParameter.getChannel())
                .issuerCountry(alertFdsParameter.getIssuerCountry())
                .parameterValues(alertFdsParameter.getParameterValues())
                .actionType(alertFdsParameter.getActionType())
                .fraudType(alertFdsParameter.getFraudType())
                .fraudPodType(alertFdsParameter.getFraudPodType())
                .fraudNote(alertFdsParameter.getFraudNote())
                .confirmedBy(alertFdsParameter.getConfirmedBy())
                .confirmedDateTime(alertFdsParameter.getConfirmedDateTime())
                .remindNote(alertFdsParameter.getRemindNote())
                .remindDate(alertFdsParameter.getRemindDate())
                .assignedTo(alertFdsParameter.getAssignedTo())
                .assignedDateTime(alertFdsParameter.getAssignedDateTime())
                .authDateTime(convertDate(alertFdsParameter.getAuthDate(), alertFdsParameter.getAuthTime()))
                .uniqueId(alertFdsParameter.getAuthSeqNo()+","+alertFdsParameter.getAuthDate()+","+alertFdsParameter.getCardNo())
                .build();
    }
    public AlertFdsParameterDto mapToDto(AlertFdsParameter alertFdsParameter, List<AlertFdsParameterHistoryDto> alertFdsParameterHistoryDtoList) {
        return AlertFdsParameterDto.builder()
                .authSeqNo(alertFdsParameter.getAuthSeqNo())
                .cardNo(alertFdsParameter.getCardNo())
                .memberBankAcq(alertFdsParameter.getMemberBankAcq())
                .merchantName(alertFdsParameter.getMerchantName())
                .mid(alertFdsParameter.getMid())
                .tid(alertFdsParameter.getTid())
                .rrn(alertFdsParameter.getRrn())
                .issuer(alertFdsParameter.getIssuer())
                .authDate(alertFdsParameter.getAuthDate())
                .authTime(alertFdsParameter.getAuthTime())
                .authAmount(alertFdsParameter.getAuthAmount())
                .traceNo(alertFdsParameter.getTraceNo())
                .messageTypeId(alertFdsParameter.getMessageTypeId())
                .authSaleType(alertFdsParameter.getAuthSaleType())
                .authIntnRspnCd(alertFdsParameter.getAuthIntnRspnCd())
                .reasonContents(alertFdsParameter.getReasonContents())
                .installmentCount(alertFdsParameter.getInstallmentCount())
                .switchBrand(alertFdsParameter.getSwitchBrand())
                .posEntryModeDetail(alertFdsParameter.getPosEntryModeDetail())
                .cardTypeCode(alertFdsParameter.getCardTypeCode())
                .onusCode(alertFdsParameter.getOnusCode())
                .eciValue(alertFdsParameter.getEciValue())
                .approvalCode(alertFdsParameter.getApprovalCode())
                .pgName(alertFdsParameter.getPgName())
                .pgType(alertFdsParameter.getPgType())
                .issuerMemberNo(alertFdsParameter.getIssuerMemberNo())
                .businessType(alertFdsParameter.getBusinessType())
                .channel(alertFdsParameter.getChannel())
                .issuerCountry(alertFdsParameter.getIssuerCountry())
                .parameterValues(alertFdsParameter.getParameterValues())
                .actionType(alertFdsParameter.getActionType())
                .fraudType(alertFdsParameter.getFraudType())
                .fraudPodType(alertFdsParameter.getFraudPodType())
                .fraudNote(alertFdsParameter.getFraudNote())
                .confirmedBy(alertFdsParameter.getConfirmedBy())
                .confirmedDateTime(alertFdsParameter.getConfirmedDateTime())
                .remindNote(alertFdsParameter.getRemindNote())
                .remindDate(alertFdsParameter.getRemindDate())
                .assignedTo(alertFdsParameter.getAssignedTo())
                .assignedDateTime(alertFdsParameter.getAssignedDateTime())
                .authDateTime(convertDate(alertFdsParameter.getAuthDate(), alertFdsParameter.getAuthTime()))
                .alertFdsParameterHistory(alertFdsParameterHistoryDtoList)
                .uniqueId(alertFdsParameter.getAuthSeqNo()+","+alertFdsParameter.getAuthDate()+","+alertFdsParameter.getCardNo())
                .build();
    }
    public AlertFdsParameter update(AlertFdsParameterDto alertFdsParameterDto,AlertFdsParameter alertFdsParameter){
      alertFdsParameter.setActionType(alertFdsParameterDto.getActionType());
      alertFdsParameter.setFraudType(alertFdsParameterDto.getFraudType());
      alertFdsParameter.setFraudPodType(alertFdsParameterDto.getFraudPodType());
        alertFdsParameter.setFraudNote(alertFdsParameterDto.getFraudNote());
        alertFdsParameter.setConfirmedBy(alertFdsParameterDto.getConfirmedBy());
        alertFdsParameter.setConfirmedDateTime(alertFdsParameterDto.getConfirmedDateTime());
        alertFdsParameter.setRemindNote(alertFdsParameterDto.getRemindNote());
        alertFdsParameter.setRemindDate(alertFdsParameterDto.getRemindDate());
        alertFdsParameter.setAssignedTo(alertFdsParameterDto.getAssignedTo());
        alertFdsParameter.setAssignedDateTime(alertFdsParameterDto.getAssignedDateTime());
        if (alertFdsParameter.getId() == null){
            alertFdsParameter.setId(UUID.randomUUID().toString());
        }
        return alertFdsParameter;
    }
    public OffsetDateTime convertDate(String authDate, String authTime) {
        DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern("yyyyMMdd");
        DateTimeFormatter timeFormatter = DateTimeFormatter.ofPattern("HHmmss");

        // Step 2: Parse the strings to LocalDate and LocalTime
        LocalDate date = LocalDate.parse(authDate, dateFormatter);
        LocalTime time = LocalTime.parse(authTime, timeFormatter);

        // Step 3: Combine LocalDate and LocalTime into LocalDateTime
        LocalDateTime localDateTime = LocalDateTime.of(date, time);

        // Optionally, convert to OffsetDateTime with UTC offset
        OffsetDateTime offsetDateTime = localDateTime.atOffset(ZoneOffset.UTC);
        return offsetDateTime;
    }
    public AlertFdsParameterUniqueIdParamDto getUniqueId(String uniqueId){
        String[] parts = uniqueId.split(",");

        // Step 2: Reassign the parts to variables
        String authSeqNo = parts.length > 0 ? parts[0] : null;
        String authDate = parts.length > 1 ? parts[1] : null;
        String cardNo = parts.length > 2 ? parts[2] : null;

        return AlertFdsParameterUniqueIdParamDto.builder()
                .authSeqNo(authSeqNo)
                .authDate(authDate)
                .cardNo(cardNo)
                .build();
    }
}

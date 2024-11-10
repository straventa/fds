package com.yokke.usermanagement.transactions.fds_transactions;


import com.yokke.base.mapper.BaseMapper;
import com.yokke.usermanagement.rule.RuleDto;
import com.yokke.usermanagement.transactions.fds_transactions_audit.FdsTransactionsAuditDto;
import com.yokke.usermanagement.user_account.UserAccount;
import com.yokke.usermanagement.user_account.UserAccountDTO;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class FdsTransactionsMapper extends BaseMapper {

    public FdsTransactionsDto mapToDto(FdsTransactions fdsTransactions) {
        return FdsTransactionsDto.builder()
                .authSeqNo(fdsTransactions.getAuthSeqNo())
                .cardNo(fdsTransactions.getCardNo())
                .memberBankAcq(fdsTransactions.getMemberBankAcq())
                .merchantName(fdsTransactions.getMerchantName())
                .mid(fdsTransactions.getMid())
                .tid(fdsTransactions.getTid())
                .rrn(fdsTransactions.getRrn())
                .issuer(fdsTransactions.getIssuer())
                .authDate(fdsTransactions.getAuthDate())
                .authTime(fdsTransactions.getAuthTime())
                .authAmount(fdsTransactions.getAuthAmount())
                .traceNo(fdsTransactions.getTraceNo())
                .messageTypeId(fdsTransactions.getMessageTypeId())
                .authSaleType(fdsTransactions.getAuthSaleType())
                .authIntnRspnCd(fdsTransactions.getAuthIntnRspnCd())
                .reasonContents(fdsTransactions.getReasonContents())
                .installmentCount(fdsTransactions.getInstallmentCount())
                .switchBrand(fdsTransactions.getSwitchBrand())
                .posEntryModeDetail(fdsTransactions.getPosEntryModeDetail())
                .cardTypeCode(fdsTransactions.getCardTypeCode())
                .onusCode(fdsTransactions.getOnusCode())
                .eciValue(fdsTransactions.getEciValue())
                .approvalCode(fdsTransactions.getApprovalCode())
                .pgName(fdsTransactions.getPgName())
                .pgType(fdsTransactions.getPgType())
                .issuerMemberNo(fdsTransactions.getIssuerMemberNo())
                .businessType(fdsTransactions.getBusinessType())
                .channel(fdsTransactions.getChannel())
                .issuerCountry(fdsTransactions.getIssuerCountry())
                .parameterValues(fdsTransactions.getParameterValues())
                .actionType(fdsTransactions.getActionType())
                .fraudType(fdsTransactions.getFraudType())
                .fraudPodType(fdsTransactions.getFraudPodType())
                .fraudNote(fdsTransactions.getFraudNote())
                .parameterValues(fdsTransactions.getParameterValues())
                .actionType(fdsTransactions.getActionType())
                .fraudType(fdsTransactions.getFraudType())
                .fraudPodType(fdsTransactions.getFraudPodType())
                .fraudRemark(fdsTransactions.getFraudRemark())
                .fraudNote(fdsTransactions.getFraudNote())
                .remindNote(fdsTransactions.getRemindNote())
                .remindDate(fdsTransactions.getRemindDate())
                .assignedDateTime(fdsTransactions.getAssignedDateTime())
                .confirmedDateTime(fdsTransactions.getConfirmedDateTime())
                .fdsTransactionId(fdsTransactions.getFdsTransactionId())
                .build();
    }

    //    public FdsTransactionsDto mapToDto(FdsTransactions fdsTransactions, UserAccountDTO confirmedUserAccount){
//        FdsTransactionsDto fdsTransactionsDto = mapToDto(fdsTransactions);
//        fdsTransactionsDto.setConfirmedUserAccount(confirmedUserAccount);
//        return fdsTransactionsDto;
//    }
//    public FdsTransactionsDto mapToDto(
//            FdsTransactions fdsTransactions,
//            UserAccountDTO confirmedUserAccount,
//            UserAccountDTO assignedUserAccount
//    ){
//        FdsTransactionsDto fdsTransactionsDto = mapToDto(fdsTransactions);
//        fdsTransactionsDto.setConfirmedUserAccount(confirmedUserAccount);
//        fdsTransactionsDto.setAssignedUserAccount(assignedUserAccount);
//        return fdsTransactionsDto;
//    }
//    public FdsTransactionsDto mapToDto(
//            FdsTransactions fdsTransactions,
//            UserAccountDTO confirmedUserAccount,
//            UserAccountDTO assignedUserAccount,
//            List<RuleDto> ruleDtoList
//    ){
//        FdsTransactionsDto fdsTransactionsDto = mapToDto(fdsTransactions);
//        fdsTransactionsDto.setConfirmedUserAccount(confirmedUserAccount);
//        fdsTransactionsDto.setAssignedUserAccount(assignedUserAccount);
//        fdsTransactionsDto.setRule(ruleDtoList);
//        return fdsTransactionsDto;
//    }
    public FdsTransactionsDto mapToDto(
            FdsTransactions fdsTransactions,
            UserAccountDTO confirmedUserAccount,
            UserAccountDTO assignedUserAccount,
            List<RuleDto> ruleDtoList,
            List<FdsTransactionsAuditDto> fdsTransactionsAuditDto
    ) {
        FdsTransactionsDto fdsTransactionsDto = mapToDto(fdsTransactions);
        fdsTransactionsDto.setConfirmedUserAccount(confirmedUserAccount);
        fdsTransactionsDto.setAssignedUserAccount(assignedUserAccount);
        fdsTransactionsDto.setFdsTransactionsAudit(fdsTransactionsAuditDto);
        fdsTransactionsDto.setRule(ruleDtoList);
        return fdsTransactionsDto;
    }

    public FdsTransactionsDto mapToDto(
            FdsTransactions fdsTransactions,
            List<RuleDto> ruleDtoList
    ) {
        FdsTransactionsDto fdsTransactionsDto = mapToDto(fdsTransactions);
        fdsTransactionsDto.setRule(ruleDtoList);
        return fdsTransactionsDto;
    }

    public FdsTransactionsDto mapToDto(
            FdsTransactions fdsTransactions,
            List<RuleDto> ruleDtoList,
            List<FdsTransactionsAuditDto> fdsTransactionsAuditDtos
    ) {
        FdsTransactionsDto fdsTransactionsDto = mapToDto(fdsTransactions);
        fdsTransactionsDto.setRule(ruleDtoList);
        fdsTransactionsDto.setFdsTransactionsAudit(fdsTransactionsAuditDtos);
        return fdsTransactionsDto;
    }

    public FdsTransactionsDto mapToDto(
            FdsTransactions fdsTransactions,
            List<RuleDto> ruleDtoList,
            List<FdsTransactionsAuditDto> fdsTransactionsAuditDtos,
            UserAccountDTO assignedUserAccount,
            UserAccountDTO confirmedUserAccount
    ) {
        FdsTransactionsDto fdsTransactionsDto = mapToDto(fdsTransactions);
        fdsTransactionsDto.setRule(ruleDtoList);
        fdsTransactionsDto.setFdsTransactionsAudit(fdsTransactionsAuditDtos);
        fdsTransactionsDto.setAssignedUserAccount(assignedUserAccount);
        fdsTransactionsDto.setConfirmedUserAccount(confirmedUserAccount);
        return fdsTransactionsDto;
    }

    public FdsTransactions update(
            FdsTransactionsDto fdsTransactionsDto, FdsTransactions fdsTransactions
    ) {
        fdsTransactions.setParameterValues(fdsTransactionsDto.getParameterValues());
        fdsTransactions.setActionType(fdsTransactionsDto.getActionType());
        fdsTransactions.setFraudType(fdsTransactionsDto.getFraudType());
        fdsTransactions.setFraudPodType(fdsTransactionsDto.getFraudPodType());
        fdsTransactions.setFraudRemark(fdsTransactionsDto.getFraudRemark());
        fdsTransactions.setFraudNote(fdsTransactionsDto.getFraudNote());
        fdsTransactions.setRemindNote(fdsTransactionsDto.getRemindNote());
        fdsTransactions.setRemindDate(fdsTransactionsDto.getRemindDate());
        return fdsTransactions;
    }

    public FdsTransactions update(
            FdsTransactionsDto fdsTransactionsDto, FdsTransactions fdsTransactions, UserAccount userAccount
    ) {
        fdsTransactions.setParameterValues(fdsTransactionsDto.getParameterValues());
        fdsTransactions.setActionType(fdsTransactionsDto.getActionType());
        fdsTransactions.setFraudType(fdsTransactionsDto.getFraudType());
        fdsTransactions.setFraudPodType(fdsTransactionsDto.getFraudPodType());
        fdsTransactions.setFraudRemark(fdsTransactionsDto.getFraudRemark());
        fdsTransactions.setFraudNote(fdsTransactionsDto.getFraudNote());
        fdsTransactions.setRemindNote(fdsTransactionsDto.getRemindNote());
        fdsTransactions.setRemindDate(fdsTransactionsDto.getRemindDate());
        fdsTransactions.setConfirmedDateTime(LocalDateTime.now());
        fdsTransactions.setConfirmedUserAccount(userAccount);
        return fdsTransactions;
    }

    public FdsTransactions updateV2(
            FdsTransactionsDto fdsTransactionsDto, FdsTransactions fdsTransactions, UserAccount userAccount
    ) {
        if (fdsTransactionsDto.getActionType() == "FRAUD" || fdsTransactionsDto.getActionType() == "NORMAL" || fdsTransactionsDto.getActionType() == "WATCHLIST") {
            fdsTransactions.setConfirmedDateTime(LocalDateTime.now());
            fdsTransactions.setConfirmedUserAccount(userAccount);
        }
        fdsTransactions.setParameterValues(fdsTransactionsDto.getParameterValues());
        fdsTransactions.setActionType(fdsTransactionsDto.getActionType());
        fdsTransactions.setFraudType(fdsTransactionsDto.getFraudType());
        fdsTransactions.setFraudPodType(fdsTransactionsDto.getFraudPodType());
        fdsTransactions.setFraudRemark(fdsTransactionsDto.getFraudRemark());
        fdsTransactions.setFraudNote(fdsTransactionsDto.getFraudNote());
        fdsTransactions.setRemindNote(fdsTransactionsDto.getRemindNote());
        fdsTransactions.setRemindDate(fdsTransactionsDto.getRemindDate());

        return fdsTransactions;
    }
}

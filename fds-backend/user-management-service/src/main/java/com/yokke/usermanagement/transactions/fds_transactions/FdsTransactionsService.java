package com.yokke.usermanagement.transactions.fds_transactions;


import com.yokke.base.exception.response_status.NotFoundException;
import com.yokke.base.service.BaseService;
import com.yokke.usermanagement.rule.RuleDto;
import com.yokke.usermanagement.rule.RuleService;
import com.yokke.usermanagement.transactions.fds_transactions_audit.FdsTransactionsAuditDto;
import com.yokke.usermanagement.transactions.fds_transactions_audit.FdsTransactionsAuditService;
import com.yokke.usermanagement.user_account.UserAccount;
import com.yokke.usermanagement.user_account.UserAccountDTO;
import com.yokke.usermanagement.user_account.UserAccountRepository;
import com.yokke.usermanagement.user_account.UserAccountService;
import com.yokke.usermanagement.user_account_rule.UserAccountRule;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

import java.time.OffsetDateTime;
import java.time.ZoneOffset;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Service
@Transactional
@RequiredArgsConstructor
public class FdsTransactionsService extends BaseService {
    private final FdsTransactionsRepository fdsTransactionsRepository;
    private final FdsTransactionsMapper fdsTransactionsMapper;
    private final RuleService ruleService;
    private final FdsTransactionsAuditService fdsTransactionsAuditService;
    private final UserAccountService userAccountService;
    private final UserAccountRepository userAccountRepository;

//    public Page<FdsTransactionsDto> read(
//            OffsetDateTime startDate,
//            OffsetDateTime endDate,
//            String mid,
//            String tid,
//            String assignedTo,
//            String actionType,
//            Boolean actionTypeIsNull,
//            Boolean actionTypeIsNotNull,
//            Pageable pageable
//    ) {
//        if (startDate == null) {
//            startDate = OffsetDateTime.now(ZoneOffset.UTC).minusWeeks(1); // Set to one week ago if null
//        }
//
//        if (endDate == null) {
//            endDate = OffsetDateTime.now(ZoneOffset.UTC); // Set to current time if null
//        }
//        List<String> ruleIds = new ArrayList<>();
//
//        assignedTo = SecurityContextHolder.getContext().getAuthentication().getName();
//        UserAccount userAccount = userAccountRepository.findByUsername(assignedTo)
//                .orElseThrow(() -> new NotFoundException("User Account not found"));
//        for (UserAccountRule rule : userAccount.getUserAccountRule()) {
//            if (rule.getIsActive()) {
//                ruleIds.add(rule.getRule().getRuleId());
//            }
//        }
//        if (ruleIds.isEmpty()) {
//            ruleIds = null;
//        }
//        if (Objects.equals(assignedTo, "all")) {
//            if (Objects.equals(userAccount.getRole().getFirst().getRoleName(), "USER")) {
//                throw new ResponseStatusException(
//                        HttpStatus.FORBIDDEN,
//                        "You're not allowed to select all."
//                );
//            }
//            ruleIds = null;
//        }
//        Sort x = pageable.getSort();
//        Boolean isRiskLevelSort = false;
//        Boolean isAsc = false;
//        final Page<FdsTransactions> fdsTransactions;
//
//        for (Sort.Order order : x) {
//            if (order.getProperty().contains("risk")) {
//                pageable = PageRequest.of(pageable.getPageNumber(), pageable.getPageSize());
//                isRiskLevelSort = true;
//            }
//            if (order.getDirection().isAscending()) {
//
//                isAsc = true;
//            }
//        }
//
//        if (isRiskLevelSort && isAsc) {
//            fdsTransactions = fdsTransactionsRepository.findAllAsc(
//                    startDate.toLocalDateTime(),
//                    endDate.toLocalDateTime(),
//                    mid,
//                    tid,
//                    ruleIds,
//                    actionType,
//                    actionTypeIsNull,
//                    actionTypeIsNotNull,
//                    pageable
//            );
//        } else if (isRiskLevelSort && !isAsc) {
//            fdsTransactions = fdsTransactionsRepository.findAllDesc(
//                    startDate.toLocalDateTime(),
//                    endDate.toLocalDateTime(),
//                    mid,
//                    tid,
//                    ruleIds,
//                    actionType,
//                    actionTypeIsNull,
//                    actionTypeIsNotNull,
//                    pageable
//            );
//        } else {
//            fdsTransactions = fdsTransactionsRepository.findAll(
//                    startDate.toLocalDateTime(),
//                    endDate.toLocalDateTime(),
//                    mid,
//                    tid,
//                    ruleIds,
//                    actionType,
//                    actionTypeIsNull,
//                    actionTypeIsNotNull,
//                    pageable
//            );
//        }
//        return fdsTransactions.map(fdsTransaction -> {
//            List<RuleDto> rules = ruleService.readByFdsTransactionsId(fdsTransaction.getFdsTransactionId());
//            UserAccountDTO assignedUserAccount = null;
//            UserAccountDTO confirmedUserAccount = null;
//            if (fdsTransaction.getAssignedUserAccount() != null) {
//
//                assignedUserAccount = userAccountService.getAudit(fdsTransaction.getAssignedUserAccount().getUserAccountId());
//            }
//            if (fdsTransaction.getConfirmedUserAccount() != null) {
//                confirmedUserAccount = userAccountService.getAudit(fdsTransaction.getConfirmedUserAccount().getUserAccountId());
//            }
//            List<FdsTransactionsAuditDto> fdsTransactionsAuditDto = fdsTransactionsAuditService.readByFdsTransactionsId(fdsTransaction.getFdsTransactionId());
//            return fdsTransactionsMapper.mapToDto(fdsTransaction, rules, fdsTransactionsAuditDto, assignedUserAccount, confirmedUserAccount);
//        });
//    }

    public Page<FdsTransactionsDto> readV2(
            OffsetDateTime startDate,
            OffsetDateTime endDate,
            String mid,
            String tid,
            List<String> actionType,
            Boolean isConfirmed,
            List<String> ruleId,
//            Boolean isAll,
            Pageable pageable
    ) {
        if (ruleId != null) {
            if (ruleId.isEmpty()) {
                ruleId = null;
            }
        }
        if (actionType != null) {
            if (actionType.isEmpty()) {
                ruleId = null;
            }
        }
        if (startDate == null) {
            startDate = OffsetDateTime.now(ZoneOffset.UTC).minusWeeks(1); // Set to one week ago if null
        }

        if (endDate == null) {
            endDate = OffsetDateTime.now(ZoneOffset.UTC); // Set to current time if null
        }
        String assignedTo = SecurityContextHolder.getContext().getAuthentication().getName();
        UserAccount userAccount = userAccountRepository.findByUsername(assignedTo)
                .orElseThrow(() -> new NotFoundException("User Account not found"));

        if (ruleId != null) {
            if (ruleId.isEmpty()) {
                for (UserAccountRule rule : userAccount.getUserAccountRule()) {
                    if (rule.getIsActive()) {
                        ruleId.add(rule.getRule().getRuleId());
                    }
                }
                if (ruleId.isEmpty()) {
                    ruleId = null;
                }
            }

        }
        if (isConfirmed.equals(true)) {
            actionType = Arrays.asList("REMIND", "INVESTIGATION");
        } else if (isConfirmed.equals(false)) {
            actionType = Arrays.asList("NORMAL", "FRAUD", "WATCHLIST");
        }

        Boolean isRiskLevelSort = false;
        Boolean isAsc = false;
        final Page<FdsTransactions> fdsTransactions;

        for (Sort.Order order : pageable.getSort()) {
            if (order.getProperty().contains("risk")) {
                pageable = PageRequest.of(pageable.getPageNumber(), pageable.getPageSize());
                isRiskLevelSort = true;
            }
            if (order.getDirection().isAscending()) {
                isAsc = true;
            }
        }

        if (isRiskLevelSort && isAsc) {
            fdsTransactions = fdsTransactionsRepository.findAllAscV2(
                    startDate.toLocalDateTime(),
                    endDate.toLocalDateTime(),
                    mid,
                    tid,
                    actionType,
                    ruleId,
                    pageable
            );
        } else if (isRiskLevelSort && !isAsc) {
            fdsTransactions = fdsTransactionsRepository.findAllDescV2(
                    startDate.toLocalDateTime(),
                    endDate.toLocalDateTime(),
                    mid,
                    tid,
                    actionType,
                    ruleId,
                    pageable
            );
        } else {
            fdsTransactions = fdsTransactionsRepository.findAllV2(
                    startDate.toLocalDateTime(),
                    endDate.toLocalDateTime(),
                    mid,
                    tid,
                    actionType,
                    ruleId,
                    pageable
            );
        }
        return fdsTransactions.map(fdsTransaction -> {
            List<RuleDto> rules = ruleService.readByFdsTransactionsId(fdsTransaction.getFdsTransactionId());
            UserAccountDTO assignedUserAccount = null;
            UserAccountDTO confirmedUserAccount = null;
            if (fdsTransaction.getAssignedUserAccount() != null) {
                assignedUserAccount = userAccountService.getAudit(fdsTransaction.getAssignedUserAccount().getUserAccountId());
            }
            if (fdsTransaction.getConfirmedUserAccount() != null) {
                confirmedUserAccount = userAccountService.getAudit(fdsTransaction.getConfirmedUserAccount().getUserAccountId());
            }
            List<FdsTransactionsAuditDto> fdsTransactionsAuditDto = fdsTransactionsAuditService.readByFdsTransactionsId(fdsTransaction.getFdsTransactionId());
            return fdsTransactionsMapper.mapToDto(fdsTransaction, rules, fdsTransactionsAuditDto, assignedUserAccount, confirmedUserAccount);
        });
    }

    public FdsTransactionsDto readById(String fdsTransactionId) {
        FdsTransactions fdsTransaction = fdsTransactionsRepository.findById(fdsTransactionId).orElseThrow();
        List<RuleDto> rules = ruleService.readByFdsTransactionsId(fdsTransaction.getFdsTransactionId());
        List<FdsTransactionsAuditDto> fdsTransactionsAuditDto = fdsTransactionsAuditService.readByFdsTransactionsId(fdsTransaction.getFdsTransactionId());
        UserAccountDTO assignedUserAccount = null;
        UserAccountDTO confirmedUserAccount = null;
        if (fdsTransaction.getAssignedUserAccount() != null) {

            assignedUserAccount = userAccountService.getAudit(fdsTransaction.getAssignedUserAccount().getUserAccountId());
        }
        if (fdsTransaction.getConfirmedUserAccount() != null) {
            confirmedUserAccount = userAccountService.getAudit(fdsTransaction.getConfirmedUserAccount().getUserAccountId());
        }
//        reviewString(fdsTransactionId);
        return fdsTransactionsMapper.mapToDto(fdsTransaction, rules, fdsTransactionsAuditDto, assignedUserAccount, confirmedUserAccount);
    }

    public FdsTransactionsDto next(String fdsTransactionId) {
        UserAccount userAccount = userAccountRepository.findByUsername(
                SecurityContextHolder.getContext().getAuthentication().getName()
        ).orElseThrow(() -> new NotFoundException("User Account not found"));
        List<String> ruleIds = new ArrayList<>();
        for (UserAccountRule rule : userAccount.getUserAccountRule()) {
            if (rule.getIsActive()) {
                ruleIds.add(rule.getRule().getRuleId());
            }
        }
        FdsTransactions fdsTransaction = fdsTransactionsRepository.findFirst(
                fdsTransactionId,
                ruleIds
        ).orElseThrow(
                () -> new ResponseStatusException(
                        HttpStatus.NOT_FOUND,
                        "Theres no alert left to review."
                )
        );
        List<RuleDto> rules = ruleService.readByFdsTransactionsId(fdsTransaction.getFdsTransactionId());
        List<FdsTransactionsAuditDto> fdsTransactionsAuditDto = fdsTransactionsAuditService.readByFdsTransactionsId(fdsTransaction.getFdsTransactionId());
        UserAccountDTO assignedUserAccount = null;
        UserAccountDTO confirmedUserAccount = null;
        if (fdsTransaction.getAssignedUserAccount() != null) {

            assignedUserAccount = userAccountService.getAudit(fdsTransaction.getAssignedUserAccount().getUserAccountId());
        }
        if (fdsTransaction.getConfirmedUserAccount() != null) {
            confirmedUserAccount = userAccountService.getAudit(fdsTransaction.getConfirmedUserAccount().getUserAccountId());
        }
        return fdsTransactionsMapper.mapToDto(fdsTransaction, rules, fdsTransactionsAuditDto, assignedUserAccount, confirmedUserAccount);
    }

    public FdsTransactionsDto update(String fdsTransactionId, FdsTransactionsDto fdsTransactionsDto) {
        FdsTransactions fdsTransactions = fdsTransactionsRepository.findById(fdsTransactionId).orElseThrow();
        UserAccount userAccount = userAccountRepository.findByUsername(
                SecurityContextHolder.getContext().getAuthentication().getName()
        ).orElseThrow(() -> new NotFoundException("User Account not found"));
        if (fdsTransactionsDto.getRemindDate() != null) {
            fdsTransactionsDto.setRemindDate(fdsTransactionsDto.getRemindDate().plusHours(7));
        }
        fdsTransactions = fdsTransactionsMapper.updateV2(fdsTransactionsDto, fdsTransactions, userAccount);
        fdsTransactions = fdsTransactionsRepository.save(fdsTransactions);
        return fdsTransactionsMapper.mapToDto(fdsTransactions);
    }


    public List<FdsTransactionsDto> update(List<FdsTransactionsDto> fdsTransactionsDtoList) {
        List<FdsTransactionsDto> response = new ArrayList<>();
        for (FdsTransactionsDto fdsTransactionsDto : fdsTransactionsDtoList) {
            FdsTransactionsDto fdsTransactionsDtoUpdated = update(fdsTransactionsDto.getFdsTransactionId(), fdsTransactionsDto);
            response.add(fdsTransactionsDtoUpdated);
        }
        return response;
    }

}

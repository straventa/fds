package com.yokke.usermanagement.transactions.fds_transactions.review;

import com.yokke.base.exception.response_status.NotFoundException;
import com.yokke.usermanagement.transactions.fds_transactions.*;
import com.yokke.usermanagement.user_account.UserAccount;
import com.yokke.usermanagement.user_account.UserAccountRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Service
@Transactional
@RequiredArgsConstructor
public class FdsTransactionReviewService {
    private final FdsTransactionsRepository fdsTransactionsRepository;
    private final FdsTransactionsMapper fdsTransactionsMapper;
    private final UserAccountRepository userAccountRepository;
    private final FdsTransactionReviewRepository fdsTransactionReviewRepository;


    public FdsTransactionsDto review(String fdsTransactionId) {
        UserAccount userAccount = userAccountRepository.findByUsername(
                SecurityContextHolder.getContext().getAuthentication().getName()
        ).orElseThrow(() -> new NotFoundException("User Account not found"));
        FdsTransactions fdsTransaction = fdsTransactionsRepository.findById(
                fdsTransactionId
        ).orElseThrow();
        if (fdsTransaction.getReviewedUserAccount() != null) {
            throw new ResponseStatusException(
                    HttpStatus.FORBIDDEN,
                    "Current alert on review by " + fdsTransaction.getReviewedUserAccount().getUsername()
            );
        }
        fdsTransaction.setReviewedDateTime(LocalDateTime.now());
        fdsTransaction.setReviewedUserAccount(userAccount);
        fdsTransactionsRepository.save(fdsTransaction);
//        List<RuleDto> rules = ruleService.readByFdsTransactionsId(fdsTransaction.getFdsTransactionId());
//        List<FdsTransactionsAuditDto> fdsTransactionsAuditDto = fdsTransactionsAuditService.readByFdsTransactionsId(fdsTransaction.getFdsTransactionId());
//        UserAccountDTO assignedUserAccount = null;
//        UserAccountDTO confirmedUserAccount = null;
//        if (fdsTransaction.getAssignedUserAccount() != null) {
//
//            assignedUserAccount = userAccountService.get(fdsTransaction.getAssignedUserAccount().getUserAccountId());
//        }
//        if (fdsTransaction.getConfirmedUserAccount() != null) {
//            confirmedUserAccount = userAccountService.get(fdsTransaction.getConfirmedUserAccount().getUserAccountId());
//        }
        return fdsTransactionsMapper.mapToDto(fdsTransaction);
    }

    public String reviewString(String fdsTransactionId) {
        String userAccountId = SecurityContextHolder.getContext().getAuthentication().getName();
        Optional<FdsTransactionReview> fdsTransactionReview = fdsTransactionReviewRepository.findById(fdsTransactionId);
        if (fdsTransactionReview.isPresent()) {
            if (Objects.equals(fdsTransactionReview.get().getUserAccountId(), userAccountId)) {
                return "ok";
            } else {
                throw new ResponseStatusException(
                        HttpStatus.FORBIDDEN,
                        "Current alert on review by " + fdsTransactionReview.get().getUserAccountId()
                );
            }
        }

        fdsTransactionReviewRepository.save(
                new FdsTransactionReview(
                        fdsTransactionId,

                        userAccountId
                )
        );
        return "ok";
    }

    public List<FdsTransactionsDto> reviewStringBulk(List<FdsTransactionsDto> fdsTransactionId) {
        String userAccountId = SecurityContextHolder.getContext().getAuthentication().getName();
        List<FdsTransactionsDto> fdsTransactionsDtoResponse = new ArrayList<>();
        for (FdsTransactionsDto fdsTransactionsDto : fdsTransactionId) {
            Optional<FdsTransactionReview> fdsTransactionReview = fdsTransactionReviewRepository.findById(fdsTransactionsDto.getFdsTransactionId());
            if (fdsTransactionReview.isPresent()) {
                if (Objects.equals(fdsTransactionReview.get().getUserAccountId(), userAccountId)) {
                    fdsTransactionsDtoResponse.add(fdsTransactionsDto);
                } else {
                    throw new ResponseStatusException(
                            HttpStatus.FORBIDDEN,
                            "Current alert on review by " + fdsTransactionReview.get().getUserAccountId()
                    );
                }
            } else {
                fdsTransactionReviewRepository.save(
                        new FdsTransactionReview(
                                fdsTransactionsDto.getFdsTransactionId(),
                                userAccountId
                        )
                );
                fdsTransactionsDtoResponse.add(fdsTransactionsDto);
            }
        }
        return fdsTransactionsDtoResponse;
    }

    public FdsTransactionsDto cancelReview(String fdsTransactionId) {
        UserAccount userAccount = userAccountRepository.findByUsername(
                SecurityContextHolder.getContext().getAuthentication().getName()
        ).orElseThrow(() -> new NotFoundException("User Account not found"));
        FdsTransactions fdsTransaction = fdsTransactionsRepository.findById(
                fdsTransactionId
        ).orElseThrow();
        fdsTransaction.setReviewedDateTime(null);
        fdsTransaction.setReviewedUserAccount(null);
        fdsTransactionsRepository.save(fdsTransaction);
        return fdsTransactionsMapper.mapToDto(fdsTransaction);
    }

    public String cancelReviewString(String fdsTransactionId) {
        String userAccountId = SecurityContextHolder.getContext().getAuthentication().getName();
        Optional<FdsTransactionReview> fdsTransactionReview = fdsTransactionReviewRepository.findById(fdsTransactionId);
        fdsTransactionReviewRepository.deleteById(fdsTransactionId);
        return "ok";
    }

    public List<FdsTransactionsDto> cancelReviewStringBulk(List<FdsTransactionsDto> fdsTransactionsDtoList) {
        String userAccountId = SecurityContextHolder.getContext().getAuthentication().getName();
        for (FdsTransactionsDto fdsTransactionsDto : fdsTransactionsDtoList) {
            fdsTransactionReviewRepository.deleteById(fdsTransactionsDto.getFdsTransactionId());
        }
        return new ArrayList<>();
    }

    public String cancelReviewAll() {
        String username = SecurityContextHolder.getContext().getAuthentication().getName();
        UserAccount userAccount = userAccountRepository.findByUsername(username)
                .orElseThrow(
                        () -> new ResponseStatusException(
                                HttpStatus.NOT_FOUND,
                                "User account with username " + username + " not found."
                        )
                );
        List<FdsTransactionReview> fdsTransactionReview = fdsTransactionReviewRepository.findByUserAccountId(username);
        fdsTransactionReviewRepository.deleteAll(fdsTransactionReview);
        return "ok";
    }

}

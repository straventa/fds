package com.yokke.usermanagement.transactions.fds_transactions_audit;

import com.yokke.usermanagement.transactions.fds_transactions.FdsTransactionsMapper;
import com.yokke.usermanagement.user_account.UserAccountDTO;
import com.yokke.usermanagement.user_account.UserAccountService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
@RequiredArgsConstructor
public class FdsTransactionsAuditService {
    private  final FdsTransactionsAuditRepository fdsTransactionsAuditRepository;
    private final FdsTransactionsAuditMapper fdsTransactionsAuditMapper;
    private final UserAccountService userAccountService;

    public List<FdsTransactionsAuditDto> readByFdsTransactionsId(
          String fdsTransactionsId
  ){
      return fdsTransactionsAuditRepository.findByFdsTransactions_FdsTransactionId(fdsTransactionsId)
              .stream()
              .map(fdsTransactionsAudit -> {
                  UserAccountDTO userAccountDTO = userAccountService.get(fdsTransactionsAudit.getUserAccount().getUserAccountId());
                    return fdsTransactionsAuditMapper.mapToDto(
                            fdsTransactionsAudit,
                            new FdsTransactionsAuditDto(),
                            userAccountDTO
                    );
              })
              .toList();
  }
}

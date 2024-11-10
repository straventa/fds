package com.yokke.usermanagement.transactions.fds_transactions_audit;


import com.yokke.base.mapper.BaseMapper;
import com.yokke.usermanagement.rule.Rule;
import com.yokke.usermanagement.user_account.UserAccount;
import com.yokke.usermanagement.user_account.UserAccountDTO;
import org.springframework.stereotype.Service;

@Service
public class FdsTransactionsAuditMapper extends BaseMapper {
    public FdsTransactionsAuditDto mapToDto(FdsTransactionsAudit fdsTransactionsAudit, FdsTransactionsAuditDto fdsTransactionsAuditDto) {
        fdsTransactionsAuditDto.setFdsTransactionsAuditId(fdsTransactionsAudit.getFdsTransactionsAuditId());
        fdsTransactionsAuditDto.setActivity(fdsTransactionsAudit.getActivity());
        fdsTransactionsAuditDto.setActivityNotes(fdsTransactionsAudit.getActivityNotes());
//        fdsTransactionsAuditDto.setUserAccount(fdsTransactionsAudit.getUserAccount());
        fdsTransactionsAuditDto.setCreatedDateTime(fdsTransactionsAudit.getCreatedDateTime());
        return fdsTransactionsAuditDto;
    }
    public FdsTransactionsAuditDto mapToDto(
            FdsTransactionsAudit fdsTransactionsAudit,
            FdsTransactionsAuditDto fdsTransactionsAuditDto,
            UserAccountDTO userAccount
    ) {
        fdsTransactionsAuditDto.setFdsTransactionsAuditId(fdsTransactionsAudit.getFdsTransactionsAuditId());
        fdsTransactionsAuditDto.setActivity(fdsTransactionsAudit.getActivity());
        fdsTransactionsAuditDto.setActivityNotes(fdsTransactionsAudit.getActivityNotes());
        fdsTransactionsAuditDto.setUserAccount(userAccount);
        fdsTransactionsAuditDto.setCreatedDateTime(fdsTransactionsAudit.getCreatedDateTime());
        return fdsTransactionsAuditDto;
    }



    public FdsTransactionsAudit mapToEntity(FdsTransactionsAuditDto fdsTransactionsAuditDto, FdsTransactionsAudit fdsTransactionsAudit) {
        fdsTransactionsAudit.setActivity(fdsTransactionsAuditDto.getActivity());
        fdsTransactionsAudit.setActivityNotes(fdsTransactionsAuditDto.getActivityNotes());
//        fdsTransactionsAudit.setUserAccount(fdsTransactionsAuditDto.getUserAccount());
        fdsTransactionsAudit.setCreatedDateTime(fdsTransactionsAuditDto.getCreatedDateTime());
        return fdsTransactionsAudit;
    }
    public FdsTransactionsAudit mapToEntity(
            FdsTransactionsAuditDto fdsTransactionsAuditDto,
            FdsTransactionsAudit fdsTransactionsAudit,
            UserAccount userAccount
    ) {
        fdsTransactionsAudit.setActivity(fdsTransactionsAuditDto.getActivity());
        fdsTransactionsAudit.setActivityNotes(fdsTransactionsAuditDto.getActivityNotes());
        fdsTransactionsAudit.setCreatedDateTime(fdsTransactionsAuditDto.getCreatedDateTime());
        fdsTransactionsAudit.setUserAccount(userAccount);
        return fdsTransactionsAudit;
    }


}

package com.yokke.usermanagement.user_account;

import com.yokke.base.exception.response_status.NotFoundException;
import com.yokke.base.mapper.BaseMapper;
import com.yokke.usermanagement.role.Role;
import com.yokke.usermanagement.role.RoleDTO;
import com.yokke.usermanagement.role.RoleRepository;
import com.yokke.usermanagement.rule.Rule;
import com.yokke.usermanagement.rule.RuleMapper;
import com.yokke.usermanagement.rule.RuleRepository;
import com.yokke.usermanagement.user_account_rule.UserAccountRule;
import com.yokke.usermanagement.user_account_rule.UserAccountRuleDto;
import com.yokke.usermanagement.user_account_rule.UserAccountRuleMapper;
import com.yokke.usermanagement.user_account_rule.UserAccountRuleRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class UserAccountMapper extends BaseMapper {
    private final RuleMapper ruleMapper;
    private final RuleRepository ruleRepository;
    private final RoleRepository roleRepository;
    private final UserAccountRuleMapper userAccountRuleMapper;
    private final UserAccountRepository userAccountRepository;
    private final UserAccountRuleRepository userAccountRuleRepository;

    public UserAccountDTO mapToDTO(final UserAccount userAccount) {

        UserAccountDTO userAccountDTO = new UserAccountDTO();
        userAccountDTO = mapAudit(userAccountDTO, userAccount);
        userAccountDTO.setUserAccountId(userAccount.getUserAccountId());
        userAccountDTO.setUsername(userAccount.getUsername());
        userAccountDTO.setEmail(userAccount.getEmail());
        userAccountDTO.setPasswordDateChanged(userAccount.getPasswordDateChanged());
        userAccountDTO.setIsLocked(userAccount.getLoginAttempt() == 0);
        userAccountDTO.setIsPasswordSetup(!(userAccount.getPassword() == null));
        return userAccountDTO;
    }

    public UserAccountDTO mapToDTO(UserAccount userAccount,
                                   UserAccountDTO userAccountDTO) {

        userAccountDTO = mapAudit(userAccount, userAccountDTO);
        userAccountDTO.setUserAccountId(userAccount.getUserAccountId());
        userAccountDTO.setUsername(userAccount.getUsername());
        userAccountDTO.setEmail(userAccount.getEmail());
        userAccountDTO.setPasswordDateChanged(userAccount.getPasswordDateChanged());
        userAccountDTO.setIsLocked(userAccount.getLoginAttempt() == 0);
        userAccountDTO.setIsPasswordSetup(!(userAccount.getPassword() == null));

        return userAccountDTO;
    }

    public UserAccountDTO mapToDTO(UserAccount userAccount,
                                   UserAccountDTO userAccountDTO,
                                   List<RoleDTO> role) {
        userAccountDTO = mapAudit(userAccount, userAccountDTO);
        userAccountDTO.setUserAccountId(userAccount.getUserAccountId());
        userAccountDTO.setUsername(userAccount.getUsername());
        userAccountDTO.setEmail(userAccount.getEmail());
        userAccountDTO.setPasswordDateChanged(userAccount.getPasswordDateChanged());
        userAccountDTO.setIsLocked(userAccount.getLoginAttempt() == 0);
        userAccountDTO.setIsPasswordSetup(!(userAccount.getPassword() == null));

        userAccountDTO.setRole(role);
        return userAccountDTO;
    }

    public UserAccountDTO mapToDTO(UserAccount userAccount,
                                   UserAccountDTO userAccountDTO,
                                   List<RoleDTO> role,
                                   List<UserAccountRuleDto> userAccountRule) {
        userAccountDTO = mapAudit(userAccount, userAccountDTO);
        userAccountDTO.setUserAccountId(userAccount.getUserAccountId());
        userAccountDTO.setUsername(userAccount.getUsername());
        userAccountDTO.setEmail(userAccount.getEmail());
        userAccountDTO.setPasswordDateChanged(userAccount.getPasswordDateChanged());
        userAccountDTO.setIsLocked(userAccount.getLoginAttempt() == 0);
        userAccountDTO.setIsPasswordSetup(!(userAccount.getPassword() == null));

        userAccountDTO.setRole(role);
        userAccountDTO.setUserAccountRule(userAccountRule);
        return userAccountDTO;
    }

    public UserAccount mapToEntity(final UserAccountDTO userAccountDTO,
                                   final UserAccount userAccount) {
        userAccount.setUsername(userAccountDTO.getUsername());
        userAccount.setEmail(userAccountDTO.getEmail());
        List<Role> roles = new ArrayList<>();
        for (RoleDTO roleDTO : userAccountDTO.getRole()) {
            Role role = roleRepository.findByRoleId(roleDTO.getRoleId())
                    .orElseThrow(() -> new NotFoundException("Role not found"));
            roles.add(role);
        }
        userAccount.setRole(roles);
        List<UserAccountRule> userAccountRules = new ArrayList<>();
        for (UserAccountRuleDto userAccountRuleDto : userAccountDTO.getUserAccountRule()) {
            Rule rule = ruleRepository.findByRuleId(userAccountRuleDto.getRule().getRuleId())
                    .orElseThrow(() -> new NotFoundException("Role not found"));
            UserAccountRule userAccountRule = userAccountRuleMapper.mapToEntity(
                    userAccountRuleDto,
                    new UserAccountRule(),
                    rule
            );
            userAccountRules.add(userAccountRule);
        }
        userAccount.setUserAccountRule(userAccountRules);
        return userAccount;
    }

}

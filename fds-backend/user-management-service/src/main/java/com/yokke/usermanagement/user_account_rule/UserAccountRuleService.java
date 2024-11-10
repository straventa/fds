package com.yokke.usermanagement.user_account_rule;

import com.yokke.base.exception.response_status.NotFoundException;
import com.yokke.base.service.BaseService;
import com.yokke.usermanagement.rule.Rule;
import com.yokke.usermanagement.rule.RuleDto;
import com.yokke.usermanagement.rule.RuleMapper;
import com.yokke.usermanagement.rule.RuleRepository;
import com.yokke.usermanagement.user_account.UserAccount;
import com.yokke.usermanagement.user_account.UserAccountDTO;
import com.yokke.usermanagement.user_account.UserAccountMapper;
import com.yokke.usermanagement.user_account.UserAccountRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Service
@Transactional
@RequiredArgsConstructor
public class UserAccountRuleService extends BaseService {
    private final UserAccountRuleRepository userAccountRuleRepository;
    private final UserAccountRuleMapper userAccountRuleMapper;
    private final RuleRepository ruleRepository;
    private final RuleMapper ruleMapper;
    private final UserAccountMapper userAccountMapper;
    private final UserAccountRepository userAccountRepository;

    @Cacheable(value = "userAccounts", key = "#userAccountId + '-rule'")
    public List<UserAccountRuleDto> readByUserAccountId(final String userAccountId) {
        return userAccountRuleRepository.findByUserAccount_UserAccountId(userAccountId).stream()
                .map(userAccountRule -> {
                    Rule rule = ruleRepository.findByUserAccountRule_UserRuleId(userAccountRule.getUserRuleId())
                            .orElseThrow(() -> new NotFoundException("Rule of user account id " + userAccountId + " not found."));
                    return userAccountRuleMapper.mapToDto(
                            userAccountRule,
                            new UserAccountRuleDto(),
                            ruleMapper.mapToDto(rule, new RuleDto())
                    );
                })
                .toList();
    }

    public List<UserAccountRuleDto> readByRuleId(final String ruleId) {
        return userAccountRuleRepository.findByRule_RuleId(ruleId).stream()
                .map(userAccountRule -> {
//                    Rule rule = ruleRepository.findByUserAccountRule_UserRuleId(userAccountRule.getUserRuleId())
//                            .orElseThrow(() -> new NotFoundException("Rule of rule id " + ruleId.toString() + " not found."));
                    UserAccount userAccount = userAccountRepository.findByUserAccountRule_UserRuleId(userAccountRule.getUserRuleId())
                            .orElseThrow(() -> new NotFoundException("User account of rule id " + ruleId + " not found."));
                    return userAccountRuleMapper.mapToDto(
                            userAccountRule,
                            new UserAccountRuleDto(),
                            userAccountMapper.mapToDTO(userAccount, new UserAccountDTO())
                    );
                })
                .toList();
    }

    public List<UserAccountRuleDto> findAll() {
        List<RuleDto> rule = ruleRepository.findAll(

                        //Sort by risk level number
                        Sort.by(Sort.Order.asc("riskLevelNumber"))
                ).stream()
                .map(rule1 -> {
                    return ruleMapper.mapToDto(
                            rule1,
                            new RuleDto()
                    );
                })
                .toList();
        //sort rule by risk level
        List<UserAccountRuleDto> userAccountRule = new ArrayList<>();
        Integer cnt = 0;
        for (RuleDto rule1 : rule) {
            UserAccountRuleDto userAccountRuleDto = new UserAccountRuleDto();
            userAccountRuleDto.setUserRuleId(UUID.randomUUID().toString());
            userAccountRuleDto.setPriority(cnt);
            userAccountRuleDto.setRule(rule1);
            userAccountRuleDto.setIsActive(false);
            userAccountRule.add(userAccountRuleDto);
        }
        return userAccountRule;
    }
}

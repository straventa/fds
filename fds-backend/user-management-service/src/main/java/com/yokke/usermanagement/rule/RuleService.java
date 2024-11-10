package com.yokke.usermanagement.rule;

import com.yokke.base.exception.response_status.NotFoundException;
import com.yokke.base.service.BaseService;
import com.yokke.usermanagement.user_account_rule.UserAccountRule;
import com.yokke.usermanagement.user_account_rule.UserAccountRuleDto;
import com.yokke.usermanagement.user_account_rule.UserAccountRuleRepository;
import com.yokke.usermanagement.user_account_rule.UserAccountRuleService;
import lombok.RequiredArgsConstructor;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.OffsetDateTime;
import java.util.ArrayList;
import java.util.List;

@Service
@Transactional
@RequiredArgsConstructor
public class RuleService extends BaseService {
    private final RuleRepository ruleRepository;
    private final RuleMapper ruleMapper;
    private final UserAccountRuleService userAccountRuleService;
    private final UserAccountRuleRepository userAccountRuleRepository;

    public Page<RuleDto> read(
            String riskLevel,
            String ruleDescription,
            String ruleName,
            String sourceData,
            List<String> userAccountId,
            Pageable pageable

    ) {
        final Page<Rule> rules = ruleRepository.findAll(
                riskLevel,
                ruleDescription,
                ruleName,
                sourceData,
                userAccountId,
                pageable
        );
//        final Page<Rule> rules = ruleRepository.findAll(
//                pageable
//        );
        return rules.map(rule -> ruleMapper.mapToDto(
                rule, new RuleDto(),
                userAccountRuleService.readByRuleId(rule.getRuleId())
        ));
    }
    
    public List<RuleDto> readByFdsTransactionsId(String fdsTransactionId) {
        return ruleRepository.findByFdsTransactions_FdsTransactionId(fdsTransactionId)
                .stream()
                .map(rule -> ruleMapper.mapToDto(
                        rule,
                        new RuleDto(),
                        null
                ))
                .toList();
    }

    public RuleDto read(String ruleId) {
        return ruleRepository.findByRuleId(ruleId)
                .map(rule -> ruleMapper.mapToDto(
                        rule,
                        new RuleDto(),
                        userAccountRuleService.readByRuleId(rule.getRuleId())
                ))
                .orElseThrow(() -> new NotFoundException("OKK"));
    }

    public RuleStatisticsDto read(String ruleId, OffsetDateTime startDate, OffsetDateTime endDate) {
        Object[][] obj = ruleRepository.countByRuleId(ruleId, startDate.toLocalDateTime(), endDate.toLocalDateTime());
        if (obj.length == 0) {
            return RuleStatisticsDto.builder()
                    .alertTotal(BigDecimal.ZERO)
                    .alertWaiting(BigDecimal.ZERO)
                    .alertReviewed(BigDecimal.ZERO)
                    .alertPercentage("0%")
                    .build();
        }
        BigDecimal alertTotal = obj[0][0] == null ? BigDecimal.ZERO : (BigDecimal) obj[0][1];
        BigDecimal alertWaiting = obj[0][1] == null ? BigDecimal.ZERO : (BigDecimal) obj[0][1];
        BigDecimal alertReviewed = obj[0][2] == null ? BigDecimal.ZERO : (BigDecimal) obj[0][2];
        return RuleStatisticsDto.builder()
                .alertTotal(alertTotal)
                .alertWaiting(alertWaiting)
                .alertReviewed(alertReviewed)
                .build();
//        return RuleStatisticsDto.builder()
//                .alertTotal(50)
//                .alertReviewed(12)
//                .alertWaiting(38)
//                .alertPercentage("24%")
//                .build();
    }

    public Page<RuleDto> read(String userAccountId, Pageable pageable) {
        return ruleRepository.findByUserAccountRule_UserAccount_UserAccountId(userAccountId, pageable)
                .map(rule -> ruleMapper.mapToDto(
                        rule,
                        new RuleDto(),
                        userAccountRuleService.readByRuleId(rule.getRuleId())
                ));

    }


    @CacheEvict(value = "userAccounts", allEntries = true)
    @Transactional
    public String create(RuleDto ruleDto) {
        final Rule rule = new Rule();
        ruleMapper.mapToEntity(ruleDto, rule);
        return ruleRepository.save(rule).getRuleId();
    }

    @CacheEvict(value = "userAccounts", allEntries = true)
    @Transactional
    public void update(String ruleId, RuleDto ruleDto) {
        final Rule rule = ruleRepository.findRuleByRuleId(ruleId)
                .orElseThrow(() -> new NotFoundException("OKK"));
        ruleMapper.mapToEntity(ruleDto, rule);
        ruleRepository.save(rule);
        List<UserAccountRule> userAccountRulesList = new ArrayList<>();
//        for (UserAccountRule userAccountRule : userAccount.getUserAccountRule()) {
//            userAccountRule.setUserAccount(userAccount);
//            userAccountRulesList.add(userAccountRule);
//        }
        for (UserAccountRuleDto userAccountRuleDto : ruleDto.getUserAccountRule()) {
            UserAccountRule userAccountRule = userAccountRuleRepository.findByUserRuleId(userAccountRuleDto.getUserRuleId()).orElseThrow(() -> new NotFoundException("OKK"));
            userAccountRule.setPriority(userAccountRuleDto.getPriority());
            userAccountRule.setIsActive(userAccountRuleDto.getIsActive());
            userAccountRulesList.add(userAccountRule);
        }
        userAccountRuleRepository.saveAll(userAccountRulesList);

    }

    @CacheEvict(value = "userAccounts", allEntries = true)
    @Transactional
    public void delete(String ruleId) {
        ruleRepository.deleteById(ruleId);
    }

}

package com.yokke.usermanagement.rule;

import com.yokke.base.mapper.BaseMapper;
import com.yokke.usermanagement.user_account_rule.UserAccountRuleDto;
import org.springframework.stereotype.Service;

import java.util.Comparator;
import java.util.List;


@Service
public class RuleMapper extends BaseMapper {

        public RuleDto mapToDto(Rule rule,  RuleDto ruleDTO) {
            ruleDTO = mapAudit(ruleDTO, rule);
            ruleDTO.setRuleId(rule.getRuleId());
            ruleDTO.setRuleName(rule.getRuleName());
            ruleDTO.setRuleDescription(rule.getRuleDescription());
            ruleDTO.setSourceData(rule.getSourceData());
            ruleDTO.setRiskLevel(rule.getRiskLevel());
            ruleDTO.setRiskLevelNumber(rule.getRiskLevelNumber());
            return ruleDTO;
        }
    public RuleDto mapToDto(Rule rule, RuleDto ruleDTO, List<UserAccountRuleDto> userAccountRule) {
        ruleDTO = mapAudit(ruleDTO, rule);
        ruleDTO.setRuleId(rule.getRuleId());
        ruleDTO.setRuleName(rule.getRuleName());
        ruleDTO.setRuleDescription(rule.getRuleDescription());
        ruleDTO.setSourceData(rule.getSourceData());
        ruleDTO.setRiskLevel(rule.getRiskLevel());
        ruleDTO.setRiskLevelNumber(rule.getRiskLevelNumber());
        ruleDTO.setUserAccountRule(userAccountRule);
        return ruleDTO;
    }

        public Rule mapToEntity(RuleDto ruleDTO,  Rule rule) {
            rule.setRuleName(ruleDTO.getRuleName());
            rule.setRuleDescription(ruleDTO.getRuleDescription());
            rule.setSourceData(ruleDTO.getSourceData());
            rule.setRiskLevel(ruleDTO.getRiskLevel());
            rule.setRiskLevelNumber(ruleDTO.getRiskLevelNumber());
            return rule;
        }
}

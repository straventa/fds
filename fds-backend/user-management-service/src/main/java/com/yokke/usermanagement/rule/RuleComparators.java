package com.yokke.usermanagement.rule;

import com.yokke.usermanagement.user_account_rule.UserAccountRuleDto;

import java.util.Comparator;
import java.util.Map;

public class RuleComparators {
    private static final Map<String, Integer> RISK_LEVEL_ORDER = Map.of(
            "High Risk", 3,
            "Medium Risk", 2,
            "Low Risk", 1
    );

//    public static final Comparator<Rule> BY_RISK_LEVEL = Comparator
//            .comparingInt((Rule r) -> RISK_LEVEL_ORDER.getOrDefault(r.getRiskLevel().trim(), 0))
//            .reversed();
    public static final Comparator<RuleDto> BY_RISK_LEVEL = Comparator
            .comparingInt((RuleDto r) -> RISK_LEVEL_ORDER.getOrDefault(r.getRiskLevel().trim(), 0))
            .reversed();
//    public static final Comparator<Rule> BY_RISK_LEVEL_THEN_NAME = BY_RISK_LEVEL
//            .thenComparing(Rule::getRuleName);

}
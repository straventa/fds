package com.yokke.usermanagement.user_account_rule;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface UserAccountRuleRepository extends JpaRepository<UserAccountRule, String> {
    @Query("select u from UserAccountRule u where u.userAccount.userAccountId = ?1")
    List<UserAccountRule> findByUserAccount_UserAccountId(String userAccountId);

    @Query("select u from UserAccountRule u where u.rule.ruleId = ?1")
    List<UserAccountRule> findByRule_RuleId(String ruleId);

    @Query("select u from UserAccountRule u where u.userRuleId = :userRuleId")
    Optional<UserAccountRule> findByUserRuleId(@Param("userRuleId") String userRuleId);
}
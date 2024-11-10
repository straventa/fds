package com.yokke.usermanagement.user_account;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Collection;
import java.util.List;
import java.util.Optional;

public interface UserAccountRepository extends JpaRepository<UserAccount, Integer> {


    @Query("select u from UserAccount u where u.email = :email  and u.deletedDate is null")
    Optional<UserAccount> findByEmail(@Param("email") String email);

    @Query("select u from UserAccount u where u.username = ?1 and u.deletedDate is null")
    Optional<UserAccount> findByUsername(String username);


//    Optional<UserAccount> findByUsername(String username);

    @Query("select (count(u) > 0) from UserAccount u where upper(u.username) = upper(?1) and u.deletedDate is null")
    boolean existsByUsernameIgnoreCase(String username);

    @Query("select (count(u) > 0) from UserAccount u where upper(u.email) = upper(?1) and u.deletedDate is null")
    boolean existsByEmailIgnoreCase(String email);

    @Query("select u from UserAccount u where upper(u.username) = upper(?1) and u.deletedDate is null")
    Optional<UserAccount> findByUsernameIgnoreCase(String username);


//    boolean existsByEmailIgnoreCase(String email);

//    boolean existsByUsernameIgnoreCase(String username);


//    Optional<UserAccount> findByUsernameIgnoreCase(String username);

    @Query("select u from UserAccount u where u.userAccountId = :userAccountId  and u.deletedDate is null ")
    Optional<UserAccount> findByUserAccountId(@Param("userAccountId") String userAccountId);

    @Query("select u from UserAccount u where u.userAccountId = :userAccountId")
    Optional<UserAccount> findByUserAccountIdAudit(@Param("userAccountId") String userAccountId);

    @Query("select u from UserAccount u where u.userAccountId = ?1  and u.deletedDate is null ")
    Optional<UserAccount> findUserAccountByUserAccountId(String userAccountId);

    @Query("""
            select distinct u from UserAccount u left join u.role role left join u.userAccountRule userAccountRule
            where (:username is null or u.username like concat('%', :username, '%'))
            and (:email is null or u.email like concat('%', :email, '%'))
            and (:roleName is null or role.roleName like concat('%', :roleName, '%'))
            and (:ruleName is null or userAccountRule.rule.ruleName like concat('%', :ruleName, '%'))
            and u.deletedDate is null 
            """)
    Page<UserAccount> findAll(
            @Param("username") String username,
            @Param("email") String email,
            @Param("roleName") String roleName,
            @Param("ruleName") String ruleName,
            Pageable pageable
    );

    @Query("""
            select u from UserAccount u inner join u.userAccountRule userAccountRule
            where userAccountRule.userRuleId = :userRuleId""")
    Optional<UserAccount> findByUserAccountRule_UserRuleId(@Param("userRuleId") String userRuleId);

    @Query("""
            select u from UserAccount u inner join u.userAccountRule userAccountRule
            where userAccountRule.rule.ruleId = :ruleId""")
    List<UserAccount> findByUserAccountRule_Rule_RuleId(@Param("ruleId") String ruleId);

    @Query("""
            select u from UserAccount u left join u.userAccountRule userAccountRule
            where userAccountRule.rule.ruleId in :ruleIds""")
    List<UserAccount> findByUserAccountRule_Rule_RuleIdIn(@Param("ruleIds") Collection<String> ruleIds);
}
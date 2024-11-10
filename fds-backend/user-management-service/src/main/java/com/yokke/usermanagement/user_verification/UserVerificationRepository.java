package com.yokke.usermanagement.user_verification;

import com.yokke.usermanagement.user_account.UserAccount;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.OffsetDateTime;
import java.util.Collection;
import java.util.List;
import java.util.Optional;


public interface UserVerificationRepository extends JpaRepository<UserVerification, Integer> {

    UserVerification findFirstByUserAccount(UserAccount userAccount);

    Optional<UserVerification> findByVerificationCode(String verificationCode);

    @Query("""
            select u from UserVerification u
            where u.userAccount.userAccountId = ?1 and u.verificationType = ?2 and u.verificationStatus = ?3""")
    Optional<UserVerification> findByUserAccount_UserAccountIdAndVerificationTypeAndIsVerified(String userAccountId, String verificationType, String isVerified);

    @Query("""
            select u from UserVerification u
            where u.verificationCode = ?1 and u.verificationType = ?2 and u.verificationExpiry = ?3""")
    UserVerification findByVerificationCodeAndVerificationTypeAndVerificationExpiry(String verificationCode, String verificationType, OffsetDateTime verificationExpiry);

    @Query("select u from UserVerification u where u.verificationCode = ?1 and u.verificationType = ?2")
    Optional<UserVerification> findByVerificationCodeAndVerificationType(String verificationCode, String verificationType);

    @Query("select count(u) from UserVerification u where u.verificationType = ?1 and u.createdDate > ?2 and  u.userAccount.email = ?3")
    long countByVerificationTypeAndCreatedDate(String verificationType, OffsetDateTime createdDate, String email);

    @Query("""
            select u from UserVerification u
            where u.password is not null 
            and u.verificationType in :verificationTypes 
            and u.isVerified = :isVerified 
            and u.verificationStatus = :verificationStatus
            order by u.createdDate DESC""")
    List<UserVerification> findFirst4Password(
            @Param("verificationTypes") Collection<String> verificationTypes,
            @Param("isVerified") Boolean isVerified,
            @Param("verificationStatus") String verificationStatus,
            Pageable pageable);
}

package com.yokke.usermanagement.user_verification;

import com.yokke.base.model.BaseModel;
import com.yokke.usermanagement.user_account.UserAccount;
import jakarta.persistence.*;
import lombok.*;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.OffsetDateTime;


@Table(name = "FDS_USER_VERIFICATION")
@EntityListeners(AuditingEntityListener.class)
@Entity
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UserVerification extends BaseModel {

    @Id
    @Column(name = "USER_VERIFICATION_ID", nullable = false, updatable = false)
    @GeneratedValue(strategy = GenerationType.UUID)
    private String userVerificationId;

    @Column(length = 50, name = "VERIFICATION_TYPE")
    private String verificationType;

    @Column(length = 100, name = "VERIFICATION_CODE")
    private String verificationCode;

    @Column(length = 50, name = "VERIFICATION_STATUS")
    private String verificationStatus;

    @Column(name = "VERIFICATION_EXPIRY")
    private OffsetDateTime verificationExpiry;

    @Column(name = "IS_VERIFIED")
    private Boolean isVerified;

    @Column(name = "PASSWORD")
    private String password;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_account_id")
    private UserAccount userAccount;


}

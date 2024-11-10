package com.yokke.usermanagement.user_account;

import com.yokke.base.model.BaseModel;
import com.yokke.usermanagement.role.Role;
import com.yokke.usermanagement.user_account_rule.UserAccountRule;
import com.yokke.usermanagement.user_verification.UserVerification;
import jakarta.persistence.*;
import lombok.*;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.time.LocalDateTime;
import java.util.Collection;
import java.util.List;


@Table(name = "FDS_USER_ACCOUNT")
@EntityListeners(AuditingEntityListener.class)
@Entity
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UserAccount extends BaseModel implements UserDetails {

    @Id
    @Column(name = "USER_ACCOUNT_ID", nullable = false, updatable = false)
    @GeneratedValue(strategy = GenerationType.UUID)
    private String userAccountId;

    @Column(length = 50, name = "USERNAME")
    private String username;

    @Column(length = 100, name = "EMAIL")
    private String email;

    @Column(length = 100, name = "PASSWORD")
    private String password;

    @Column(length = 200, name = "ACTIVE_TOKEN")
    private String activeToken;

    @Column(name = "PASSWORD_DATE_CHANGED")
    private LocalDateTime passwordDateChanged;

    @Column(length = 1000, name = "PASSWORD_HISTORY")
    private String passwordHistory;

    @Column(name = "LOGIN_ATTEMPT")
    private Integer loginAttempt;

    @Column(name = "TOKEN", length = 1000)
    private String token;


    @OneToMany(mappedBy = "userAccount", fetch = FetchType.LAZY)
    private List<UserVerification> userAccountUserVerifications;

    @ManyToMany
    @JoinTable(
            name = "FDS_USER_ROLE",
            joinColumns = @JoinColumn(name = "USER_ACCOUNT_ID"),
            inverseJoinColumns = @JoinColumn(name = "ROLE_ID")
    )
    private List<Role> role;

    @OneToMany(mappedBy = "userAccount", fetch = FetchType.LAZY)
    private List<UserAccountRule> userAccountRule;


    @Column(name = "IS_DELETED")
    private Boolean isDeleted;

    @Column(name = "DELETED_BY")
    private String deletedBy;

    @Column(name = "DELETED_DATE")
    private LocalDateTime deletedDate;


    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return null;
    }

    /**
     * @return
     */
    @Override
    public boolean isAccountNonExpired() {
        return false;
    }

    /**
     * @return
     */
    @Override
    public boolean isAccountNonLocked() {
        return false;
    }

    /**
     * @return
     */
    @Override
    public boolean isCredentialsNonExpired() {
        return false;
    }

    /**
     * @return
     */
    @Override
    public boolean isEnabled() {
        return false;
    }
}

package com.yokke.usermanagement.role;

import com.yokke.usermanagement.privilege.Privilege;
import com.yokke.usermanagement.user_account.UserAccount;
import com.yokke.base.model.BaseModel;
import jakarta.persistence.*;
import lombok.*;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.util.List;


@Table(name="FDS_ROLE")
@EntityListeners(AuditingEntityListener.class)
@Entity
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Role extends BaseModel {

    @Id
    @Column(name = "ROLE_ID", nullable = false, updatable = false)
    @GeneratedValue(strategy = GenerationType.UUID)
    private String roleId;

    @Column( length = 50,name = "ROLE_NAME")
    private String roleName;

    @Column( length = 50,name="ROLE_CODE")
    private String roleCode;

    @Column( name="ROLE_DESCRIPTION")
    private String roleDescription;

    @ManyToMany(mappedBy = "role")
    private List<UserAccount> userAccount;

    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(
            name = "FDS_ROLE_PRIVILEGE",
            joinColumns = @JoinColumn(name = "ROLE_ID"),
            inverseJoinColumns = @JoinColumn(name = "PRIVILEGE_ID")
    )
    private List<Privilege> privilege;

}

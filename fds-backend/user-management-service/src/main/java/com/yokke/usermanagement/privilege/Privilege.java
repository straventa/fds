package com.yokke.usermanagement.privilege;

import com.yokke.usermanagement.role.Role;
import com.yokke.usermanagement.user_account.UserAccount;
import com.yokke.base.model.BaseModel;
import jakarta.persistence.*;
import lombok.*;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.util.List;


@Table(name = "FDS_PRIVILEGE")
@EntityListeners(AuditingEntityListener.class)
@Entity
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Privilege extends BaseModel {

    @Id
    @Column(name = "PRIVILEGE_ID", nullable = false, updatable = false)
    @GeneratedValue(strategy = GenerationType.UUID)
    private String privilegeId;

    @Column(name="PRIVILEGE_NAME",length = 50)
    private String privilegeName;

    @Column(name = "PRIVILEGE_CODE", length = 50)
    private String privilegeCode;

    @Column(name = "PRIVILEGE_DESCRIPTION")
    private String privilegeDescription;

    @ManyToMany(mappedBy = "privilege")
    private List<Role> role;


}

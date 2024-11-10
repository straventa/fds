package com.yokke.usermanagement.role;

import com.yokke.usermanagement.privilege.Privilege;
import com.yokke.usermanagement.user_account.UserAccount;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;


public interface RoleRepository extends JpaRepository<Role, Integer> {
    Role findFirstByUserAccount(UserAccount userAccount);

    Role findFirstByPrivilege(Privilege privilege);

    @Query("select r from Role r inner join r.userAccount userAccount where userAccount.userAccountId = :userAccountId")
    Optional<Role> findByUserAccount_UserAccountId(@Param("userAccountId") Integer userAccountId);

    @Query("select r from Role r where r.roleCode = 'SALESMAN001'")
    Role findByRoleSalesman();

    @Query("select r from Role r inner join r.userAccount userAccount where userAccount.userAccountId = ?1")
    List<Role> findRoleByUserAccount_UserAccountId(String userAccountId);

    @Query("select r from Role r where r.roleId = :roleId")
    Optional<Role> findByRoleId(@Param("roleId") String roleId);
}

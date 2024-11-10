package com.yokke.usermanagement.privilege;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;


public interface PrivilegeRepository extends JpaRepository<Privilege, Integer> {


    @Query("select p from Privilege p where p.privilegeId = ?1")
    Optional<Privilege> findByPrivilegeId(String privilegeId);

    @Transactional
    @Modifying
    @Query("delete from Privilege p where p.privilegeId = ?1")
    void deleteByPrivilegeId(String privilegeId);
}

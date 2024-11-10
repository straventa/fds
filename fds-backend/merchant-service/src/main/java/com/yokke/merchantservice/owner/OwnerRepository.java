package com.yokke.merchantservice.owner;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;

public interface OwnerRepository extends JpaRepository<Owner, String> {
    @Query("select o from Owner o where o.mid = ?1")
    Optional<Owner> findByMid(String mid);
}
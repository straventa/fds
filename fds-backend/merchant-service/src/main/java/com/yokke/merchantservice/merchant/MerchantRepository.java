package com.yokke.merchantservice.merchant;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;

public interface MerchantRepository extends JpaRepository<Merchant, String> {
    @Query("select m from Merchant m where m.mid = ?1")
    Optional<Merchant> findByMid(String mid);
}
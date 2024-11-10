package com.yokke.usermanagement.auth.token;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface TokenValidityRepository extends CrudRepository<TokenValidity, String> {
    Optional<TokenValidity> findByToken(String token);
}
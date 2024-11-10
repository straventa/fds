package com.yokke.usermanagement.auth.token;


import io.swagger.v3.oas.annotations.tags.Tag;
import io.swagger.v3.oas.annotations.tags.Tags;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@RequestMapping("/fds/api/auth/token")
@RequiredArgsConstructor
@Tags(
        value = {
                @Tag(name = "User Management"),
        }
)
public class TokenValidityController {

    private final TokenValidityRepository tokenValidityRepository;

    // Create a new token
    @PostMapping
    public TokenValidity createToken(@RequestBody TokenValidity tokenEntity) {
        return tokenValidityRepository.save(tokenEntity);
    }

    @GetMapping
    public Iterable<TokenValidity> findAll() {
        return tokenValidityRepository.findAll();
    }

    // Get token by ID
    @GetMapping("/{id}")
    public TokenValidity getTokenById(@PathVariable String id) {
        Optional<TokenValidity> tokenEntity = tokenValidityRepository.findById(id);
        return tokenEntity.orElse(null); // Return null if token is not found
    }
}
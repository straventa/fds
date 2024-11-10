package com.yokke.usermanagement.auth.token;

import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.redis.core.RedisHash;

@RedisHash("TokenValidity")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class TokenValidity {

    @Id
    private String id;
    private String token;
}

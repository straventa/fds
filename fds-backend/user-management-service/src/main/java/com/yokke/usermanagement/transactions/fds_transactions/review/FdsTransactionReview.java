package com.yokke.usermanagement.transactions.fds_transactions.review;


import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.redis.core.RedisHash;
import org.springframework.data.redis.core.index.Indexed;

@RedisHash("FdsTransactionReview")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor

public class FdsTransactionReview {

    @Id
    private String id;
    @Indexed

    private String userAccountId;
}

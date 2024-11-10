package com.yokke.usermanagement.transactions.fds_transactions.review;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface FdsTransactionReviewRepository extends CrudRepository<FdsTransactionReview, String> {
    List<FdsTransactionReview> findByUserAccountId(String userAccountId);
}

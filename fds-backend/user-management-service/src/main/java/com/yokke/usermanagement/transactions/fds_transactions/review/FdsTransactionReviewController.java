package com.yokke.usermanagement.transactions.fds_transactions.review;

import com.yokke.usermanagement.transactions.fds_transactions.FdsTransactionsDto;
import io.swagger.v3.oas.annotations.tags.Tag;
import io.swagger.v3.oas.annotations.tags.Tags;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@RestController
@RequestMapping(value = "", produces = "application/json")
@Tags(
        {
                @Tag(name = "Fds Transactions"),
                @Tag(name = "Review")
        }
)
@RequiredArgsConstructor
public class FdsTransactionReviewController {
    private final FdsTransactionReviewService fdsTransactionReviewService;

    @PostMapping("/fds/api/fds-transaction/review")
    public ResponseEntity<String> review(
            @RequestBody final FdsTransactionsDto fdsTransactionReviewDto
    ) {
        return ResponseEntity.ok(fdsTransactionReviewService.reviewString(
                fdsTransactionReviewDto.getFdsTransactionId()
        ));
    }

    @PostMapping("/fds/api/fds-transaction/review-bulk")
    public ResponseEntity<List<FdsTransactionsDto>> reviewBulk(
            @RequestBody final List<FdsTransactionsDto> fdsTransactionReviewDto
    ) {
        return ResponseEntity.ok(fdsTransactionReviewService.reviewStringBulk(
                fdsTransactionReviewDto
        ));
    }

    @GetMapping("/fds/api/fds-transaction/review/{fdsTransactionId}")
    public ResponseEntity<String> reviewGet(
            @PathVariable(name = "fdsTransactionId") final String transactionId) {
        return ResponseEntity.ok(fdsTransactionReviewService.reviewString(
                transactionId
        ));
    }

    @PostMapping("/fds/api/fds-transaction/cancel-review")
    public ResponseEntity<String> cancelReview(
            @RequestBody final FdsTransactionsDto fdsTransactionReviewDto
    ) {
        return ResponseEntity.ok(fdsTransactionReviewService.cancelReviewString(
                fdsTransactionReviewDto.getFdsTransactionId()));
    }

    @PostMapping("/fds/api/fds-transaction/cancel-review-bulk")
    public ResponseEntity<List<FdsTransactionsDto>> cancelReviewBulk(
            @RequestBody final List<FdsTransactionsDto> fdsTransactionReviewDto
    ) {
        return ResponseEntity.ok(fdsTransactionReviewService.cancelReviewStringBulk(
                fdsTransactionReviewDto));
    }

    @GetMapping("/fds/api/fds-transaction/cancel-review-all")
    public ResponseEntity<String> cancelReviewAll(
    ) {
        return ResponseEntity.ok(fdsTransactionReviewService.cancelReviewAll());
    }
}

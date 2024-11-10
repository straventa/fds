package com.yokke.usermanagement.transactions.fds_transactions;

import com.yokke.usermanagement.rule.FdsTransactionsParamDto;
import io.swagger.v3.oas.annotations.tags.Tag;
import io.swagger.v3.oas.annotations.tags.Tags;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@RestController
@RequestMapping(value = "", produces = "application/json")
@Tags(
        {
                @Tag(name = "Fds Transactions"),
                @Tag(name = "User Management")
        }
)
@RequiredArgsConstructor
public class FdsTransactionsController {
    private final FdsTransactionsService fdsTransactionsService;
    private final FdsTransactionScheduler fdsTransactionScheduler;

//    @GetMapping("/fds/api/fds-transaction")
//    public ResponseEntity<Page<FdsTransactionsDto>> read(
//            @PageableDefault(
//                    size = 10,
//                    page = 0,
//                    sort = {"assignedDateTime"},
//                    direction = org.springframework.data.domain.Sort.Direction.DESC
//            ) final Pageable pageable,
//            @RequestParam(name = "start-date", required = false) OffsetDateTime startDate,
//            @RequestParam(name = "end-date", required = false) OffsetDateTime endDate,
//            @RequestParam(name = "mid", required = false) String mid,
//            @RequestParam(name = "tid", required = false) String tid,
//            @RequestParam(name = "assigned-to", required = false, defaultValue = "me") String assignedTo,
//            @RequestParam(name = "action-type", required = false) String actionType,
//            @RequestParam(name = "action-type-is-null", required   = false) Boolean actionTypeIsNull,
//            @RequestParam(name = "action-type-is-not-null", required = false) Boolean actionTypeIsNotNull
//
//    ) {
//        return ResponseEntity.ok(fdsTransactionsService.read(
//                startDate,
//                endDate,
//                mid,
//                tid,
//                assignedTo,
//                actionType,
//                actionTypeIsNull,
//                actionTypeIsNotNull,
//                pageable));
//    }

    @PostMapping("/fds/api/v2/fds-transaction")
    public ResponseEntity<Page<FdsTransactionsDto>> readV2(
//            @PageableDefault(
//                    size = 10,
//                    page = 0,
//                    sort = {"assignedDateTime"},
//                    direction = org.springframework.data.domain.Sort.Direction.DESC
//            ) final Pageable pageable,
//            @RequestParam(name = "start-date", required = false) OffsetDateTime startDate,
//            @RequestParam(name = "end-date", required = false) OffsetDateTime endDate,
//            @RequestParam(name = "mid", required = false) String mid,
//            @RequestParam(name = "tid", required = false) String tid,
//            @RequestParam(name = "action-type", required = false) List<String> actionType,
//            @RequestParam(name = "is-confirmed", required = false) Boolean isConfirmed,
//            @RequestParam(name = "rule-id", required = false) List<String> ruleId,
//            @RequestParam(name = "isAll", required = false) Boolean isAll
            @RequestBody FdsTransactionsParamDto fdsTransactionsParamDto

    ) {
        return ResponseEntity.ok(fdsTransactionsService.readV2(
                fdsTransactionsParamDto.getStartDate(),
                fdsTransactionsParamDto.getEndDate(),
                fdsTransactionsParamDto.getMid(),
                fdsTransactionsParamDto.getTid(),
                fdsTransactionsParamDto.getActionType(),
                fdsTransactionsParamDto.getIsConfirmed(),
                fdsTransactionsParamDto.getRuleId(),
//                fdsTransactionsParamDto.getIsAll(),
                PageRequest.of(
                        fdsTransactionsParamDto.getPage(),
                        fdsTransactionsParamDto.getSize(),
                        Sort.Direction.valueOf(fdsTransactionsParamDto.getOrder().toUpperCase()),
                        fdsTransactionsParamDto.getSort()
                )
        ));
    }

    @GetMapping("/fds/api/fds-transaction/{fdsTransactionId}")
    public ResponseEntity<FdsTransactionsDto> read(
            @PathVariable(name = "fdsTransactionId") final String transactionId
    ) {
        return ResponseEntity.ok(fdsTransactionsService.readById(
                transactionId
        ));
    }

    @GetMapping("/fds/api/fds-transaction/{fdsTransactionId}/next")
    public ResponseEntity<FdsTransactionsDto> next(
            @PathVariable(name = "fdsTransactionId") final String transactionId
    ) {
        return ResponseEntity.ok(fdsTransactionsService.next(
                transactionId
        ));
    }

    @PutMapping("/fds/api/fds-transaction/{fdsTransactionId}")
    public ResponseEntity<FdsTransactionsDto> update(
            @PathVariable(name = "fdsTransactionId") final String transactionId,
            @RequestBody @Valid final FdsTransactionsDto fdsTransactionsDto
    ) {
        return ResponseEntity.ok(fdsTransactionsService.update(
                transactionId,
                fdsTransactionsDto
        ));
    }

    @PutMapping("/fds/api/fds-transaction")
    public ResponseEntity<List<FdsTransactionsDto>> update(
            @RequestBody @Valid final List<FdsTransactionsDto> fdsTransactionsDtoList
    ) {
        return ResponseEntity.ok(
                fdsTransactionsService.update(
                        fdsTransactionsDtoList
                )
        );
    }


}

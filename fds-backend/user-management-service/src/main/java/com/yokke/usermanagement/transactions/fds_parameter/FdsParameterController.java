package com.yokke.usermanagement.transactions.fds_parameter;

import com.yokke.usermanagement.transactions.fds_transactions_audit.FdsParameterService;
import io.swagger.v3.oas.annotations.tags.Tag;
import io.swagger.v3.oas.annotations.tags.Tags;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequestMapping(value = "", produces = "application/json")
@Tags(
        {
                @Tag(name = "Fds Transactions"),
                @Tag(name = "Fds Parameter")

        }
)
@RequiredArgsConstructor
public class FdsParameterController {
    private final FdsParameterService fdsParameterService;

    @GetMapping("/fds/api/fds-parameter")
    public ResponseEntity<Page<FdsParameterDto>> read(
            @PageableDefault(
                    size = 1000,
                    page = 0,
                    sort = {"fdsParameterKey"},
                    direction = Sort.Direction.ASC
            ) final Pageable pageable,
            @RequestParam(name = "fds-category", required = false) String fdsParameterCategory

    ) {
        return ResponseEntity.ok(fdsParameterService.read(
                fdsParameterCategory,
                pageable
        ));
    }
}

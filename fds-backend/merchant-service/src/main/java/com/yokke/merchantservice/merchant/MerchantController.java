package com.yokke.merchantservice.merchant;

import io.swagger.v3.oas.annotations.tags.Tag;
import io.swagger.v3.oas.annotations.tags.Tags;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value="/fds/api/merchant")
@Tags(
        {
                @Tag(name = "Merchant"),
                @Tag(name = "FDS")
        }
)
@RequiredArgsConstructor
public class MerchantController {
    private final MerchantService merchantService;

    @GetMapping
    public ResponseEntity<Page<MerchantDto>> read(
            @PageableDefault(
                    page = 0,
                    size = 10,
                    sort = "mid",
                    direction = Sort.Direction.DESC
            ) Pageable pageable
    ) {
        return ResponseEntity.ok(merchantService.read(pageable));
    }

    @GetMapping("/{merchantId}")
    public ResponseEntity<MerchantDto> read(
            @PathVariable(name = "merchantId") final String merchantId
    ) {
        return ResponseEntity.ok(merchantService.read(merchantId));
    }

}

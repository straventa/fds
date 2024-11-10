package com.yokke.merchantservice.owner;

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
@RequestMapping(value="/fds/api/owner")
@Tags(
        {
                @Tag(name = "Owner"),
                @Tag(name = "FDS")
        }
)
@RequiredArgsConstructor
public class OwnerController {
    private final OwnerService ownerService;

    @GetMapping
    public ResponseEntity<Page<OwnerDto>> read(
            @PageableDefault(
                    page = 0,
                    size = 10,
                    sort = "mid",
                    direction = Sort.Direction.DESC
            ) Pageable pageable
    ) {
        return ResponseEntity.ok(ownerService.read(pageable));
    }

    @GetMapping("/{ownerId}")
    public ResponseEntity<OwnerDto> read(
            @PathVariable(name = "ownerId") final String ownerId
    ) {
        return ResponseEntity.ok(ownerService.read(ownerId));
    }
}

package com.yokke.merchantservice.cdd;

import io.swagger.v3.oas.annotations.tags.Tag;
import io.swagger.v3.oas.annotations.tags.Tags;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = "/fds/api/cdd")
@Tags(
        {
                @Tag(name = "Cdd"),
                @Tag(name = "FDS")
        }
)
@RequiredArgsConstructor
public class CddController {
    private final CddService cddService;

    @GetMapping("/{cmmnCdId}")
    public ResponseEntity<CddDto> read(
            @PathVariable(name = "cmmnCdId") final String cmmnCdId
    ) {
        return ResponseEntity.ok(cddService.readByByDtlCdId(cmmnCdId));
    }
}

package com.yokke.fdsservice.alert_fds_parameter;


import io.swagger.v3.oas.annotations.tags.Tag;
import io.swagger.v3.oas.annotations.tags.Tags;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.OffsetDateTime;
import java.util.List;

@RestController
@RequestMapping(value="/fds/api/alert-fds-parameter")
@Tags(
        {
                @Tag(name = "Alert FDS Parameter"),
                @Tag(name = "FDS")
        }
)@RequiredArgsConstructor
public class AlertFdsParameterController {
    private final AlertFdsParameterService alertFdsParameterService;

    @GetMapping
    public ResponseEntity<Page<AlertFdsParameterDto>> read(
            @PageableDefault (
                    page = 0,
                    size = 10,
                    sort = "authDate",
                    direction = Sort.Direction.DESC
            )Pageable pageable,
            @RequestParam(name="start-date", required = false) OffsetDateTime startDate,
            @RequestParam(name="end-date", required = false) OffsetDateTime endDate,
            @RequestParam(name="mid",required = false) String mid,
            @RequestParam(name="tid",required = false) String tid,
            @RequestParam(name="assigned-to",required = false,defaultValue = "me") String assignedTo,
            @RequestParam(name="action-type",required = false) String actionType,
            @RequestParam(name="is-marked",required = false) Boolean isMarked

    ){
        return ResponseEntity.ok(alertFdsParameterService.read(
                startDate,
                endDate,
                mid,
                tid,
                assignedTo,
                actionType,
                isMarked,
                pageable
        ));
    }

    @GetMapping("/{uniqueId}")
    public ResponseEntity<AlertFdsParameterDto> read(
            @PathVariable(name = "uniqueId") final String uniqueId
    ){
        return ResponseEntity.ok(alertFdsParameterService.read(uniqueId));
    }
    @GetMapping("/next/{uniqueId}")
    public ResponseEntity<AlertFdsParameterDto> next(
            @PathVariable(name = "uniqueId") final String uniqueId
    ){
        return ResponseEntity.ok(alertFdsParameterService.next(
                uniqueId
        ));
    }

    @PutMapping("/{uniqueId}")
    public ResponseEntity<AlertFdsParameterDto> update(
            @PathVariable(name = "uniqueId") final String uniqueId,
            @RequestBody final AlertFdsParameterDto alertFdsParameterDto
    ){
        return ResponseEntity.ok(alertFdsParameterService.update(uniqueId, alertFdsParameterDto));
    }

    @GetMapping("/statistics/analyst")
    public ResponseEntity<List<AlertFdsParameterAnalystDto>> readAnalystStatistics(
            @RequestParam(name="start-date", required = false) OffsetDateTime startDate,
            @RequestParam(name="end-date", required = false) OffsetDateTime endDate
    ){
        return ResponseEntity.ok(alertFdsParameterService.statisticsByAnalyst(
                startDate,
                endDate
        ));
    }
}

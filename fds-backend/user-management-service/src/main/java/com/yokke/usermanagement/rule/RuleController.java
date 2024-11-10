package com.yokke.usermanagement.rule;


import io.swagger.v3.oas.annotations.tags.Tag;
import io.swagger.v3.oas.annotations.tags.Tags;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.OffsetDateTime;
import java.util.List;

@Slf4j
@RestController
@RequestMapping(value = "",produces = "application/json")
@Tags(
        {
                @Tag(name = "Rule"),
                @Tag(name = "User Management")
        }
)
@RequiredArgsConstructor
public class RuleController {
    private  final RuleService ruleService;

    @PostMapping("/fds/api/rule")
    public ResponseEntity<String> create(
            @RequestBody final RuleDto ruleDto
    ){
        return ResponseEntity.ok(ruleService.create(ruleDto));
    }

    @GetMapping("/fds/api/rule")
    public ResponseEntity<Page<RuleDto>> read(
            @RequestParam(name="risk-level",required = false) final String riskLevel,
            @RequestParam(name="rule-description",required = false) final String ruleDescription,
            @RequestParam(name="rule-name",required = false) final String ruleName,
            @RequestParam(name="source-data",required = false) final String sourceData,
            @RequestParam(name="user-account-id",required = false) final List<String> userAccountId,
            @PageableDefault(
                    size = 10,
                    page = 0,
                    sort = {"riskLevel"},
                    direction = org.springframework.data.domain.Sort.Direction.DESC
            ) final Pageable pageable
    ){
        return ResponseEntity.ok(ruleService.read(
                riskLevel,
                ruleDescription,
                ruleName,
                sourceData,
                userAccountId,
                pageable
        ));
    }

    @GetMapping("/fds/api/rule/{ruleId}")
    public ResponseEntity<RuleDto> read(
            @PathVariable(name = "ruleId") final String ruleId
    ){
        return ResponseEntity.ok(ruleService.read(ruleId));
    }
    @GetMapping("/fds/api/user-account/{userAccountId}/rule")
    public ResponseEntity<Page<RuleDto>> read(
            @PathVariable(name = "userAccountId") final String userAccountId,
          @PageableDefault(
                    size = 10,
                    page = 0,
                    sort = {"riskLevel"},
                    direction = org.springframework.data.domain.Sort.Direction.DESC
          ) final Pageable pageable
    ){
        return ResponseEntity.ok(ruleService.read(userAccountId,pageable));
    }
    @GetMapping("/fds/api/rule/{ruleId}/statistics")
    public ResponseEntity<RuleStatisticsDto> read(
            @PathVariable(name = "ruleId") final String ruleId,
            @RequestParam(name = "start-date") final OffsetDateTime startDate,
            @RequestParam(name = "end-date") final OffsetDateTime endDate
    ){
        return ResponseEntity.ok(ruleService.read(
                ruleId,
                startDate,
                endDate
        ));
    }

    @PutMapping("/fds/api/rule/{ruleId}")
    public ResponseEntity<Void> update(
            @PathVariable(name = "ruleId") final String ruleId,
            @RequestBody final RuleDto ruleDto
    ){
        log.debug("Updating rule with id: {}",ruleId);
        log.debug("RuleDto: {}",ruleDto.toString());
        ruleService.update(ruleId,ruleDto);
        return ResponseEntity.ok().build();
    }

    @DeleteMapping("/fds/api/rule/{ruleId}")
    public ResponseEntity<Void> delete(
            @PathVariable(name = "ruleId") final String ruleId
    ){
        ruleService.delete(ruleId);
        return ResponseEntity.ok().build();
    }





}

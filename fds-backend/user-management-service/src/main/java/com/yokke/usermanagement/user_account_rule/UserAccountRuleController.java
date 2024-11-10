package com.yokke.usermanagement.user_account_rule;

import io.swagger.v3.oas.annotations.tags.Tag;
import io.swagger.v3.oas.annotations.tags.Tags;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping(value = "")
@RequiredArgsConstructor
@Tags(value = {
        @Tag(name = "User Management"),
        @Tag(name = "User Account Rule")
})
public class UserAccountRuleController {
    private final UserAccountRuleService userAccountRuleService;

    @GetMapping("/fds/api/user-account-rule")
    public ResponseEntity<List<UserAccountRuleDto>> read() {
        return ResponseEntity.ok(userAccountRuleService.findAll());
    }

    @GetMapping("/fds/api/user-account/{userAccountId}/user-account-rule")
    public ResponseEntity<List<UserAccountRuleDto>> readByUserAccountId(
            @PathVariable(name = "userAccountId") String userAccountId
    ) {
        return ResponseEntity.ok(userAccountRuleService.readByUserAccountId(userAccountId));
    }
}

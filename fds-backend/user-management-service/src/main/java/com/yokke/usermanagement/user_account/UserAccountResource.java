package com.yokke.usermanagement.user_account;

import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import io.swagger.v3.oas.annotations.tags.Tags;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping(value = "/fds/api/user-account", produces = MediaType.APPLICATION_JSON_VALUE)
@RequiredArgsConstructor
@Tags(
        value = {
                @Tag(name = "User Management"),
        }
)
public class UserAccountResource {

    private final UserAccountService userAccountService;

    @GetMapping
    public ResponseEntity<Page<UserAccountDTO>> getAllUserAccounts(
            @RequestParam(name = "username", required = false) final String username,
            @RequestParam(name = "email", required = false) final String email,
            @RequestParam(name = "roleName", required = false) final String roleName,
            @RequestParam(name = "ruleName", required = false) final String ruleName,
            @PageableDefault(
                    page = 0,
                    size = 10,
                    sort = "username",
                    direction = Sort.Direction.DESC
            ) final Pageable pageable
    ) {
        Page<UserAccountDTO> userAccounts = userAccountService.read(
                username,
                email,
                roleName,
                ruleName,
                pageable
        );
        return ResponseEntity.ok(userAccounts);
    }

    @GetMapping("/{userAccountId}")
    public ResponseEntity<UserAccountDTO> getUserAccount(
            @PathVariable(name = "userAccountId") final String userAccountId) {
        return ResponseEntity.ok(userAccountService.get(userAccountId));
    }

    @PostMapping
    @ApiResponse(responseCode = "201")
    public ResponseEntity<UserAccountDTO> createUserAccount(
            @RequestBody @Valid final UserAccountDTO userAccountDTO) {
        final UserAccountDTO createdUserAccountId = userAccountService.create(userAccountDTO);
        return new ResponseEntity<>(createdUserAccountId, HttpStatus.CREATED);
    }

    @PutMapping("/{userAccountId}")
    public ResponseEntity<UserAccountDTO> update(
            @PathVariable(name = "userAccountId") final String userAccountId,
            @RequestBody @Valid final UserAccountDTO userAccountDTO) {

        return ResponseEntity.ok(userAccountService.update(userAccountId, userAccountDTO));
    }

    @DeleteMapping("/{userAccountId}")
    @ApiResponse(responseCode = "204")
    public ResponseEntity<Void> deleteUserAccount(
            @PathVariable(name = "userAccountId") final String userAccountId) {
        userAccountService.delete(userAccountId);
        return ResponseEntity.noContent().build();
    }

//    @GetMapping("/me")
//    public ResponseEntity<UserAccountDTO> getCurrentUserAccount() {
//        return ResponseEntity.ok(userAccountService.me());
//    }
}

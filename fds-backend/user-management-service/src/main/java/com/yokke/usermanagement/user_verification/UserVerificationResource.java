package com.yokke.usermanagement.user_verification;

import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import io.swagger.v3.oas.annotations.tags.Tags;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RestController
@RequestMapping(value = "/fds/api/user-verifications", produces = MediaType.APPLICATION_JSON_VALUE)
@Tags(
        value = {
                @Tag(name = "User Management"),
        }
)
public class UserVerificationResource {

    private final UserVerificationService userVerificationService;

    public UserVerificationResource(final UserVerificationService userVerificationService) {
        this.userVerificationService = userVerificationService;
    }

    @GetMapping
    public ResponseEntity<List<UserVerificationDTO>> getAllUserVerifications() {
        return ResponseEntity.ok(userVerificationService.findAll());
    }

    @GetMapping("/{userVerificationId}")
    public ResponseEntity<UserVerificationDTO> getUserVerification(
            @PathVariable(name = "userVerificationId") final Integer userVerificationId) {
        return ResponseEntity.ok(userVerificationService.get(userVerificationId));
    }

    @PostMapping
    @ApiResponse(responseCode = "201")
    public ResponseEntity<String> createUserVerification(
            @RequestBody @Valid final UserVerificationDTO userVerificationDTO) {
        final String createdUserVerificationId = userVerificationService.create(userVerificationDTO);
        return new ResponseEntity<>(createdUserVerificationId, HttpStatus.CREATED);
    }

    @PutMapping("/{userVerificationId}")
    public ResponseEntity<Integer> updateUserVerification(
            @PathVariable(name = "userVerificationId") final Integer userVerificationId,
            @RequestBody @Valid final UserVerificationDTO userVerificationDTO) {
        userVerificationService.update(userVerificationId, userVerificationDTO);
        return ResponseEntity.ok(userVerificationId);
    }

    @DeleteMapping("/{userVerificationId}")
    @ApiResponse(responseCode = "204")
    public ResponseEntity<Void> deleteUserVerification(
            @PathVariable(name = "userVerificationId") final Integer userVerificationId) {
        userVerificationService.delete(userVerificationId);
        return ResponseEntity.noContent().build();
    }

}

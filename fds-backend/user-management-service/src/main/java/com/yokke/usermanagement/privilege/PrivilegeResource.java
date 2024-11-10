package com.yokke.usermanagement.privilege;

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
@RequestMapping(value = "/fds/api/privileges", produces = MediaType.APPLICATION_JSON_VALUE)
@Tags(
        value = {
                @Tag(name = "User Management"),
        }
)
public class PrivilegeResource {

    private final PrivilegeService privilegeService;

    public PrivilegeResource(final PrivilegeService privilegeService) {
        this.privilegeService = privilegeService;
    }

    @GetMapping
    public ResponseEntity<List<PrivilegeDTO>> getAllPrivileges() {
        return ResponseEntity.ok(privilegeService.findAll());
    }

    @GetMapping("/{privilegeId}")
    public ResponseEntity<PrivilegeDTO> getPrivilege(
            @PathVariable(name = "privilegeId") final String privilegeId) {
        return ResponseEntity.ok(privilegeService.get(privilegeId));
    }

    @PostMapping
    @ApiResponse(responseCode = "201")
    public ResponseEntity<String> createPrivilege(
            @RequestBody @Valid final PrivilegeDTO privilegeDTO) {
        final String createdPrivilegeId = privilegeService.create(privilegeDTO);
        return new ResponseEntity<>(createdPrivilegeId, HttpStatus.CREATED);
    }

    @PutMapping("/{privilegeId}")
    public ResponseEntity<String> updatePrivilege(
            @PathVariable(name = "privilegeId") final String privilegeId,
            @RequestBody @Valid final PrivilegeDTO privilegeDTO) {
        privilegeService.update(privilegeId, privilegeDTO);
        return ResponseEntity.ok(privilegeId);
    }

    @DeleteMapping("/{privilegeId}")
    @ApiResponse(responseCode = "204")
    public ResponseEntity<Void> deletePrivilege(
            @PathVariable(name = "privilegeId") final String privilegeId) {
//        final ReferencedWarning referencedWarning = privilegeService.getReferencedWarning(privilegeId);
//        if (referencedWarning != null) {
//            throw new ReferencedException(referencedWarning);
//        }
        privilegeService.delete(privilegeId);
        return ResponseEntity.noContent().build();
    }

}

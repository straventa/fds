package com.yokke.usermanagement.role;

import com.yokke.base.dto.BaseDto;
import jakarta.validation.constraints.Size;
import lombok.*;


@AllArgsConstructor
@NoArgsConstructor
@Builder
@Getter
@Setter
public class RoleDTO extends BaseDto {

    private String roleId;

    @Size(max = 50)
    private String roleName;

    @Size(max = 50)
    private String roleCode;

    private String roleDescription;

}

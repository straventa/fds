package com.yokke.usermanagement.privilege;

import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;


@Getter
@Setter
public class PrivilegeDTO {

    private String privilegeId;

    @Size(max = 50)
    private String privilegeName;

    @Size(max = 50)
    private String privilegeCode;

    private String privilegeDescription;

}

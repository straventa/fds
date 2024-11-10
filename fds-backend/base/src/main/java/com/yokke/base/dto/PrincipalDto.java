package com.yokke.base.dto;


import com.yokke.base.response.BaseResponse;
import jakarta.validation.constraints.Size;
import lombok.*;


@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class PrincipalDto extends BaseResponse {

    private String userAccountId;

    @Size(max = 50)
    private String username;

    @Size(max = 100)
    private String email;

    @Size(max = 100)
    private String password;

    private Integer organization;

    private Integer team;

    private Integer userData;


}

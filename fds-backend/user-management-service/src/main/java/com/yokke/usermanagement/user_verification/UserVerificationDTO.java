package com.yokke.usermanagement.user_verification;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.yokke.base.dto.BaseDto;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

import java.time.OffsetDateTime;


@Getter
@Setter
public class UserVerificationDTO extends BaseDto {

    private String userVerificationId;

    @Size(max = 50)
    private String verificationType;

    @Size(max = 100)
    private String verificationCode;

    @Size(max = 50)
    private String verificationStatus;

    private OffsetDateTime verificationExpiry;

    @JsonProperty("isVerified")
    private Boolean isVerified;

    private String userAccount;

}

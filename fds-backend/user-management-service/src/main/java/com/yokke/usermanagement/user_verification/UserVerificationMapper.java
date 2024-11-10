package com.yokke.usermanagement.user_verification;

import com.yokke.base.exception.response_status.NotFoundException;
import com.yokke.base.mapper.BaseMapper;
import com.yokke.usermanagement.user_account.UserAccount;
import com.yokke.usermanagement.user_account.UserAccountRepository;
import org.springframework.stereotype.Service;

@Service
public class UserVerificationMapper  extends BaseMapper {

    public UserVerificationDTO mapToDTO( UserVerification userVerification,
                                          UserVerificationDTO userVerificationDTO) {
        userVerificationDTO = mapAudit(userVerificationDTO,userVerification);
        userVerificationDTO.setUserVerificationId(userVerification.getUserVerificationId());
        userVerificationDTO.setVerificationType(userVerification.getVerificationType());
        userVerificationDTO.setVerificationCode(userVerification.getVerificationCode());
        userVerificationDTO.setVerificationStatus(userVerification.getVerificationStatus());
        userVerificationDTO.setVerificationExpiry(userVerification.getVerificationExpiry());
        userVerificationDTO.setIsVerified(userVerification.getIsVerified());
        userVerificationDTO.setUserAccount(userVerification.getUserAccount() == null ? null : userVerification.getUserAccount().getUserAccountId());
        return userVerificationDTO;
    }

    public UserVerification mapToEntity(final UserVerificationDTO userVerificationDTO,
                                         final UserVerification userVerification) {
        userVerification.setVerificationType(userVerificationDTO.getVerificationType());
        userVerification.setVerificationCode(userVerificationDTO.getVerificationCode());
        userVerification.setVerificationStatus(userVerificationDTO.getVerificationStatus());
        userVerification.setVerificationExpiry(userVerificationDTO.getVerificationExpiry());
        userVerification.setIsVerified(userVerificationDTO.getIsVerified());

        userVerification.setUserAccount(userVerification.getUserAccount());
        return userVerification;
    }
}

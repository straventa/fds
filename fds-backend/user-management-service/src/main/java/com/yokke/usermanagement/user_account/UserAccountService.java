package com.yokke.usermanagement.user_account;

//import ;

import com.yokke.base.exception.response_status.NotFoundException;
import com.yokke.base.service.BaseService;
import com.yokke.usermanagement.role.RoleService;
import com.yokke.usermanagement.user_account_rule.UserAccountRule;
import com.yokke.usermanagement.user_account_rule.UserAccountRuleDto;
import com.yokke.usermanagement.user_account_rule.UserAccountRuleRepository;
import com.yokke.usermanagement.user_account_rule.UserAccountRuleService;
import com.yokke.usermanagement.user_verification.UserVerificationService;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;


@Service
@RequiredArgsConstructor
public class UserAccountService extends BaseService {
    private final UserAccountRepository userAccountRepository;
    private final RoleService roleService;
    private final UserAccountMapper userAccountMapper;
    private final UserAccountRuleService userAccountRuleService;
    private final UserVerificationService userVerificationService;
    private final UserAccountRuleRepository userAccountRuleRepository;

    @Cacheable(
            value = "userAccounts",
            key = "T(org.springframework.security.core.context.SecurityContextHolder).getContext().getAuthentication().name + '-' + #username + '-' + #email + '-' + #roleName + '-' + #ruleName + '-' + #pageable.pageNumber + '-' + #pageable.pageSize"
    )
    public Page<UserAccountDTO> read(
            final String username,
            final String email,
            final String roleName,
            final String ruleName,
            Pageable pageable
    ) {
        final Page<UserAccount> userAccounts = userAccountRepository.findAll(
                username,
                email,
                roleName,
                ruleName,
                pageable
        );
        return userAccounts.map(userAccount -> userAccountMapper.mapToDTO(
                userAccount,
                new UserAccountDTO(),
                roleService.readByUserAccountId(userAccount.getUserAccountId()),
                userAccountRuleService.readByUserAccountId(userAccount.getUserAccountId())
        ));
    }

    @Cacheable(value = "userAccounts", key = "#userAccountId")
    public UserAccountDTO get(final String userAccountId) {
        return userAccountRepository.findByUserAccountId(userAccountId)
                .map(userAccount -> userAccountMapper.mapToDTO(
                        userAccount,
                        new UserAccountDTO(),
                        roleService.readByUserAccountId(userAccountId),
                        userAccountRuleService.readByUserAccountId(userAccountId)
                ))
                .orElseThrow(() -> new ResponseStatusException(
                        HttpStatus.NOT_FOUND,
                        "User account with ID " + userAccountId + " not found."
                ));
    }

    @Cacheable(value = "userAccounts", key = "'audit-'+#userAccountId")
    public UserAccountDTO getAudit(final String userAccountId) {
        return userAccountRepository.findByUserAccountIdAudit(userAccountId)
                .map(userAccount -> userAccountMapper.mapToDTO(
                        userAccount,
                        new UserAccountDTO(),
                        roleService.readByUserAccountId(userAccountId),
                        userAccountRuleService.readByUserAccountId(userAccountId)
                ))
                .orElseThrow(() -> new ResponseStatusException(
                        HttpStatus.NOT_FOUND,
                        "User account with ID " + userAccountId + " not found."
                ));
    }

    @CachePut(value = "userAccounts", key = "#result.userAccountId")
    @CacheEvict(value = "userAccounts", allEntries = true)
    @Transactional
    public UserAccountDTO create(final UserAccountDTO userAccountDTO) {
        String uuid = UUID.randomUUID().toString();
        raiseIfEmailOrUsernameExist(userAccountDTO.getEmail(), userAccountDTO.getUsername());
        userVerificationService.sendEmailVerification(userAccountDTO.getUsername(), userAccountDTO.getEmail(), userAccountDTO.getUsername(), userAccountDTO.getRole().getFirst().getRoleName(), uuid);
        UserAccount userAccount = userAccountMapper.mapToEntity(userAccountDTO, new UserAccount());
        userAccount.setLoginAttempt(5);
        userAccountRepository.save(userAccount);
        userVerificationService.create(userAccount, uuid);
        List<UserAccountRule> userAccountRulesList = new ArrayList<>();
        for (UserAccountRule userAccountRule : userAccount.getUserAccountRule()) {
            userAccountRule.setUserAccount(userAccount);
            userAccountRulesList.add(userAccountRule);
        }
        userAccountRuleRepository.saveAll(userAccountRulesList);


        return userAccountMapper.mapToDTO(
                userAccount,
                new UserAccountDTO(),
                roleService.readByUserAccountId(userAccount.getUserAccountId()),
                userAccountRuleService.readByUserAccountId(userAccount.getUserAccountId())
        );
    }


    public void raiseIfEmailOrUsernameExist(String email, String username) {
        if (userAccountRepository.existsByEmailIgnoreCase(email)) {
            throw new ResponseStatusException(HttpStatus.CONFLICT,
                    String.format("Email %s already exist", email));
        } else if (userAccountRepository.existsByUsernameIgnoreCase(username)) {
            throw new ResponseStatusException(HttpStatus.CONFLICT,
                    String.format("Username %s already exist", username));
        }
    }

    @CachePut(value = "userAccounts", key = "#result.userAccountId")
    @CacheEvict(value = "userAccounts", allEntries = true)
    @Transactional
    public UserAccountDTO update(final String userAccountId, final UserAccountDTO userAccountDTO) {
        final UserAccount userAccount = userAccountRepository.findUserAccountByUserAccountId(userAccountId)
                .orElseThrow(() -> new NotFoundException("OKK"));
        userAccountMapper.mapToEntity(userAccountDTO, userAccount);
        if (!userAccountDTO.getIsLocked()) {
            userAccount.setLoginAttempt(5);
        }
        String code = UUID.randomUUID().toString();
        if (userAccountDTO.getEmail() != userAccount.getEmail()) {
            userVerificationService.sendEmailVerification(
                    userAccountDTO.getUsername(),
                    userAccountDTO.getEmail(),
                    "",
                    userAccountDTO.getUserAccountRule().get(0).getRule().getRuleName(),
                    code
            );
            userVerificationService.create(userAccount, code);
            userAccount.setPassword(null);
        }
        userAccountRepository.save(userAccount);
        List<UserAccountRule> userAccountRulesList = new ArrayList<>();
//        for (UserAccountRule userAccountRule : userAccount.getUserAccountRule()) {
//            userAccountRule.setUserAccount(userAccount);
//            userAccountRulesList.add(userAccountRule);
//        }

        for (UserAccountRuleDto userAccountRuleDto : userAccountDTO.getUserAccountRule()) {
            UserAccountRule userAccountRule = userAccountRuleRepository.findByUserRuleId(userAccountRuleDto.getUserRuleId()).orElseThrow(() -> new NotFoundException("OKK"));
            userAccountRule.setPriority(userAccountRuleDto.getPriority());
            userAccountRule.setIsActive(userAccountRuleDto.getIsActive());
            userAccountRulesList.add(userAccountRule);
        }
        userAccountRuleRepository.saveAll(userAccountRulesList);

        return userAccountMapper.mapToDTO(
                userAccount,
                new UserAccountDTO(),
                roleService.readByUserAccountId(userAccountId),
                userAccountRuleService.readByUserAccountId(userAccountId)
        );
    }


    public String getUserAccountId() {
        UserAccount auth2 = (UserAccount) SecurityContextHolder.getContext()
                .getAuthentication().getPrincipal();
        return auth2.getUserAccountId();
    }

    public UserAccount getCurrentUserAccount() {
        return (UserAccount) SecurityContextHolder.getContext()
                .getAuthentication().getPrincipal();
    }

    //    @Cacheable(
//            value = "userAccounts",
//            key = "T(org.springframework.security.core.context.SecurityContextHolder).getContext().getAuthentication().name + '-' + 'me'"
//    )
//    public UserAccountDTO me() {
//        UserAccount userAccount = userAccountRepository.findByUserAccountId(getUserAccountId()).orElseThrow(()-> new NotFoundException("OKK"));
//        List<RoleDTO> role = roleService.readByUserAccountId(getUserAccountId());
//        return userAccountMapper.mapToDTO(
//                userAccount,
//                new UserAccountDTO(),
//                role
//        );
//    }

    //    public UserAccountDTO getCurrentUserAccountDto() {
//        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
//        if (authentication == null || !authentication.isAuthenticated()) {
//            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "Not authenticated");
//        }
//
//        UserAccount userAccount = (UserAccount) authentication.getPrincipal();
//        UserAccount res = userAccountRepository.findByUserAccountId(userAccount.getUserAccountId())
//                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "User not found"));
//        return userAccountMapper.mapToDTO(
//                res,
//                new UserAccountDTO()
//        );
//    }
    @CacheEvict(value = "userAccounts", allEntries = true)
    @Transactional
    public void delete(final String userAccountId) {
        UserAccount userAccount = userAccountRepository.findByUserAccountId(userAccountId)
                .orElseThrow(
                        () -> new ResponseStatusException(
                                HttpStatus.NOT_FOUND,
                                "User account with id " + userAccountId + " not found."
                        )
                );

        userAccount.setDeletedBy(getUserAccountId());
        userAccount.setIsDeleted(true);
        userAccount.setDeletedDate(LocalDateTime.now());
        userAccountRepository.save(userAccount);
        //        userAccountRepository.deleteById(userAccountId);
    }


}

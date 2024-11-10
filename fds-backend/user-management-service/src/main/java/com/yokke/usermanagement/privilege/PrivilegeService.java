package com.yokke.usermanagement.privilege;

import com.yokke.base.exception.response_status.NotFoundException;
import com.yokke.usermanagement.role.RoleRepository;
import com.yokke.usermanagement.user_account.UserAccountRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
@RequiredArgsConstructor
public class PrivilegeService {

    private final PrivilegeRepository privilegeRepository;
    private final UserAccountRepository userPrivilegeRepository;
    private final RoleRepository rolePrivilegeRepository;
    private final PrivilegeMapper privilegeMapper;


    public List<PrivilegeDTO> findAll() {
        final List<Privilege> privileges = privilegeRepository.findAll(Sort.by("privilegeId"));
        return privileges.stream()
                .map(privilege -> privilegeMapper.mapToDTO(privilege, new PrivilegeDTO()))
                .toList();
    }

    public PrivilegeDTO get(final String privilegeId) {
        return privilegeRepository.findByPrivilegeId(privilegeId)
                .map(privilege -> privilegeMapper.mapToDTO(privilege, new PrivilegeDTO()))
                .orElseThrow(() -> new NotFoundException("OKK"));
    }

    public String create(final PrivilegeDTO privilegeDTO) {
        final Privilege privilege = new Privilege();
        privilegeMapper.mapToDTO(privilege, privilegeDTO);
        return privilegeRepository.save(privilege).getPrivilegeId();
    }

    public void update(final String privilegeId, final PrivilegeDTO privilegeDTO) {
        final Privilege privilege = privilegeRepository.findByPrivilegeId(privilegeId)
                .orElseThrow(() -> new NotFoundException("OKK"));
        privilegeMapper.mapToEntity(privilegeDTO, privilege);
        privilegeRepository.save(privilege);
    }

    public void delete(final String privilegeId) {
        privilegeRepository.deleteByPrivilegeId(privilegeId);
    }


}

package com.yokke.usermanagement.role;

import com.yokke.base.exception.response_status.NotFoundException;
import com.yokke.base.service.BaseService;
import com.yokke.usermanagement.privilege.PrivilegeRepository;
import com.yokke.usermanagement.user_account.UserAccountRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
@RequiredArgsConstructor
public class RoleService extends BaseService {

    private final RoleRepository roleRepository;
    private final UserAccountRepository userAccountRepository;
    private final PrivilegeRepository privilegeRepository;
    private final RoleMapper roleMapper;

    public List<RoleDTO> findAll() {
        final List<Role> roles = roleRepository.findAll(Sort.by("roleId"));
        return roles.stream()
                .map(role -> roleMapper.mapToDTO(role, new RoleDTO()))
                .toList();
    }

    public RoleDTO get(final Integer roleId) {
        return roleRepository.findById(roleId)
                .map(role -> roleMapper.mapToDTO(role, new RoleDTO()))
                .orElseThrow(() -> new NotFoundException("OKK"));
    }


    @Cacheable(value = "userAccounts", key = "#userAccountId + '-role'")
    public List<RoleDTO> readByUserAccountId(final String userAccountId) {
        return roleRepository.findRoleByUserAccount_UserAccountId(userAccountId).stream()
                .map(role -> roleMapper.mapToDTO(role, new RoleDTO()))
                .toList();
    }

    public String create(final RoleDTO roleDTO) {
        final Role role = new Role();
        roleMapper.mapToEntity(roleDTO, role);
        return roleRepository.save(role).getRoleId();
    }

    public void update(final Integer roleId, final RoleDTO roleDTO) {
        final Role role = roleRepository.findById(roleId)
                .orElseThrow(() -> new NotFoundException("OKK"));
        roleMapper.mapToEntity(roleDTO, role);
        roleRepository.save(role);
    }

    public void delete(final Integer roleId) {
        roleRepository.deleteById(roleId);
    }


}

package com.yokke.usermanagement.role;

import com.yokke.base.mapper.BaseMapper;
import org.springframework.stereotype.Service;

@Service
public class RoleMapper extends BaseMapper {

    public RoleDTO mapToDTO(Role role, RoleDTO roleDTO) {
        roleDTO = mapAudit(roleDTO, role);
        roleDTO.setRoleId(role.getRoleId());
        roleDTO.setRoleName(role.getRoleName());
        roleDTO.setRoleCode(role.getRoleCode());
        roleDTO.setRoleDescription(role.getRoleDescription());
        return roleDTO;
    }

    public Role mapToEntity(final RoleDTO roleDTO, final Role role) {
        role.setRoleName(roleDTO.getRoleName());
        role.setRoleCode(roleDTO.getRoleCode());
        role.setRoleDescription(roleDTO.getRoleDescription());
        return role;
    }

//    public ReferencedWarning getReferencedWarning(final Integer roleId) {
//        final ReferencedWarning referencedWarning = new ReferencedWarning();
//        final Role role = roleRepository.findById(roleId)
//                .orElseThrow(()-> new NotFoundException("OKK"));
////        final Role roleUserRole = userAccountRepository.findFirstByRole(role);
////        if (roleUserRole != null) {
////            referencedWarning.setKey("role.userRole.role.referenced");
////            referencedWarning.addParam(roleUserRole.getRoleId());
////            return referencedWarning;
////        }
//        final Privilege roleRolePrivilege = privilegeRepository.findFirstByRole(role);
//        if (roleRolePrivilege != null) {
//            referencedWarning.setKey("role.rolePrivilege.role.referenced");
//            referencedWarning.addParam(roleRolePrivilege.getPrivilegeId());
//            return referencedWarning;
//        }
//        return null;
//    }
}

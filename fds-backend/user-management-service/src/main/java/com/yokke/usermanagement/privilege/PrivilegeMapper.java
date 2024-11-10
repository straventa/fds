package com.yokke.usermanagement.privilege;

import com.yokke.base.mapper.BaseMapper;
import org.springframework.stereotype.Service;

@Service
public class PrivilegeMapper extends BaseMapper {
    public PrivilegeDTO mapToDTO(final Privilege privilege, final PrivilegeDTO privilegeDTO) {
        privilegeDTO.setPrivilegeId(privilege.getPrivilegeId());
        privilegeDTO.setPrivilegeName(privilege.getPrivilegeName());
        privilegeDTO.setPrivilegeCode(privilege.getPrivilegeCode());
        privilegeDTO.setPrivilegeDescription(privilege.getPrivilegeDescription());
        return privilegeDTO;
    }

    public Privilege mapToEntity(final PrivilegeDTO privilegeDTO, final Privilege privilege) {
        privilege.setPrivilegeName(privilegeDTO.getPrivilegeName());
        privilege.setPrivilegeCode(privilegeDTO.getPrivilegeCode());
        privilege.setPrivilegeDescription(privilegeDTO.getPrivilegeDescription());
        return privilege;
    }

//    public ReferencedWarning getReferencedWarning(final Integer privilegeId) {
//        final ReferencedWarning referencedWarning = new ReferencedWarning();
//        final Privilege privilege = privilegeRepository.findById(privilegeId)
//                .orElseThrow(()-> new NotFoundException("OKK"));
//
//        return null;
//    }
}

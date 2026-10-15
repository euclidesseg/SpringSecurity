package SpringSecurity.SpringSecurity.service.impl;

import SpringSecurity.SpringSecurity.dto.SavePermissionDTO;
import SpringSecurity.SpringSecurity.dto.ShowPermissionDTO;
import SpringSecurity.SpringSecurity.exception.ObjectNotFoundException;
import SpringSecurity.SpringSecurity.persistance.entity.security.GrantedPermission;
import SpringSecurity.SpringSecurity.persistance.entity.security.Operation;
import SpringSecurity.SpringSecurity.persistance.entity.security.Role;
import SpringSecurity.SpringSecurity.persistance.repository.security.IOperationRepository;
import SpringSecurity.SpringSecurity.persistance.repository.security.IPermissionRepository;
import SpringSecurity.SpringSecurity.persistance.repository.security.IRoleRepository;
import SpringSecurity.SpringSecurity.service.IPermissionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class PermissionServiceImpl implements IPermissionService {
    @Autowired
    private IPermissionRepository permissionRepository;
    @Autowired
    private IRoleRepository roleRepository;
    @Autowired
    private IOperationRepository operationRepository;

    @Override
    public Page<ShowPermissionDTO> findAll(Pageable pageable) {
        return this.permissionRepository.findAll(pageable).map(this::mapEntityShowDTO);

    }
    private ShowPermissionDTO mapEntityShowDTO(GrantedPermission grantedPermission) {
        if(grantedPermission == null) return null;
        ShowPermissionDTO showDto = new ShowPermissionDTO();
        showDto.setId(grantedPermission.getId());
        showDto.setRole(grantedPermission.getRole().getName());
        showDto.setOperation(grantedPermission.getOperation().getName());
        showDto.setHttp_method(grantedPermission.getOperation().getHttp_method());
        showDto.setModule(grantedPermission.getOperation().getModule().getName());

        System.out.println(showDto.getId());
        System.out.println(showDto.getRole());
        System.out.println(showDto.getHttp_method());
        System.out.println(showDto.getModule());
        return showDto;
    }

    @Override
    public Optional<ShowPermissionDTO> findOneById(Long permissionId) {
        return this.permissionRepository.findById(permissionId).map(this::mapEntityShowDTO);

    }

    @Override
    public ShowPermissionDTO addOnePermission(SavePermissionDTO savePermissionDTO) {
        Role role = this.roleRepository.findByName(savePermissionDTO.getRole()).orElseThrow(() -> new ObjectNotFoundException("role not found with role "+ savePermissionDTO.getRole()));
        Operation operation = this.operationRepository.findByName(savePermissionDTO.getOperation()).orElseThrow(() -> new ObjectNotFoundException("Operation not found with operation:" + savePermissionDTO.getOperation()));

        GrantedPermission grantedPermissionTosave = new GrantedPermission();
        grantedPermissionTosave.setOperation(operation);
        grantedPermissionTosave.setRole(role);

        GrantedPermission permissionSaved = this.permissionRepository.save(grantedPermissionTosave);
        return this.mapEntityShowDTO(permissionSaved);
    }

    @Override
    public boolean removeOnePermission(SavePermissionDTO savePermissionDTO) {
        Role role = this.roleRepository.findByName(savePermissionDTO.getRole()).orElseThrow(() -> new ObjectNotFoundException("role not found with role "+ savePermissionDTO.getRole()));
        Operation operation = this.operationRepository.findByName(savePermissionDTO.getOperation()).orElseThrow(() -> new ObjectNotFoundException("Operation not found with operation:" + savePermissionDTO.getOperation()));


        GrantedPermission grantedPermission = permissionRepository.findByRoleIdAndOperationId(role.getId(), operation.getId())
                .orElseThrow(() -> new ObjectNotFoundException("Operation not found with operation:" + savePermissionDTO.getOperation()+ savePermissionDTO.getRole()));
        try {
            this.permissionRepository.deleteById(grantedPermission.getId());
            return true;
        } catch (Exception e) {
            return false;
        }
    }
}

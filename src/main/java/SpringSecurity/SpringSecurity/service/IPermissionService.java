package SpringSecurity.SpringSecurity.service;

import SpringSecurity.SpringSecurity.dto.SavePermissionDTO;
import SpringSecurity.SpringSecurity.dto.ShowPermissionDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Optional;

public interface IPermissionService {
    public Page<ShowPermissionDTO> findAll(Pageable pageable);
    public Optional<ShowPermissionDTO> findOneById(Long permissionId);
    public ShowPermissionDTO addOnePermission(SavePermissionDTO savePermissionDTO);
    public boolean removeOnePermission(SavePermissionDTO savePermissionDTO);
}

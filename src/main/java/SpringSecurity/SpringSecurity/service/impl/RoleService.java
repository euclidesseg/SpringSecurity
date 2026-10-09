package SpringSecurity.SpringSecurity.service.impl;

import SpringSecurity.SpringSecurity.persistance.entity.security.Role;
import SpringSecurity.SpringSecurity.persistance.repository.security.IRoleRepository;
import SpringSecurity.SpringSecurity.service.IRoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class RoleService implements IRoleService {

    @Autowired
    private IRoleRepository iRoleRepository;

    @Value("${security.default.role}")// obtengo una propiedad desde mi archivo de propiedades
    private String defaultRole;

    @Override
    public Optional<Role> findDefaultRole() {
        return iRoleRepository.findByName(defaultRole);
    }
}

package SpringSecurity.SpringSecurity.service;

import SpringSecurity.SpringSecurity.persistance.entity.security.Role;

import java.util.Optional;

public interface IRoleService {

    Optional<Role> findDefaultRole();
}

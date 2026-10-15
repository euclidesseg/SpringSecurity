package SpringSecurity.SpringSecurity.persistance.repository.security;

import SpringSecurity.SpringSecurity.persistance.entity.security.GrantedPermission;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface IPermissionRepository extends JpaRepository<GrantedPermission, Long> {

    Optional<GrantedPermission> findByRoleIdAndOperationId(Long roleId, Long operationId);

}

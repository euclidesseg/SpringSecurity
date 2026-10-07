package SpringSecurity.SpringSecurity.persistance.repository.security;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import SpringSecurity.SpringSecurity.persistance.entity.security.Role;
import java.util.Optional;

@Repository
public interface IRoleRepository extends JpaRepository<Role, Long> {
      Optional<Role> findByName(String name);
}

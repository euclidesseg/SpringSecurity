package SpringSecurity.SpringSecurity.persistance.repository.security;

import SpringSecurity.SpringSecurity.persistance.entity.security.JwtToken;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface IJwtTokenRepository extends JpaRepository<JwtToken, Long> {
    Optional<JwtToken> findByToken(String token);
}

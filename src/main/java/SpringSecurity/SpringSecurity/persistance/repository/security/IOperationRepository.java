package SpringSecurity.SpringSecurity.persistance.repository.security;

import SpringSecurity.SpringSecurity.persistance.entity.security.Operation;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface IOperationRepository extends JpaRepository<Operation, Long> {

    @Query("SELECT o FROM Operation o where o.permit_all = true") // obtiene u na lista de operaciones cuando la columna permite_all = true
    List<Operation> findByPublicAccess();

    Optional<Operation> findByName(String name);
}

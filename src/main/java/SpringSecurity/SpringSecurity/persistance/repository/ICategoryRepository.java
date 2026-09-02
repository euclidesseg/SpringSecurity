package SpringSecurity.SpringSecurity.persistance.repository;

import SpringSecurity.SpringSecurity.persistance.entity.Category;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ICategoryRepository extends JpaRepository<Category, Long> {
}

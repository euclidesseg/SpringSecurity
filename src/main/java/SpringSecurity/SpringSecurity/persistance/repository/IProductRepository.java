package SpringSecurity.SpringSecurity.persistance.repository;

import SpringSecurity.SpringSecurity.persistance.entity.Product;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Repository;



@Repository
public interface IProductRepository extends JpaRepository<Product, Long> {

    //@PreAuthorize("hasAuthority('READ_ALL_PRODUCTS')") // basado en authorities
    @Override
    Page<Product> findAll(Pageable pageable);
}

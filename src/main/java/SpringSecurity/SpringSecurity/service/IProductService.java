package SpringSecurity.SpringSecurity.service;

import SpringSecurity.SpringSecurity.dto.SaveProductDTO;
import SpringSecurity.SpringSecurity.persistance.entity.Product;
import jakarta.validation.Valid;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Optional;


public interface IProductService {
    Page<Product> findAll(Pageable pageable);

    Optional<Product> findOneById(Long productId);

    Product createOne (SaveProductDTO saveProductDTO);

    Product updateOneById(Long productId, @Valid SaveProductDTO saveProduct);

    Product disableOneById(Long productId);
}

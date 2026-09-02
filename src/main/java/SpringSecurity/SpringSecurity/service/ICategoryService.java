package SpringSecurity.SpringSecurity.service;

import SpringSecurity.SpringSecurity.dto.SaveCategoryDTO;
import SpringSecurity.SpringSecurity.persistance.entity.Category;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Optional;

public interface ICategoryService {
    Page<Category> findAll(Pageable pageable);

    Optional<Category> findOneById(Long categoryId);

    Category createOne(SaveCategoryDTO saveCategoryDTO);

    Category updateOneById(Long categoryId, SaveCategoryDTO saveCategoryDTO);

    Category disableOneById(Long categoryId);
}

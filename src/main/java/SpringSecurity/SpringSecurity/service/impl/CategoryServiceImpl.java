package SpringSecurity.SpringSecurity.service.impl;

import SpringSecurity.SpringSecurity.dto.SaveCategoryDTO;
import SpringSecurity.SpringSecurity.exception.ObjectNotFoundException;
import SpringSecurity.SpringSecurity.persistance.entity.Category;
import SpringSecurity.SpringSecurity.persistance.repository.ICategoryRepository;
import SpringSecurity.SpringSecurity.service.ICategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class CategoryServiceImpl implements ICategoryService {

    @Autowired
    ICategoryRepository iCategoryRepository;



    @Override
    public Page<Category> findAll(Pageable pageable) {
        return iCategoryRepository.findAll(pageable);
    }

    @Override
    public Optional<Category> findOneById(Long categoryId) {
        return iCategoryRepository.findById(categoryId);
    }

    @Override
    public Category createOne(SaveCategoryDTO saveCategoryDTO) {

        Category newCategory =  new Category();
        newCategory.setName(saveCategoryDTO.getName());
        newCategory.setStatus(Category.CategoryStatus.ENABLED);

        return iCategoryRepository.save(newCategory);
    }

    @Override
    public Category updateOneById(Long categoryId, SaveCategoryDTO saveCategoryDTO) {
        Category categoryFromDB = iCategoryRepository.findById(categoryId).orElseThrow(() -> new ObjectNotFoundException("Category not found with ID "+ categoryId));
        categoryFromDB.setName(saveCategoryDTO.getName());
        return iCategoryRepository.save(categoryFromDB);
    }

    @Override
    public Category disableOneById(Long categoryId) {
        Category categoryFromDB = iCategoryRepository.findById(categoryId).orElseThrow(() -> new ObjectNotFoundException("Category not found with ID "+ categoryId));
        categoryFromDB.setStatus(Category.CategoryStatus.DISABLED);
        return this.iCategoryRepository.save(categoryFromDB);
    }
}

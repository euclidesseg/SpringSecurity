package SpringSecurity.SpringSecurity.controller;

import SpringSecurity.SpringSecurity.dto.SaveCategoryDTO;
import SpringSecurity.SpringSecurity.persistance.entity.Category;
import SpringSecurity.SpringSecurity.service.ICategoryService;
import SpringSecurity.SpringSecurity.service.impl.CategoryServiceImpl;
import jakarta.validation.Valid;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@RequestMapping("/category") // ruta base
public class CategoryController {


    public CategoryServiceImpl categoryService;
    public CategoryController(CategoryServiceImpl categoryService){
        this.categoryService = categoryService;
    }

    // @PreAuthorize("hasAnyRole('ADMINISTRATOR', 'ASSISTANT_ADMINISTRATOR')") //Basado en roles
    @PreAuthorize("hasAuthority('READ_ALL_CATEGORIES')") // basado en authorities
    @GetMapping
    public ResponseEntity<Page<Category>> findAll(Pageable pageable){
        Page<Category> categoriesPage = this.categoryService.findAll(pageable);

        if(categoriesPage.hasContent()){
            return ResponseEntity.ok(categoriesPage);
        }
        return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        //return ResponseEntity.noContent().build();
    }


    //@PreAuthorize("hasAnyRole('ADMINISTRATOR', 'ASSISTANT_ADMINISTRATOR')")
    @PreAuthorize("hasAuthority('READ_ONE_CATEGORY')")
    @GetMapping("/{categoryId}")
    public ResponseEntity<Category> findOneById(@PathVariable Long categoryId){
        Optional<Category> category = categoryService.findOneById(categoryId);
        if (category.isPresent()){
            return ResponseEntity.ok(category.get());
        }
        return ResponseEntity.noContent().build();
    }

    //@PreAuthorize("hasRole('ADMINISTRATOR')")
    @PreAuthorize("hasAuthority('CREATE_ONE_CATEGORY')")
    @PostMapping
    public ResponseEntity<Category> createOne(@RequestBody @Valid SaveCategoryDTO saveCategoryDTO){
        Category category = categoryService.createOne(saveCategoryDTO);
        // Cuando se guarda un categoria nunca devuelve null si falla solo lanza una excepción
        return ResponseEntity.status(HttpStatus.CREATED).body(category);
    }

    //@PreAuthorize("hasAnyRole('ADMINISTRATOR', 'ASSISTANT_ADMINISTRATOR')")
    @PreAuthorize("hasAuthority('UPDATE_ONE_CATEGORY')")
    @PutMapping("/{categoryId}")
    public ResponseEntity <Category> updateOneById(@PathVariable Long categoryId, @RequestBody @Valid SaveCategoryDTO saveCategoryDTO){
        Category category = this.categoryService.updateOneById(categoryId, saveCategoryDTO);
        return ResponseEntity.ok(category);
    }
    //@PreAuthorize("hasRole('ADMINISTRATOR')")
    @PreAuthorize("hasAuthority('DISABLE_ONE_CATEGORY')")
    @PutMapping("/{categoryId}/disabled")
    // creamos un controlador diferente a post delete y get
    // que permita expresar una acción específica sobre el recurso.
    public ResponseEntity <Category> disableOneById(@PathVariable Long categoryId){
        Category category = this.categoryService.disableOneById(categoryId);
        return ResponseEntity.ok(category);
    }

}

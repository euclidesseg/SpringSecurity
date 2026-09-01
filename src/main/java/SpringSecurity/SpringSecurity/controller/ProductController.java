package SpringSecurity.SpringSecurity.controller;

import SpringSecurity.SpringSecurity.dto.SaveProductDTO;
import SpringSecurity.SpringSecurity.persistance.entity.Product;
import SpringSecurity.SpringSecurity.service.IProductService;
import jakarta.validation.Valid;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@RequestMapping("/products") // ruta base
public class ProductController {


    private IProductService productService;
    public ProductController(IProductService productService){
        this.productService = productService;
    }

    @GetMapping
    // las páginas empiezan desde 0 y el tamaño de la página el que queramos
    // pageable contiene el número de la página que deseo devolver y el tamaño de la página es decir el número de registros
    /* http://localhost:4001/api/v1/products?page=0&size=5 asi sería la url para indicar el número de la página
     * y el tamaño de la página
     * https://docs.spring.io/spring-boot/docs/current/reference/html/application-properties.html
     * Documentación de como y donde cambiar los valores de los parámetros por defecto
     */

    public ResponseEntity<Page<Product>> findAll(Pageable pageable){
        Page<Product> productsPage = this.productService.findAll(pageable);

        if(productsPage.hasContent()){
            return ResponseEntity.ok(productsPage);
        }
        return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        //return ResponseEntity.noContent().build();
    }


    @GetMapping("/{productId}")
    public ResponseEntity<Product> findOneById(@PathVariable long productId){
        Optional<Product> product = productService.findOneById(productId);
        if (product.isPresent()){
            return ResponseEntity.ok(product.get());
        }
        return ResponseEntity.noContent().build();
    }

    @PostMapping
    public ResponseEntity<Product> findOneById(@RequestBody @Valid SaveProductDTO saveProductDTO){
        Product product = productService.createOne(saveProductDTO);
        // Cuando se guarda un producto nunca devuelve null si falla solo lanza una excepcion

        return ResponseEntity.status(HttpStatus.CREATED).body(product);
    }

    @PutMapping("/{productId}")
    public ResponseEntity <Product> updateOneById(@PathVariable Long productId, @RequestBody @Valid SaveProductDTO saveProductDTO){
        Product product = this.productService.updateOneById(productId, saveProductDTO);
        return ResponseEntity.ok(product);
    }
    @PutMapping("/{productId}/disabled")
    // creamos un controlador diferente a post delete y get
    // que permita expresar una acción específica sobre el recurso.
    public ResponseEntity <Product> disableOneById(@PathVariable Long productId){
        Product product = this.productService.disableOneById(productId);
        return ResponseEntity.ok(product);
    }

}

package SpringSecurity.SpringSecurity.service.impl;

import SpringSecurity.SpringSecurity.dto.SaveProductDTO;
import SpringSecurity.SpringSecurity.persistance.entity.Category;
import SpringSecurity.SpringSecurity.persistance.entity.Product;
import SpringSecurity.SpringSecurity.persistance.repository.IProductRepository;
import SpringSecurity.SpringSecurity.service.IProductService;
import SpringSecurity.SpringSecurity.interfacesimpl.SupplierImpl;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class ProductServiceImpl implements IProductService {

    private final IProductRepository productRepository;
    public ProductServiceImpl(IProductRepository productRepository){
        this.productRepository = productRepository;
    }

    @Override
    public Page<Product> findAll(Pageable pageable) {
        return this.productRepository.findAll(pageable);
    }

    @Override
    public Optional<Product> findOneById(Long productId) {
        return productRepository.findById(productId);
    }

    @Override
    public Product createOne(SaveProductDTO saveProductDTO) {
        Product newProduct = new Product();
        newProduct.setPrice(saveProductDTO.getPrice());
        newProduct.setName(saveProductDTO.getName());
        newProduct.setStatus(Product.ProductStatus.ENABLED);

        // asignar categoria primero
        Category category = new Category() ;
        category.setId(saveProductDTO.getCategoryId());
        newProduct.setCategory(category);
        // en el saveproduct viene un categoryId que se va a relacionar solo si ya existe en la base de datos
        return productRepository.save(newProduct);
    }

    @Override
    public Product updateOneById(Long productId, SaveProductDTO saveProductDTO) {
        SupplierImpl supplier = new SupplierImpl(productId);
        Product productFromDB = productRepository.findById(productId).orElseThrow(supplier);
        productFromDB.setPrice(saveProductDTO.getPrice());
        productFromDB.setName(saveProductDTO.getName());
        // asignar categoria primero
        Category category = new Category() ;
        category.setId(saveProductDTO.getCategoryId());

        return productRepository.save(productFromDB);
    }

    @Override
    public Product disableOneById(Long productId) {

        Product productFromDB = productRepository.findById(productId).orElseThrow();
        productFromDB.setStatus(Product.ProductStatus.DISABLED);
        return productRepository.save(productFromDB);
    }
    /* Supplier<T> representa una función que no recibe parámetros y produce un resultado mediante get().
     * En orElseThrow(), la lambda () -> new ObjectNotFoundException(...)
     * implementa ese get() y permite crear la excepción únicamente cuando el Optional está vacío.
    * */
}

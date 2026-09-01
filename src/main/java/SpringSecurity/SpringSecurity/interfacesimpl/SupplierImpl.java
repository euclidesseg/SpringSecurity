package SpringSecurity.SpringSecurity.interfacesimpl;

import SpringSecurity.SpringSecurity.exception.ObjectNotFoundException;

import java.util.function.Supplier;

public class SupplierImpl implements Supplier<ObjectNotFoundException> {

    private long productId;
    public  SupplierImpl(long productId){
        this.productId = productId;
    }
    @Override
    public ObjectNotFoundException get() {
        return new ObjectNotFoundException("Product not found with id" + productId);
    }
}

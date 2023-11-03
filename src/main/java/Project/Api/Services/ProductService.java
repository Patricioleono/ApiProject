package Project.Api.Services;

import Project.Api.Entity.Product;
import Project.Api.Repository.ProductRepository;
import jakarta.persistence.EntityNotFoundException;
import org.apache.coyote.Response;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.List;
import java.util.Optional;

@Service
public class ProductService {
    private final ProductRepository productRepository;

    public ProductService(ProductRepository productRepository){
        this.productRepository = productRepository;
    }

    //save data
    public ResponseEntity<Product> saveProduct(@RequestBody Product product){
        Product newProduct = productRepository.save(product);
        return ResponseEntity.ok(newProduct);
    }

    //get all data
    public ResponseEntity<List<Product>> fetchAllProducts(){
        return ResponseEntity.ok(productRepository.findAll());
    }

    //data by ID
    public ResponseEntity<Optional<Product>> fetchProductById(Long id){
        Optional<Product> product = productRepository.findById(id);
        if(product.isPresent()){
            return ResponseEntity.ok(product);
        }else{
            return ResponseEntity.notFound().build();
        }
    }

    //update data
    public ResponseEntity<Product> updateProduct(Long id, Product updateProduct){
        if(id == null){
            throw new IllegalArgumentException("El ID no puede estar Vacio");
        }

        Product Existingproduct = productRepository.findById(id).orElseThrow(
                () -> new EntityNotFoundException(String.valueOf(id)));

        Existingproduct.setName(updateProduct.getName());
        Existingproduct.setPrice(updateProduct.getPrice());
        Existingproduct.setQuantity(updateProduct.getQuantity());

        Product savedEntity = productRepository.save(Existingproduct);
        return ResponseEntity.ok(savedEntity);
    }

    public ResponseEntity<String> deleteProduct(Long id){
        productRepository.deleteById(id);
        return ResponseEntity.ok("Producto con ID = "+ id +" Eliminada con Exito");
    }
}
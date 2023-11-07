
package Project.Api.Controller;


import Project.Api.Entity.Product;
import Project.Api.Repository.ProductRepository;
import Project.Api.Services.ProductService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/v1")
public class ProductController {
    private final ProductService productService;
    private final ProductRepository productRepository;

    public ProductController(ProductService productService, ProductRepository productRepository){
        this.productService = productService;
        this.productRepository = productRepository;
    }


    //example POST HTTP
    @PostMapping("/product")
    public ResponseEntity<Product> saveProduct(@RequestBody Product product){
        Product newProduct = productRepository.save(product);
        return ResponseEntity.ok(newProduct);
    }

    //example GET HTTP
    @GetMapping("/products")
    public ResponseEntity<List<Product>> getAllProducts(){
        return productService.fetchAllProducts();
    }

    //example GET one data HTTP
    @GetMapping("/products/{id}")
    public ResponseEntity<Optional<Product>> getProductById(@PathVariable Long id){
        Optional<Product> product = productService.fetchProductById(id).getBody();
        if(product != null){
            return ResponseEntity.ok(product);
        }else{
            return ResponseEntity.notFound().build();
        }
    }

    //example PUT HTTP
    @PutMapping("/products/{productId}")
    public ResponseEntity<Product> updateProduct(@PathVariable(value = "productId")Long productId, @RequestBody Product product){
        return productService.updateProduct(productId, product);
    }

    //example DELETE HTTP
    @DeleteMapping(value = "/products/{productId}")
    public String deleteProduct(@PathVariable Long productId){
        productService.deleteProduct(productId);
        return "Producto Eliminado con el ID = " + productId;
    }
}

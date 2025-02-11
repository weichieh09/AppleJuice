package tw.com.lyls.AppleJuice.controller.v2;

import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import tw.com.lyls.AppleJuice.domain.mysql.Product;
import tw.com.lyls.AppleJuice.repository.mysql.ProductRepository;

import java.util.List;

@Slf4j
@RestController
@RequestMapping("/api/v2/product")
@Tag(name = "Product", description = "產品相關 API")
public class ProductController {

    @Autowired
    private ProductRepository productRepository;

    // 查詢產品（需要有 "VIEW" 權限）
    @PreAuthorize("hasPermission('PRODUCT', 'VIEW')")
    @GetMapping
    public List<Product> getProducts() {
        return productRepository.findAll();
    }

    // 新增產品（需要有 "CREATE" 權限）
    @PreAuthorize("hasPermission('PRODUCT', 'CREATE')")
    @PostMapping
    public Product createProduct(@RequestBody Product product) {
        return productRepository.save(product);
    }

    // 修改產品（需要有 "EDIT" 權限）
    @PreAuthorize("hasPermission('PRODUCT', 'EDIT')")
    @PutMapping("/{id}")
    public Product updateProduct(@PathVariable Long id, @RequestBody Product product) {
        product.setId(id);
        return productRepository.save(product);
    }

    // 刪除產品（需要有 "DELETE" 權限）
    @PreAuthorize("hasPermission('PRODUCT', 'DELETE')")
    @DeleteMapping("/{id}")
    public void deleteProduct(@PathVariable Long id) {
        productRepository.deleteById(id);
    }

    // 查詢單筆產品細節（需要有 "VIEW" 權限）
    @PreAuthorize("hasPermission('PRODUCT', 'VIEW')")
    @GetMapping("/{id}")
    public Product getProduct(@PathVariable Long id) {
        return productRepository.findById(id).orElseThrow(() -> new RuntimeException("Product not found"));
    }
}
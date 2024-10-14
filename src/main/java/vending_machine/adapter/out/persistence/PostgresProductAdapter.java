package vending_machine.adapter.out.persistence;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import vending_machine.application.port.out.ProductPort;
import vending_machine.domain.Product;
import vending_machine.domain.ProductEntity;
import vending_machine.domain.exception.ProductDoesNotExistException;

import java.util.List;
import java.util.Optional;

@RequiredArgsConstructor
@Component
public class PostgresProductAdapter implements ProductPort {

    private final ProductRepository productRepository;

    @Override
    public Product loadProductById(Long productId) {
        return productRepository.findById(productId)
                .map(ProductEntity::toDomain).orElseThrow(() -> new ProductDoesNotExistException());
    }

    @Override
    public Product loadProductByName(String productName) {
        return productRepository.findByProductName(productName)
                .map(ProductEntity::toDomain).orElseThrow(() -> new ProductDoesNotExistException());
    }

    @Override
    public void decreaseProductQuantity(Product product) {
        Optional<ProductEntity> productEntityOpt = productRepository.findById(product.getId());
        productEntityOpt.ifPresent(productEntity -> {
            productEntity.setQuantity(productEntity.getQuantity() - 1);
            productRepository.save(productEntity);
        });
    }

    @Override
    public void updateProductQuantity(Product product, int newQuantity) {
        Optional<ProductEntity> productEntityOpt = productRepository.findById(product.getId());
        productEntityOpt.ifPresent(productEntity -> {
            productEntity.setQuantity(newQuantity);
            productRepository.save(productEntity);
        });
    }

    @Override
    public List<Product> findAll() {
        return productRepository.findAll()
                .stream().map(ProductEntity::toDomain).toList();
    }

    @Override
    public void saveProduct(Product product) {
        ProductEntity productEntity = new ProductEntity();
        productEntity = productEntity.fromDomain(product);
        productRepository.save(productEntity);
    }
}

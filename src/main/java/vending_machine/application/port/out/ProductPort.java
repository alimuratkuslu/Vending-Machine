package vending_machine.application.port.out;

import vending_machine.domain.Product;

import java.util.List;

public interface ProductPort {

    Product loadProductById(Long productId);

    Product loadProductByName(String productName);
    void decreaseProductQuantity(Product product);
    void updateProductQuantity(Product product, int newQuantity);
    List<Product> findAll();

    void saveProduct(Product product);
}

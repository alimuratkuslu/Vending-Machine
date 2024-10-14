package vending_machine.domain;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "product")
@NoArgsConstructor
@AllArgsConstructor
@Data
public class ProductEntity {


    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true)
    private String productName;

    @Column(nullable = false)
    private int price;

    @Column(nullable = false)
    private int quantity;

    public ProductEntity fromDomain(Product product) {
        return new ProductEntity(product.getId(), product.getProductName(), product.getPrice(), product.getQuantity());
    }

    public Product toDomain() {
        return new Product(this.id, this.productName, this.price, this.quantity);
    }
}

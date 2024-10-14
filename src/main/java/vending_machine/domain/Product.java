package vending_machine.domain;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.Data;
import lombok.NoArgsConstructor;
import vending_machine.domain.exception.InsufficientFundsException;

@Data
@Entity
@NoArgsConstructor
public class Product {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String productName;
    private int price;
    private int quantity;

    public Product(Long id, String productName, int price, int quantity) {
        this.id = id;
        this.productName = productName;
        this.price = price;
        this.quantity = quantity;
    }

    public boolean isInStock() {
        return quantity > 0;
    }

    public void decreaseQuantity(int amount) {
        if (quantity >= amount) {
            this.quantity -= amount;
        } else {
            throw new InsufficientFundsException();
        }
    }
}

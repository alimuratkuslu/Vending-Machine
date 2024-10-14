package vending_machine.application.port.in;

import vending_machine.domain.Product;
import vending_machine.domain.Transaction;

import java.util.List;

public interface VendingMachineUseCase {

    Transaction purchaseProduct(String productName, int payment);
    void restockProduct(Long productId, int quantity);
    List<Product> checkInventory();

    void saveProduct(Product product);

    int getTotalMoney();
}

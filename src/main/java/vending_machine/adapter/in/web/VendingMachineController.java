package vending_machine.adapter.in.web;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import vending_machine.application.port.in.VendingMachineUseCase;
import vending_machine.domain.Product;
import vending_machine.domain.dto.PurchaseProductDto;
import vending_machine.domain.Transaction;
import vending_machine.domain.dto.RestockProductDto;
import vending_machine.domain.exception.InsufficientFundsException;
import vending_machine.domain.exception.ProductNotAvailableException;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/vending-machine")
@CrossOrigin(origins = "http://localhost:3000")
public class VendingMachineController {

    private final VendingMachineUseCase vendingMachineUseCase;

    @PostMapping("/purchase")
    public ResponseEntity<Object> purchaseProduct(@RequestBody PurchaseProductDto purchaseProductDto) {
        try {
            Transaction transaction = vendingMachineUseCase.purchaseProduct(purchaseProductDto.getProductName(), purchaseProductDto.getPayment());
            return ResponseEntity.ok(transaction);
        } catch (ProductNotAvailableException ex) {
            return ResponseEntity.status(404).body(ex.getMessage());
        } catch (InsufficientFundsException ex) {
            return ResponseEntity.status(400).body(ex.getMessage());
        } catch (Exception ex) {
            return ResponseEntity.status(500).body(ex.getMessage());
        }
    }

    @PostMapping("/save")
    public ResponseEntity<String> saveProduct(@RequestBody Product product) {
        vendingMachineUseCase.saveProduct(product);
        return ResponseEntity.ok("Product with name " + product.getProductName() + " saved");
    }

    @PutMapping("/restock")
    public ResponseEntity<Void> restockProduct(@RequestBody RestockProductDto restockProductDto) {
        vendingMachineUseCase.restockProduct(restockProductDto.getProductId(), restockProductDto.getQuantity());
        return ResponseEntity.ok().build();
    }

    @GetMapping("/products")
    public ResponseEntity<List<Product>> getAllProducts() {
        return ResponseEntity.ok(vendingMachineUseCase.checkInventory());
    }

    @GetMapping("/total-money")
    public ResponseEntity<Integer> getTotalMoney() {
        return ResponseEntity.ok(vendingMachineUseCase.getTotalMoney());
    }
}

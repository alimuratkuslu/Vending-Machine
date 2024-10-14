package vending_machine.application.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import vending_machine.adapter.out.persistence.TransactionRepository;
import vending_machine.application.port.in.VendingMachineUseCase;
import vending_machine.application.port.out.ProductPort;
import vending_machine.application.port.out.TransactionPort;
import vending_machine.domain.*;
import vending_machine.domain.exception.InsufficientFundsException;
import vending_machine.domain.exception.ProductNotAvailableException;

import java.util.ArrayList;
import java.util.List;

@RequiredArgsConstructor
@Service
public class VendingMachineService implements VendingMachineUseCase {

    private final ProductPort productPort;
    private final TransactionPort transactionPort;

    @Override
    public Transaction purchaseProduct(String productName, int payment) {
        Product product = productPort.loadProductByName(productName);
        if (product == null || product.getQuantity() <= 0) {
            throw new ProductNotAvailableException();
        }

        if (payment < product.getPrice()) {
            throw new InsufficientFundsException();
        }

        productPort.decreaseProductQuantity(product);
        int change = payment - product.getPrice();

        Change changeDetails = calculateChange(change);

        Transaction transaction = new Transaction(product, payment, product.getPrice(), changeDetails.getCoins(), changeDetails.getNotes());
        transactionPort.saveTransaction(transaction);
        return transaction;
    }

    @Override
    public void restockProduct(Long productId, int quantity) {
        Product product = productPort.loadProductById(productId);
        productPort.updateProductQuantity(product, quantity);
    }

    @Override
    public List<Product> checkInventory() {
        return productPort.findAll();
    }

    @Override
    public void saveProduct(Product product) {
        productPort.saveProduct(product);
    }

    @Override
    public int getTotalMoney() {
        return transactionPort.getTotalMoney();
    }

    private Change calculateChange(int changeAmount) {
        List<Coin> coinChange = new ArrayList<>();
        List<BankNote> bankNoteChange = new ArrayList<>();

        for (BankNote bankNote : BankNote.values()) {
            while (changeAmount >= bankNote.getValue()) {
                bankNoteChange.add(bankNote);
                changeAmount -= bankNote.getValue();
            }
        }

        for (Coin coin : Coin.values()) {
            while (changeAmount >= coin.getValue()) {
                coinChange.add(coin);
                changeAmount -= coin.getValue();
            }
        }

        if (changeAmount > 0) {
            throw new IllegalArgumentException("Unable to provide exact change.");
        }

        return new Change(coinChange, bankNoteChange);
    }
}

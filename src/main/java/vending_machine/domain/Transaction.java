package vending_machine.domain;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Transaction {

    @Id
    @Column(name = "id", nullable = false)
    private Long id;
    private Product product;
    private int amountPaid;
    private List<Coin> coins;
    private List<BankNote> notes;

    public Transaction(Product product, int amountPaid, List<Coin> coins, List<BankNote> notes) {
        this.product = product;
        this.amountPaid = amountPaid;
        this.coins = coins;
        this.notes = notes;
    }
}

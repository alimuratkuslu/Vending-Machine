package vending_machine.domain;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Entity
@Table(name = "transaction")
@NoArgsConstructor
@AllArgsConstructor
@Data
public class TransactionEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "product_id", nullable = false)
    private Product product;
    private int amountPaid;
    private List<Coin> coins;
    private List<BankNote> notes;

    public TransactionEntity fromDomain(Transaction transaction) {
        return new TransactionEntity(
                transaction.getId(),
                transaction.getProduct(),
                transaction.getAmountPaid(),
                transaction.getCoins(),
                transaction.getNotes());
    }

    public Transaction toDomain() {
        return new Transaction(
                this.id,
                this.product,
                this.amountPaid,
                this.getCoins(),
                this.getNotes());
    }
}

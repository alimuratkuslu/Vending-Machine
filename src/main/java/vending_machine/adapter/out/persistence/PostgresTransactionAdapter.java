package vending_machine.adapter.out.persistence;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import vending_machine.application.port.out.TransactionPort;
import vending_machine.domain.Transaction;
import vending_machine.domain.TransactionEntity;

@RequiredArgsConstructor
@Component
public class PostgresTransactionAdapter implements TransactionPort {

    private final TransactionRepository transactionRepository;

    @Override
    public void saveTransaction(Transaction transaction) {
        TransactionEntity transactionEntity = new TransactionEntity();
        transactionEntity = transactionEntity.fromDomain(transaction);
        transactionRepository.save(transactionEntity);
    }

    @Override
    public int getTotalMoney() {
        return transactionRepository.findAll()
                .stream()
                .map(TransactionEntity::toDomain)
                .mapToInt(Transaction::getProductPrice).sum();
    }
}

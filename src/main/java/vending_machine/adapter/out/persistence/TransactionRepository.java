package vending_machine.adapter.out.persistence;

import org.springframework.data.jpa.repository.JpaRepository;
import vending_machine.domain.TransactionEntity;

public interface TransactionRepository extends JpaRepository<TransactionEntity, Long> {
}

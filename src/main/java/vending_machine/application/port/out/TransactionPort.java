package vending_machine.application.port.out;

import vending_machine.domain.Transaction;

public interface TransactionPort {

    void saveTransaction(Transaction transaction);
    int getTotalMoney();
}

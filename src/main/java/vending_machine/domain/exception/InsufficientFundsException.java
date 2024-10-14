package vending_machine.domain.exception;

public class InsufficientFundsException extends RuntimeException {
    public InsufficientFundsException() {
        super("Insufficient funds provided for the transaction");
    }
}

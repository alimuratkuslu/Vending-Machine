package vending_machine.domain.exception;

public class ProductDoesNotExistException extends RuntimeException {

    public ProductDoesNotExistException() {
        super("Product does not exist");
    }
}

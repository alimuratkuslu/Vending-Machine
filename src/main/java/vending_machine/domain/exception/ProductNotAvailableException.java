package vending_machine.domain.exception;

public class ProductNotAvailableException extends RuntimeException {
    public ProductNotAvailableException() {
        super("Product is out of stock");
    }
}

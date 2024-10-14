package vending_machine.domain;

public enum Coin {

    HUNDRED_CENTS(100),
    FIFTY_CENTS(50),
    TWENTY_FIVE_CENTS(25),
    TEN_CENTS(10),
    FIVE_CENTS(5),
    ONE_CENT(1);

    private final int value;

    Coin(int value) {
        this.value = value;
    }

    public int getValue() {
        return value;
    }
}

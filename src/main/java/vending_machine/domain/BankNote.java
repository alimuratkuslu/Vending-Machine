package vending_machine.domain;

public enum BankNote {

    TWO_HUNDRED(20000),
    HUNDRED(10000),
    FIFTY(5000),
    TWENTY(2000),
    TEN(1000),
    FIVE(500);

    private final int value;

    BankNote(int value) {
        this.value = value;
    }

    public int getValue() {
        return value;
    }
}

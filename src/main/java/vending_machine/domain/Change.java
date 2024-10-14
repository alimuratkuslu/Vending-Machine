package vending_machine.domain;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Change {

    private List<Coin> coins;
    private List<BankNote> notes;
}

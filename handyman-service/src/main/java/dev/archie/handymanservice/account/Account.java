package dev.archie.handymanservice.account;

import dev.archie.handymanservice.account.bank.Bank;
import lombok.Builder;
import lombok.Data;

@Builder(toBuilder = true)
@Data
public class Account {

    private int id;

    private String cardNumber;

    private PaymentSystem paymentSystem;

    private Bank bank;

}

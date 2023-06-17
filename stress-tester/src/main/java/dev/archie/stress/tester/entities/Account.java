package dev.archie.stress.tester.entities;

import lombok.Data;

@Data
public class Account {
    private Long id;

    private String cardNumber;

    private PaymentSystem paymentSystem;

    private Bank bank;
}

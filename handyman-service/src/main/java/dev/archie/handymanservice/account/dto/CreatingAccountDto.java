package dev.archie.handymanservice.account.dto;

import dev.archie.handymanservice.account.PaymentSystem;
import lombok.Data;

import javax.persistence.EnumType;
import javax.persistence.Enumerated;

@Data
public class CreatingAccountDto {

    private String cardNumber;

    @Enumerated(EnumType.STRING)
    private PaymentSystem paymentSystem;

    private String bankName;
}

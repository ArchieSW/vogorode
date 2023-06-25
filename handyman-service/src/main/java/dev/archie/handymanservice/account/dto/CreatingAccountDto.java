package dev.archie.handymanservice.account.dto;

import dev.archie.handymanservice.account.PaymentSystem;
import lombok.Data;

@Data
public class CreatingAccountDto {

    private String cardNumber;

    private PaymentSystem paymentSystem;

    private String bankName;
}

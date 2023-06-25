package dev.archie.landscapeservice.account.dto;

import dev.archie.landscapeservice.account.PaymentSystem;
import lombok.Data;

@Data
public class CreatingAccountDto {

    private String cardNumber;

    private PaymentSystem paymentSystem;

    private String bankName;
}

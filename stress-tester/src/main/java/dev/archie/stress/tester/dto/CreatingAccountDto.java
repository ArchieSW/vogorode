package dev.archie.stress.tester.dto;

import lombok.Builder;
import lombok.Data;

import dev.archie.stress.tester.entities.PaymentSystem;

@Data
@Builder
public class CreatingAccountDto {

    private String cardNumber;

    private PaymentSystem paymentSystem;

    private String bankName;
}

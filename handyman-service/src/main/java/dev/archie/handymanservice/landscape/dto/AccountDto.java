package dev.archie.handymanservice.landscape.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import dev.archie.handymanservice.account.PaymentSystem;
import lombok.Data;

@Data
public class AccountDto {

    private Long id;

    private String cardNumber;

    private PaymentSystem paymentSystem;

    private BankDto bank;

    @JsonIgnoreProperties("accounts")
    private HandymanDto handyman;

}

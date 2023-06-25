package dev.archie.handymanservice.account;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import dev.archie.handymanservice.account.bank.Bank;
import dev.archie.handymanservice.handyman.Handyman;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.DocumentReference;

@Builder(toBuilder = true)
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Document(collection = "accounts")
public class Account {
    @Id
    private String id;

    private Long innerId;

    @JsonIgnoreProperties("accounts")
    @DocumentReference
    private Handyman handyman;

    private String cardNumber;

    private PaymentSystem paymentSystem;

    @DocumentReference
    private Bank bank;

}

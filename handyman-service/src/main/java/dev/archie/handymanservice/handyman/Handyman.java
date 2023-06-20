package dev.archie.handymanservice.handyman;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import dev.archie.handymanservice.account.Account;
import dev.archie.handymanservice.handyman.skill.Skill;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.DocumentReference;

import java.util.List;
import java.util.UUID;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Document
public class Handyman {

    @Id
    private String id;

    private UUID innerId;

    private Double latitude;

    private Double longitude;

    private String firstName;

    private String lastName;

    private String email;

    private String phone;

    private byte[] photo;

    @JsonIgnoreProperties("handyman")
    @DocumentReference
    private List<Account> accounts;

    @JsonIgnoreProperties("handyman")
    @DocumentReference
    private List<Skill> skills;

}

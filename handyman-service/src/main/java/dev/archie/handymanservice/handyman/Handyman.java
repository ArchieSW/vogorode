package dev.archie.handymanservice.handyman;

import lombok.Builder;
import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.List;
import java.util.UUID;

@Data
@Builder
@Document
public class Handyman {

    @Id
    private String id;

    private UUID innerId;

    private Double latitude;

    private Double longitude;

    private List<String> skills;

}

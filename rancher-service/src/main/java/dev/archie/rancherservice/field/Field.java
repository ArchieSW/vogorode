package dev.archie.rancherservice.field;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import dev.archie.rancherservice.rancher.Gardener;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.DocumentReference;

import java.util.List;

@Builder
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Document(collection = "fields")
public class Field {

    @Id
    private String id;

    private Double latitude;

    private Double longitude;

    private Double area;

    private List<String> jobs;

    private String address;

    @JsonIgnoreProperties(value = "fields")
    @DocumentReference
    private Gardener gardener;
}

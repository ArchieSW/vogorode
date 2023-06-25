package dev.archie.rancherservice.rancher;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonSetter;
import com.fasterxml.jackson.annotation.Nulls;
import dev.archie.rancherservice.field.Field;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.DocumentReference;

import javax.persistence.Id;
import java.util.List;
import java.util.UUID;

@Builder
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Document(collection = "gardeners")
public class Gardener {

    @Id
    private String id;

    private UUID innerId;

    private String firstName;

    private String lastName;

    private String email;

    private String phone;

    private String login;

    @JsonIgnoreProperties(value = "gardener")
    @DocumentReference
    private List<Field> fields;

    @JsonSetter(nulls = Nulls.AS_EMPTY)
    public void setFields(List<Field> fields) {
        this.fields = fields;
    }

}
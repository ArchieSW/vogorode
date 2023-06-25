package dev.archie.landscapeservice.gardener;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonSetter;
import com.fasterxml.jackson.annotation.Nulls;
import dev.archie.landscapeservice.field.Field;
import dev.archie.landscapeservice.user.User;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import java.util.List;

@Getter
@Setter
@Entity
@Table(name = "gardener")
public class Gardener extends User {

    @JsonIgnoreProperties(value = "gardener")
    @OneToMany(mappedBy = "gardener", cascade = CascadeType.REMOVE, orphanRemoval = true)
    private List<Field> fields;

    @JsonSetter(nulls = Nulls.AS_EMPTY)
    public void setFields(List<Field> fields) {
        this.fields = fields;
    }

}
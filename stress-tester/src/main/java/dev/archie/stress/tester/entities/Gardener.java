package dev.archie.stress.tester.entities;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Gardener {

    private Long id;

    private String firstName;

    private String lastName;

    private String email;

    private String phone;

    private List<Field> fields;
}
package dev.archie.stress.tester.entities;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class UserType {
    public static final long HANDYMAN_USER_TYPE_ID = 2;
    public static final long RANCHER_USER_TYPE_ID = 1;

    private Long id;
    private String type;
}

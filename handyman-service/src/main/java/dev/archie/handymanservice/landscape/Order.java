package dev.archie.handymanservice.landscape;


import dev.archie.handymanservice.handyman.skill.Skill;
import dev.archie.handymanservice.landscape.dto.Field;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.Set;

@Data
public class Order {
    private Long id;

    private WorkType workType;

    private WorkStatus status;

    private LocalDateTime createdAt;

    private Field field;

    private Set<Skill> skills;

}

package dev.archie.rancherservice.landscape.dto;


import dev.archie.rancherservice.landscape.User;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.List;

@Data
public class OrderDto {
    private Long id;

    private WorkType workType;

    private WorkStatus status;

    private LocalDateTime createdAt;

    private FieldDto field;

    private User user;

    private List<SkillDto> skills;

    private int grade;
}

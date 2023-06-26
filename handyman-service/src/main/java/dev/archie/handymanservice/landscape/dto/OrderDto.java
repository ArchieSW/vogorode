package dev.archie.handymanservice.landscape.dto;


import dev.archie.handymanservice.landscape.User;
import dev.archie.handymanservice.landscape.WorkStatus;
import dev.archie.handymanservice.landscape.WorkType;
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
}

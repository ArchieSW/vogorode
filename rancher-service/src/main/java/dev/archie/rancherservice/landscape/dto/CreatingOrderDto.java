package dev.archie.rancherservice.landscape.dto;

import lombok.Builder;
import lombok.Data;

import java.util.List;
import java.util.UUID;

@Data
@Builder
public class CreatingOrderDto {

    private WorkType workType;

    private WorkStatus status;

    private Long fieldId;

    private UUID userId;

    private List<String> skills;

    private int grade;
}

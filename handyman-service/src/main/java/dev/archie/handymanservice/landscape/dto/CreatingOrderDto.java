package dev.archie.handymanservice.landscape.dto;

import dev.archie.handymanservice.landscape.WorkStatus;
import dev.archie.handymanservice.landscape.WorkType;
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
}

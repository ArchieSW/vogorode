package dev.archie.landscapeservice.order.dto;

import dev.archie.landscapeservice.order.WorkStatus;
import dev.archie.landscapeservice.order.WorkType;
import lombok.Data;

import java.util.UUID;

@Data
public class CreatingOrderDto {

    private WorkType workType;

    private WorkStatus status;

    private Long fieldId;

    private UUID userId;

}

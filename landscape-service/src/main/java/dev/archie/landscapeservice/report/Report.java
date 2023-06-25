package dev.archie.landscapeservice.report;

import dev.archie.landscapeservice.handyman.Handyman;
import dev.archie.landscapeservice.order.WorkType;
import dev.archie.landscapeservice.user.User;
import lombok.Data;

import java.util.List;

@Data
public class Report {
    List<WorkType> works;
    List<Handyman> workers;
    Boolean isWorkDeficit;
}

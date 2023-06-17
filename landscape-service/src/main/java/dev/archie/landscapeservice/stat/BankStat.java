package dev.archie.landscapeservice.stat;

import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@Builder
@NoArgsConstructor
public class BankStat {

    private String name;
    private LocalDateTime earliestCreationDate;
    private LocalDateTime latestCreationDate;

    public BankStat(String name, LocalDateTime earliestCreationDate, LocalDateTime latestCreationDate) {
        this.name = name;
        this.earliestCreationDate = earliestCreationDate;
        this.latestCreationDate = latestCreationDate;
    }
}

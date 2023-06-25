package dev.archie.landscapeservice.stat;

import lombok.Data;

import java.util.UUID;

@Data
public class RancherStat {
    private UUID gardenerId;
    private String login;
    private double minPlotSize;
    private double maxPlotSize;
    private double averagePlotArea;

    public RancherStat(UUID gardenerId, String login, double minPlotSize, double maxPlotSize, double averagePlotArea) {
        this.gardenerId = gardenerId;
        this.login = login;
        this.minPlotSize = minPlotSize;
        this.maxPlotSize = maxPlotSize;
        this.averagePlotArea = averagePlotArea;
    }
}

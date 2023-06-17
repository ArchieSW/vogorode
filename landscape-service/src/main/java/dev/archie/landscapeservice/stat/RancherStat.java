package dev.archie.landscapeservice.stat;

import lombok.Data;

@Data
public class RancherStat {
    private Long gardenerId;
    private String login;
    private double minPlotSize;
    private double maxPlotSize;
    private double averagePlotArea;

    public RancherStat(Long gardenerId, String login, double minPlotSize, double maxPlotSize, double averagePlotArea) {
        this.gardenerId = gardenerId;
        this.login = login;
        this.minPlotSize = minPlotSize;
        this.maxPlotSize = maxPlotSize;
        this.averagePlotArea = averagePlotArea;
    }
}

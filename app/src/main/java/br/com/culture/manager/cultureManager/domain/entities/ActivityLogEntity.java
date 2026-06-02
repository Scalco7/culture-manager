package br.com.culture.manager.cultureManager.domain.entities;

import java.time.LocalDateTime;

public class ActivityLogEntity {
    private String name;
    private PlotEntity plot;
    private Integer timeSpent;
    private LocalDateTime date;

    public ActivityLogEntity(String name, LocalDateTime date, PlotEntity plot, Integer timeSpent) {
        this.name = name;
        this.date = date;
        this.plot = plot;
        this.timeSpent = timeSpent;
    }

    public String getName(){
        return name;
    }

    public void setName(String name){
        this.name = name;
    }

    public LocalDateTime getDate() {
        return date;
    }

    public void setDate(LocalDateTime date) {
        this.date = date;
    }

    public PlotEntity getPlot() {
        return plot;
    }

    public void setPlot(PlotEntity plot) {
        this.plot = plot;
    }

    public Integer getTimeSpent() {
        return timeSpent;
    }

    public void setTimeSpent(Integer timeSpent) {
        this.timeSpent = timeSpent;
    }
}

package br.com.culture.manager.cultureManager.ui.models;

import java.time.LocalDateTime;

import br.com.culture.manager.cultureManager.domain.entities.PlotEntity;

public class ActivityLogModel {
    private long id;
    private PlotEntity plot;
    private String name;
    private Integer timeSpent;
    private LocalDateTime date;

    public ActivityLogModel(long id, PlotEntity plot, String name, Integer timeSpent, LocalDateTime date) {
        this.date = date;
        this.id = id;
        this.name = name;
        this.plot = plot;
        this.timeSpent = timeSpent;
    }

    public LocalDateTime getDate() {
        return date;
    }

    public void setDate(LocalDateTime date) {
        this.date = date;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public Integer getTimeSpent() {
        return timeSpent;
    }

    public void setTimeSpent(Integer timeSpent) {
        this.timeSpent = timeSpent;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public PlotEntity getPlot() {
        return plot;
    }

    public void setPlot(PlotEntity plot) {
        this.plot = plot;
    }
}

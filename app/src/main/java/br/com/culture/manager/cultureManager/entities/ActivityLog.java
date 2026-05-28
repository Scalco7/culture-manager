package br.com.culture.manager.cultureManager.entities;

import java.time.LocalDateTime;

public class ActivityLog {
    private Plot plot;
    private Integer timeSpent;
    private LocalDateTime date;

    public ActivityLog(LocalDateTime date, Plot plot, Integer timeSpent) {
        this.date = date;
        this.plot = plot;
        this.timeSpent = timeSpent;
    }

    public LocalDateTime getDate() {
        return date;
    }

    public void setDate(LocalDateTime date) {
        this.date = date;
    }

    public Plot getPlot() {
        return plot;
    }

    public void setPlot(Plot plot) {
        this.plot = plot;
    }

    public Integer getTimeSpent() {
        return timeSpent;
    }

    public void setTimeSpent(Integer timeSpent) {
        this.timeSpent = timeSpent;
    }
}

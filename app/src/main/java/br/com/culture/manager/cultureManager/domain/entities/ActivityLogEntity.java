package br.com.culture.manager.cultureManager.domain.entities;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

import java.time.LocalDateTime;

@Entity(tableName = "activity_log")
public class ActivityLogEntity {
    @PrimaryKey(autoGenerate = true)
    private long id;
    private String name;
//    private PlotEntity plot;
    private Integer timeSpent;
    private LocalDateTime date;

    public ActivityLogEntity(long id, String name, LocalDateTime date, Integer timeSpent) {
        this.id = id;
        this.name = name;
        this.date = date;
//        this.plot = plot;
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

//    public PlotEntity getPlot() {
//        return plot;
//    }
//
//    public void setPlot(PlotEntity plot) {
//        this.plot = plot;
//    }

    public Integer getTimeSpent() {
        return timeSpent;
    }

    public void setTimeSpent(Integer timeSpent) {
        this.timeSpent = timeSpent;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }
}

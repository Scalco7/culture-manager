package br.com.culture.manager.cultureManager.domain.entities;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.ForeignKey;
import androidx.room.Ignore;
import androidx.room.PrimaryKey;

import java.time.LocalDateTime;

@Entity(tableName = "activity_log", foreignKeys = {
        @ForeignKey(
                entity = PlotEntity.class,
                parentColumns = "id",
                childColumns = "plot_id")
})
public class ActivityLogEntity {
    @PrimaryKey(autoGenerate = true)
    private long id;
    @ColumnInfo(name = "plot_id")
    private long plotId;
    @NonNull
    private String name;
    @NonNull
    @ColumnInfo(name = "time_spent")
    private Integer timeSpent;
    @NonNull
    private LocalDateTime date;

    public ActivityLogEntity(long id, long plotId, @NonNull String name, @NonNull LocalDateTime date, @NonNull Integer timeSpent) {
        this.id = id;
        this.plotId = plotId;
        this.name = name;
        this.date = date;
        this.timeSpent = timeSpent;
    }

    @Ignore
    public ActivityLogEntity(long plotId, @NonNull String name, @NonNull LocalDateTime date, @NonNull Integer timeSpent) {
        this.plotId = plotId;
        this.name = name;
        this.date = date;
        this.timeSpent = timeSpent;
    }

    @NonNull
    public String getName() {
        return name;
    }

    public void setName(@NonNull String name) {
        this.name = name;
    }

    @NonNull
    public LocalDateTime getDate() {
        return date;
    }

    public void setDate(@NonNull LocalDateTime date) {
        this.date = date;
    }

    @NonNull
    public Integer getTimeSpent() {
        return timeSpent;
    }

    public void setTimeSpent(@NonNull Integer timeSpent) {
        this.timeSpent = timeSpent;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public long getPlotId() {
        return plotId;
    }

    public void setPlotId(long plotId) {
        this.plotId = plotId;
    }
}

package br.com.culture.manager.cultureManager.domain.entities;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.Ignore;
import androidx.room.PrimaryKey;

@Entity(tableName = "plot")
public class PlotEntity {
    @PrimaryKey(autoGenerate = true)
    private long id;
    @NonNull
    private String name;
    @NonNull
    @ColumnInfo(name = "area_size")
    private Float areaSize;

    public PlotEntity(long id, @NonNull String name, @NonNull Float areaSize) {
        this.id = id;
        this.name = name;
        this.areaSize = areaSize;
    }

    @Ignore
    public PlotEntity(@NonNull String name, @NonNull Float areaSize) {
        this.name = name;
        this.areaSize = areaSize;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    @NonNull
    public Float getAreaSize() {
        return areaSize;
    }

    public void setAreaSize(@NonNull Float areaSize) {
        this.areaSize = areaSize;
    }

    @NonNull
    public String getName() {
        return name;
    }

    public void setName(@NonNull String name) {
        this.name = name;
    }

    @NonNull
    @Override
    public String toString() {
        return name;
    }
}

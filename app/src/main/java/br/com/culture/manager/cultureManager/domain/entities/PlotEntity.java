package br.com.culture.manager.cultureManager.domain.entities;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "plot")
public class PlotEntity {
    @PrimaryKey(autoGenerate = true)
    private long id;
    private String name;
    private Float areaSize;
//    private FarmEntity farm;

    public PlotEntity(long id, String name, Float areaSize) {
        this.id = id;
        this.name = name;
        this.areaSize = areaSize;
    }

    public Float getAreaSize() {
        return areaSize;
    }

    public void setAreaSize(Float areaSize) {
        this.areaSize = areaSize;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }
}

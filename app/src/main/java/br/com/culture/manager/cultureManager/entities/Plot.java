package br.com.culture.manager.cultureManager.entities;

public class Plot {
    private String name;
    private Float areaSize;

    public Plot(String name, Float areaSize) {
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
}

package br.com.culture.manager.cultureManager.entities;

import java.time.LocalDateTime;

import br.com.culture.manager.cultureManager.enums.WindStrength;

public class Weather {
    private String name;
    private WindStrength windStrength;

    private String weather;

    private LocalDateTime date;

    public Weather(WindStrength windStrength, String name, String weather, LocalDateTime date) {
        this.windStrength = windStrength;
        this.name = name;
        this.weather = weather;
        this.date = date;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getWeather() {
        return weather;
    }

    public void setWeather(String weather) {
        this.weather = weather;
    }

    public LocalDateTime getDate() {
        return date;
    }

    public void setDate(LocalDateTime date) {
        this.date = date;
    }

    public WindStrength getWindStrength() {
        return windStrength;
    }

    public void setWindStrength(WindStrength windStrength) {
        this.windStrength = windStrength;
    }
}

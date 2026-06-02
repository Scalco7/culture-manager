package br.com.culture.manager.cultureManager.entities;

import java.time.LocalDateTime;
import java.util.Comparator;

import br.com.culture.manager.cultureManager.enums.WindStrength;

public class WeatherEntity {
    private String name;
    private WindStrength windStrength;

    private String weather;

    private LocalDateTime date;

    public WeatherEntity(WindStrength windStrength, String name, String weather) {
        this.windStrength = windStrength;
        this.name = name;
        this.weather = weather;
        this.date = LocalDateTime.now();
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

    public static Comparator<WeatherEntity> weatherAsc() {
        return new Comparator<WeatherEntity>() {
            @Override
            public int compare(WeatherEntity w1, WeatherEntity w2) {
                return w1.getDate().compareTo(w2.getDate());
            }
        };
    }

    public static Comparator<WeatherEntity> weatherDesc() {
        return new Comparator<WeatherEntity>() {
            @Override
            public int compare(WeatherEntity w1, WeatherEntity w2) {
                return w2.getDate().compareTo(w1.getDate());
            }
        };
    }
}

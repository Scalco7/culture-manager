package br.com.culture.manager.cultureManager.domain.entities;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.Ignore;
import androidx.room.PrimaryKey;

import java.time.LocalDateTime;

import br.com.culture.manager.cultureManager.domain.enums.WindStrength;

@Entity(tableName = "weather")
public class WeatherEntity {
    @PrimaryKey(autoGenerate = true)
    private long id;
    @NonNull
    private String name;
    @ColumnInfo(name = "wind_strength")
    private WindStrength windStrength;
    @NonNull
    private String weather;
    @NonNull
    private LocalDateTime date;

    @Ignore
    public WeatherEntity(WindStrength windStrength, @NonNull String name, @NonNull String weather) {
        this.windStrength = windStrength;
        this.name = name;
        this.weather = weather;
        this.date = LocalDateTime.now();
    }

    public WeatherEntity(@NonNull LocalDateTime date, long id, @NonNull String name, @NonNull String weather, WindStrength windStrength) {
        this.date = date;
        this.id = id;
        this.name = name;
        this.weather = weather;
        this.windStrength = windStrength;
    }

    @NonNull
    public String getName() {
        return name;
    }

    public void setName(@NonNull String name) {
        this.name = name;
    }

    @NonNull
    public String getWeather() {
        return weather;
    }

    public void setWeather(@NonNull String weather) {
        this.weather = weather;
    }

    @NonNull
    public LocalDateTime getDate() {
        return date;
    }

    public void setDate(@NonNull LocalDateTime date) {
        this.date = date;
    }

    public WindStrength getWindStrength() {
        return windStrength;
    }

    public void setWindStrength(WindStrength windStrength) {
        this.windStrength = windStrength;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }
}

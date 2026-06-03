package br.com.culture.manager.cultureManager.data.data_access_objects;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import java.util.List;

import br.com.culture.manager.cultureManager.domain.entities.WeatherEntity;

@Dao
public interface WeatherDAO {
    @Insert
    void insert(WeatherEntity weatherEntity);

    @Update
    void update(WeatherEntity weatherEntity);

    @Delete
    void delete(WeatherEntity weatherEntity);

    @Query("SELECT * FROM weather ORDER BY date DESC")
    List<WeatherEntity> getAllRecents();

    @Query("SELECT * FROM weather ORDER BY date ASC")
    List<WeatherEntity> getAllOlders();
}

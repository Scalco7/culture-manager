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
    long insert(WeatherEntity weatherEntity);

    @Update
    int update(WeatherEntity weatherEntity);

    @Delete
    int delete(WeatherEntity weatherEntity);

    @Query("SELECT * FROM weather ORDER BY date DESC")
    List<WeatherEntity> getAllRecents();

    @Query("SELECT * FROM weather ORDER BY date ASC")
    List<WeatherEntity> getAllOlders();

    @Query("SELECT * FROM weather WHERE id = :id")
    WeatherEntity getById(long id);
}

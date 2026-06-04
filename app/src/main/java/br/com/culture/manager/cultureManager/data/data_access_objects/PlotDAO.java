package br.com.culture.manager.cultureManager.data.data_access_objects;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import java.util.List;

import br.com.culture.manager.cultureManager.domain.entities.PlotEntity;

@Dao
public interface PlotDAO {
    @Insert
    long insert(PlotEntity plotEntity);

    @Update
    int update(PlotEntity plotEntity);

    @Delete
    int delete(PlotEntity plotEntity);

    @Query("SELECT * FROM plot")
    List<PlotEntity> getAll();

    @Query("SELECT * FROM plot WHERE id = :id")
    PlotEntity getById(long id);
}

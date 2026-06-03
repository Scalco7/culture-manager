package br.com.culture.manager.cultureManager.data.data_access_objects;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import java.util.List;

import br.com.culture.manager.cultureManager.domain.entities.FarmEntity;

@Dao
public interface FarmDAO{
    @Insert
    long insert(FarmEntity farmEntity);

    @Update
    int update(FarmEntity farmEntity);

    @Query("SELECT * FROM farm")
    List<FarmEntity> getAll();
}

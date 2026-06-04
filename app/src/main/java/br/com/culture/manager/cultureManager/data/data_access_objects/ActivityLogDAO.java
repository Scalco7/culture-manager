package br.com.culture.manager.cultureManager.data.data_access_objects;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import java.util.List;

import br.com.culture.manager.cultureManager.domain.entities.ActivityLogEntity;

@Dao
public interface ActivityLogDAO {

    @Insert
    long insert(ActivityLogEntity activityLogEntity);

    @Update
    int update(ActivityLogEntity activityLogEntity);

    @Delete
    int delete(ActivityLogEntity activityLogEntity);

    @Query("SELECT * FROM activity_log")
    List<ActivityLogEntity> getAll();
}

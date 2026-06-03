package br.com.culture.manager.cultureManager.data.db;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

import br.com.culture.manager.cultureManager.data.data_access_objects.FarmDAO;
import br.com.culture.manager.cultureManager.domain.entities.FarmEntity;

@Database(entities = {FarmEntity.class}, version = 1, exportSchema = false)
public abstract class LocalDatabase extends RoomDatabase {
    private static volatile LocalDatabase instance;

    public static LocalDatabase getInstance(Context context) {
        if (instance == null) {
            synchronized (LocalDatabase.class) {
                if (instance == null) {
                    instance = Room.databaseBuilder(context, LocalDatabase.class, "culture_manager_db")
                            .allowMainThreadQueries().build();
                }
            }
        }
        return instance;
    }

    public abstract FarmDAO getFarmDAO();
}

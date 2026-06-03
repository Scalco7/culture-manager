package br.com.culture.manager.cultureManager.data.db;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.room.TypeConverters;

import br.com.culture.manager.cultureManager.data.data_access_objects.FarmDAO;
import br.com.culture.manager.cultureManager.data.data_access_objects.PlotDAO;
import br.com.culture.manager.cultureManager.data.data_access_objects.WeatherDAO;
import br.com.culture.manager.cultureManager.data.persistence.ConverterLocalDateTimePersistence;
import br.com.culture.manager.cultureManager.domain.entities.ActivityLogEntity;
import br.com.culture.manager.cultureManager.domain.entities.FarmEntity;
import br.com.culture.manager.cultureManager.domain.entities.PlotEntity;
import br.com.culture.manager.cultureManager.domain.entities.WeatherEntity;

@Database(entities = {FarmEntity.class, PlotEntity.class, ActivityLogEntity.class, WeatherEntity.class},
        version = 1,
        exportSchema = false
)
@TypeConverters({ConverterLocalDateTimePersistence.class})
public abstract class LocalDatabase extends RoomDatabase {
    private static volatile LocalDatabase instance;

    public static LocalDatabase getInstance(Context context) {
        if (instance == null) {
            synchronized (LocalDatabase.class) {
                if (instance == null) {
                    instance = Room.databaseBuilder(context, LocalDatabase.class, "culture_manager_db")
                            .allowMainThreadQueries().build();

                    Builder<LocalDatabase> builder = Room.databaseBuilder(context, LocalDatabase.class, "culture_manager_db");

                    builder.fallbackToDestructiveMigration();
                    builder.allowMainThreadQueries();
                    instance = builder.build();
                }
            }
        }
        return instance;
    }

    public abstract FarmDAO getFarmDAO();
    public abstract WeatherDAO getWeatherDAO();

    public abstract PlotDAO getPlotDAO();
}

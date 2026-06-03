package br.com.culture.manager.cultureManager.data.persistence;

import androidx.room.TypeConverter;

import java.time.LocalDateTime;
import java.time.ZoneOffset;

public class ConverterLocalDateTimePersistence {
    @TypeConverter
    public static LocalDateTime fromLongToLocalDateTime(Long epochSecond) {
        if(epochSecond == null){
            return null;
        }

        return LocalDateTime.ofEpochSecond(epochSecond, 0, ZoneOffset.UTC);
    }

    @TypeConverter
    public static Long fromLocalDateTimeToLong(LocalDateTime date) {
        if(date == null){
            return null;
        }

        return date.toEpochSecond(ZoneOffset.UTC);
    }
}

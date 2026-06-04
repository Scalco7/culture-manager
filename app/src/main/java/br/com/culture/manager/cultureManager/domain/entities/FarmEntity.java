package br.com.culture.manager.cultureManager.domain.entities;

import androidx.annotation.NonNull;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "farm")
public class FarmEntity {
    @PrimaryKey(autoGenerate = true)
    private long id;
    @NonNull
    private String name;

    public FarmEntity(@NonNull String name, long id) {
        this.name = name;
        this.id = id;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    @NonNull
    public String getName() {
        return name;
    }

    public void setName(@NonNull String name) {
        this.name = name;
    }
}

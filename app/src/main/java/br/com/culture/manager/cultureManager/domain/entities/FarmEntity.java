package br.com.culture.manager.cultureManager.domain.entities;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "farm")
public class FarmEntity {
    @PrimaryKey(autoGenerate = true)
    private long id;
    private String name;

    public FarmEntity(String name, long id) {
        this.name = name;
        this.id = id;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}

package com.kotlarz_marlene_dogservicescheduler.Entity;

import androidx.room.Entity;
import androidx.room.ForeignKey;

@Entity(tableName = "service_playing_table")
public class ServicePlaying extends Service {
    private String toy;

    // Constructor ServicePlaying
    public ServicePlaying(String duration, String location, String type, String toy) {
        super(duration, location, type);
        this.toy = toy;
    }

    // Getter and Setter
    public String getToy() {
        return toy;
    }

    public void setToy(String toy) {
        this.toy = toy;
    }
}
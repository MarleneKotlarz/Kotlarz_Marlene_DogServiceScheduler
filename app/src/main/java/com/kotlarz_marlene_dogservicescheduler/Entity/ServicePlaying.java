package com.kotlarz_marlene_dogservicescheduler.Entity;

import androidx.room.Entity;
import androidx.room.ForeignKey;

@Entity(tableName = "service_playing_table")
public class ServicePlaying extends Service {
    private String option;

    // Constructor ServicePlaying
    public ServicePlaying(String duration, String type, String option) {
        super(duration, type);
        setLocation("Home");
        this.option = option;
    }

    // Getter and Setter
    public String getOption() {
        return option;
    }

    public void setOption(String option) {
        this.option = option;
    }
}
package com.kotlarz_marlene_dogservicescheduler.Entity;

import androidx.room.Entity;
import androidx.room.ForeignKey;


@Entity(tableName = "service_walking_table")
public class ServiceWalking extends Service {
    private int intensity;

    // Constructor ServiceWalking
    public ServiceWalking(String duration, String location, String type, int intensity) {
        super(duration, location, type);
        this.intensity = intensity;
    }

    // Getter and Setter
    public int getIntensity() {
        return intensity;
    }

    public void setIntensity(int intensity) {
        this.intensity = intensity;
    }
}

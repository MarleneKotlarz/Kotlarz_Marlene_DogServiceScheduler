package com.kotlarz_marlene_dogservicescheduler.Entity;

import androidx.room.Entity;
import androidx.room.ForeignKey;


@Entity(tableName = "service_walking_table")
public class ServiceWalking extends Service {

    private String option;

    // Constructor ServiceWalking
    public ServiceWalking(String duration, String location, String type, String option) {
        super(duration, location, type);
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

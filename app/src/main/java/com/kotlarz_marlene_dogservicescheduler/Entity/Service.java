package com.kotlarz_marlene_dogservicescheduler.Entity;

import androidx.room.PrimaryKey;

//---------- CODE INCLUDING INHERITANCE, POLYMORPHISM, AND ENCAPSULATION ----------//

public abstract class Service {

    @PrimaryKey(autoGenerate = true)
    private int service_id;
    private String duration, location, type;

    // Constructor
    public Service(String duration, String location, String type) {
        this.duration = duration;
        this.location = location;
        this.type = type;

    }

    // Method to get the correct service type
    public String serviceTypeSelection(String type) {
        this.type = type;
        return type;
    }

    // Getters and Setters
    public int getService_id() {
        return service_id;
    }

    public void setService_id(int service_id) {
        this.service_id = service_id;
    }

    public String getDuration() {
        return duration;
    }

    public void setDuration(String duration) {
        this.duration = duration;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }


}

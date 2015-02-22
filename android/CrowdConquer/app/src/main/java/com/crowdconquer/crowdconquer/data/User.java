package com.crowdconquer.crowdconquer.data;

import android.location.Location;

public class User {
    private String email;
    private Location location;
    

    public User(String email) {
        this.email = email;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public Location getLocation() {
        return location;
    }

    public void setLocation(Location location) {
        this.location = location;
    }
}

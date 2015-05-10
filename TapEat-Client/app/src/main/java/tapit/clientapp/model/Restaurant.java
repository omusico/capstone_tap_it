package tapit.clientapp.model;

import java.io.Serializable;

import tapit.clientapp.services.LocationService;

/**
 * Created by ichenwu on 3/4/15.
 */
public class Restaurant implements Serializable {
    private String name;
    private String uniqueUserName;
    private String description;
    private double longitude;
    private double latitude;
    private String number;
    private int wait_time;
    private int image;

    public Restaurant(String name, String uniqueUserName, int waittime, int image){
        this.name = name;
        this.uniqueUserName = uniqueUserName;
        this.wait_time = waittime;
        this.image = image;
        this.longitude = Double.NaN;
        this.latitude = Double.NaN;
        this.description = "taiwanese";
        this.number = "xxx-xxx-xxxx";
    }

    public Restaurant(String name, String uniqueUserName, int waittime, int image, double latitude, double longitude){
        this.name = name;
        this.uniqueUserName = uniqueUserName;
        this.wait_time = waittime;
        this.image = image;
        this.longitude = longitude;
        this.latitude = latitude;
        this.description = "taiwanese";
        this.number = "xxx-xxx-xxxx";
    }

    public String getUniqueUserName() {
        return uniqueUserName;
    }

    public void setUniqueUserName(String uniqueUserName) {
        this.uniqueUserName = uniqueUserName;
    }

    public String getName(){
        return name;
    }

    public String getDescription(){
        return description;
    }


    public String getWaitTime(){
        return Integer.toString(wait_time) + " mins";
    }

    public int getImage(){
        return image;
    }

    public double getLongitude() {
        return longitude;
    }

    public double getLatitude() {
        return latitude;
    }

    public String getNumber() {
        return number;
    }

    public int getWait_time() {
        return wait_time;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setLongitude(int longitude) {
        this.longitude = longitude;
    }

    public void setLatitude(int latitude) {
        this.latitude = latitude;
    }

    public void setNumber(String number) {
        this.number = number;
    }

    public void setWait_time(int wait_time) {
        this.wait_time = wait_time;
    }

    public void setImage(int image) {
        this.image = image;
    }

    public LocationService.GeoPoint getRestaurantGeoPoint(){
        return new LocationService.GeoPoint(this.latitude, this.longitude);
    }
}

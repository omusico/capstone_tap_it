package tapit.clientapp.model;

import java.util.List;

/**
 * Created by Luan on 5/2/15.
 */
public class Location {
    private List<String> address;
    private String city;
    private String stateCode;
    private String zipCode;
    private double latitude;
    private double longitude;

    public Location(String city, String stateCode, String zipCode) {
        this.city = city;
        this.stateCode = stateCode;
        this.zipCode = zipCode;
    }

    public String getCity() {
        return city;
    }

    public String getStateCode() {
        return stateCode;
    }

    public String getZipCode() {
        return zipCode;
    }

    public List<String> getAddress() {
        return address;
    }

    public double getLatitude() {
        return latitude;
    }

    public double getLongitude() {
        return longitude;
    }



    public void setAddress(List<String> address) {
        this.address = address;
    }

    public void setLatitude(double latitude) {
        this.latitude = latitude;
    }

    public void setLongitude(double longitude) {
        this.longitude = longitude;
    }
}

package tapit.clientapp.model;

import java.io.Serializable;
import java.util.List;

/**
 * Created by Luan on 5/2/15.
 */
public class Location implements Serializable{
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

    /*
        Return all address information: street, city, state, ZIP code.
        Format is a list of string with the following elements:
            - first line of address
            - second line of address (if applicable)
            - neighborhood or crossing streets (if applicable)
            - city/state/ZIP
     */
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

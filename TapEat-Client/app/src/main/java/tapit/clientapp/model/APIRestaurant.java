package tapit.clientapp.model;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import tapit.clientapp.R;
import tapit.clientapp.services.LocationService;

/**
 * Created by Luan on 5/2/15.
 */
public class APIRestaurant implements Serializable {
    private String name;
    private double distance;
    private String phone;
    private List<String> categories;
    private Location location;
    private int wait_time;
    private int image;
    private String getUniqueUserName;
    // TODO: menu list
    // TODO: open hour

    // Simple constructor, set other properties using setters
    public APIRestaurant(String name, double distance, String phone) {
        this.name = name;

        // convert meters to miles
        this.distance= (double) Math.round(distance * 0.000621371 * 100) / 100;
        this.phone = phone;

        if(this.name == "Cheese Wizards Food Truck"){
            this.image = R.drawable.chinesefood;
        }else if(this.name == "Morsel"){
            this.image = R.drawable.morsel;
        }else if(this.name == "Itadakimasu"){
            this.image = R.drawable.itadakimas;
        }else if(this.name == "Aladdin Falafel Corner"){
            this.image = R.drawable.aladin;
        }else if(this.name == "Shawarma King"){
            this.image = R.drawable.sharwamaking;
        }else if(this.name == "Sunrice"){
            this.image = R.drawable.sunrise;
        }else if(this.name == "Guanaco's Tacos Pupuseria"){
            this.image = R.drawable.guanacotaco;
        }else if(this.name == "Teriyaki 1st"){
            this.image = R.drawable.teriaki1st;
        }else if(this.name == "Din Tai Fung"){
                this.image = R.drawable.dingtaifung;
        }else if(this.name == "Banh Mi Unwrapped"){
            this.image = R.drawable.banhmiunwrapped;
        }else if(this.name == "TapEat Dawg Bar") {
            this.image = R.drawable.dawg_bar;
        } else {
            this.image = R.drawable.dingtaifung;
        }

        this.wait_time = 30;
    }

    public void setCategories(List<String> categories) { this.categories = categories; }

    public void setAddress (Location address) { this.location = address; }

    public String getName() {
        return name;
    }

    public String getDistance() {
        return Double.toString(distance) + " mi";
    }

    public String getPhone() {
        return phone;
    }

    public List<String> getCategories() {
        return categories;
    }

    public Location getAddress() {
        return location;
    }

    public String getWaitTime() {
        return Integer.toString(wait_time) + " mins";
    }

    public int getImage() {
        return image;
    }

    public LocationService.GeoPoint getRestaurantGeoPoint(){
        return new LocationService.GeoPoint(location.getLatitude(), location.getLongitude());
    }

    public String getUniqueUserName() {
        return getUniqueUserName;
    }

    public void setCategories(JSONArray categories) {
        this.categories = new ArrayList<>();
        try {
            for (int i = 0; i < categories.length(); i++) {
                JSONArray categoryItem = categories.getJSONArray(i);
                this.categories.add(categoryItem.getString(0));
            }
        } catch (JSONException e) {
            System.out.println("Parsing error: " + e.getMessage());
        }
    }

    public void setAddress(JSONObject location) {
        try {
            this.location = new Location(
                    location.getString("city"),
                    location.getString("state_code"),
                    location.getString("postal_code")
            );

            JSONObject coordinate = location.getJSONObject("coordinate");
            this.location.setLatitude(coordinate.getDouble("latitude"));
            this.location.setLongitude(coordinate.getDouble("longitude"));

            JSONArray displayAddress = location.getJSONArray("display_address");
            List<String> address = new ArrayList<>();
            for (int i = 0; i < displayAddress.length(); i++) {
                address.add(displayAddress.getString(i));
            }
            this.location.setAddress(address);
            this.getUniqueUserName = address.get(0).split("\\s+")[0] + "" + phone;

        } catch (JSONException e) {
            System.out.println("Parsing error: " + e.getMessage());
        }
    }
}

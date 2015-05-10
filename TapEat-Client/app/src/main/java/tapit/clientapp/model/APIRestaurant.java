package tapit.clientapp.model;

import java.util.List;
import java.util.ArrayList;
import org.json.*;

/**
 * Created by Luan on 5/2/15.
 */
public class APIRestaurant {
    private String name;
    private double distance;
    private String phone;
    private List<String> categories;
    private Location location;
    // TODO: menu list
    // TODO: open hour

    // Simple constructor, set other properties using setters
    public APIRestaurant(String name, double distance, String phone) {
        this.name = name;
        this.distance= distance;
        this.phone = phone;
    }

    public String getName() {
        return name;
    }

    public double getDistance() {
        return distance;
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
        } catch (JSONException e) {
            System.out.println("Parsing error: " + e.getMessage());
        }
    }
}

package tapit.clientapp;

import java.io.Serializable;

/**
 * Created by ichenwu on 3/4/15.
 */
public class Restaurant implements Serializable {
    private String name;
    private String description;
    private int longitude;
    private int latitude;
    private String number;
    private int wait_time;
    private int image;

    public Restaurant(String name, int image){
        this.name = name;
        this.wait_time = 0;
        this.image = image;
        this.longitude = 0;
        this.latitude = 0;
        this.description = "taiwanese";
        this.number = "xxx-xxx-xxxx";
    }

    public String getName(){
        return name;
    }

    public String getDescription(){
        return description;
    }


    public String getWaitTime(){
        return Integer.toString(wait_time) + " minutes";
    }

    public int getImage(){
        return image;
    }

}

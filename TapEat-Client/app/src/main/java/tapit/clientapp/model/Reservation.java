package tapit.clientapp.model;

/**
 * Created by stephenlee on 4/22/15.
 */
public class Reservation {
    private String RestaurantName;
    private int partySize;
    private String peference;
    private String customerName;
    private String customerUsername;
    private String customerPhone;
    private String checkinTime;

    public Reservation(String restaurantName, int partySize, String peference, String customerName, String customerUsername, String customerPhone, String checkinTime) {
        RestaurantName = restaurantName;
        this.partySize = partySize;
        this.peference = peference;
        this.customerName = customerName;
        this.customerUsername = customerUsername;
        this.customerPhone = customerPhone;
        this.checkinTime = checkinTime;
    }

    public Reservation(){}

    public String getRestaurantName() {
        return RestaurantName;
    }

    public int getPartySize() {
        return partySize;
    }

    public String getPeference() {
        return peference;
    }

    public String getCustomerName() {
        return customerName;
    }

    public String getCustomerUsername() {
        return customerUsername;
    }

    public String getCustomerPhone() {
        return customerPhone;
    }

    public String getCheckinTime() { return checkinTime; }
}

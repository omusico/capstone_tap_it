package tapit.businessapp.model;

/**
 * Created by frank on 4/21/15.
 */
public class Reservation {

    private String RestaurantName;
    private int partySize;
    private String peference;
    private String customerName;
    private String customerUsername;
    private String customerPhone;

    public Reservation(String restaurantName, int partySize, String peference, String customerName, String customerUsername, String customerPhone) {
        RestaurantName = restaurantName;
        this.partySize = partySize;
        this.peference = peference;
        this.customerName = customerName;
        this.customerUsername = customerUsername;
        this.customerPhone = customerPhone;
    }

    public Reservation(){

    }

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
}

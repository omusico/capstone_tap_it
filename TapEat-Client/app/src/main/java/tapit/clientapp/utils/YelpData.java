package tapit.clientapp.utils;

import org.scribe.builder.ServiceBuilder;
import org.scribe.oauth.OAuthService;
import org.scribe.model.OAuthRequest;
import org.scribe.model.Token;
import org.scribe.model.Verb;
import org.scribe.model.Response;
import org.json.*;

import java.util.ArrayList;
import java.util.List;

import tapit.clientapp.model.APIRestaurant;

/**
 * Created by Luan on 5/2/15.
 * TO-USE: create YelpData Object and call searchForBusinessesByLocation
 */
public class YelpData {

    private static final String API_HOST = "api.yelp.com";
    private static final String SEARCH_PATH = "/v2/search";
    private static final int SEARCH_LIMIT = 10;

    // Yelp! OAuth credentials
    private static final String CONSUMER_KEY = "Wj8EFGLaB6a9LnQ8QRLipg";
    private static final String CONSUMER_SECRET = "8LWEL2pPll3t71AhHJu4_DqevxI";
    private static final String TOKEN = "Bbf7bhdMjZZVEcCleP8jMdFAMdIURG5I";
    private static final String TOKEN_SECRET = "seprz36bLIy3Aosp9lb8_4mnKbs";

    OAuthService service;
    Token accessToken;

    // Setup Yelp! API OAuth credentials.
    public YelpData() {
        this.service = new ServiceBuilder().provider(TwoStepOAuth.class).apiKey(CONSUMER_KEY).apiSecret(CONSUMER_SECRET).build();
        this.accessToken = new Token(TOKEN, TOKEN_SECRET);
    }

    /*
        Search for nearby restaurants using location params: latitude & longitude.
        Return List of APIRestaurant.
     */
    public List<APIRestaurant> searchForBusinessesByLocation(double lat, double lon) {
        OAuthRequest request = new OAuthRequest(Verb.GET, "http://" + API_HOST + SEARCH_PATH);
        String location = lat + "," + lon;
        request.addQuerystringParameter("ll", location);
        request.addQuerystringParameter("limit", String.valueOf(SEARCH_LIMIT));

        // Sign and send request.
        System.out.println("Querying " + request.getCompleteUrl() + " ...");
        this.service.signRequest(this.accessToken, request);
        Response response = request.send();

        // Parse and return response.
        return parseSearchResults(response.getBody());
    }

    /*
        Parse search result into list of APIRestaurant.
        Check for empty list on use.
     */
    private List<APIRestaurant> parseSearchResults(String rawJSON) {
        List<APIRestaurant> output = new ArrayList<>();

        try {
            JSONObject data = new JSONObject(rawJSON);
            JSONArray restaurants = data.getJSONArray("businesses");

            // Populate list of APIRestaurant.
            for (int i = 0; i < restaurants.length(); i++) {
                JSONObject obj = (JSONObject) restaurants.get(i);
                APIRestaurant rest = new APIRestaurant(
                        obj.getString("name"),
                        obj.getDouble("distance"),
                        obj.getString("phone")
                    );
                rest.setCategories(obj.getJSONArray("categories"));
                rest.setAddress(obj.getJSONObject("location"));
            }
        } catch (JSONException e) {
            System.out.println("Parsing error: " + e.getMessage());
            System.out.println("Raw JSON data: " + rawJSON);
        }

        return output;
    }
}

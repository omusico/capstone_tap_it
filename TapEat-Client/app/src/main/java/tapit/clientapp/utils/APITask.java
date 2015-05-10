package tapit.clientapp.utils;

import android.os.AsyncTask;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.scribe.builder.ServiceBuilder;
import org.scribe.model.OAuthRequest;
import org.scribe.model.Response;
import org.scribe.model.Token;
import org.scribe.model.Verb;
import org.scribe.oauth.OAuthService;

import java.util.ArrayList;
import java.util.List;

import tapit.clientapp.model.APIRestaurant;

/**
 * Created by Luan Chuong on 5/5/2015.
 */
public class APITask extends AsyncTask<Void, Void, List<APIRestaurant>> {

    private static final String API_HOST = "api.yelp.com";
    private static final String SEARCH_PATH = "/v2/search";
    private static final int SEARCH_LIMIT = 10;

    // Yelp! OAuth credentials
    private static final String CONSUMER_KEY = "Wj8EFGLaB6a9LnQ8QRLipg";
    private static final String CONSUMER_SECRET = "8LWEL2pPll3t71AhHJu4_DqevxI";
    private static final String TOKEN = "y38Vpe-GpWdxybEZWwZGhrnUmigGHiRv";
    private static final String TOKEN_SECRET = "j2Wrm4vqZahdO8b94QIpFL_20h4";

    OAuthService service;
    Token accessToken;

    @Override
    protected List<APIRestaurant> doInBackground(Void... params) {
        this.service = new ServiceBuilder().provider(TwoStepOAuth.class).apiKey(CONSUMER_KEY).apiSecret(CONSUMER_SECRET).build();
        this.accessToken = new Token(TOKEN, TOKEN_SECRET);

        OAuthRequest request = new OAuthRequest(Verb.GET, "http://" + API_HOST + SEARCH_PATH);

        //yelpdata.searchForBusinessesByLocation(47.6097, 122.3331);
        //String location = lat + "," + lon;
        // TODO: remove hardcoded location, implement params

        String location = "37.788022,-122.399797";
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
        System.out.println(rawJSON);
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

    @Override
    protected void onPostExecute(List<APIRestaurant> result) {
        // Do something w/ result if necessary.
    }

    @Override
    protected void onPreExecute() {}

    @Override
    protected void onProgressUpdate(Void... values) {}
}

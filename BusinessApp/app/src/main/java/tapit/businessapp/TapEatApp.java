package tapit.businessapp;

import android.app.Application;

import com.firebase.client.Firebase;
import com.parse.Parse;

import tapit.businessapp.utils.Constants;


/**
 * Created by ichenwu on 3/10/15.
 */
public class TapEatApp extends Application {

    private static TapEatApp instance;


    public TapEatApp() {
    }

    public static TapEatApp getInstance() {
        return instance;
    }

    @Override
    public void onCreate() {
        Parse.initialize(this, Constants.APPLICATION_ID, Constants.CLIENT_KEY);

        Firebase.setAndroidContext(this);

    }
}
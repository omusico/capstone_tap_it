package tapit.clientapp.utils;

import android.app.Application;

import com.firebase.client.Firebase;
import com.parse.Parse;
import com.parse.ParseInstallation;

import java.util.Arrays;

/**
 * Created by ichenwu on 3/6/15.
 */
public class TapEatApp extends Application{

    private static TapEatApp instance;


    public TapEatApp() {
    }

    public static TapEatApp getInstance() {
        return instance;
    }

    @Override
    public void onCreate() {
        //Initial Firebase
        Firebase.setAndroidContext(this);

        Parse.initialize(this, Constants.APPLICATION_ID, Constants.CLIENT_KEY);

        // Save the current Installation to Parse, reinstall can make the change effective.
        String[] channels = {"tapeat_client"};
        ParseInstallation installation = ParseInstallation.getCurrentInstallation();
        installation.put("channels", Arrays.asList(channels));
        installation.saveInBackground();
    }

}



package tapit.clientapp.activities;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import com.firebase.client.Firebase;
import com.parse.ParseObject;
import com.parse.ParseUser;

import tapit.clientapp.model.Reservation;
import tapit.clientapp.utils.Constants;
import tapit.clientapp.R;
import tapit.clientapp.model.Restaurant;
import tapit.clientapp.services.LocationService;
import tapit.clientapp.utils.DataPath;


public class CheckInActivity extends ActionBarActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_check_in);

        Intent launchedMe = getIntent();
        final Restaurant restaurant = (Restaurant) launchedMe.getSerializableExtra("restaurant");

//      Set page title
        setTitle(restaurant.getName());

        final EditText notes = (EditText) findViewById(R.id.notes);
        InputMethodManager imm = (InputMethodManager)getSystemService(
                Context.INPUT_METHOD_SERVICE);
        imm.hideSoftInputFromWindow(notes.getWindowToken(), 0);

        // create class object
        LocationService gps = new LocationService(CheckInActivity.this);
        // check if GPS enabled
        long toRestaurantDistance = Long.MAX_VALUE;
        if(gps.canGetLocation()){
            LocationService.GeoPoint currentLocation = gps.getGeoPointLocation();
            toRestaurantDistance = gps.distanceFromTwoGeoPoint(currentLocation, restaurant.getRestaurantGeoPoint());
            Toast.makeText(getApplicationContext(), "you are " + toRestaurantDistance + " meters far from this place.", Toast.LENGTH_LONG).show();
        }else{
            // can't get location
            // GPS or Network is not enabled
            // Ask user to enable GPS/network in settings
            gps.showSettingsAlert();
        }

        final Button checkInButton = (Button) findViewById(R.id.checkin);

        final RadioGroup partySize = (RadioGroup) findViewById(R.id.partySize);

        if(toRestaurantDistance > Constants.GEO_RADIUS_ALLOWENCE){
            checkInButton.setBackgroundColor(Color.GRAY);
            checkInButton.setEnabled(false);
        }else {
            partySize.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
                public void onCheckedChanged(RadioGroup group, int checkedId) {
                checkInButton.setEnabled(true);
                }
            });

            checkInButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    RadioButton selectedSize = (RadioButton) findViewById(partySize.getCheckedRadioButtonId());
                    int size = Integer.parseInt(selectedSize.getText().toString());
                    //Parse for user
                    ParseUser currentUser = ParseUser.getCurrentUser();
                    String username = currentUser.getUsername();
                    String customerName = currentUser.get("name").toString();

                    //Firebase for sync up the list.
                    Firebase fire = new Firebase(Constants.FIREBASE_URL + '/' + DataPath.RESERVATIONS + '/' + restaurant.getUniqueUserName());
                    fire.push().setValue(new Reservation(restaurant.getName(), size, notes.getText().toString(), customerName, username, currentUser.get("phone").toString()));

                    finish();
                }
            });
        }

    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_check_in, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }



}

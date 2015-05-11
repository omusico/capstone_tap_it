package tapit.clientapp.activities;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.firebase.client.ChildEventListener;
import com.firebase.client.DataSnapshot;
import com.firebase.client.Firebase;
import com.firebase.client.FirebaseError;
import com.firebase.client.ValueEventListener;

import tapit.clientapp.R;
import tapit.clientapp.model.Restaurant;
import tapit.clientapp.utils.Constants;


public class RestaurantInfoActivity extends ActionBarActivity {

    SharedPreferences sharedPref;
    Button checkin;
    Restaurant thisRestaurant;
    String reservation;
    Firebase fire;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_restaurant_info);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        Intent launchedMe = getIntent();
        Bundle restaurantBundle = launchedMe.getBundleExtra("restaurantBundle");
        thisRestaurant = (Restaurant) restaurantBundle.getSerializable("serializedRestaurant");

        TextView restaurantName = (TextView) findViewById(R.id.RestaurantName);
        setTitle(thisRestaurant.getName());

        TextView restaurantDescription = (TextView) findViewById(R.id.RestaurantDescription);
        restaurantDescription.setText(thisRestaurant.getDescription());

        TextView restaurantWaitTime = (TextView) findViewById(R.id.RestaurantWaitTime);
        restaurantWaitTime.setText(thisRestaurant.getWaitTime());

        ImageView restaurantImage = (ImageView) findViewById(R.id.RestaurantImage);
        restaurantImage.setImageResource(thisRestaurant.getImage());

        // Set deafult text to checkin
        checkin = (Button) findViewById(R.id.checkin);
        checkin.setText("check in");

        sharedPref = this.getSharedPreferences(Constants.SHAREDPREFERENCE_RESERVATION, MODE_PRIVATE);
        reservation = sharedPref.getString(thisRestaurant.getUniqueUserName(), null);
        if( reservation != null ){
            fire = new Firebase(reservation);
            fire.addChildEventListener(new ChildEventListener() {
                @Override
                public void onChildAdded(DataSnapshot snapshot, String previousChildKey) {
                }

                @Override
                public void onChildChanged(DataSnapshot snapshot, String previousChildKey) {
                    if (snapshot.getValue() != null){
                        checkin.setText("change of plan?");
                    }
                }

                @Override
                public void onChildRemoved(DataSnapshot snapshot) {
                    checkin.setText("Check in");
                }

                @Override
                public void onChildMoved(DataSnapshot snapshot, String string){
                }

                @Override
                public void onCancelled(FirebaseError firebaseError) {
                }
            });
        }
        checkin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                // use restaurant name as id for now
                Intent nextActivity = new Intent(RestaurantInfoActivity.this, CheckInActivity.class);
                nextActivity.putExtra("restaurant", thisRestaurant);
                startActivity(nextActivity);
            }
        });

    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_restaurant_info, menu);
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

    @Override
    public void onStart(){
        super.onStart();

        reservation = sharedPref.getString(thisRestaurant.getUniqueUserName(), null);
        if (reservation != null) {
            fire = new Firebase(reservation);
            fire.addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot snapshot) {
                    if (snapshot.getValue() != null) {
                        checkin.setText("change of plan?");
                    }
                }
                @Override
                public void onCancelled(FirebaseError error) {
                }
            });
        } else {
            checkin.setText("check in");
        }
    }
}

package tapit.clientapp;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.parse.ParseUser;


public class RestaurantInfoActivity extends ActionBarActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_restaurant_info);

        Intent launchedMe = getIntent();
        Bundle restaurantBundle = launchedMe.getBundleExtra("restaurantBundle");
        final Restaurant thisRestaurant = (Restaurant) restaurantBundle.getSerializable("serializedRestaurant");

        TextView restaurantName = (TextView) findViewById(R.id.RestaurantName);
        restaurantName.setText(thisRestaurant.getName());

        TextView restaurantDescription = (TextView) findViewById(R.id.RestaurantDescription);
        restaurantDescription.setText(thisRestaurant.getDescription());

        TextView restaurantWaitTime = (TextView) findViewById(R.id.RestaurantWaitTime);
        restaurantWaitTime.setText(thisRestaurant.getWaitTime());

        ImageView restaurantImage = (ImageView) findViewById(R.id.RestaurantImage);
        restaurantImage.setImageResource(thisRestaurant.getImage());

        Button checkin = (Button) findViewById(R.id.checkin);
        checkin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                // use restaurant name as id for now
                Intent nextActivity = new Intent(RestaurantInfoActivity.this, CheckInActivity.class);
                nextActivity.putExtra("restaurantName", thisRestaurant.getName());
                startActivity(nextActivity);
            }
        });


        //Temporary logout button, need to be removed later
        Button logout = (Button) findViewById(R.id.logout);
        logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ParseUser.logOut();
                Intent loginActivity = new Intent(RestaurantInfoActivity.this, LoginActivity.class);
                startActivity(loginActivity);
                finish();
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
}

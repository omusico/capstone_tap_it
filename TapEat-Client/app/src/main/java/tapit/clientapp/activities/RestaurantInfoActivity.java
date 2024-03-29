package tapit.clientapp.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

import tapit.clientapp.R;
import tapit.clientapp.model.APIRestaurant;


public class RestaurantInfoActivity extends ActionBarActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fragment_restaurant_info);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        Intent launchedMe = getIntent();
        Bundle restaurantBundle = launchedMe.getBundleExtra("restaurantBundle");
        final APIRestaurant thisRestaurant = (APIRestaurant) restaurantBundle.getSerializable("serializedRestaurant");

        TextView restaurantName = (TextView) findViewById(R.id.RestaurantName);
        setTitle(thisRestaurant.getName());

        // Get restaurant type
        TextView restaurantDescription = (TextView) findViewById(R.id.RestaurantDescription);
        List<String> categories = thisRestaurant.getCategories();
        String categoriesDec = categories.get(0);
        restaurantDescription.setText(categoriesDec);

        TextView restaurantWaitTime = (TextView) findViewById(R.id.RestaurantWaitTime);
        restaurantWaitTime.setText(thisRestaurant.getWaitTime());

        TextView restaurantDistance = (TextView) findViewById(R.id.Restaurantdistance);
        restaurantDistance.setText(thisRestaurant.getDistance());

        ImageView restaurantImage = (ImageView) findViewById(R.id.RestaurantImage);
        restaurantImage.setImageResource(thisRestaurant.getImage());

        Button checkin = (Button) findViewById(R.id.checkin);
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
}

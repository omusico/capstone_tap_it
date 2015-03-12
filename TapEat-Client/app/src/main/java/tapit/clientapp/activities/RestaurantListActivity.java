package tapit.clientapp.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Menu;
import android.view.MenuItem;

import com.parse.ParseUser;

import tapit.clientapp.ItemAdapter;
import tapit.clientapp.R;
import tapit.clientapp.model.Restaurant;


public class RestaurantListActivity extends ActionBarActivity {

    private RecyclerView recyclerView;
    private RecyclerView.Adapter adapter;
    private RecyclerView.LayoutManager layoutManager;

    Restaurant[] dataArray = new Restaurant[]{new Restaurant("Ding Tai Fung", 40, R.drawable.dingtaifung, 47.6550232, -122.3082931), new Restaurant("Kukai Ramen", 15, R.drawable.kukai)};
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

//        String signedInUser = PreferenceManager.getDefaultSharedPreferences(getBaseContext()).getString("username", "");
//        if(signedInUser.length() == 0){
//            Intent loginActivity = new Intent(RestaurantListActivity.this, LoginActivity.class);
//            startActivity(loginActivity);
//            finish();
//        }

        // Check if current user is logged in, else direct to login page
        ParseUser currentUser = ParseUser.getCurrentUser();
        if (currentUser != null) {
            // do stuff with the user
        } else {
            Intent loginActivity = new Intent(RestaurantListActivity.this, LoginActivity.class);
            startActivity(loginActivity);
            finish();
        }


        setContentView(R.layout.activity_restaurant_list);
        recyclerView = (RecyclerView) findViewById(R.id.recycler_view);
        recyclerView.setHasFixedSize(true);
        layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);
        adapter = new ItemAdapter(dataArray);
        recyclerView.setAdapter(adapter);

    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
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

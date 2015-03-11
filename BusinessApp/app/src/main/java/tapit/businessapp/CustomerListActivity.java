package tapit.businessapp;

import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ListView;

import com.parse.FindCallback;
import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.ParseQuery;

import java.util.List;


public class CustomerListActivity extends ActionBarActivity {

    private List<ParseObject> parties;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_customer_list);

//        setParties();

        ParseQuery<ParseObject> query = ParseQuery.getQuery("party");
        query.findInBackground(new FindCallback<ParseObject>() {
            public void done(List<ParseObject> restaurantList, ParseException e) {
                if (e == null) {
                    Log.d("restaurant", "Retrieved " + restaurantList.size());
                    parties = restaurantList;
                    PartyListAdapter adapter = new PartyListAdapter(CustomerListActivity.this, R.layout.party_list_item, parties);
                    ListView partyListView = (ListView)findViewById(R.id.party_list);
                    partyListView.setAdapter(adapter);
                } else {
                    Log.d("score", "Error: " + e.getMessage());
                }
            }
        });


    }

//    private void setParties(){
//        ParseQuery<ParseObject> query = ParseQuery.getQuery("party");
//        query.whereExists("RestaurantName");
//        query.findInBackground(new FindCallback<ParseObject>() {
//            public void done(List<ParseObject> restaurantList, ParseException e) {
//                if (e == null) {
//                    Log.d("restaurant", "Retrieved " + restaurantList.size() );
//                    parties = restaurantList;
//                } else {
//                    Log.d("score", "Error: " + e.getMessage());
//                }
//            }
//        });
//
//    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_customer_list, menu);
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

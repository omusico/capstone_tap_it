package tapit.businessapp;

import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import com.firebase.client.DataSnapshot;
import com.firebase.client.Firebase;
import com.firebase.client.FirebaseError;
import com.firebase.client.ValueEventListener;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import tapit.businessapp.model.Reservation;
import tapit.businessapp.utils.Constants;
import tapit.businessapp.utils.DataPath;


public class CustomerListActivity extends ActionBarActivity {

    private int clickPosition = -1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_customer_list);


        // Get a reference to our posts
        Firebase fire = new Firebase(Constants.FIREBASE_URL + '/' + DataPath.RESERVATIONS + '/' + Constants.RESTAURANT_NAME);

        // Attach an listener to read the data at our posts reference

        fire.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot snapshot) {
                Map<String, Reservation> reservationMap = new HashMap<String, Reservation>();

                for(DataSnapshot child : snapshot.getChildren()) {
//                    Map<String, HashMap> childMap = (Map<String, HashMap>) child;
                    Reservation reservation = new Reservation(
                        child.child("restaurantName").getValue().toString(),
                        Integer.parseInt(child.child("partySize").getValue().toString()),
                        child.child("peference").getValue().toString(),
                        child.child("customerName").getValue().toString(),
                        child.child("customerUsername").getValue().toString(),
                        child.child("customerPhone").getValue().toString()
                    );
                    reservationMap.put(child.getKey(), reservation);
                }

                System.out.println(snapshot.getValue());
                List<String> keyList = new ArrayList<String>();
                keyList.addAll(reservationMap.keySet());
                final PartyListAdapter adapter = new PartyListAdapter(CustomerListActivity.this, R.layout.party_list_item, keyList, reservationMap);

                ListView partyListView = (ListView)findViewById(R.id.party_list);
                partyListView.setAdapter(adapter);

                partyListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                        @Override
                        public void onItemClick(AdapterView<?> a, View v, final int position, long id) {
                            clickPosition = position;
                        }
                });
            }

            @Override
            public void onCancelled(FirebaseError firebaseError) {
                System.out.println("The read failed: " + firebaseError.getMessage());
            }
        });

//        ParseQuery<ParseObject> query = ParseQuery.getQuery("party");
//        query.findInBackground(new FindCallback<ParseObject>() {
//            public void done(List<ParseObject> restaurantList, ParseException e) {
//                if (e == null) {
//                    Log.d("restaurant", "Retrieved " + restaurantList.size());
//                    parties = restaurantList;
//                    final PartyListAdapter adapter = new PartyListAdapter(CustomerListActivity.this, R.layout.party_list_item, parties);
//                    ListView partyListView = (ListView)findViewById(R.id.party_list);
//                    partyListView.setAdapter(adapter);
//
//                    partyListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
//                        @Override
//                        public void onItemClick(AdapterView<?> a, View v, final int position, long id) {
//                            clickPosition = position;
//                        }
//                    });
//
//                } else {
//                    Log.d("score", "Error: " + e.getMessage());
//                }
//            }
//        });
    }


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

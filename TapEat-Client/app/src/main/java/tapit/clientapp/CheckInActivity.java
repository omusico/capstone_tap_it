package tapit.clientapp;

import android.content.Context;
import android.content.Intent;
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
import android.widget.TextView;

import com.parse.ParseObject;
import com.parse.ParseUser;


public class CheckInActivity extends ActionBarActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_check_in);

        Intent launchedMe = getIntent();
        final String restaurantName = launchedMe.getStringExtra("restaurantName");

        TextView restaurantNameLabel = (TextView) findViewById(R.id.RestaurantName);
        restaurantNameLabel.setText(restaurantName);

        final EditText notes = (EditText) findViewById(R.id.notes);
        InputMethodManager imm = (InputMethodManager)getSystemService(
                Context.INPUT_METHOD_SERVICE);
        imm.hideSoftInputFromWindow(notes.getWindowToken(), 0);


        final Button checkInButton = (Button) findViewById(R.id.checkin);

        final RadioGroup partySize = (RadioGroup) findViewById(R.id.partySize);
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

                ParseUser currentUser = ParseUser.getCurrentUser();
                String username = currentUser.getUsername();
                String customerName = currentUser.get("name").toString();


                ParseObject party = new ParseObject("party");
                party.put("RestaurantName", restaurantName);
                party.put("size", size);
                party.put("notes", notes.getText().toString());

                party.put("customerName", customerName);
                party.put("customerUsername", username);
                party.put("customerPhone", currentUser.get("phone"));

                party.saveInBackground();

                finish();

            }
        });


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

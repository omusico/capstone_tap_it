package tapit.clientapp;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.parse.ParseUser;
import com.parse.SignUpCallback;
import com.parse.ParseException;

public class SignUpActivity extends ActionBarActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);

        final EditText nameField = (EditText) findViewById(R.id.name);

        final EditText usernameField = (EditText) findViewById(R.id.username);
        final EditText passwordField = (EditText) findViewById(R.id.password);
        final Button signupButton = (Button) findViewById(R.id.signup);

//        usernameField.addTextChangedListener(new TextWatcher() {
//            @Override
//            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
//                signupButton.setEnabled(false);
//            }
//
//            @Override
//            public void onTextChanged(CharSequence s, int start, int before, int count) {
//                if(s.length()!=0){
//                    signupButton.setEnabled(true);
//                }
//            }
//
//            @Override
//            public void afterTextChanged(Editable s) {
//
//            }
//        });

        signupButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ParseUser user = new ParseUser();
                user.setUsername(usernameField.getText().toString());
                user.setPassword(passwordField.getText().toString());
                user.put("name", nameField.getText().toString());

                user.signUpInBackground(new SignUpCallback() {
                    public void done(ParseException e) {
                        if (e == null) {

                            // Hooray! Let them use the app now.
                            Intent nextActivity = new Intent(SignUpActivity.this,RestaurantListActivity.class);
                            startActivity(nextActivity);
                            finish();
                        } else {
                            Log.d("error", e.toString());
                            // Sign up didn't succeed. Look at the ParseException
                            // to figure out what went wrong
                        }
                    }
                });
            }
        });


    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_sign_up, menu);
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

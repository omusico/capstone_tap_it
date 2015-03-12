package tapit.clientapp.activities;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.parse.LogInCallback;
import com.parse.ParseException;
import com.parse.ParseUser;

import tapit.clientapp.R;

public class LoginActivity extends ActionBarActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        final EditText usernameField = (EditText) findViewById(R.id.username);
        final EditText passwordField = (EditText) findViewById(R.id.password);
        Button loginButton = (Button) findViewById(R.id.login);
        Button signupButton = (Button) findViewById(R.id.signup);

        signupButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent signupActivity = new Intent(LoginActivity.this, SignUpActivity.class);
                startActivity(signupActivity);
            }
        });

        loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final String username = usernameField.getText().toString();
                String password = passwordField.getText().toString();

                ParseUser.logInInBackground(username, password, new LogInCallback() {
                    public void done(ParseUser user, ParseException e) {
                        if (user != null) {
                            // Hooray! The user is logged in.
                            Intent nextActivity = new Intent(LoginActivity.this, RestaurantListActivity.class);
                            startActivity(nextActivity);
                            finish();
                        } else {
                            // login failed. alert window pop out.
                            final AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(LoginActivity.this);
                            // set title
                            alertDialogBuilder.setTitle("Wrong username or password!");
                            // set dialog message
                            alertDialogBuilder
                                    .setMessage("Retry login?")
                                    .setCancelable(false)
                                    .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                                        public void onClick(DialogInterface dialog, int id) {
                                            dialog.cancel();
                                        }
                                    });

                            // create alert dialog
                            AlertDialog alertDialog = alertDialogBuilder.create();
                            // show it
                            alertDialog.show();
                        }
                    }
                });

//                ParseQuery<ParseObject> query = ParseQuery.getQuery(Parse.User);
//                query.whereEqualTo("username", username);
//                query.findInBackground(new FindCallback<ParseObject>() {
//                    public void done(List<ParseObject> users, ParseException e) {
//                        if (e == null) {
//                            Log.d("username", "Retrieved " + users.size());
//                            if(users.size() != 0){
//                                PreferenceManager.getDefaultSharedPreferences(getBaseContext()).edit().putString("username", username).commit();
//
//                                Intent nextActivity = new Intent(LoginActivity.this, RestaurantListActivity.class);
//                                startActivity(nextActivity);
//                            }
//                        } else {
//                            Log.d("username", "Error: " + e.getMessage());
//                        }
//                    }
//                });
            }
        });
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_login, menu);
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

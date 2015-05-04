package tapit.clientapp.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.util.Log;

import com.parse.ParseException;
import com.parse.ParseUser;
import com.parse.SignUpCallback;

import tapit.clientapp.R;
import tapit.clientapp.fragments.sign_up_wizard.nameStep;
import tapit.clientapp.fragments.sign_up_wizard.phoneNumberStep;
import tapit.clientapp.fragments.sign_up_wizard.preferenceStep;

/**
 * Created by stephenlee on 5/2/15.
 */
public class FancySignUpActivity extends FragmentActivity {

    ViewPager pager;
    String name;
    String phoneNumber;
    int preference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.sign_up_viewpager);

        pager = (ViewPager) findViewById(R.id.viewPager);
        pager.setAdapter(new MyPagerAdapter(getSupportFragmentManager()));
    }

    private class MyPagerAdapter extends FragmentPagerAdapter {

        public MyPagerAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int pos) {
            switch(pos) {

                case 0: return nameStep.newInstance();
                case 1: return phoneNumberStep.newInstance();
                case 2: return preferenceStep.newInstance();
                default: return preferenceStep.newInstance();
            }
        }

        @Override
        public int getCount() {
            return 3;
        }
    }

    public ViewPager getpager(){
        return pager;
    }

    @Override
    public void onBackPressed() {
        if(pager.getCurrentItem() > 0) {
            pager.setCurrentItem(pager.getCurrentItem() - 1 , true);
        } else {
            super.onBackPressed(); // This will pop the Activity from the stack.
        }
    }

    public void submitForm(){
        ParseUser user = new ParseUser();
        user.setUsername(getPhoneNumber());
        user.setPassword(getPhoneNumber());
        user.put("name", getCustomerName());
        user.put("phone", getPhoneNumber());
        user.put("preference", getPreference());
        user.signUpInBackground(new SignUpCallback() {
            public void done(ParseException e) {
                if (e == null) {

                    // Hooray! Let them use the app now.
                    Intent nextActivity = new Intent(FancySignUpActivity.this,MainActivity.class);
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

    /**
     * Custom setter and getter method to retrieve or set
     * Customer name and phone number for sign up
     **/
    public void setCustomerName(String name){
        this.name = name;
    }

    public void setPhoneNumber(String number){
        this.phoneNumber = number;
    }

    public void setPreference(int p){
        this.preference = p;
    }

    public String getPhoneNumber(){
        if(this.phoneNumber != null) {
            return this.phoneNumber;
        } else {
            return "0000000000";
        }
    }

    public String getCustomerName(){
        return this.name;
    }

    public int getPreference(){
        return this.preference;
    }
}

package tapit.clientapp.activities;

import android.app.Fragment;
import android.app.FragmentManager;
import android.content.Intent;
import android.location.Location;
import android.os.Bundle;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.app.ActionBarDrawerToggle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.RelativeLayout;

import com.parse.ParseUser;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import tapit.clientapp.R;
import tapit.clientapp.fragments.PreferencesFragment;
import tapit.clientapp.fragments.RestaurantListFragment;
import tapit.clientapp.hamburger_menu.DrawerListAdapter;
import tapit.clientapp.hamburger_menu.NavItem;
import tapit.clientapp.model.APIRestaurant;
import tapit.clientapp.services.LocationService;
import tapit.clientapp.utils.APITask;


public class MainActivity extends ActionBarActivity {

    // ActionBarDrawer
    private static String TAG = MainActivity.class.getSimpleName();
    ListView mDrawerList;
    RelativeLayout mDrawerPane;
    private ActionBarDrawerToggle mDrawerToggle;
    private DrawerLayout mDrawerLayout;
    private ArrayList<NavItem> mNavItems = new ArrayList<NavItem>();
    private FragmentManager fragmentManager = getFragmentManager();

    // Current user location
    private Location currentLocation;

    // Yelp API restaurant list
    List<APIRestaurant> result;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        /*
            Yelp API call:
                input: latitude & longitude (as String) of user's current location
                return: list of API restaurants, see object's file for attributes & methods
         */
        try {
            getLocation();
            if (currentLocation != null) {
                result = new APITask().execute(String.valueOf(currentLocation.getLatitude()), String.valueOf(currentLocation.getLongitude())).get();
            }
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //show hamburger menu & initialize drawer
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        drawerHelp();

        // Check if current user is logged in, else direct to login page
        // if logged in, inflate restaurant list fragment
        ParseUser currentUser = ParseUser.getCurrentUser();
        if (currentUser != null) {
            RestaurantListFragment newFragment = new RestaurantListFragment();

            Bundle args = new Bundle();
            args.putSerializable("searchResult", (Serializable)result);
            newFragment.setArguments(args);

            fragmentManager.beginTransaction()
                    .replace(R.id.mainContent, newFragment)
                    .commit();
        } else {
            Intent loginActivity = new Intent(MainActivity.this, FancySignUpActivity.class);
            startActivity(loginActivity);
            finish();
        }
    }

    /*
    * Handle back navigation on fragments
    * */
    @Override
    public void onBackPressed(){
        if (fragmentManager.getBackStackEntryCount() > 0) {
            Log.i("MainActivity", "popping backstack");
            fragmentManager.popBackStack();
        } else {
            Log.i("MainActivity", "nothing on backstack, calling super");
            super.onBackPressed();
        }
    }

    private void setActionBarTitle(String title) {
        getSupportActionBar().setTitle(title);
    }

    private void getLocation(){
        // create class object
        LocationService gps = new LocationService(MainActivity.this);
        // check if GPS enabled
        if(gps.canGetLocation()){
            currentLocation = gps.getLocation();
        }else{
            // can't get location
            // GPS or Network is not enabled
            // Ask user to enable GPS/network in settings
            gps.showSettingsAlert();
        }
    }

    private void drawerHelp(){
        mNavItems.add(new NavItem("Nearby Restaurants"));
        mNavItems.add(new NavItem("Wait Line Status"));
        mNavItems.add(new NavItem("Favourites"));
        mNavItems.add(new NavItem("Recent Visited"));

        // DrawerLayout
        mDrawerLayout = (DrawerLayout) findViewById(R.id.drawerLayout);

        // FragmentLayout
        final RelativeLayout mMainContent = (RelativeLayout) findViewById(R.id.mainContent);

        // Populate the Navigtion Drawer with options
        mDrawerPane = (RelativeLayout) findViewById(R.id.drawerPane);
        mDrawerList = (ListView) findViewById(R.id.navList);
        DrawerListAdapter listAdapter = new DrawerListAdapter(this, mNavItems);
        mDrawerList.setAdapter(listAdapter);

        mDrawerToggle = new ActionBarDrawerToggle(this, mDrawerLayout, R.string.drawer_open, R.string.drawer_close) {
            @Override
            public void onDrawerOpened(View drawerView) {
                super.onDrawerOpened(drawerView);

                invalidateOptionsMenu();
            }

            @Override
            public void onDrawerClosed(View drawerView) {
                super.onDrawerClosed(drawerView);
                invalidateOptionsMenu();
            }

            @Override
            public void onDrawerSlide(View drawerView, float slideOffset) {
                super.onDrawerSlide(drawerView, slideOffset);
                mMainContent.setTranslationX(slideOffset * drawerView.getWidth()/2);
                mDrawerLayout.bringChildToFront(drawerView);
                mDrawerLayout.requestLayout();
            }
        };

        mDrawerLayout.setDrawerListener(mDrawerToggle);

        // Show hamburger Menu
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        // Drawer Item click listeners
        mDrawerList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                selectItemFromDrawer(position);
            }
        });
    }

    // Called when invalidateOptionsMenu() is invoked
    public boolean onPrepareOptionsMenu(Menu menu) {
        // If the nav drawer is open, hide action items related to the content view
        boolean drawerOpen = mDrawerLayout.isDrawerOpen(mDrawerPane);
        menu.findItem(R.id.action_settings).setVisible(!drawerOpen);
        return super.onPrepareOptionsMenu(menu);
    }

    /*
    * Called when a particular item from the navigation drawer
    * is selected.
    * */
    private void selectItemFromDrawer(int position) {
        Fragment preferenceFragment = new PreferencesFragment();
        Fragment restaurantList = new RestaurantListFragment();

        Fragment currentFragment;
        switch(position) {
            case 0: currentFragment = restaurantList;
                break;
            case 1: currentFragment = preferenceFragment;
                break;
            default:
                currentFragment = restaurantList;
                break;
        }

        Bundle args = new Bundle();
        args.putSerializable("searchResult", (Serializable)result);
        currentFragment.setArguments(args);

        fragmentManager.beginTransaction()
                .replace(R.id.mainContent, currentFragment)
                .commit();

        mDrawerList.setItemChecked(position, true);
        setTitle(mNavItems.get(position).getTitle());

        // Close the drawer
        mDrawerLayout.closeDrawer(mDrawerPane);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Pass the event to ActionBarDrawerToggle
        // If it returns true, then it has handled
        // the nav drawer indicator touch event
        if (mDrawerToggle.onOptionsItemSelected(item)) {
            return true;
        }

        // Handle your other action bar items...

        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onPostCreate(Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);
        mDrawerToggle.syncState();
    }
}

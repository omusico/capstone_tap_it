package tapit.clientapp.fragments;

import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import tapit.clientapp.R;
import tapit.clientapp.activities.MainActivity;
import tapit.clientapp.model.Restaurant;

/**
 * Created by stephenlee on 4/30/15.
 */
public class RestaurantInfoFragment extends Fragment {

    public RestaurantInfoFragment() {
        // Required empty public constructor
    };

    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_restaurant_info, container, false);

        Bundle restaurantBundle = getArguments();
        final Restaurant thisRestaurant = (Restaurant) restaurantBundle.getSerializable("serializedRestaurant");

        TextView restaurantName = (TextView) root.findViewById(R.id.RestaurantName);

        // Set actionBar Title of the parent activity page
        ((MainActivity) getActivity())
                .setActionBarTitle(thisRestaurant.getName());

        TextView restaurantDescription = (TextView) root.findViewById(R.id.RestaurantDescription);
        restaurantDescription.setText(thisRestaurant.getDescription());

        TextView restaurantWaitTime = (TextView) root.findViewById(R.id.RestaurantWaitTime);
        restaurantWaitTime.setText(thisRestaurant.getWaitTime());

        ImageView restaurantImage = (ImageView) root.findViewById(R.id.RestaurantImage);
        restaurantImage.setImageResource(thisRestaurant.getImage());

        Button checkin = (Button) root.findViewById(R.id.checkin);
        checkin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

//                // use restaurant name as id for now
//                Intent nextActivity = new Intent(RestaurantInfoActivity.this, CheckInActivity.class);
//                nextActivity.putExtra("restaurant", thisRestaurant);
//                startActivity(nextActivity);
            }
        });

        return root;
    };
}

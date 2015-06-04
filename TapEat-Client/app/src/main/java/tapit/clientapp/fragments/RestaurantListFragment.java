package tapit.clientapp.fragments;

import android.app.Fragment;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.List;

import tapit.clientapp.R;
import tapit.clientapp.model.APIRestaurant;
import tapit.clientapp.model.Location;
import tapit.clientapp.utils.ItemAdapter;

/**
 * Created by stephenlee on 4/30/15.
 */
public class RestaurantListFragment extends Fragment{

    private RecyclerView recyclerView;
    private RecyclerView.Adapter recycleAdapter;
    private RecyclerView.LayoutManager layoutManager;
    List<APIRestaurant> result;

    public RestaurantListFragment() {
        // Required empty public constructor
    };

    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState){
        View root = inflater.inflate(R.layout.fragment_restaurant_list, container, false);

        try {
            result = (List<APIRestaurant>) getArguments().get("searchResult");
        } catch (NullPointerException e) {
            System.out.println("Error: " + e.getMessage());
        }
        // For capstone
        Location dummy = new Location ("Seatte", "WA", "98195");
        List<String> address = new ArrayList<String>();
        List<String> categories = new ArrayList<String>();
        address.add("4001 Stevens Way NE");
        categories.add("Nerdy Bar");
        dummy.setAddress(address);
        APIRestaurant tapEatBar = new APIRestaurant("TapEat Dawg Bar", 0.1, "2068165042");
        tapEatBar.setAddress(dummy);
        tapEatBar.setCategories(categories);
        result.add(0, tapEatBar);

        recyclerView = (RecyclerView) root.findViewById(R.id.recycler_view);
        recyclerView.setHasFixedSize(true);
        layoutManager = new LinearLayoutManager(getActivity());
        recyclerView.setLayoutManager(layoutManager);
        recycleAdapter = new ItemAdapter(result, getActivity());
        recyclerView.setAdapter(recycleAdapter);

        return root;
    };

}

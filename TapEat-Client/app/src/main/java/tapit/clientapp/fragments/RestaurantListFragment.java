package tapit.clientapp.fragments;

import android.app.Fragment;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import tapit.clientapp.ItemAdapter;
import tapit.clientapp.R;
import tapit.clientapp.model.Restaurant;

/**
 * Created by stephenlee on 4/30/15.
 */
public class RestaurantListFragment extends Fragment{

    private RecyclerView recyclerView;
    private RecyclerView.Adapter recycleAdapter;
    private RecyclerView.LayoutManager layoutManager;

    Restaurant[] dataArray = new Restaurant[]{
            new Restaurant("Ding Tai Fung", "dtfSeattleUniversityVillage1", 40, R.drawable.dingtaifung, 47.6550232, -122.3082931),
            new Restaurant("Kukai Ramen", "kukaiSeattle", 15, R.drawable.kukai)
    };

    public RestaurantListFragment() {
        // Required empty public constructor
    };

    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState){
        View root = inflater.inflate(R.layout.fragment_restaurant_list, container, false);

        recyclerView = (RecyclerView) root.findViewById(R.id.recycler_view);
        recyclerView.setHasFixedSize(true);
        layoutManager = new LinearLayoutManager(getActivity());
        recyclerView.setLayoutManager(layoutManager);
        recycleAdapter = new ItemAdapter(dataArray, getActivity());
        recyclerView.setAdapter(recycleAdapter);

        return root;
    };

}

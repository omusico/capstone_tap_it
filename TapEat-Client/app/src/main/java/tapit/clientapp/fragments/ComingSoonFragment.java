package tapit.clientapp.fragments;

import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import tapit.clientapp.R;

/**
 * Created by stephenlee on 4/29/15.
 */
public class ComingSoonFragment extends Fragment {
    public ComingSoonFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.coming_soon, container, false);
    }
}

package tapit.clientapp.fragments.sign_up_wizard;

import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import tapit.clientapp.R;
import tapit.clientapp.activities.FancySignUpActivity;

/**
 * Created by stephenlee on 5/2/15.
 */
public class preferenceStep extends Fragment implements View.OnClickListener {

    Button p1;
    Button p2;
    int userSelect;

    //You must have an empty constructor for every step
    public preferenceStep() {
    }

    //Set your layout here
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View v = inflater.inflate(R.layout.fragment_preferences, container, false);
        p1 = (Button) v.findViewById(R.id.preference_1);
        p2 = (Button) v.findViewById(R.id.preference_2);

        p1.setOnClickListener(this);
        p2.setOnClickListener(this);

        v.findViewById(R.id.submit_user).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ((FancySignUpActivity)getActivity()).setPreference(userSelect);
                ((FancySignUpActivity)getActivity()).submitForm();
            };
        });

        return v;
    }

    public static preferenceStep newInstance() {

        preferenceStep f = new preferenceStep();

        // pass parameters to fragment
//        Bundle b = new Bundle();
//        f.setArguments(b);

        return f;
    }

    @Override
    public void onClick(View v) {
        p1.setBackground(getResources().getDrawable(R.drawable.rounded_rectangle_transparent));
        p1.setTextColor(Color.rgb(255, 255, 255));
        p2.setBackground(getResources().getDrawable(R.drawable.rounded_rectangle_transparent));
        p2.setTextColor(Color.rgb(255, 255, 255));
        userSelect = v.getId();

        switch(v.getId()){
            case R.id.preference_1:
                p1.setBackground(getResources().getDrawable(R.drawable.rounded_rectangle_transparent_pressed));
                p1.setTextColor(getResources().getColor(R.color.mintGreen));
                break;
            case R.id.preference_2:
                p2.setBackground(getResources().getDrawable(R.drawable.rounded_rectangle_transparent_pressed));
                p2.setTextColor(getResources().getColor(R.color.mintGreen));
                break;
            default:
                p1.setBackground(getResources().getDrawable(R.drawable.rounded_rectangle_transparent_pressed));
                p1.setTextColor(Color.rgb(255, 255, 255));
                p2.setBackground(getResources().getDrawable(R.drawable.rounded_rectangle_transparent_pressed));
                p2.setTextColor(Color.rgb(255, 255, 255));
        }

    }
}
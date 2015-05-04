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

    private Button p1;
    private Button p2;
    View selectedButton;

    //You must have an empty constructor for every step
    public preferenceStep() {
    }

    //Set your layout here
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        final FancySignUpActivity parent = ((FancySignUpActivity)getActivity());
        View v = inflater.inflate(R.layout.fragment_preferences, container, false);
        p1 = (Button) v.findViewById(R.id.p_infrequent);
        p2 = (Button) v.findViewById(R.id.p_frequent);

        p1.setOnClickListener(this);
        p2.setOnClickListener(this);

        v.findViewById(R.id.submit_user).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                parent.submitForm();
            };
        });

        if (parent.getPreference() == null) {
            FancySignUpActivity temp = ((FancySignUpActivity)getActivity());
            View preferenceButton = v.findViewById(R.id.p_infrequent);

            selectedButton = preferenceButton;
            temp.setPreference(preferenceButton);
        }

        previousSelection();

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
        ((FancySignUpActivity)getActivity()).setPreference(v);
        selectedButton = v;
        buttonToggle(v);
    }

    // Make sure all the buttons are correctly selected
    private void buttonToggle(View v){
        ViewGroup vg = (ViewGroup) v.getParent();
        for( int i = 0 ; i < vg.getChildCount() ;i++){
            View current = vg.getChildAt(i);
            if (current instanceof Button && current.getId() == v.getId()) {
                Button b = (Button) current;
                b.setBackground(getResources().getDrawable(R.drawable.rounded_rectangle_transparent_pressed));
                b.setTextColor(getResources().getColor(R.color.mintGreen));
            } else if (current instanceof Button){
                Button nb = (Button) current;
                nb.setBackground(getResources().getDrawable(R.drawable.rounded_rectangle_transparent));
                nb.setTextColor(Color.rgb(255, 255, 255));
            }
        }
    }

    // Make sure render previous selection
    private void previousSelection(){
        View selection = ((FancySignUpActivity)getActivity()).getPreference();
        buttonToggle(selection);
    }
}
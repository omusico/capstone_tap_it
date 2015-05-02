package tapit.clientapp.fragments.sign_up_wizard;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import org.codepond.wizardroid.WizardStep;

import tapit.clientapp.R;

/**
 * Created by stephenlee on 5/2/15.
 */
public class preferenceStep extends WizardStep {
    //You must have an empty constructor for every step
    public preferenceStep() {
    }

    //Set your layout here
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_preferences, container, false);
        TextView tv = (TextView) v.findViewById(R.id.textView);
        tv.setText("almost there");
        return v;
    }
}

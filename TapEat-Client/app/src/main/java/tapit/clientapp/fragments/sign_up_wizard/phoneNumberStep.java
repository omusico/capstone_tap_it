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
public class phoneNumberStep extends WizardStep {

    //You must have an empty constructor for every step
    public phoneNumberStep() {
    }

    //Set your layout here
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_preferences, container, false);
        TextView tv = (TextView) v.findViewById(R.id.textView);
        tv.setText("This is an example of Step 2 and also the last step in this wizard. " +
                "By pressing Finish you will conclude this wizard and go back to the main activity." +
                "Hit the back button to go back to the previous step.");
        return v;
    }
}

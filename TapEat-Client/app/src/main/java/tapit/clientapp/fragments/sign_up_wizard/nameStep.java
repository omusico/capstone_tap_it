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
public class nameStep extends WizardStep {
    //Wire the layout to the step
    public nameStep() {
    }

    //Set your layout here
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        //TODO: change the layout to ideal
        View v = inflater.inflate(R.layout.fragment_preferences, container, false);
        TextView tv = (TextView) v.findViewById(R.id.textView);
        tv.setText("This is an example of Step 1 in the wizard. Press the Next " +
                "button to proceed to the next step. Hit the back button to go back to the calling activity.");

        return v;
    }
}

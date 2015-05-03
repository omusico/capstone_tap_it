package tapit.clientapp.fragments.sign_up_wizard;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
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
        //TODO: add button
        View v = inflater.inflate(R.layout.fragment_sign_up, container, false);
        TextView tv = (EditText) v.findViewById(R.id.editText);
        tv.setHint("Name");

        return v;
    }
}

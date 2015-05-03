package tapit.clientapp.fragments.sign_up_wizard;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
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
        View v = inflater.inflate(R.layout.fragment_sign_up, container, false);
        TextView tv = (EditText) v.findViewById(R.id.editText);
        ImageView iv = (ImageView) v.findViewById(R.id.main_backgroundImage);
        iv.setImageResource(R.drawable.background_num);
        tv.setHint("Phone Number");

        return v;
    }
}

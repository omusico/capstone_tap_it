package tapit.clientapp.fragments.sign_up_wizard;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.View.OnFocusChangeListener;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;

import tapit.clientapp.R;
import tapit.clientapp.activities.FancySignUpActivity;

/**
 * Created by stephenlee on 5/2/15.
 */
public class nameStep extends Fragment {
    //Wire the layout to the step
    public nameStep() {
    }

    //Set your layout here
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        final ViewPager pager = ((FancySignUpActivity)getActivity()).getpager();
        //TODO: add button
        View v = inflater.inflate(R.layout.fragment_sign_up, container, false);
        final TextView tv = (EditText) v.findViewById(R.id.editText);
        tv.setHint("Name");
        tv.setOnFocusChangeListener(new OnFocusChangeListener() {

            @Override
            public void onFocusChange(View v, boolean hasFocus) {
            /* When focus is lost check that the text field
            * has valid values.
            */
                if (!hasFocus) {
                    ((FancySignUpActivity)getActivity()).setCustomerName(tv.getText().toString());
                }
            }
        });

        ImageButton ib = (ImageButton) v.findViewById(R.id.action_next);
        ib.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ((FancySignUpActivity)getActivity()).setCustomerName(tv.getText().toString());
                pager.setCurrentItem(pager.getCurrentItem()+1);
            }
        });
        return v;
    }

    public static nameStep newInstance() {

        nameStep f = new nameStep();

        // pass parameters to fragment
//        Bundle b = new Bundle();
//        f.setArguments(b);

        return f;
    }
}

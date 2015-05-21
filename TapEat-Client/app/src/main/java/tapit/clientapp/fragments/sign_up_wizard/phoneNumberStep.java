package tapit.clientapp.fragments.sign_up_wizard;

import android.content.res.Configuration;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.EditorInfo;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import tapit.clientapp.R;
import tapit.clientapp.activities.FancySignUpActivity;

/**
 * Created by stephenlee on 5/2/15.
 */
public class phoneNumberStep extends Fragment {

    //You must have an empty constructor for every step
    public phoneNumberStep() {
    }

    //Set your layout here
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        final ViewPager pager = ((FancySignUpActivity)getActivity()).getpager();

        View v = inflater.inflate(R.layout.fragment_sign_up, container, false);
        final TextView tv = (EditText) v.findViewById(R.id.editText);
        ImageView iv = (ImageView) v.findViewById(R.id.main_backgroundImage);
        iv.setImageResource(R.drawable.background_num);
        tv.setHint("Phone Number");
        tv.setRawInputType(Configuration.KEYBOARD_12KEY);

        tv.setOnFocusChangeListener(new View.OnFocusChangeListener() {

            @Override
            public void onFocusChange(View v, boolean hasFocus) {
            /* When focus is lost check that the text field
            * has valid values.
            */
            if (!hasFocus) {
                ((FancySignUpActivity)getActivity()).setPhoneNumber(tv.getText().toString());
            }
            }
        });

        tv.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                if (actionId == EditorInfo.IME_ACTION_DONE) {
                    ((FancySignUpActivity)getActivity()).setPhoneNumber(tv.getText().toString());
                    pager.setCurrentItem(pager.getCurrentItem()+1);
                }
                return false;
            }
        });

        v.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                ((FancySignUpActivity)getActivity()).setPhoneNumber(tv.getText().toString());
                return true;
            };
        });

        Button ib = (Button) v.findViewById(R.id.action_next);
        ib.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
            ((FancySignUpActivity)getActivity()).setPhoneNumber(tv.getText().toString());
            pager.setCurrentItem(pager.getCurrentItem()+1);
            }
        });

        return v;
    }

    public static phoneNumberStep newInstance() {

        phoneNumberStep f = new phoneNumberStep();

        // pass parameters to fragment
//        Bundle b = new Bundle();
//        f.setArguments(b);

        return f;
    }
}

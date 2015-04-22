package tapit.businessapp;


import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.telephony.SmsManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.firebase.client.Firebase;

import java.util.List;
import java.util.Map;

import tapit.businessapp.model.Reservation;
import tapit.businessapp.utils.Constants;
import tapit.businessapp.utils.DataPath;

/**
 * Created by ichenwu on 3/10/15.
 */
public class PartyListAdapter extends ArrayAdapter<String> {

    private List<String> reservationKeyList;
    private Map<String, Reservation> reservationMap;
    private int layoutResourceId;
    private Context context;

    public PartyListAdapter(Context context, int layoutResourceId, List<String> reservationKeyList, Map<String, Reservation> reservationMap) {
        super(context, layoutResourceId, reservationKeyList);
        this.layoutResourceId = layoutResourceId;
        this.context = context;
        this.reservationKeyList = reservationKeyList;
        this.reservationMap = reservationMap;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        View row = convertView;


        LayoutInflater inflater = ((Activity) context).getLayoutInflater();
        row = inflater.inflate(layoutResourceId, parent, false);

        final PartyListHolder holder = new PartyListHolder();
        String reservationKey = this.reservationKeyList.get(position);
        holder.party = this.reservationMap.get(reservationKey);
        holder.callButton = (Button)row.findViewById(R.id.callButton);
        holder.callButton.setTag(holder.party);

        holder.name = (TextView)row.findViewById(R.id.party_name);

        holder.callButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.i("triggered sms to ",holder.party.getCustomerPhone());
                sendSMS(holder.party);
            }
        });

        holder.removeButton = (Button) row.findViewById(R.id.noshow);
        holder.removeButton.setTag(holder.party);
        holder.removeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder adb = new AlertDialog.Builder(context);
                adb.setTitle("Delete?");
                adb.setMessage("Are you sure you want to remove " + reservationMap.get(reservationKeyList.get(position)).getCustomerName()+ " from the list");
                adb.setNegativeButton("Cancel", null);
                adb.setPositiveButton("Ok", new AlertDialog.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        Firebase fire = new Firebase(Constants.FIREBASE_URL + '/' + DataPath.RESERVATIONS + '/' + Constants.RESTAURANT_NAME);
                        fire.child(reservationKeyList.get(position)).setValue(null);

//                        items.remove(position);
//                        notifyDataSetChanged();
                    }
                });
                adb.show();
            }
        });

        row.setTag(holder);

        setupItem(holder);
        return row;
    }

    protected void sendSMS(Reservation party) {
        String toPhoneNumber = party.getCustomerPhone();
        String smsMessage = "ready in 5 minutes!";
        try {
            SmsManager smsManager = SmsManager.getDefault();
            smsManager.sendTextMessage(toPhoneNumber, null, smsMessage, null, null);
            Toast.makeText(context, "SMS sent.",
                    Toast.LENGTH_LONG).show();
        } catch (Exception e) {
            Toast.makeText(context,
                    "Sending SMS failed.",
                    Toast.LENGTH_LONG).show();
            e.printStackTrace();
        }
    }

    private void setupItem(PartyListHolder holder) {
        holder.name.setText(holder.party.getCustomerName());
    }

    public static class PartyListHolder {
        Reservation party;
        TextView name;
        Button callButton;
        Button removeButton;
    }
}
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

import com.parse.ParseObject;

import java.util.List;

/**
 * Created by ichenwu on 3/10/15.
 */
public class PartyListAdapter extends ArrayAdapter<ParseObject> {

    private List<ParseObject> items;
    private int layoutResourceId;
    private Context context;

    public PartyListAdapter(Context context, int layoutResourceId, List<ParseObject> items) {
        super(context, layoutResourceId, items);
        this.layoutResourceId = layoutResourceId;
        this.context = context;
        this.items = items;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        View row = convertView;


        LayoutInflater inflater = ((Activity) context).getLayoutInflater();
        row = inflater.inflate(layoutResourceId, parent, false);

        final PartyListHolder holder = new PartyListHolder();
        holder.party = items.get(position);
        holder.callButton = (Button)row.findViewById(R.id.callButton);
        holder.callButton.setTag(holder.party);

        holder.name = (TextView)row.findViewById(R.id.party_name);

        holder.callButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.i("aaaaaaaaaaaaaaaaaa",holder.party.get("customerPhone").toString());
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
                adb.setMessage("Are you sure you want to remove " + items.get(position).getString("customerName")+ " from the list");
                adb.setNegativeButton("Cancel", null);
                adb.setPositiveButton("Ok", new AlertDialog.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        items.get(position).deleteInBackground();
                        items.remove(position);
                        notifyDataSetChanged();
                    }
                });
                adb.show();
            }
        });

        row.setTag(holder);

        setupItem(holder);
        return row;
    }

    protected void sendSMS(ParseObject party) {
        String toPhoneNumber = party.get("customerPhone").toString();
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
        holder.name.setText(holder.party.getString("customerName"));
    }

    public static class PartyListHolder {
        ParseObject party;
        TextView name;
        Button callButton;
        Button removeButton;
    }
}
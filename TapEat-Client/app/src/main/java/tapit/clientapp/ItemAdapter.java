package tapit.clientapp;

import android.app.Activity;
import android.app.Fragment;
import android.app.FragmentManager;
import android.content.Context;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import tapit.clientapp.fragments.RestaurantInfoFragment;
import tapit.clientapp.model.Restaurant;


public class ItemAdapter extends RecyclerView.Adapter<ItemAdapter.ViewHolder> {

    private Restaurant[] dataSource;
    private Context _context;

    public ItemAdapter(Restaurant[] dataArgs, Context context){
        dataSource = dataArgs;
        _context = context;
    }
    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        // create a new view
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item, parent, false);

        ViewHolder viewHolder = new ViewHolder(view);

        return viewHolder;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, final int position) {
        holder.RestaurantName.setText(dataSource[position].getName());
        holder.wait_time.setText(dataSource[position].getWaitTime());
        holder.RestaurantImage.setImageResource(dataSource[position].getImage());

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                Intent nextActivity = new Intent(v.getContext(),RestaurantInfoActivity.class);
//                Restaurant selectedRestaurant = dataSource[position];
//                Bundle restaurantBundle = new Bundle();
//                restaurantBundle.putSerializable("serializedRestaurant", selectedRestaurant);
//                nextActivity.putExtra("restaurantBundle", restaurantBundle);
//                v.getContext().startActivity(nextActivity);

                // Initialize RestaurantInfoFragment
                Fragment restaurantInfo = new RestaurantInfoFragment();

                Bundle restaurantBundle = new Bundle();
                restaurantBundle.putSerializable("serializedRestaurant", dataSource[position]);
                restaurantInfo.setArguments(restaurantBundle);

                // Grab fragmentManager from app
                FragmentManager fragmentManager = ((Activity) _context).getFragmentManager();
                fragmentManager.beginTransaction()
                        .replace(R.id.mainContent, restaurantInfo)
                        .commit();
            }
        });
    }

    @Override
    public int getItemCount() {
        return dataSource.length;
    }

    public static class ViewHolder extends RecyclerView.ViewHolder{
        protected TextView RestaurantName;
        protected TextView wait_time;
        protected ImageView RestaurantImage;
        public ViewHolder(View itemView) {
            super(itemView);
            RestaurantName =  (TextView) itemView.findViewById(R.id.RestaurantName);
            wait_time = (TextView) itemView.findViewById(R.id.wait_time);
            RestaurantImage = (ImageView) itemView.findViewById(R.id.RestaurantImage);
//            itemView.setOnClickListener(new View.OnClickListener() {
//                @Override
//                public void onClick(View v) {
//                    Intent nextActivity = new Intent(v.getContext(), RestaurantInfoActivity.class);
//                    Restaurant selectedRestaurant = dataSource
//                }
//            });
        }


    }
}
package tapit.clientapp.utils;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

import tapit.clientapp.R;
import tapit.clientapp.activities.RestaurantInfoActivity;
import tapit.clientapp.model.APIRestaurant;


public class ItemAdapter extends RecyclerView.Adapter<ItemAdapter.ViewHolder> {

    private List<APIRestaurant> dataSource;
    private Context _context;

    public ItemAdapter(List<APIRestaurant> dataArgs, Context context){
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
        holder.RestaurantName.setText(dataSource.get(position).getName());
        holder.wait_time.setText(dataSource.get(position).getWaitTime());

        holder.RestaurantImage.setImageResource(dataSource.get(position).getImage());

        holder.Category.setText(dataSource.get(position).getCategories().get(0));

        holder.Distance.setText(dataSource.get(position).getDistance());

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent nextActivity = new Intent(v.getContext(),RestaurantInfoActivity.class);
                APIRestaurant selectedRestaurant = dataSource.get(position);
                Bundle restaurantBundle = new Bundle();
                restaurantBundle.putSerializable("serializedRestaurant", selectedRestaurant);
                nextActivity.putExtra("restaurantBundle", restaurantBundle);
                v.getContext().startActivity(nextActivity);


            }
        });
    }

    @Override
    public int getItemCount() {
        return dataSource.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder{
        protected TextView RestaurantName;
        protected TextView wait_time;
        protected ImageView RestaurantImage;
        protected TextView Category;
        protected TextView Distance;
        public ViewHolder(View itemView) {
            super(itemView);
            RestaurantName =  (TextView) itemView.findViewById(R.id.RestaurantName);
            wait_time = (TextView) itemView.findViewById(R.id.wait_time);
            RestaurantImage = (ImageView) itemView.findViewById(R.id.RestaurantImage);
            Category = (TextView) itemView.findViewById(R.id.category);
            Distance = (TextView) itemView.findViewById(R.id.distance);
//            itemView.setOnClickListener(new View.OnClickListener() {
//                @Override
//                public void onClick(View v) {
//                    Intent nextActivity = new Intent(v.getContext(),RestaurantInfoActivity.class);
//                    Restaurant selectedRestaurant = dataSource[position];
//                }
//            });
        }


    }
}
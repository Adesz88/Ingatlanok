package com.example.ingatlanok;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;

import java.util.ArrayList;

public class UserListingsAdapter extends RecyclerView.Adapter<UserListingsAdapter.ViewHolder>{

    private ArrayList<PropertyModel> propertiesData;
    private Context context;
    private int lastPosition = -1;

    public UserListingsAdapter(Context context, ArrayList<PropertyModel> propertiesData) {
        this.propertiesData = propertiesData;
        this.context = context;
    }

    @NonNull
    @Override
    public UserListingsAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new ViewHolder(LayoutInflater.from(context).inflate(R.layout.user_listings_item, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull UserListingsAdapter.ViewHolder holder, int position) {
        PropertyModel currentProperty = propertiesData.get(position);

        holder.bindTo(currentProperty);

        if (holder.getAdapterPosition() > lastPosition){
            Animation animation = AnimationUtils.loadAnimation(context, R.anim.fall_down);
            holder.itemView.startAnimation(animation);
            lastPosition = holder.getAdapterPosition();
        }
    }

    @Override
    public int getItemCount() {
        return propertiesData.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{
        private TextView titleText;
        private TextView locationText;
        private TextView priceText;
        private ImageView coverImage;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            titleText = itemView.findViewById(R.id.itemTitle);
            locationText = itemView.findViewById(R.id.location);
            priceText = itemView.findViewById(R.id.price);
            coverImage = itemView.findViewById(R.id.coverImage);
        }

        public void bindTo(PropertyModel currentProperty) {
            titleText.setText(currentProperty.getName());
            locationText.setText(currentProperty.getCity() + ", " + currentProperty.getStreet());
            priceText.setText(Double.toString(currentProperty.getPrice()) + " M Ft");

            Glide.with(context).load(currentProperty.getCoverImageResource()).into(coverImage);

            itemView.findViewById(R.id.editButton).setOnClickListener(view -> ((UserListingsActivity)context)
                    .editProperty(currentProperty));

            itemView.findViewById(R.id.deleteButton).setOnClickListener(view -> ((UserListingsActivity)context).deleteProperty(currentProperty));
        }
    }
}

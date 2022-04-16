package com.example.ingatlanok;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;

import java.util.ArrayList;

public class PropertyModelAdapter extends RecyclerView.Adapter<PropertyModelAdapter.ViewHolder> {//Todo filterezés
    private ArrayList<PropertyModel> propertiesData;
    private ArrayList<PropertyModel> propertiesDataAll;
    private Context context;

    public PropertyModelAdapter(Context context, ArrayList<PropertyModel> propertiesData) {
        this.propertiesData = propertiesData;
        this.propertiesDataAll = propertiesData;
        this.context = context;
    }

    @NonNull
    @Override
    public PropertyModelAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new ViewHolder(LayoutInflater.from(context).inflate(R.layout.list_item, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull PropertyModelAdapter.ViewHolder holder, int position) {
        PropertyModel currentProperty = propertiesData.get(position);

        holder.bindTo(currentProperty);
        //Todo ide jön az animáció
    }

    @Override
    public int getItemCount() {
        return propertiesData.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{
        //Todo feltöltés adatokkal
        private TextView titleText;
        private TextView infoText;
        private TextView priceText;
        private ImageView coverImage;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            titleText = itemView.findViewById(R.id.itemTitle);
            infoText = itemView.findViewById(R.id.subTitle);
            priceText = itemView.findViewById(R.id.price);
            coverImage = itemView.findViewById(R.id.coverImage);
        }

        public void bindTo(PropertyModel currentProperty) {
            titleText.setText(currentProperty.getName());
            infoText.setText(currentProperty.getCity());
            priceText.setText(Double.toString(currentProperty.getPrice()) + " M Ft");

            Glide.with(context).load(currentProperty.getCoverImageResource()).into(coverImage);
        }
    }
}

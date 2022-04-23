package com.example.ingatlanok;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;

import java.util.ArrayList;

public class PropertyModelAdapter extends RecyclerView.Adapter<PropertyModelAdapter.ViewHolder> implements Filterable{

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

    @Override
    public Filter getFilter() {
        return propertyFilter;
    }

    private Filter propertyFilter = new Filter() {
        @Override//Todo filterezés kiegészítése
        protected FilterResults performFiltering(CharSequence charSequence) {
            ArrayList<PropertyModel> filteredList = new ArrayList<>();
            FilterResults results = new FilterResults();

            if (charSequence == null || charSequence.length() == 0){
                results.count = propertiesDataAll.size();
                results.values = propertiesDataAll;
            } else {
                String filterPattern = charSequence.toString().toLowerCase().trim();
                for (PropertyModel property : propertiesDataAll){
                    if (property.getName().toLowerCase().contains(filterPattern) ||property.getCity().toLowerCase().contains(filterPattern)){
                        filteredList.add(property);
                    }
                }
                results.count = filteredList.size();
                results.values = filteredList;
            }
            return results;
        }

        @Override
        protected void publishResults(CharSequence charSequence, FilterResults filterResults) {
            propertiesData = (ArrayList) filterResults.values;
            notifyDataSetChanged();
        }
    };

    public class ViewHolder extends RecyclerView.ViewHolder{
        //Todo feltöltés adatokkal
        private TextView titleText;
        private TextView locationText;
        private TextView priceText;
        private ImageView coverImage;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            titleText = itemView.findViewById(R.id.itemTitle);
            locationText = itemView.findViewById(R.id.subTitle);
            priceText = itemView.findViewById(R.id.price);
            coverImage = itemView.findViewById(R.id.coverImage);
        }

        public void bindTo(PropertyModel currentProperty) {
            titleText.setText(currentProperty.getName());
            locationText.setText(currentProperty.getCity() + ", " + currentProperty.getStreet());
            priceText.setText(Double.toString(currentProperty.getPrice()) + " M Ft");

            Glide.with(context).load(currentProperty.getCoverImageResource()).into(coverImage);

            itemView.findViewById(R.id.details).setOnClickListener(view -> ((PropertyListActivity)context).showDetailedDescription(currentProperty));
        }
    }
}

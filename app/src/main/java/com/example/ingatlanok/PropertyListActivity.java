package com.example.ingatlanok;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.MenuItemCompat;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.res.TypedArray;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.SearchView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;

import java.util.ArrayList;

public class PropertyListActivity extends AppCompatActivity {
    private static final String LOG_TAG = MainActivity.class.getName();

    private FirebaseUser user;
    private RecyclerView recyclerView;
    private ArrayList<PropertyModel> propertyList;
    private PropertyModelAdapter propertyAdapter;
    private FirebaseFirestore firestore;
    private CollectionReference properties;
    private CollectionReference users;
    private NotificationHandler notificationHandler;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_property_list);
        user = FirebaseAuth.getInstance().getCurrentUser();
        if (user == null){
            finish();
        }
        recyclerView = findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new GridLayoutManager(this, 1));
        propertyList = new ArrayList<>();
        propertyAdapter = new PropertyModelAdapter(this, propertyList);
        recyclerView.setAdapter(propertyAdapter);

        IntentFilter filter = new IntentFilter();
        filter.addAction(Intent.ACTION_POWER_CONNECTED);
        this.registerReceiver(powerReceiver, filter);
        notificationHandler = new NotificationHandler(this);
        notificationHandler.cancel();

        firestore = FirebaseFirestore.getInstance();
        properties = firestore.collection("Properties");
        users = firestore.collection("Users");
        queryData();
    }

    BroadcastReceiver powerReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            if (intent.getAction() == null){
                return;
            }

            users.whereEqualTo("email", user.getEmail()).get().addOnSuccessListener(queryDocumentSnapshots -> {
                for (QueryDocumentSnapshot document : queryDocumentSnapshots){
                    if (Boolean.parseBoolean(document.get("notification").toString()) == true){
                        notificationHandler.send("Új ingatlan hírdetést töltöttek fel.");
                        users.document(document.getId()).update("notification", false);
                    }
                }
            });
        }
    };

    private void queryData(){
        propertyList.clear();
        properties.orderBy("name").get().addOnSuccessListener(queryDocumentSnapshots -> {
           for (QueryDocumentSnapshot document : queryDocumentSnapshots){
               PropertyModel property = document.toObject(PropertyModel.class);
               property.setId(document.getId());
               propertyList.add(property);
           }

           if (propertyList.size() == 0){
               initializeData();
               queryData();
           }
           propertyAdapter.notifyDataSetChanged();
        });
    }

    private void initializeData(){
        String[] propertyNames = getResources().getStringArray(R.array.property_names);
        String[] propertyCities = getResources().getStringArray(R.array.property_cities);
        String[] propertyStreets = getResources().getStringArray(R.array.property_streets);
        String[] propertyHeatings = getResources().getStringArray(R.array.property_heatins);
        String[] propertyDescriptions = getResources().getStringArray(R.array.property_descriptions);
        String[] propertyUsers = getResources().getStringArray(R.array.property_users);
        TypedArray propertyPrices = getResources().obtainTypedArray(R.array.property_prices);
        TypedArray propertySizes = getResources().obtainTypedArray(R.array.property_sizes);
        TypedArray propertyRooms = getResources().obtainTypedArray(R.array.property_rooms);
        TypedArray propertyCoverImages = getResources().obtainTypedArray(R.array.property_cover_images);

        for (int i = 0; i < propertyNames.length; i++) {

            properties.add(new PropertyModel(propertyNames[i], propertyPrices.getFloat(i, 0),
                    propertyCities[i], propertyStreets[i], propertySizes.getFloat(i, 0),
                    propertyRooms.getFloat(i, 0), propertyHeatings[i], propertyDescriptions[i],
                    propertyUsers[i], propertyCoverImages.getResourceId(i, 0)));
        }
        propertyCoverImages.recycle();
    }

    public void showDetailedDescription(PropertyModel currentProperty) {
        Intent intent = new Intent(this, DetailedDescriptionActivity.class);
        intent.putExtra("PROPERTY_ID", currentProperty._getId());
        startActivity(intent);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        super.onCreateOptionsMenu(menu);
        getMenuInflater().inflate(R.menu.property_list_menu, menu);
        MenuItem searchItem = menu.findItem(R.id.app_bar_search);
        MenuItem locationItem = menu.findItem(R.id.app_bar_location);
        SearchView searchView = (SearchView) MenuItemCompat.getActionView(searchItem);
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String s) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String s) {
                propertyAdapter.getFilter().filter(s);
                return false;
            }
        });
        
        locationItem.setOnMenuItemClickListener(new MenuItem.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem menuItem) {
                Log.d(LOG_TAG, "location button clicked"); //Todo filterezés
                return false;
            }
        });
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()){
            case R.id.user_listings_button:
                Intent intent = new Intent(this, UserListingsActivity.class);
                startActivityForResult(intent, 1);
                return true;

            case R.id.log_out_button:
                FirebaseAuth.getInstance().signOut();
                finish();
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 1){
            Log.d(LOG_TAG, "onActivityResult: ");
            queryData();
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        unregisterReceiver(powerReceiver);
    }

}
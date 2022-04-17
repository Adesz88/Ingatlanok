package com.example.ingatlanok;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.res.TypedArray;
import android.os.Bundle;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;

import java.util.ArrayList;

public class PropertyListActivity extends AppCompatActivity {
    private static final String INTENT_PASSCODE = "M8XpcBs6y4oM";
    private static final String LOG_TAG = MainActivity.class.getName();

    private FirebaseUser user;
    private RecyclerView recyclerView;
    private ArrayList<PropertyModel> propertyList;
    private PropertyModelAdapter propertyAdapter;
    private FirebaseFirestore firestore;
    private CollectionReference properties;

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

        firestore = FirebaseFirestore.getInstance();
        properties = firestore.collection("Properties");
        queryData();
    }

    private void queryData(){
        propertyList.clear();
        properties.orderBy("name").get().addOnSuccessListener(queryDocumentSnapshots -> {
           for (QueryDocumentSnapshot document : queryDocumentSnapshots){
               PropertyModel property = document.toObject(PropertyModel.class);
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
        TypedArray propertyPrices = getResources().obtainTypedArray(R.array.property_prices);
        TypedArray propertyCoverImages = getResources().obtainTypedArray(R.array.property_cover_images);

        for (int i = 0; i < propertyNames.length; i++) {
            properties.add(new PropertyModel(propertyNames[i], propertyPrices.getFloat(i, 0),
                    propertyCities[i], propertyCoverImages.getResourceId(i, 0)));
        }
        propertyCoverImages.recycle();
    }
}
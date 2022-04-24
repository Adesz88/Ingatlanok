package com.example.ingatlanok;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;

import java.util.ArrayList;

public class UserListingsActivity extends AppCompatActivity {
    private static final String LOG_TAG = MainActivity.class.getName();

    private FirebaseUser user;
    private RecyclerView recyclerView;
    private ArrayList<PropertyModel> propertyList;
    private UserListingsAdapter propertyAdapter;
    private PropertyModelAdapter propertyModelAdapter;
    private FirebaseFirestore firestore;
    private CollectionReference properties;
    FloatingActionButton addButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_listings);
        user = FirebaseAuth.getInstance().getCurrentUser();
        if (user == null){
            finish();
        }
        addButton = findViewById(R.id.floatingAddButton);
        recyclerView = findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new GridLayoutManager(this, 1));
        propertyList = new ArrayList<>();
        propertyAdapter = new UserListingsAdapter(this, propertyList);
        recyclerView.setAdapter(propertyAdapter);

        firestore = FirebaseFirestore.getInstance();
        properties = firestore.collection("Properties");
        queryData();

        addButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                addNewProperty();
            }
        });

        Intent returnIntent = new Intent();
        setResult(1, returnIntent);
    }

    public void queryData(){
        propertyList.clear();
        properties.whereEqualTo("user", user.getEmail()).get().addOnSuccessListener(queryDocumentSnapshots -> {
            for (QueryDocumentSnapshot document : queryDocumentSnapshots){
                PropertyModel property = document.toObject(PropertyModel.class);
                property.setId(document.getId());
                propertyList.add(property);
            }

            propertyAdapter.notifyDataSetChanged();
            Log.d(LOG_TAG, "queryData: ");
        });
    }

    public void editProperty(PropertyModel currentProperty) {
        Intent intent = new Intent(this, AddEditListingActivity.class);
        intent.putExtra("PROPERTY_ID", currentProperty._getId());
        intent.putExtra("edit", true);
        startActivityForResult(intent, 1);
    }

    public void addNewProperty(){
        Intent intent = new Intent(this, AddEditListingActivity.class);
        intent.putExtra("edit", false);
        startActivityForResult(intent, 1);
    }

    public void deleteProperty(PropertyModel currentProperty) {
        DocumentReference reference = properties.document(currentProperty._getId());
        reference.delete().addOnSuccessListener(success ->{
            Toast.makeText(this, "A hírdetés sikeresen törölve", Toast.LENGTH_SHORT);
        }).addOnFailureListener(failure ->{
            Toast.makeText(this, "A hírdetés törlése sikertelen", Toast.LENGTH_LONG);
        });
        queryData();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 1){
            queryData();
        }
    }
}
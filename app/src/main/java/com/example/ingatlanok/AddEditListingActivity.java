package com.example.ingatlanok;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;

public class AddEditListingActivity extends AppCompatActivity {
    private static final String LOG_TAG = MainActivity.class.getName();

    private FirebaseUser user;
    private FirebaseFirestore firestore;
    private CollectionReference properties;
    private CollectionReference users;
    private String id;
    private boolean edit;

    EditText titleEditText;
    EditText cityEditText;
    EditText streetEditText;
    EditText priceEditText;
    EditText sizeEditText;
    EditText roomsEditText;
    EditText heatingEditText;
    EditText descriptionEditText;
    Button sendButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_edit_listing);
        user = FirebaseAuth.getInstance().getCurrentUser();
        id = getIntent().getStringExtra("PROPERTY_ID");
        if (user == null){
            finish();
        }

        firestore = FirebaseFirestore.getInstance();
        properties = firestore.collection("Properties");
        users = firestore.collection("Users");

        edit = getIntent().getBooleanExtra("edit", false);

        titleEditText = findViewById(R.id.titleEditText);
        cityEditText = findViewById(R.id.cityEditText);
        streetEditText = findViewById(R.id.streetEditText);
        priceEditText = findViewById(R.id.priceEditText);
        sizeEditText = findViewById(R.id.sizeEditText);
        roomsEditText = findViewById(R.id.roomsEditText);
        heatingEditText = findViewById(R.id.heatingEditText);
        descriptionEditText = findViewById(R.id.descriptionEditText);
        sendButton = findViewById(R.id.sendButton);

        if (edit){
            queryData();
        }
    }

    public void cancel(View view) {
        finish();
    }

    public void send(View view) {
        boolean success;
        if (edit){
            success = updateProperty();
        } else {
            success = uploadProperty();
        }

        if (success){
            Intent returnIntent = new Intent();
            setResult(1, returnIntent);
            finish();
        } else{
            Toast.makeText(this, "Hibás vagy hiányzó adatok", Toast.LENGTH_LONG).show();
        }
    }

    private void queryData(){
        properties.orderBy("name").get().addOnSuccessListener(queryDocumentSnapshots -> {
            for (QueryDocumentSnapshot document : queryDocumentSnapshots){
                if (document.getId().equals(id)){
                    titleEditText.setText(document.getString("name"));
                    priceEditText.setText(document.get("price").toString());
                    cityEditText.setText(document.getString("city"));
                    streetEditText.setText(document.getString("street"));
                    sizeEditText.setText(document.get("size").toString());
                    roomsEditText.setText(document.get("rooms").toString());
                    heatingEditText.setText(document.getString("heating"));
                    descriptionEditText.setText(document.getString("description"));
                }
            }
        });
    }

    private boolean uploadProperty(){
        try {
            if (titleEditText.getText().toString().isEmpty() || cityEditText.getText().toString().isEmpty()
                    || streetEditText.getText().toString().isEmpty() || heatingEditText.getText().toString().isEmpty()){
                return false;
            }
            PropertyModel property = new PropertyModel();
            property.setName(titleEditText.getText().toString());
            property.setCity(cityEditText.getText().toString());
            property.setStreet(streetEditText.getText().toString());
            property.setPrice(Float.parseFloat(priceEditText.getText().toString()));
            property.setSize(Float.parseFloat(sizeEditText.getText().toString()));
            property.setRooms(Float.parseFloat(roomsEditText.getText().toString()));
            property.setHeating(heatingEditText.getText().toString());
            property.setDescription(descriptionEditText.getText().toString());
            property.setUser(user.getEmail());
            property.setCoverImageResource(R.drawable.no_image);
            properties.add(property);

            //értesítés beállítása a felhasználóknál
            users.orderBy("name").get().addOnSuccessListener(queryDocumentSnapshots -> {
                for (QueryDocumentSnapshot document : queryDocumentSnapshots){
                    users.document(document.getId()).update("notification", true);
                }
            });
        } catch (Exception e){
            return false;
        }
        return true;
    }

    private boolean updateProperty(){
        try {
            if (titleEditText.getText().toString().isEmpty() || cityEditText.getText().toString().isEmpty()
                    || streetEditText.getText().toString().isEmpty() || heatingEditText.getText().toString().isEmpty()){
                return false;
            }
            properties.document(id).update("name", titleEditText.getText().toString(),
                    "city", cityEditText.getText().toString(),
                    "street", streetEditText.getText().toString(),
                    "price", Float.parseFloat(priceEditText.getText().toString()),
                    "size", Float.parseFloat(sizeEditText.getText().toString()),
                    "rooms", Float.parseFloat(roomsEditText.getText().toString()),
                    "heating", heatingEditText.getText().toString(),
                    "description", descriptionEditText.getText().toString());
        } catch (Exception e){
            return false;
        }
        return true;
    }
}
package com.example.ingatlanok;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.FirebaseFirestore;

public class AddEditListingActivity extends AppCompatActivity {
    private FirebaseUser user;
    private FirebaseFirestore firestore;
    private CollectionReference properties;
    private boolean edit;

    EditText titleEditText;
    EditText cityEditText;
    EditText streetEditText;
    EditText priceEditText;
    EditText sizeEditText;
    EditText roomsEditText;
    EditText heatingEditText;
    EditText descriptionEditText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_edit_listing);
        user = FirebaseAuth.getInstance().getCurrentUser();
        if (user == null){
            finish();
        }

        edit = getIntent().getBooleanExtra("edit", false);

        titleEditText = findViewById(R.id.titleEditText);
        cityEditText = findViewById(R.id.cityEditText);
        streetEditText = findViewById(R.id.streetEditText);
        priceEditText = findViewById(R.id.priceEditText);
        sizeEditText = findViewById(R.id.sizeEditText);
        roomsEditText = findViewById(R.id.roomsEditText);
        heatingEditText = findViewById(R.id.heatingEditText);
        descriptionEditText = findViewById(R.id.descriptionEditText);
    }

    public void cancel(View view) {
        finish();
    }

    public void send(View view) {
        if (edit){

        } else {
            uploadProperty();
            finish();
        }
    }

    private void uploadProperty(){
        PropertyModel property = new PropertyModel();
        //property.setName(titleEditText.getText().toString());
        /*property.setCity(cityEditText.getText().toString());
        property.setStreet(streetEditText.getText().toString());
        property.setPrice(Float.parseFloat(priceEditText.getText().toString()));
        property.setSize(Float.parseFloat(sizeEditText.getText().toString()));
        property.setRooms(Float.parseFloat(roomsEditText.getText().toString()));
        property.setHeating(heatingEditText.getText().toString());
        property.setDescription(descriptionEditText.getText().toString());
        property.setUser(user.getEmail());
        property.setCoverImageResource(R.drawable.no_image);*/
        property.setName("valami");
        properties.add(property);
    }
}
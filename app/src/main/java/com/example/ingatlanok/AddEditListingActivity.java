package com.example.ingatlanok;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.EditText;

public class AddEditListingActivity extends AppCompatActivity {
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

        titleEditText = findViewById(R.id.titleEditText);
        cityEditText = findViewById(R.id.cityEditText);
        streetEditText = findViewById(R.id.streetEditText);
        priceEditText = findViewById(R.id.priceEditText);
        sizeEditText = findViewById(R.id.sizeEditText);
        roomsEditText = findViewById(R.id.roomsEditText);
        heatingEditText = findViewById(R.id.heatingEditText);
        descriptionEditText = findViewById(R.id.descriptionEditText);

    }
}
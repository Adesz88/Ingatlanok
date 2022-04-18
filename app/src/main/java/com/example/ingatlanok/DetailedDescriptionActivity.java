package com.example.ingatlanok;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.EditText;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;

public class DetailedDescriptionActivity extends AppCompatActivity {
    private FirebaseUser user;
    private TextView titleText;
    private FirebaseFirestore firestore;
    private CollectionReference properties;
    private String id;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detailed_description);
        user = FirebaseAuth.getInstance().getCurrentUser();
        id = getIntent().getStringExtra("PROPERTY_ID");
        if (user == null || id == null){
            finish();
        }
        titleText = findViewById(R.id.itemTitle);
        firestore = FirebaseFirestore.getInstance();
        properties = firestore.collection("Properties");
        queryData();
    }

    private void queryData() {
        String email;
        properties.orderBy("name").get().addOnSuccessListener(queryDocumentSnapshots -> {
            for (QueryDocumentSnapshot document : queryDocumentSnapshots){
                if (document.getId().equals(id)){
                    titleText.setText(document.get("name").toString());
                }
            }
        });
    }
}
package com.example.ingatlanok;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;

public class DetailedDescriptionActivity extends AppCompatActivity {
    private FirebaseUser user;

    private TextView titleText;
    private TextView priceText;
    private TextView locationText;
    private TextView sizeText;
    private TextView roomsText;
    private TextView heatingText;
    private TextView descriptionText;
    private TextView userText;
    private TextView phoneText;
    private ImageView coverImage;

    private FirebaseFirestore firestore;
    private CollectionReference properties;
    private CollectionReference users;
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
        priceText = findViewById(R.id.price);
        locationText = findViewById(R.id.location);
        sizeText = findViewById(R.id.size);
        roomsText = findViewById(R.id.rooms);
        heatingText = findViewById(R.id.heating);
        descriptionText = findViewById(R.id.description);
        userText = findViewById(R.id.user);
        phoneText = findViewById(R.id.phone);
        coverImage = findViewById(R.id.coverImage);

        firestore = FirebaseFirestore.getInstance();
        properties = firestore.collection("Properties");
        users = firestore.collection("Users");
        queryData();
    }

    //a h??rdet??s let??lt??se
    private void queryData() {
        properties.orderBy("name").get().addOnSuccessListener(queryDocumentSnapshots -> {
            for (QueryDocumentSnapshot document : queryDocumentSnapshots){
                if (document.getId().equals(id)){
                    titleText.setText(document.getString("name"));
                    priceText.setText("??r: " + document.get("price").toString());
                    locationText.setText(document.getString("city") + ", " + document.getString("street"));
                    sizeText.setText("Alapter??let: " + document.get("size").toString() + " m2");
                    roomsText.setText("Szob??k sz??ma: " + document.get("rooms").toString());
                    heatingText.setText("F??t??s: " + document.getString("heating"));
                    descriptionText.setText("R??szletes le??r??s: " + document.getString("description"));

                    //a h??rdet??shez tartoz?? felhaszn??l?? adatainak let??lt??se
                    users.whereEqualTo("email", document.getString("user")).get().addOnSuccessListener(queryUserDocumentSnapshots -> {
                        for (QueryDocumentSnapshot UserDocument : queryUserDocumentSnapshots){
                            userText.setText("H??rdet??: " + UserDocument.getString("name"));
                            phoneText.setText("Telefonsz??m: " + UserDocument.getString("phone"));
                        }
                    });
                    Glide.with(DetailedDescriptionActivity.this).load(Integer.parseInt(document.get("coverImageResource").toString())).into(coverImage);
                }
            }
        });
    }
}
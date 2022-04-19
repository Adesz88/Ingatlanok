package com.example.ingatlanok;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class UserLIstingsActivity extends AppCompatActivity {
    private FirebaseUser user;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_listings);
        user = FirebaseAuth.getInstance().getCurrentUser();
        if (user == null){
            finish();
        }
    }
}
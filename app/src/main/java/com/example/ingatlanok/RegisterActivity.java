package com.example.ingatlanok;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;

public class RegisterActivity extends AppCompatActivity {
    private static final String INTENT_PASSCODE = "M8XpcBs6y4oM";
    private static final String LOG_TAG = MainActivity.class.getName();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        String intentPasscode = getIntent().getStringExtra("INTENT_PASSCODE");
        if(!intentPasscode.equals(INTENT_PASSCODE)){
            finish();
        }
    }
    //Todo firebase megadva a gradle-ben email bejeletkezés folytatása
}
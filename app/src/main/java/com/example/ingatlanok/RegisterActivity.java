package com.example.ingatlanok;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class RegisterActivity extends AppCompatActivity {
    private static final String INTENT_PASSCODE = "M8XpcBs6y4oM";
    private static final String LOG_TAG = MainActivity.class.getName();

    private FirebaseAuth firebaseAuth;

    EditText usernameEditText;
    EditText emailEditText;
    EditText passwordEditText;
    EditText passwordAgainEditText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        String intentPasscode = getIntent().getStringExtra("INTENT_PASSCODE");
        if(!intentPasscode.equals(INTENT_PASSCODE)){
            finish();
        }

        usernameEditText = findViewById(R.id.usernameEditText);
        emailEditText = findViewById(R.id.emailEditText);
        passwordEditText = findViewById(R.id.passwordEditText);
        passwordAgainEditText = findViewById(R.id.passwordAgainEditText);

        firebaseAuth = FirebaseAuth.getInstance();
    }
    //Todo firebase megadva a gradle-ben email bejeletkezés folytatása
    public void register(View view){
        String username = usernameEditText.getText().toString();
        String email = emailEditText.getText().toString();
        String password = passwordEditText.getText().toString();
        String passwordAgain = passwordAgainEditText.getText().toString();

        if (!password.equals(passwordAgain)){
            Toast.makeText(RegisterActivity.this, "A beírt jelszó nem egyezik!", Toast.LENGTH_SHORT).show();
        }

        firebaseAuth.createUserWithEmailAndPassword(email, password).addOnCompleteListener(this, new OnCompleteListener<AuthResult>(){

            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if (task.isSuccessful()){
                    Log.d(LOG_TAG, "User created");
                    Toast.makeText(RegisterActivity.this, "Sikeres regisztráció", Toast.LENGTH_SHORT).show();
                    finish();//Todo ide kell átirányítás főoldalra
                } else {
                    Log.d(LOG_TAG, "User wasn't created");
                    Toast.makeText(RegisterActivity.this, "Hiba történt a regisztrációkor:" + task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    public void cancel(View view){
        finish();
    }
}
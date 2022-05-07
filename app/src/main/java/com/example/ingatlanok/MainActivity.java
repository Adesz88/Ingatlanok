package com.example.ingatlanok;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.res.TypedArray;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.ArrayList;
import java.util.Random;

public class MainActivity extends AppCompatActivity {
    private static final String INTENT_PASSCODE = "M8XpcBs6y4oM";
    private static final String LOG_TAG = MainActivity.class.getName();

    private FirebaseAuth firebaseAuth;
    private FirebaseFirestore firestore;
    private CollectionReference users;

    EditText emailEditText;
    EditText passwordEditText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        emailEditText = findViewById(R.id.emailEditText);
        passwordEditText = findViewById(R.id.passwordEditText);

        firebaseAuth = FirebaseAuth.getInstance();
        firestore = FirebaseFirestore.getInstance();
        users = firestore.collection("Users");
        initializeData();
    }

    //felhasználók feltöltése, regisztrálása
    private void initializeData(){
        Random random = new Random();
        ArrayList<User> usersList = new ArrayList<>();
        users.orderBy("name").get().addOnSuccessListener(queryDocumentSnapshots -> {
            if (queryDocumentSnapshots.size() == 0){
                String[] userNames = getResources().getStringArray(R.array.user_names);
                String[] userEmails = getResources().getStringArray(R.array.user_emails);
                String[] userPhones = getResources().getStringArray(R.array.user_phones);
                TypedArray userNotifications = getResources().obtainTypedArray(R.array.user_notifications);

                for (int i = 0; i < userNames.length; i++) {
                    users.add(new User(userEmails[i], userNames[i], userPhones[i], userNotifications.getBoolean(i, false)));
                    String pass = "pass" + random.nextInt(9) + random.nextInt(9);
                    if (userEmails[i].equals("test@email.com")) {
                        firebaseAuth.createUserWithEmailAndPassword(userEmails[i], "pass12");
                    } else{
                        firebaseAuth.createUserWithEmailAndPassword(userEmails[i], pass);
                    }
                }
            }
        });
    }

    public void register(View view) {
        Intent intent = new Intent(this, RegisterActivity.class);
        intent.putExtra("INTENT_PASSCODE", INTENT_PASSCODE);
        startActivity(intent);
    }

    public void login(View view) {
        String email = emailEditText.getText().toString();
        String password = passwordEditText.getText().toString();
        if (!email.isEmpty() && !password.isEmpty()){
            firebaseAuth.signInWithEmailAndPassword(email, password).addOnCompleteListener(this, new OnCompleteListener<AuthResult>(
            ) {
                @Override
                public void onComplete(@NonNull Task<AuthResult> task) {
                    if (task.isSuccessful()){
                        Log.d(LOG_TAG, "Successful login");
                        showListings();
                    } else {
                        Log.d(LOG_TAG, "Unsuccessful login");
                        Toast.makeText(MainActivity.this, "Sikertelen bejelentkezés: " + task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                    }
                }
            });
        } else {
            Toast.makeText(MainActivity.this, "Üres felhasználónév vagy jelszó mező", Toast.LENGTH_SHORT).show();
        }
    }

    private void showListings(){
        Intent intent = new Intent(this, PropertyListActivity.class);
        intent.putExtra("INTENT_PASSCODE", INTENT_PASSCODE);
        startActivity(intent);
    }
}
package com.example.job_portal;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.Button;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class MainActivity extends AppCompatActivity {

    Button signInBtn;
    Button RegisterBtn;
    Button adminBtn;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_main);

        signInBtn = findViewById(R.id.signin);
        RegisterBtn = findViewById(R.id.registertoapp);
        adminBtn = findViewById(R.id.adminDash);


        signInBtn.setOnClickListener(view ->{
//            startActivity(new Intent(MainActivity.this, SignInActivity.class));
            startActivity(new Intent(MainActivity.this, SignInActivity.class));
        });

        RegisterBtn.setOnClickListener(view ->{
//            startActivity(new Intent(MainActivity.this, SignInActivity.class));
            startActivity(new Intent(MainActivity.this, RegisterActivity.class));
        });

        adminBtn.setOnClickListener(view -> {
            startActivity(new Intent(MainActivity.this, ACCESSADMINDASHACTIVITY.class));
        });
    }
}
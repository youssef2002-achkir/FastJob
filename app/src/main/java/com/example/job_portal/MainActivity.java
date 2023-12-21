package com.example.job_portal;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.Window;
import android.widget.Button;
import androidx.appcompat.app.AppCompatActivity;

/**
 * The main activity that is launched when the application starts.
 */
public class MainActivity extends AppCompatActivity {

    // UI elements
    Button signInBtn;
    Button registerBtn;
    Button adminBtn;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_main);

        // Initialization of UI elements
        signInBtn = findViewById(R.id.signin);
        registerBtn = findViewById(R.id.registertoapp);
        adminBtn = findViewById(R.id.adminDash);

        // Set click listener for the "Sign In" button
        signInBtn.setOnClickListener(view -> {
            startActivity(new Intent(MainActivity.this, SignInActivity.class));
        });

        // Set click listener for the "Register" button
        registerBtn.setOnClickListener(view -> {
            startActivity(new Intent(MainActivity.this, RegisterActivity.class));
        });

        // Set click listener for the "Admin Dashboard" button
        adminBtn.setOnClickListener(view -> {
            startActivity(new Intent(MainActivity.this, ACCESSADMINDASHACTIVITY.class));
        });
    }
}

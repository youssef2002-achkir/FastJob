package com.example.job_portal;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

/**
 * Activity responsible for handling access to the admin dashboard using a secret key.
 */
public class ACCESSADMINDASHACTIVITY extends AppCompatActivity {

    // UI elements
    TextInputEditText secretkeyfield;
    Button toDashBtn;

    // Database reference
    DatabaseReference DB;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_accessadmindashactivity);

        // Initialize UI elements
        secretkeyfield = findViewById(R.id.secretKey);
        toDashBtn = findViewById(R.id.toDashboard);

        // Initialize Firebase Database reference
        DB = FirebaseDatabase.getInstance().getReference();

        // Reference to the secret key in the database
        DatabaseReference secretKey = DB.child("SecretKey");

        // Add a ValueEventListener to listen for changes in the secret key
        secretKey.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                // Print the value of the secret key to the console (for debugging)
                System.out.println(snapshot.getValue().toString());

                // Set a click listener for the "toDashboard" button
                toDashBtn.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        // Check if the entered secret key matches the one in the database
                        if (secretkeyfield.getText().toString().equals(snapshot.getValue().toString())) {
                            // If matched, navigate to the AdminDashboardActivity
                            startActivity(new Intent(ACCESSADMINDASHACTIVITY.this, AdminDashbordActivity.class));
                        } else {
                            // If not matched, show an "ACCESS DENIED" toast
                            Toast.makeText(ACCESSADMINDASHACTIVITY.this, "ACCESS DENIED", Toast.LENGTH_SHORT).show();
                        }
                    }
                });
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                // Handle the event when the database read is cancelled (if needed)
            }
        });
    }
}

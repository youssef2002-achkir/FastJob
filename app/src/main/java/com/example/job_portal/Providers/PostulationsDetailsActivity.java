package com.example.job_portal;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

/**
 * Activity for displaying details of postulations in a specific category for admin.
 */
public class PostulationsDetailsActivity extends AppCompatActivity {

    // UI elements
    LinearLayout layout;
    DatabaseReference DB;
    Button back;
    Button cancel;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_postulations_details);

        // Initialization of UI elements
        layout = findViewById(R.id.AdminAppscontainer);
        back = findViewById(R.id.backadminbtn);
        cancel = findViewById(R.id.logoutbtnadmin);

        // Initialization of Firebase Database reference
        DB = FirebaseDatabase.getInstance().getReference();

        // Get the category passed from the previous activity
        Intent intent = getIntent();
        Bundle args = intent.getBundleExtra("BUNDLE");
        String category = (String) args.getSerializable("CATEGORY");

        // Set up a ValueEventListener to retrieve data from the database
        DatabaseReference categoryRef = DB.child("POSTULATIONS/" + category);
        categoryRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for (DataSnapshot ds : snapshot.getChildren()) {
                    // Extract postulation details from the database
                    String position = ds.child("JOB_TITLE").getValue().toString();
                    String name = ds.child("EMPLOYEE_NAME").getValue().toString();
                    String email = ds.child("EMAIL").getValue().toString();
                    String speciality = ds.child("SPECIALITY").getValue().toString();

                    // Create card element
                    final View view = getLayoutInflater().inflate(R.layout.offerscardadmin, null);
                    TextView jobTitle = view.findViewById(R.id.offerjobadmin);
                    TextView offerSpeciality = view.findViewById(R.id.offerspecadmin);
                    TextView offerEmail = view.findViewById(R.id.offerdateadmin);
                    TextView offerSalary = view.findViewById(R.id.offersalaryadmin);
                    Button deleteButton = view.findViewById(R.id.deleteofferadmin);

                    // Insert database values into the UI
                    jobTitle.setText(position);
                    offerSpeciality.setText(name);
                    offerEmail.setText(email);
                    offerSalary.setText(speciality);

                    // Add the card element to the layout
                    layout.addView(view);

                    // Set click listener for the "Delete" button
                    deleteButton.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            // Remove the postulation from the database and the layout
                            ds.getRef().removeValue();
                            layout.removeView(view);
                        }
                    });
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                // Handle database error
            }
        });

        // Set click listener for the "Back" button
        back.setOnClickListener(view -> {
            finish();
        });

        // Set click listener for the "Cancel" button
        cancel.setOnClickListener(view -> {
            startActivity(new Intent(PostulationsDetailsActivity.this, MainActivity.class));
        });
    }
}

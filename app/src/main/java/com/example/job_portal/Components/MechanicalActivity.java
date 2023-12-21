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
import java.io.Serializable;
import java.util.ArrayList;

/**
 * Activity for displaying job offers in the Mechanical Engineering category.
 */
public class MechanicalActivity extends AppCompatActivity {

    // UI elements
    LinearLayout layout;
    DatabaseReference DB;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mechanical);

        // Initialization of UI elements
        layout = findViewById(R.id.container);

        // Initialization of Firebase Database reference
        DB = FirebaseDatabase.getInstance().getReference();
        DatabaseReference mechanicalRef = DB.child("OFFERS/Mechanical Engineering");

        // Set up a ValueEventListener to retrieve data from the database
        mechanicalRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for (DataSnapshot ds : snapshot.getChildren()) {

                    // Extract job offer details from the database
                    String position = ds.child("JOB_TITLE").getValue().toString();
                    String salary = ds.child("SALARY").getValue().toString();
                    String company = ds.child("COMPANY").getValue().toString();
                    String dateBegin = ds.child("BEGIN_DATE").getValue().toString();

                    // Create card element
                    final View view = getLayoutInflater().inflate(R.layout.card, null);
                    TextView jobTitle = view.findViewById(R.id.jobtileEco);
                    TextView companyTextView = view.findViewById(R.id.companyEco);
                    TextView salaryTextView = view.findViewById(R.id.salaryEco);
                    TextView dateBeginTextView = view.findViewById(R.id.dateEco);
                    Button applyButton = view.findViewById(R.id.applyEco);

                    // Insert database values into the UI
                    jobTitle.setText(position);
                    companyTextView.setText(company);
                    salaryTextView.setText(salary);
                    dateBeginTextView.setText(dateBegin);

                    // Add the card element to the layout
                    layout.addView(view);

                    // Extract additional details for passing to the next activity
                    String employerName = ds.child("EMPLOYER_NAME").getValue().toString();
                    String speciality = ds.child("SPECIALITY").getValue().toString();

                    // Create a list to store job information
                    ArrayList<String> jobInformation = new ArrayList<>();
                    jobInformation.add(position);
                    jobInformation.add(salary);
                    jobInformation.add(company);
                    jobInformation.add(dateBegin);
                    jobInformation.add(employerName);
                    jobInformation.add(speciality);

                    // Set click listener for the "Apply" button
                    applyButton.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            // Start the JobDetailsActivity and pass job information as extras
                            Intent intent = new Intent(MechanicalActivity.this, JobDetailsActivity.class);
                            Bundle args = new Bundle();
                            args.putSerializable("JobInformation", (Serializable) jobInformation);
                            intent.putExtra("BUNDLE", args);
                            startActivity(intent);
                        }
                    });
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                // Handle database error
            }
        });

        // Initialization of UI elements for buttons
        Button backBtn;
        Button homeBtn;
        backBtn = findViewById(R.id.backBtn);
        homeBtn = findViewById(R.id.homeBtn);

        // Set click listener for the "Add Application" button
        Button addApp;
        addApp = findViewById(R.id.AddAppBtn);
        addApp.setOnClickListener(view -> {
            startActivity(new Intent(MechanicalActivity.this, AddApplicationActivity.class));
        });

        // Set click listener for the "Back" button
        backBtn.setOnClickListener(view -> {
            finish();
        });

        // Set click listener for the "Home" button
        homeBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(MechanicalActivity.this, CategoriesActivity.class));
            }
        });
    }
}

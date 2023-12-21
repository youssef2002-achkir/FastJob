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
 * Activity for displaying job offers in the Admin Assistance category.
 */
public class AdminAssistanceActivity extends AppCompatActivity {

    // UI elements
    LinearLayout layout;

    // Database reference
    DatabaseReference DB;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_assistance);

        // Initialize UI elements
        layout = findViewById(R.id.container);

        // Initialize Firebase Database reference
        DB = FirebaseDatabase.getInstance().getReference();
        DatabaseReference assistantRef = DB.child("OFFERS/Admin Assistance");

        // Add a ValueEventListener to listen for changes in job offers
        assistantRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                // Iterate through each job offer in the category
                for (DataSnapshot ds : snapshot.getChildren()) {
                    // Extract job offer information from the database
                    String position = ds.child("JOB_TITLE").getValue().toString();
                    String salary = ds.child("SALARY").getValue().toString();
                    String company = ds.child("COMPANY").getValue().toString();
                    String datebegin = ds.child("BEGIN_DATE").getValue().toString();
                    String employerName = ds.child("EMPLOYER_NAME").getValue().toString();
                    String speciality = ds.child("SPECIALITY").getValue().toString();

                    // Create a card element using the card layout
                    final View view = getLayoutInflater().inflate(R.layout.card, null);
                    TextView JOBTITLE = view.findViewById(R.id.jobtileEco);
                    TextView COMPANY = view.findViewById(R.id.companyEco);
                    TextView SALARY = view.findViewById(R.id.salaryEco);
                    TextView DATEBEGIN = view.findViewById(R.id.dateEco);
                    Button APPLY = view.findViewById(R.id.applyEco);

                    // Set the job offer information in the card
                    JOBTITLE.setText(position);
                    COMPANY.setText(company);
                    SALARY.setText(salary);
                    DATEBEGIN.setText(datebegin);

                    // Add the card to the layout
                    layout.addView(view);

                    // Create a list to store job information
                    ArrayList<String> jobInformation = new ArrayList<>();
                    jobInformation.add(position);
                    jobInformation.add(salary);
                    jobInformation.add(company);
                    jobInformation.add(datebegin);
                    jobInformation.add(employerName);
                    jobInformation.add(speciality);

                    // Set a click listener for the "Apply" button
                    APPLY.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            // Start JobDetailsActivity and pass job information to it
                            Intent intent = new Intent(AdminAssistanceActivity.this, JobDetailsActivity.class);
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
                // Handle the event when the database read is cancelled (if needed)
            }
        });

        // Set click listeners for buttons
        Button backBtn;
        Button homeBtn;
        Button addApp;

        backBtn = findViewById(R.id.backBtn);
        homeBtn = findViewById(R.id.homeBtn);
        addApp = findViewById(R.id.AddAppBtn);

        // Set a click listener for the "Add Application" button
        addApp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(AdminAssistanceActivity.this, AddApplicationActivity.class));
            }
        });

        // Set a click listener for the "Back" button
        backBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        // Set a click listener for the "Home" button
        homeBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(AdminAssistanceActivity.this, CategoriesActivity.class));
            }
        });
    }
}

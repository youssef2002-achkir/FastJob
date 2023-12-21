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
 * Activity for displaying job offers in the Electrical Engineering category.
 */
public class ElectricalActivity extends AppCompatActivity {

    // UI elements
    LinearLayout layout;
    Button backBtn;
    Button homeBtn;
    Button addApp;

    // Firebase Database reference
    DatabaseReference DB;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_electrical);

        // Initialize UI elements
        layout = findViewById(R.id.container);
        backBtn = findViewById(R.id.backBtn);
        homeBtn = findViewById(R.id.homeBtn);
        addApp = findViewById(R.id.AddAppBtn);

        // Firebase Database reference for Electrical Engineering category
        DB = FirebaseDatabase.getInstance().getReference();
        DatabaseReference electricalRef = DB.child("OFFERS/Electrical Engineering");

        // Set up ValueEventListener to fetch and display job offers
        electricalRef.addValueEventListener(
            new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot snapshot) {
                    for (DataSnapshot ds : snapshot.getChildren()) {

                        // Retrieve job offer details from the database
                        String position = ds.child("JOB_TITLE").getValue().toString();
                        String salary = ds.child("SALARY").getValue().toString();
                        String company = ds.child("COMPANY").getValue().toString();
                        String datebegin = ds.child("BEGIN_DATE").getValue().toString();

                        // Inflate card layout for each job offer
                        final View view = getLayoutInflater().inflate(R.layout.card, null);
                        TextView JOBTITLE = view.findViewById(R.id.jobtileEco);
                        TextView COMPANY = view.findViewById(R.id.companyEco);
                        TextView SALARY = view.findViewById(R.id.salaryEco);
                        TextView DATEBEGIN = view.findViewById(R.id.dateEco);
                        Button APPLY = view.findViewById(R.id.applyEco);

                        // Set job offer details in the card
                        JOBTITLE.setText(position);
                        COMPANY.setText(company);
                        SALARY.setText(salary);
                        DATEBEGIN.setText(datebegin);

                        // Add the card to the layout
                        layout.addView(view);

                        // Retrieve additional information
                        String employerName = ds.child("EMPLOYER_NAME").getValue().toString();
                        String speciality = ds.child("SPECIALITY").getValue().toString();

                        // Create a list to hold job information
                        ArrayList<String> jobInformation = new ArrayList<>();
                        jobInformation.add(position);
                        jobInformation.add(salary);
                        jobInformation.add(company);
                        jobInformation.add(datebegin);
                        jobInformation.add(employerName);
                        jobInformation.add(speciality);

                        // Set OnClickListener for the "Apply" button
                        APPLY.setOnClickListener(
                            new View.OnClickListener() {
                                @Override
                                public void onClick(View view) {
                                    // Start JobDetailsActivity with job information
                                    Intent intent = new Intent(ElectricalActivity.this, JobDetailsActivity.class);
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
                    // Handle onCancelled event if needed
                }
            });

        // Set click listeners for buttons
        addApp.setOnClickListener(view -> startActivity(new Intent(ElectricalActivity.this, AddApplicationActivity.class)));
        backBtn.setOnClickListener(view -> finish());
        homeBtn.setOnClickListener(view -> startActivity(new Intent(ElectricalActivity.this, CategoriesActivity.class)));
    }
}

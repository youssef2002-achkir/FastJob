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
 * Activity class for displaying job offers in the Journalism category.
 */
public class JournalismActivity extends AppCompatActivity {

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
        setContentView(R.layout.activity_journalism);

        // Initialization of UI elements
        layout = findViewById(R.id.container);
        backBtn = findViewById(R.id.backBtn);
        homeBtn = findViewById(R.id.homeBtn);
        addApp = findViewById(R.id.AddAppBtn);

        // Firebase Database reference to the Journalism category
        DB = FirebaseDatabase.getInstance().getReference();
        DatabaseReference journalismRef = DB.child("OFFERS/Journalism");

        journalismRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for (DataSnapshot ds : snapshot.getChildren()) {
                    // Get job details from the database
                    String position = ds.child("JOB_TITLE").getValue().toString();
                    String salary = ds.child("SALARY").getValue().toString();
                    String company = ds.child("COMPANY").getValue().toString();
                    String datebegin = ds.child("BEGIN_DATE").getValue().toString();

                    // Create card element
                    final View view = getLayoutInflater().inflate(R.layout.card, null);
                    TextView JOBTITLE = view.findViewById(R.id.jobtileEco);
                    TextView COMPANY = view.findViewById(R.id.companyEco);
                    TextView SALARY = view.findViewById(R.id.salaryEco);
                    TextView DATEBEGIN = view.findViewById(R.id.dateEco);
                    Button APPLY = view.findViewById(R.id.applyEco);

                    // Insert database values into the card
                    JOBTITLE.setText(position);
                    COMPANY.setText(company);
                    SALARY.setText(salary);
                    DATEBEGIN.setText(datebegin);

                    layout.addView(view);

                    // Get additional job information
                    String employerName = ds.child("EMPLOYER_NAME").getValue().toString();
                    String speciality = ds.child("SPECIALITY").getValue().toString();

                    // ArrayList to pass job information to another activity
                    ArrayList<String> jobInformation = new ArrayList<>();
                    jobInformation.add(position);
                    jobInformation.add(salary);
                    jobInformation.add(company);
                    jobInformation.add(datebegin);
                    jobInformation.add(employerName);
                    jobInformation.add(speciality);

                    // Set click listener for the "APPLY" button
                    APPLY.setOnClickListener(view1 -> {
                        Intent intent = new Intent(JournalismActivity.this, JobDetailsActivity.class);
                        Bundle args = new Bundle();
                        args.putSerializable("JobInformation", (Serializable) jobInformation);
                        intent.putExtra("BUNDLE", args);
                        startActivity(intent);
                    });
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
            }
        });

        // Set click listeners for buttons
        backBtn.setOnClickListener(view -> finish());

        homeBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(JournalismActivity.this, CategoriesActivity.class));
            }
        });

        // Set click listener for the "Add Application" button
        addApp.setOnClickListener(view -> startActivity(new Intent(JournalismActivity.this, AddApplicationActivity.class)));
    }
}

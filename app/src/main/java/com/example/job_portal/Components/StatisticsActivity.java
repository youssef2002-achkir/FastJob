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
 * Activity for displaying statistics job offers.
 */
public class StatisticsActivity extends AppCompatActivity {

    // Firebase Database reference
    DatabaseReference DB;

    // UI elements
    LinearLayout layout;
    Button backBtn;
    Button homeBtn;
    Button addApp;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_statistics);

        // Initialization of UI elements
        layout = findViewById(R.id.container);
        backBtn = findViewById(R.id.backBtn);
        homeBtn = findViewById(R.id.homeBtn);
        addApp = findViewById(R.id.AddAppBtn);

        // Initialization of Firebase Database reference
        DB = FirebaseDatabase.getInstance().getReference();

        // Reference to the "Statistics" node in the database
        DatabaseReference statisticsRef = DB.child("OFFERS/Statistics");

        // Event listener to retrieve and display statistics job offers
        statisticsRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for (DataSnapshot ds : snapshot.getChildren()) {

                    // Extracting job offer details from the database
                    String position = ds.child("JOB_TITLE").getValue().toString();
                    String salary = ds.child("SALARY").getValue().toString();
                    String company = ds.child("COMPANY").getValue().toString();
                    String dateBegin = ds.child("BEGIN_DATE").getValue().toString();

                    // Create card element
                    final View view = getLayoutInflater().inflate(R.layout.card, null);
                    TextView JOBTITLE = view.findViewById(R.id.jobtileEco);
                    TextView COMPANY = view.findViewById(R.id.companyEco);
                    TextView SALARY = view.findViewById(R.id.salaryEco);
                    TextView DATEBEGIN = view.findViewById(R.id.dateEco);
                    Button APPLY = view.findViewById(R.id.applyEco);

                    // Insert database values into card
                    JOBTITLE.setText(position);
                    COMPANY.setText(company);
                    SALARY.setText(salary);
                    DATEBEGIN.setText(dateBegin);

                    layout.addView(view);

                    // Extracting additional job information
                    String employerName = ds.child("EMPLOYER_NAME").getValue().toString();
                    String speciality = ds.child("SPECIALITY").getValue().toString();

                    // Creating a list to store job information
                    ArrayList<String> jobInformation = new ArrayList<>();
                    jobInformation.add(position);
                    jobInformation.add(salary);
                    jobInformation.add(company);
                    jobInformation.add(dateBegin);
                    jobInformation.add(employerName);
                    jobInformation.add(speciality);

                    // Set up a click listener for the "Apply" button
                    APPLY.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            // Navigate to JobDetailsActivity and pass job information
                            Intent intent = new Intent(StatisticsActivity.this, JobDetailsActivity.class);
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

        // Set up click listeners for buttons
        addApp.setOnClickListener(view -> {
            startActivity(new Intent(StatisticsActivity.this, AddApplicationActivity.class));
        });

        backBtn.setOnClickListener(view -> {
            finish();
        });

        homeBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(StatisticsActivity.this, CategoriesActivity.class));
            }
        });
    }
}

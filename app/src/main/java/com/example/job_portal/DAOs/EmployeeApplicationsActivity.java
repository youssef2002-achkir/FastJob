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
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import java.util.ArrayList;

/**
 * Activity for displaying job applications submitted by an employee.
 */
public class EmployeeApplicationsActivity extends AppCompatActivity {

    // UI elements
    LinearLayout layout;
    Button newPostBtn;
    Button AppbackBtn;
    Button AppprofileBtn;
    Button AppsignoutBtn;

    // Firebase Database reference
    DatabaseReference DB;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_employee_applications);

        // Initialize UI elements
        newPostBtn = findViewById(R.id.appAddPostBtn);
        AppprofileBtn = findViewById(R.id.employeeprofilebtn);
        AppsignoutBtn = findViewById(R.id.applogoutBtn);
        AppbackBtn = findViewById(R.id.appbackBtn);

        // Set OnClickListener for adding a new post
        newPostBtn.setOnClickListener(
            view -> startActivity(new Intent(EmployeeApplicationsActivity.this, AddApplicationActivity.class)));

        // Set OnClickListener for the back button
        AppbackBtn.setOnClickListener(
            view -> finish());

        // Set OnClickListener for the signout button
        AppsignoutBtn.setOnClickListener(
            view -> startActivity(new Intent(EmployeeApplicationsActivity.this, MainActivity.class)));

        // Initialize UI element for displaying applications
        layout = findViewById(R.id.Appcontainer);

        // Retrieve employee name from the intent
        Bundle bundle = getIntent().getExtras();
        String employeeName = bundle.getString("EMPLOYEE NAME");

        // Firebase Database reference
        DB = FirebaseDatabase.getInstance().getReference();

        // List of job categories
        ArrayList<String> categories = new ArrayList<>();
        categories.add("Economics");
        categories.add("Statistics");
        categories.add("Electrical Engineering");
        categories.add("Mechanical Engineering");
        categories.add("Software Engineering");
        categories.add("Architecture");
        categories.add("Admin Assistance");
        categories.add("Journalism");

        // Iterate over each job category
        for (int i = 0; i < categories.size(); i++) {
            DatabaseReference postulationsRef = DB.child("POSTULATIONS/" + categories.get(i));
            Query q = postulationsRef.orderByChild("EMPLOYEE_NAME").equalTo(employeeName);
            q.addValueEventListener(
                new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        for (DataSnapshot ds : snapshot.getChildren()) {

                            // Retrieve application details from the database
                            String position = ds.child("JOB_TITLE").getValue().toString();
                            String email = ds.child("EMAIL").getValue().toString();
                            String phone = ds.child("PHONE_NUMBER").getValue().toString();
                            String speciality = ds.child("SPECIALITY").getValue().toString();

                            // Inflate card layout for each application
                            final View apps = getLayoutInflater().inflate(R.layout.appscard, null);
                            TextView JOBTITLE = apps.findViewById(R.id.appjob);
                            TextView EMAIL = apps.findViewById(R.id.appemail);
                            TextView Date = apps.findViewById(R.id.appdate);
                            TextView SPECIALITY = apps.findViewById(R.id.appspec);
                            Button DELETE = apps.findViewById(R.id.deleteapp);

                            // Set application details in the card
                            JOBTITLE.setText(position);
                            EMAIL.setText(email);
                            Date.setText(phone);
                            SPECIALITY.setText(speciality);

                            // Add the card to the layout
                            layout.addView(apps);

                            // Create a list to hold application information
                            ArrayList<String> applicationInformation = new ArrayList<>();
                            applicationInformation.add(position);
                            applicationInformation.add(speciality);
                            applicationInformation.add(phone);
                            applicationInformation.add(email);

                            // Set OnClickListener for the "Delete" button
                            DELETE.setOnClickListener(
                                new View.OnClickListener() {
                                    @Override
                                    public void onClick(View view) {
                                        // Remove application from the database and layout
                                        ds.getRef().removeValue();
                                        layout.removeView(apps);
                                    }
                                });
                        }
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {
                        // Handle onCancelled event if needed
                    }
                });
        }
    }
}

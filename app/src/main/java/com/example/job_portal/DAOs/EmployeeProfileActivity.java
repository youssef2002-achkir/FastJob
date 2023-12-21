package com.example.job_portal;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import java.io.Serializable;
import java.util.ArrayList;

/**
 * Activity for displaying and managing the profile of an employee.
 */
public class EmployeeProfileActivity extends AppCompatActivity {

    // UI elements
    TextView FullName;
    TextView Email;
    TextView PhoneNumber;
    TextView FirstName;
    TextView LastName;
    Button BackBtn;
    Button HomeBtn;
    Button EditBtn;
    Button AppsBtn;
    Button LogoutBtn;

    // Firebase Database reference
    DatabaseReference DB;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_employee_profile);

        // Initialize UI elements
        FullName = findViewById(R.id.profileEmployeeName);
        Email = findViewById(R.id.profileEmployeeEmail);
        PhoneNumber = findViewById(R.id.profileEmployeePhone);
        FirstName = findViewById(R.id.profileEmployeeFName);
        LastName = findViewById(R.id.profileEmployeeLName);
        BackBtn = findViewById(R.id.employeePBack);
        HomeBtn = findViewById(R.id.employeePHome);
        EditBtn = findViewById(R.id.employeePEdit);
        AppsBtn = findViewById(R.id.employeePApps);
        LogoutBtn = findViewById(R.id.employeePLogout);

        // Retrieve employee information from the intent
        Intent intent = getIntent();
        Bundle args = intent.getBundleExtra("BUNDLE");
        ArrayList<String> EMPInformation = (ArrayList<String>) args.getSerializable("EMPInformation");

        // Set employee details in UI elements
        FullName.setText("Hello Dear, " + EMPInformation.get(0));
        Email.setText(EMPInformation.get(1));
        FirstName.setText(EMPInformation.get(2));
        LastName.setText(EMPInformation.get(3));
        PhoneNumber.setText(EMPInformation.get(4));

        // Initialize Firebase Database reference
        DB = FirebaseDatabase.getInstance().getReference();

        // Set OnClickListener for the "Logout" button
        LogoutBtn.setOnClickListener(view -> {
            startActivity(new Intent(EmployeeProfileActivity.this, MainActivity.class));
        });

        // Set OnClickListener for the "Home" button
        HomeBtn.setOnClickListener(view -> {
            finish();
        });

        // Set OnClickListener for the "Back" button
        BackBtn.setOnClickListener(view -> {
            finish();
        });

        // Set OnClickListener for the "Applications" button
        AppsBtn.setOnClickListener(view -> {
            // Start EmployeeApplicationsActivity and pass employee name as an extra
            Intent apps = new Intent(this, EmployeeApplicationsActivity.class);
            String name = EMPInformation.get(0);
            Bundle bundle = new Bundle();
            bundle.putString("EMPLOYEE NAME", name);
            apps.putExtras(bundle);
            startActivity(apps);
        });

        // Set OnClickListener for the "Edit" button
        EditBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Start EvaluateApplicationActivity and pass necessary information
                ArrayList<String> infos = new ArrayList<>();
                infos.add(EMPInformation.get(0)); // Employee name
                infos.add("Employees"); // Role (in this case, Employees)
                Intent intent = new Intent(EmployeeProfileActivity.this, EvaluateApplicationActivity.class);
                Bundle args = new Bundle();
                args.putSerializable("username", (Serializable) infos);
                intent.putExtra("BUNDLE", args);
                startActivity(intent);
            }
        });
    }
}

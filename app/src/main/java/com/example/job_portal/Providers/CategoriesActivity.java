package com.example.job_portal;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import java.io.Serializable;
import java.util.ArrayList;

/**
 * Activity for displaying job categories and handling user interactions.
 */
public class CategoriesActivity extends AppCompatActivity {

    // UI elements for each job category
    CardView EconomicsCard;
    CardView ElectricalCard;
    CardView MechanicalCard;
    CardView StatisticsCard;
    CardView SoftwareEngCard;
    CardView AdminAssistantCard;
    CardView ArchitectureCard;
    CardView JournalismCard;

    // Buttons for various actions
    Button AppyBtn;
    Button backBtn;
    Button profileBtn;
    Button signOut;

    // Firebase Database reference
    DatabaseReference DB;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_categories);

        // Initialize UI elements
        EconomicsCard = findViewById(R.id.economicsCard);
        ElectricalCard = findViewById(R.id.electricalCard);
        MechanicalCard = findViewById(R.id.mechanicCard);
        StatisticsCard = findViewById(R.id.statisticsCard);
        SoftwareEngCard = findViewById(R.id.softwareCard);
        AdminAssistantCard = findViewById(R.id.SecretaryCard);
        ArchitectureCard = findViewById(R.id.ArchitectCard);
        JournalismCard = findViewById(R.id.JournalistCard);

        // Initialize buttons
        AppyBtn = findViewById(R.id.AddCatBtn);
        backBtn = findViewById(R.id.backCatBtn);
        profileBtn = findViewById(R.id.employeeProfileBtn);
        signOut = findViewById(R.id.logoutCatBtn);

        // Get employee email from the intent
        Intent intent = getIntent();
        Bundle args = intent.getBundleExtra("BUNDLE");
        String employeeEmail = (String) args.getSerializable("employeeEmail");

        // Set click listeners for each job category
        EconomicsCard.setOnClickListener(view -> startActivity(new Intent(CategoriesActivity.this, EconomicsActivity.class)));
        ElectricalCard.setOnClickListener(view -> startActivity(new Intent(CategoriesActivity.this, ElectricalActivity.class)));
        MechanicalCard.setOnClickListener(view -> startActivity(new Intent(CategoriesActivity.this, MechanicalActivity.class)));
        StatisticsCard.setOnClickListener(view -> startActivity(new Intent(CategoriesActivity.this, StatisticsActivity.class)));
        SoftwareEngCard.setOnClickListener(view -> startActivity(new Intent(CategoriesActivity.this, SoftwareEngineeringActivity.class)));
        AdminAssistantCard.setOnClickListener(view -> startActivity(new Intent(CategoriesActivity.this, AdminAssistanceActivity.class)));
        ArchitectureCard.setOnClickListener(view -> startActivity(new Intent(CategoriesActivity.this, ArchitectureActivity.class)));
        JournalismCard.setOnClickListener(view -> startActivity(new Intent(CategoriesActivity.this, JournalismActivity.class)));

        // Set click listeners for action buttons
        AppyBtn.setOnClickListener(view -> startActivity(new Intent(CategoriesActivity.this, AddApplicationActivity.class)));
        backBtn.setOnClickListener(view -> startActivity(new Intent(CategoriesActivity.this, SignInActivity.class)));
        signOut.setOnClickListener(view -> startActivity(new Intent(CategoriesActivity.this, MainActivity.class)));

        // Initialize Firebase Database reference for employees
        DB = FirebaseDatabase.getInstance().getReference();
        DatabaseReference employeesRef = DB.child("Employees");

        // Query to get employee information based on email
        Query EmpEmail = employeesRef.orderByChild("EMAIL").equalTo(employeeEmail);

        // Add a ValueEventListener to listen for changes in employee information
        EmpEmail.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for (DataSnapshot ds : snapshot.getChildren()) {
                    // Create a list to store employee information
                    ArrayList<String> employeeInfos = new ArrayList<>();
                    employeeInfos.add(ds.child("FULL_NAME").getValue().toString());
                    employeeInfos.add(ds.child("EMAIL").getValue().toString());
                    employeeInfos.add(ds.child("FIRST_NAME").getValue().toString());
                    employeeInfos.add(ds.child("LAST_NAME").getValue().toString());
                    employeeInfos.add(ds.child("PHONE_NUMBER").getValue().toString());

                    // Set click listener for the "Profile" button
                    profileBtn.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            // Print employee information and start EmployeeProfileActivity
                            System.out.println(employeeInfos.get(0));
                            System.out.println(employeeInfos.get(1));
                            System.out.println(employeeInfos.get(2));
                            System.out.println(employeeInfos.get(3));
                            System.out.println(employeeInfos.get(4));
                            Intent intent = new Intent(CategoriesActivity.this, EmployeeProfileActivity.class);
                            Bundle args = new Bundle();
                            args.putSerializable("EMPInformation", (Serializable) employeeInfos);
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
    }
}

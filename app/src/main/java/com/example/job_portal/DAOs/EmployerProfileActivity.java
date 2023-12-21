package com.example.job_portal;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;
import com.google.firebase.database.DatabaseReference;
import java.io.Serializable;
import java.util.ArrayList;

/**
 * Activity for displaying and managing the profile of an employer.
 */
public class EmployerProfileActivity extends AppCompatActivity {

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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_employer_profile);

        // Initialize UI elements
        FullName = findViewById(R.id.profileEmployerName);
        Email = findViewById(R.id.profileEmployerEmail);
        PhoneNumber = findViewById(R.id.profileEmployerPhone);
        FirstName = findViewById(R.id.profileEmployerFName);
        LastName = findViewById(R.id.profileEmployerLName);
        BackBtn = findViewById(R.id.employerPBack);
        HomeBtn = findViewById(R.id.employerPHome);
        EditBtn = findViewById(R.id.employerPEdit);
        AppsBtn = findViewById(R.id.employerPApps);
        LogoutBtn = findViewById(R.id.employerPLogout);

        // Get employer information from the intent
        Intent intent = getIntent();
        Bundle args = intent.getBundleExtra("BUNDLE");
        ArrayList<String> EMPInformation = (ArrayList<String>) args.getSerializable("EMPLOYERInformation");

        // Display employer information in UI elements
        FullName.setText("Hello Dear, " + EMPInformation.get(0));
        Email.setText(EMPInformation.get(1));
        FirstName.setText(EMPInformation.get(2));
        LastName.setText(EMPInformation.get(3));
        PhoneNumber.setText(EMPInformation.get(4));

        // Set OnClickListener for the "Logout" button
        LogoutBtn.setOnClickListener(view -> {
            startActivity(new Intent(EmployerProfileActivity.this, MainActivity.class));
        });

        // Set OnClickListener for the "Home" button
        HomeBtn.setOnClickListener(view -> {
            startActivity(new Intent(EmployerProfileActivity.this, JobPostulationActivity.class));
        });

        // Set OnClickListener for the "Back" button
        BackBtn.setOnClickListener(view -> {
            finish();
        });

        // Set OnClickListener for the "Offers" button
        AppsBtn.setOnClickListener(view -> {
            // Start EmployerOffersActivity and pass employer name in the intent
            Intent offers = new Intent(this, EmployerOffersActivity.class);
            String name = EMPInformation.get(0);
            Bundle bundle = new Bundle();
            bundle.putString("EMPLOYER NAME", name);
            offers.putExtras(bundle);
            startActivity(offers);
        });

        // Set OnClickListener for the "Edit" button
        EditBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Start EvaluateApplicationActivity and pass necessary information in the intent
                ArrayList<String> infos = new ArrayList<>();
                infos.add(EMPInformation.get(0));
                infos.add("Employers");
                Intent intent = new Intent(EmployerProfileActivity.this, EvaluateApplicationActivity.class);
                Bundle args = new Bundle();
                args.putSerializable("username", (Serializable) infos);
                intent.putExtra("BUNDLE", args);
                startActivity(intent);
            }
        });
    }
}

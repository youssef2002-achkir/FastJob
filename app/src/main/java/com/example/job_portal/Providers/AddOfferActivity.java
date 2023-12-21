package com.example.job_portal;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class AddOfferActivity extends AppCompatActivity implements AdapterView.OnItemSelectedListener {

    EditText jobtitle;
    EditText salary;
    EditText company;
    EditText employerName;
    EditText beginDate;
    Button submitDataBtn;
    Button resetDataBtn;
    Button backBtn;
    Button homeBtn;
    DatabaseReference DB;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_offer);

        // Initialization of UI elements
        initializeUI();

        // Initialization of the Firebase Database reference
        DB = FirebaseDatabase.getInstance().getReference();

        // Configuration of the Spinner (Dropdown)
        ArrayList<String> categories = new ArrayList<>();
        categories.add("Economics");
        categories.add("Statistics");
        categories.add("Electrical Engineering");
        categories.add("Mechanical Engineering");
        categories.add("Software Engineering");
        categories.add("Architecture");
        categories.add("Admin Assistance");
        categories.add("Journalism");

        ArrayAdapter<String> adapter =
            new ArrayAdapter<String>(this, android.R.layout.simple_spinner_dropdown_item, categories);

        Spinner spinner = findViewById(R.id.speciality);
        spinner.setAdapter(adapter);

        spinner.setOnItemSelectedListener(this);

        // Setting click listeners for buttons
        setButtonClickListeners();
    }

    // Method to initialize UI elements
    private void initializeUI() {
        jobtitle = findViewById(R.id.position);
        salary = findViewById(R.id.salary);
        company = findViewById(R.id.company);
        employerName = findViewById(R.id.employerName);
        beginDate = findViewById(R.id.date);
        submitDataBtn = findViewById(R.id.submitOfferBtn);
        resetDataBtn = findViewById(R.id.resetBtn);
        backBtn = findViewById(R.id.backOfferBtn);
        homeBtn = findViewById(R.id.homeOfferBtn);
    }

    // Method to set click listeners for buttons
    private void setButtonClickListeners() {
        submitDataBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Validation and submission of the job offer
                validateAndSubmitOffer();
            }
        });

        resetDataBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Resetting the form fields
                resetForm();
            }
        });

        homeBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Navigating to the home activity
                navigateToHomeActivity();
            }
        });

        backBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
    }

    // Method to validate and submit the job offer
    private void validateAndSubmitOffer() {
        if (salary.getText().toString().isEmpty()
            || jobtitle.getText().toString().isEmpty()
            || beginDate.getText().toString().isEmpty()
            || company.getText().toString().isEmpty()
            || employerName.getText().toString().isEmpty()) {
            Toast.makeText(AddOfferActivity.this, "You have empty record", Toast.LENGTH_SHORT).show();
        } else {
            String value = String.valueOf(((Spinner) findViewById(R.id.speciality)).getSelectedItem());
            Map<String, Object> data = new HashMap<>();
            data.put("SALARY", salary.getText().toString());
            data.put("SPECIALITY", String.valueOf(((Spinner) findViewById(R.id.speciality)).getSelectedItem()));
            data.put("JOB_TITLE", jobtitle.getText().toString());
            data.put("BEGIN_DATE", beginDate.getText().toString());
            data.put("COMPANY", company.getText().toString());

            DB = FirebaseDatabase.getInstance().getReference();
            DatabaseReference employersRef = DB.child("Employers");

            Query EmployerNameVer = employersRef.orderByChild("FULL_NAME").equalTo(employerName.getText().toString());
            EmployerNameVer.addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot snapshot) {
                    if (snapshot.getChildrenCount() > 0) {
                        data.put("EMPLOYER_NAME", employerName.getText().toString());

                        DatabaseReference categoryRef = DB.child("OFFERS/" + value);
                        DatabaseReference newRef = categoryRef.push();
                        newRef.setValue(data);
                        Toast.makeText(AddOfferActivity.this, "Your Offer Successfully Added ", Toast.LENGTH_SHORT).show();
                    } else {
                        Toast.makeText(AddOfferActivity.this, "This Employer:" + employerName.getText().toString()
                                + "  Does not Exist! Please Enter Your Valid Name", Toast.LENGTH_SHORT).show();
                    }
                }

                @Override
                public void onCancelled(@NonNull DatabaseError error) {
                }
            });
        }
    }

    // Method to reset the form fields
    private void resetForm() {
        jobtitle.getText().clear();
        salary.getText().clear();
        beginDate.getText().clear();
        company.getText().clear();
        employerName.getText().clear();
    }

    // Method to navigate to the home activity
    private void navigateToHomeActivity() {
        startActivity(new Intent(AddOfferActivity.this, JobPostulationActivity.class));
    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        // On selecting a spinner item
        String item = parent.getItemAtPosition(position).toString();

        ((TextView) parent.getChildAt(0)).setTextColor(Color.GRAY);
        ((TextView) parent.getChildAt(0)).setTextSize(16);
    }

    public void onNothingSelected(AdapterView<?> arg0) {
        // TODO Auto-generated method stub
    }
}

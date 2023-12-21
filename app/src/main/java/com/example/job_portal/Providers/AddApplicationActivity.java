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

/**
 * Activity to add a job application.
 */
public class AddApplicationActivity extends AppCompatActivity implements AdapterView.OnItemSelectedListener {

    private EditText Appjobtitle;
    private EditText AppName;
    private EditText AppEmail;
    private EditText AppAbout;
    private EditText AppPhone;
    private Spinner AppSpeciality;
    private Button submitAppBtn;
    private Button resetAppBtn;
    private Button backBtn;
    private Button homeBtn;

    private DatabaseReference DB;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_application);

        // Initialization of UI elements
        initializeUI();

        // Initialization of the Firebase Database reference
        DB = FirebaseDatabase.getInstance().getReference();

        // Configuration of the Spinner (Dropdown)
        configureSpinner();

        // Setting click listeners for buttons
        setButtonClickListeners();
    }

    /**
     * Method to initialize UI elements.
     */
    private void initializeUI() {
        Appjobtitle = findViewById(R.id.applyJob);
        AppName = findViewById(R.id.applyName);
        AppEmail = findViewById(R.id.applyEmail);
        AppAbout = findViewById(R.id.applyAbout);
        AppPhone = findViewById(R.id.Applyphone);
        AppSpeciality = findViewById(R.id.Applyspeciality);
        submitAppBtn = findViewById(R.id.submitAppBtn);
        resetAppBtn = findViewById(R.id.resetAppBtn);
        backBtn = findViewById(R.id.backAppBtn);
        homeBtn = findViewById(R.id.homeAppBtn);
    }

    /**
     * Method to configure the Spinner (Dropdown).
     */
    private void configureSpinner() {
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
            new ArrayAdapter<>(this, android.R.layout.simple_spinner_dropdown_item, categories);

        AppSpeciality.setAdapter(adapter);
        AppSpeciality.setOnItemSelectedListener(this);
    }

    /**
     * Method to set click listeners for buttons.
     */
    private void setButtonClickListeners() {
        submitAppBtn.setOnClickListener(view -> validateAndSubmitApplication());
        resetAppBtn.setOnClickListener(view -> resetForm());
        homeBtn.setOnClickListener(view -> navigateToHomeActivity());
        backBtn.setOnClickListener(view -> finish());
    }

    /**
     * Method to validate and submit the job application.
     */
    private void validateAndSubmitApplication() {
        if (isAnyFieldEmpty()) {
            Toast.makeText(AddApplicationActivity.this, "Empty Records", Toast.LENGTH_SHORT).show();
        } else {
            String value = String.valueOf(AppSpeciality.getSelectedItem());
            Map<String, Object> data = createApplicationData();

            validateEmployee(value, data);
        }
    }

    /**
     * Method to check if any field is empty.
     *
     * @return True if any field is empty, false otherwise.
     */
    private boolean isAnyFieldEmpty() {
        return AppName.getText().toString().isEmpty()
            || AppEmail.getText().toString().isEmpty()
            || Appjobtitle.getText().toString().isEmpty()
            || AppPhone.getText().toString().isEmpty()
            || AppAbout.getText().toString().isEmpty();
    }

    /**
     * Method to create data for the job application.
     *
     * @return Map containing job application data.
     */
    private Map<String, Object> createApplicationData() {
        Map<String, Object> data = new HashMap<>();
        data.put("EMAIL", AppEmail.getText().toString());
        data.put("SPECIALITY", String.valueOf(AppSpeciality.getSelectedItem()));
        data.put("JOB_TITLE", Appjobtitle.getText().toString());
        data.put("PHONE_NUMBER", AppPhone.getText().toString());
        data.put("ABOUT", AppAbout.getText().toString());
        return data;
    }

    /**
     * Method to validate the employee.
     *
     * @param value Data value.
     * @param data  Job application data.
     */
    private void validateEmployee(String value, Map<String, Object> data) {
        DatabaseReference employersRef = DB.child("Employees");
        Query EmployeeNameVer =
            employersRef.orderByChild("FULL_NAME").equalTo(AppName.getText().toString());
        EmployeeNameVer.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if (snapshot.getChildrenCount() > 0) {
                    handleValidEmployee(value, data, snapshot);
                } else {
                    handleInvalidEmployee();
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
            }
        });
    }

    /**
     * Method to handle the case of a valid employee.
     *
     * @param value    Data value.
     * @param data     Job application data.
     * @param snapshot Data snapshot.
     */
    private void handleValidEmployee(String value, Map<String, Object> data, DataSnapshot snapshot) {
        for (DataSnapshot ds : snapshot.getChildren()) {
            String emailVerification = ds.child("EMAIL").getValue().toString();
            String phoneVerification = ds.child("PHONE_NUMBER").getValue().toString();
            if (emailVerification.equals(AppEmail.getText().toString())
                && phoneVerification.equals(AppPhone.getText().toString())) {
                data.put("EMPLOYEE_NAME", AppName.getText().toString());
                DatabaseReference categoryRef = DB.child("POSTULATIONS/" + value);
                DatabaseReference newRef = categoryRef.push();
                newRef.setValue(data);
                Toast.makeText(AddApplicationActivity.this,
                    "Your Application Successfully Added ", Toast.LENGTH_SHORT).show();
            } else {
                handleInvalidEmail();
            }
        }
    }

    /**
     * Method to handle the case of an invalid employee.
     */
    private void handleInvalidEmployee() {
        Toast.makeText(AddApplicationActivity.this,
            "This Employee Does not Exist. Please Enter Your Valid Name", Toast.LENGTH_SHORT).show();
    }

    /**
     * Method to handle the case of an invalid email.
     */
    private void handleInvalidEmail() {
        Toast.makeText(AddApplicationActivity.this,
            "Invalid Email: Employee " + AppName.getText().toString() +
                "  Doesn't have such email or Phone Number", Toast.LENGTH_SHORT).show();
    }

    /**
     * Method to reset the form fields.
     */
    private void resetForm() {
        Appjobtitle.getText().clear();
        AppName.getText().clear();
        AppAbout.getText().clear();
        AppPhone.getText().clear();
        AppEmail.getText().clear();
    }

    /**
     * Method to navigate to the home activity.
     */
    private void navigateToHomeActivity() {
        startActivity(new Intent(AddApplicationActivity.this, CategoriesActivity.class));
    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        String item = parent.getItemAtPosition(position).toString();
        ((TextView) parent.getChildAt(0)).setTextColor(Color.GRAY);
        ((TextView) parent.getChildAt(0)).setTextSize(16);
    }

    @Override
    public void onNothingSelected(AdapterView<?> arg0) {
        // TODO Auto-generated method stub
    }
}

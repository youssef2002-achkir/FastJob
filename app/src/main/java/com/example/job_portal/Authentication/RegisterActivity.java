package com.example.job_portal;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.TextView;
import android.widget.Toast;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;

/**
 * Activity for user registration.
 */
public class RegisterActivity extends AppCompatActivity {

    // UI elements
    TextInputEditText RegEmail;
    TextInputEditText RegPassword;
    TextInputEditText RegFirstName;
    TextInputEditText RegLastName;
    TextInputEditText RegPhoneNumber;
    TextView LoginHere;
    Button btnRegister;
    CheckBox roleEmployee;
    CheckBox roleEmployer;

    // Firebase authentication class
    FirebaseAuth UserAuth;
    DatabaseReference db1;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        // Initialization of UI elements
        RegEmail = findViewById(R.id.RegEmail);
        RegPassword = findViewById(R.id.RegPassword);
        RegFirstName = findViewById(R.id.RegFName);
        RegLastName = findViewById(R.id.RegLName);
        RegPhoneNumber = findViewById(R.id.RegPhoneNum);
        LoginHere = findViewById(R.id.LoginLink);
        btnRegister = findViewById(R.id.RegButton);
        roleEmployee = findViewById(R.id.employeeRole);
        roleEmployer = findViewById(R.id.employerRole);

        // Initialization of Firebase Authentication and Database references
        UserAuth = FirebaseAuth.getInstance();
        db1 = FirebaseDatabase.getInstance().getReference();

        // Set up a click listener for the Register button
        btnRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Create a map to store user data
                Map<String, Object> data = new HashMap<>();
                data.put("EMAIL", RegEmail.getText().toString());
                data.put("FIRST_NAME", RegFirstName.getText().toString());
                data.put("LAST_NAME", RegLastName.getText().toString());
                data.put("PHONE_NUMBER", RegPhoneNumber.getText().toString());

                // Check the selected role and update the data accordingly
                if (roleEmployee.isChecked() && !roleEmployer.isChecked()) {
                    data.put("ROLE", "EMPLOYEE");
                    data.put("FULL_NAME", RegFirstName.getText().toString() + ' ' + RegLastName.getText().toString());

                    // Save user data to the database under "Employees"
                    DatabaseReference userRef = db1.child("Employees");
                    DatabaseReference newRef = userRef.push();
                    newRef.setValue(data);

                } else if (!roleEmployee.isChecked() && roleEmployer.isChecked()) {
                    data.put("ROLE", "EMPLOYER");
                    data.put("FULL_NAME", RegFirstName.getText().toString() + ' ' + RegLastName.getText().toString());

                    // Save user data to the database under "Employers"
                    DatabaseReference userRef = db1.child("Employers");
                    DatabaseReference newRef = userRef.push();
                    newRef.setValue(data);
                }

                // Proceed with user creation
                createUser();
            }
        });

        // Set up a click listener for the "Login Here" text
        LoginHere.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(RegisterActivity.this, SignInActivity.class));
            }
        });
    }

    // String containing possible characters for generating a random string
    private static final String CHARACTERS = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789";

    // Method to generate a random string of given length
    public static String generateRandomString(int length) {
        Random random = new Random();
        StringBuilder sb = new StringBuilder(length);

        for (int i = 0; i < length; i++) {
            int randomIndex = random.nextInt(CHARACTERS.length());
            char randomChar = CHARACTERS.charAt(randomIndex);
            sb.append(randomChar);
        }

        return sb.toString();
    }

    // Method to create a new user using Firebase authentication
    private void createUser() {
        String email = RegEmail.getText().toString();
        String password = RegPassword.getText().toString();
        String firstName = RegFirstName.getText().toString();
        String lastName = RegLastName.getText().toString();
        String number = RegPhoneNumber.getText().toString();
        CheckBox employee = roleEmployee;
        CheckBox employer = roleEmployer;

        // Check if any of the required fields is empty
        if (TextUtils.isEmpty(email)) {
            RegEmail.setError("Email can't be empty");
            RegEmail.requestFocus();
        } else if (TextUtils.isEmpty(password)) {
            RegPassword.setError("Password can't be empty");
            RegPassword.requestFocus();
        } else if (TextUtils.isEmpty(firstName)) {
            RegFirstName.setError("First Name can't be empty");
            RegFirstName.requestFocus();
        } else if (TextUtils.isEmpty(lastName)) {
            RegLastName.setError("Last Name can't be empty");
            RegLastName.requestFocus();
        } else if (TextUtils.isEmpty(number)) {
            RegPhoneNumber.setError("Phone Number can't be empty");
            RegPhoneNumber.requestFocus();
        } else if (employee.isChecked() && employer.isChecked()) {
            // If both checkboxes are checked
            employee.setError("Just one role");
            employer.setError("Just one role");
            Toast.makeText(RegisterActivity.this, "Please choose only one role at a time", Toast.LENGTH_SHORT).show();
        } else if (!employee.isChecked() && !employer.isChecked()) {
            // If neither checkbox is checked
            employee.setError("Please select one role");
            employer.setError("Please select one role");
            Toast.makeText(RegisterActivity.this, "You should have a role to register", Toast.LENGTH_SHORT).show();
        } else {
            // If all conditions are met, proceed with user creation
            UserAuth.createUserWithEmailAndPassword(email, password)
                    .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            if (task.isSuccessful()) {
                                Toast.makeText(RegisterActivity.this, "You are successfully registered", Toast.LENGTH_SHORT).show();
                                startActivity(new Intent(RegisterActivity.this, SignInActivity.class));
                            } else {
                                Toast.makeText(RegisterActivity.this, "Registration Error: " + task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                            }
                        }
                    });
        }
    }
}

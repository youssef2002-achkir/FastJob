package com.example.job_portal;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import java.io.Serializable;

/**
 * Activity for user sign-in.
 */
public class SignInActivity extends AppCompatActivity {

    // UI elements
    TextInputEditText LoginEmail;
    TextInputEditText LoginPassword;
    TextView Register;
    Button btnLogin;

    // Firebase authentication class
    FirebaseAuth UserLogin;

    // Firebase Database reference
    DatabaseReference DB;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_in);

        // Initialization of UI elements
        LoginEmail = findViewById(R.id.LoginEmail);
        LoginPassword = findViewById(R.id.LoginPassword);
        Register = findViewById(R.id.RegisterLink);
        btnLogin = findViewById(R.id.loginButton);

        // Initialization of Firebase Authentication instance
        UserLogin = FirebaseAuth.getInstance();

        // Initialization of Firebase Database reference
        DB = FirebaseDatabase.getInstance().getReference();

        // Set up a click listener for the Login button
        btnLogin.setOnClickListener(view -> loginUser());

        // Set up a click listener for the "Register" text
        Register.setOnClickListener(view -> startActivity(new Intent(SignInActivity.this, RegisterActivity.class)));
    }

    // Method to handle user login
    private void loginUser() {
        String email = LoginEmail.getText().toString();
        String password = LoginPassword.getText().toString();

        // References to "Employees" and "Employers" nodes in the database
        DatabaseReference employeesRef = DB.child("Employees");
        DatabaseReference employersRef = DB.child("Employers");

        // Query to check if the entered email exists in the "Employees" node
        Query empEmailQuery = employeesRef.orderByChild("EMAIL").equalTo(email);

        // Check if email or password is empty
        if (TextUtils.isEmpty(email)) {
            LoginEmail.setError("Email can't be empty");
            LoginEmail.requestFocus();
        } else if (TextUtils.isEmpty(password)) {
            LoginPassword.setError("Password can't be empty");
            LoginPassword.requestFocus();
        } else {
            // Perform user login using Firebase Authentication
            UserLogin.signInWithEmailAndPassword(email, password)
                .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            // If login is successful
                            Toast.makeText(SignInActivity.this, "You successfully signed in", Toast.LENGTH_SHORT).show();

                            // Check if the user is an employee or employer
                            empEmailQuery.addValueEventListener(new ValueEventListener() {
                                @Override
                                public void onDataChange(@NonNull DataSnapshot snapshot) {
                                    if (snapshot.getChildrenCount() > 0) {
                                        // If the email exists in the "Employees" node, navigate to CategoriesActivity
                                        Intent intent = new Intent(SignInActivity.this, CategoriesActivity.class);
                                        Bundle args = new Bundle();
                                        args.putSerializable("employeeEmail", (Serializable) email);
                                        intent.putExtra("BUNDLE", args);
                                        startActivity(intent);
                                    } else {
                                        // If the email does not exist in the "Employees" node, navigate to JobPostulationActivity
                                        Intent intent = new Intent(SignInActivity.this, JobPostulationActivity.class);
                                        Bundle args = new Bundle();
                                        args.putSerializable("employerEmail", (Serializable) email);
                                        intent.putExtra("BUNDLE", args);
                                        startActivity(intent);
                                    }
                                }

                                @Override
                                public void onCancelled(@NonNull DatabaseError error) {
                                    // Handle database error
                                }
                            });
                        } else {
                            // If login is unsuccessful, display an error message
                            Toast.makeText(SignInActivity.this, "Login Error: " + task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                        }
                    }
                });
        }
    }
}

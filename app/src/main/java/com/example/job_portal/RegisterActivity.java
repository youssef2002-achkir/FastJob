package com.example.job_portal;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
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
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.SetOptions;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;

public class RegisterActivity extends AppCompatActivity {

    TextInputEditText RegEmail;
    TextInputEditText RegPassword;
    TextInputEditText RegFirstName;
    TextInputEditText RegLastName;
    TextInputEditText RegPhoneNumber;
    TextView LoginHere;
    Button btnRegister;
    CheckBox roleEmployee;
    CheckBox roleEmployer;
    //Firebase authentication class
    FirebaseAuth UserAuth;
    DatabaseReference db1;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        RegEmail = findViewById(R.id.RegEmail);
        RegPassword = findViewById(R.id.RegPassword);
        RegFirstName = findViewById(R.id.RegFName);
        RegLastName = findViewById(R.id.RegLName);
        RegPhoneNumber = findViewById(R.id.RegPhoneNum);
        LoginHere = findViewById(R.id.LoginLink);
        btnRegister = findViewById(R.id.RegButton);
        roleEmployee = findViewById(R.id.employeeRole);
        roleEmployer = findViewById(R.id.employerRole);


        UserAuth = FirebaseAuth.getInstance();
        db1 = FirebaseDatabase.getInstance().getReference();



        btnRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Map<String, Object> data = new HashMap<>();
                data.put("EMAIL", RegEmail.getText().toString());
                data.put("FIRST_NAME", RegFirstName.getText().toString());
                data.put("LAST_NAME", RegLastName.getText().toString());
                data.put("PHONE_NUMBER", RegPhoneNumber.getText().toString());

                if (roleEmployee.isChecked() && !roleEmployer.isChecked()){
                    data.put("ROLE", "EMPLOYEE");
                    data.put("FULL_NAME", RegFirstName.getText().toString()
                                            + ' ' +RegLastName.getText().toString());

                    DatabaseReference userRef = db1.child("Employees");
                    DatabaseReference newRef =  userRef.push();
                    newRef.setValue(data);

                }else if (!roleEmployee.isChecked() && roleEmployer.isChecked()){
                    data.put("ROLE", "EMPLOYER");
                    data.put("FULL_NAME", RegFirstName.getText().toString()
                            + ' ' +RegLastName.getText().toString());
                    DatabaseReference userRef = db1.child("Employers");
                    DatabaseReference newRef =  userRef.push();
                    newRef.setValue(data);
                }

                createUser();
            }
        });


        LoginHere.setOnClickListener(view -> {
            startActivity(new Intent(RegisterActivity.this, SignInActivity.class));
        });
    }

    private static final String CHARACTERS = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789";

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

    private void createUser(){

        String email = RegEmail.getText().toString();
        String password = RegPassword.getText().toString();
        String firstName = RegFirstName.getText().toString();
        String lastName = RegLastName.getText().toString();
        String number = RegPhoneNumber.getText().toString();
        CheckBox employee = roleEmployee;
        CheckBox employer = roleEmployer;

        if (TextUtils.isEmpty(email)){
            RegEmail.setError("Email can't be empty");
            RegEmail.requestFocus();
        }else if (TextUtils.isEmpty(password)){
            RegPassword.setError("Password can't be empty");
            RegPassword.requestFocus();

        }else if (TextUtils.isEmpty(firstName)){
            RegFirstName.setError("First Name can't be empty");
            RegFirstName.requestFocus();
        }else if (TextUtils.isEmpty(lastName)){
            RegLastName.setError("Last Name can't be empty");
            RegLastName.requestFocus();
        }else if (TextUtils.isEmpty(number)){
            RegPhoneNumber.setError("Phone Number can't be empty");
            RegPhoneNumber.requestFocus();
        }else if (employee.isChecked() && employer.isChecked()){
            employee.setError("just one role");
            employer.setError("just one role");
            Toast.makeText(RegisterActivity.this, "Please choose only one role at a time", Toast.LENGTH_SHORT).show();
        }else if (!employee.isChecked() && !employer.isChecked()){
            employee.setError("please select one rule");
            employer.setError("please select one role");
            Toast.makeText(RegisterActivity.this, "You should Have a role to regidter", Toast.LENGTH_SHORT).show();
        }else{
            UserAuth.createUserWithEmailAndPassword(email,password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                @Override
                public void onComplete(@NonNull Task<AuthResult> task) {
                    if (task.isSuccessful()){
                        Toast.makeText(RegisterActivity.this, "You are successfully registered", Toast.LENGTH_SHORT).show();
                        startActivity(new Intent(RegisterActivity.this, SignInActivity.class));
                    }else{
                        Toast.makeText(RegisterActivity.this, "Registration Error : " + task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                    }
                }
            });
        }
    }
}
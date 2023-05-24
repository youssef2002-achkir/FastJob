package com.example.job_portal;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

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
import java.util.ArrayList;


public class SignInActivity extends AppCompatActivity {

    TextInputEditText LoginEmail;
    TextInputEditText LoginPassword;
    TextView Register;
    Button btnLogin;

    FirebaseAuth UserLogin;

    DatabaseReference DB;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_in);

        LoginEmail = findViewById(R.id.LoginEmail);
        LoginPassword = findViewById(R.id.LoginPassword);
        Register = findViewById(R.id.RegisterLink);
        btnLogin = findViewById(R.id.loginButton);

        UserLogin = FirebaseAuth.getInstance();

        DB = FirebaseDatabase.getInstance().getReference();


        btnLogin.setOnClickListener(view -> {
            loginUser();
        });

        Register.setOnClickListener(view ->{
            startActivity(new Intent(SignInActivity.this, RegisterActivity.class));
        });
    }

    private void loginUser(){
        String email = LoginEmail.getText().toString();
        String password = LoginPassword.getText().toString();

        DB = FirebaseDatabase.getInstance().getReference();
        DatabaseReference employeesRef = DB.child("Employees");
        DatabaseReference employersRef = DB.child("Employers");

        Query EmpEmailVer = employeesRef.orderByChild("EMAIL").equalTo(email);

        if (TextUtils.isEmpty(email)){
            LoginEmail.setError("Email can't be empty");
            LoginEmail.requestFocus();
        }else if (TextUtils.isEmpty(password)){
            LoginPassword.setError("Password can't be empty");
            LoginPassword.requestFocus();
        }else{
            UserLogin.signInWithEmailAndPassword(email,password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                @Override
                public void onComplete(@NonNull Task<AuthResult> task) {
                    if (task.isSuccessful()){

                        Toast.makeText(SignInActivity.this, "You successfully signed in", Toast.LENGTH_SHORT).show();
                        EmpEmailVer.addValueEventListener(new ValueEventListener() {
                            @Override
                            public void onDataChange(@NonNull DataSnapshot snapshot) {
                                if (snapshot.getChildrenCount() > 0){
                                    Intent intent = new Intent(SignInActivity.this, CategoriesActivity.class);
                                    Bundle args = new Bundle();
                                    args.putSerializable("employeeEmail",(Serializable)email);
                                    intent.putExtra("BUNDLE",args);
                                    startActivity(intent);
                                }else {
                                    Intent intent = new Intent(SignInActivity.this, JobPostulationActivity.class);
                                    Bundle args = new Bundle();
                                    args.putSerializable("employerEmail",(Serializable)email);
                                    intent.putExtra("BUNDLE",args);
                                    startActivity(intent);
                                }

                            }

                            @Override
                            public void onCancelled(@NonNull DatabaseError error) {

                            }
                        });



                    }else{
                        Toast.makeText(SignInActivity.this, "LoginError: " + task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                    }
                }
            });
        }
    }

}
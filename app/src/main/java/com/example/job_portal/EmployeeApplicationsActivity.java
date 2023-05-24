package com.example.job_portal;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.io.Serializable;
import java.util.ArrayList;

public class EmployeeApplicationsActivity extends AppCompatActivity {

    LinearLayout layout;

    DatabaseReference DB;
    Button newPostBtn;

    Button AppbackBtn;
    Button AppprofileBtn;
    Button AppsignoutBtn;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_employee_applications);

        newPostBtn = findViewById(R.id.appAddPostBtn);
        AppprofileBtn = findViewById(R.id.employeeprofilebtn);
        AppsignoutBtn = findViewById(R.id.applogoutBtn);
        AppbackBtn = findViewById(R.id.appbackBtn);

        newPostBtn.setOnClickListener(view -> {
            startActivity(new Intent(EmployeeApplicationsActivity.this, AddApplicationActivity.class));
        });

        AppbackBtn.setOnClickListener(view -> {
            finish();
        });

        AppsignoutBtn.setOnClickListener(view -> {
            startActivity(new Intent(EmployeeApplicationsActivity.this, MainActivity.class));
        });


        layout = findViewById(R.id.Appcontainer);

        Bundle bundle = getIntent().getExtras();
        String employeeName= bundle.getString("EMPLOYEE NAME");

        System.out.println(employeeName);
        System.out.println(employeeName);
        System.out.println(employeeName);
        System.out.println(employeeName);
        System.out.println(employeeName);
        System.out.println(employeeName);

        DB = FirebaseDatabase.getInstance().getReference();

        ArrayList<String> categories = new ArrayList<>();
        categories.add("Economics");
        categories.add("Statistics");
        categories.add("Electrical Engineering");
        categories.add("Mechanical Engineering");
        categories.add("Software Engineering");
        categories.add("Architecture");
        categories.add("Admin Assistance");
        categories.add("Journalism");

        for (int i=0; i< categories.size(); i++){
            DatabaseReference postulationsRef = DB.child("POSTULATIONS/" + categories.get(i));
            Query q = postulationsRef.orderByChild("EMPLOYEE_NAME").equalTo(employeeName);
            q.addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot snapshot) {
                    for(DataSnapshot ds : snapshot.getChildren()) {

                        String position = ds.child("JOB_TITLE").getValue().toString();
                        String email = ds.child("EMAIL").getValue().toString();
                        String phone = ds.child("PHONE_NUMBER").getValue().toString();
                        String speciality = ds.child("SPECIALITY").getValue().toString();

                        //create card element
                        final View apps = getLayoutInflater().inflate(R.layout.appscard, null);
                        TextView JOBTITLE = apps.findViewById(R.id.appjob);
                        TextView EMAIL = apps.findViewById(R.id.appemail);
                        TextView Date = apps.findViewById(R.id.appdate);
                        TextView SPECIALITY =  apps.findViewById(R.id.appspec);
                        Button DELETE = apps.findViewById(R.id.deleteapp);


                        //insert database values
                        JOBTITLE.setText(position);
                        EMAIL.setText(email);
                        Date.setText(phone);
                        SPECIALITY.setText(speciality);


                        layout.addView(apps);

                        ArrayList<String> applicationInformation = new ArrayList<>();
                        applicationInformation.add(position);
                        applicationInformation.add(speciality);
                        applicationInformation.add(phone);
                        applicationInformation.add(email);

                        DELETE.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {

                                ds.getRef().removeValue();
                                layout.removeView(apps);
                            }
                        });


                    }
                }

                @Override
                public void onCancelled(@NonNull DatabaseError error) {

                }
            });
        }
    }
}
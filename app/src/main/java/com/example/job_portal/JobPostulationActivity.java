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

public class JobPostulationActivity extends AppCompatActivity {

    LinearLayout layout;

    DatabaseReference DB;
    Button newPostBtn;

    Button backBtn;
    Button profileBtn;
    Button signoutBtn;
    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_job_postulation);

        newPostBtn = findViewById(R.id.AddOfferBtn);
        profileBtn = findViewById(R.id.employerprofilebtn);
        signoutBtn = findViewById(R.id.logoutPostBtn);
        backBtn = findViewById(R.id.backPostBtn);

        Intent intent = getIntent();
        Bundle args = intent.getBundleExtra("BUNDLE");
        String employerEmail = (String) args.getSerializable("employerEmail");

        System.out.println(employerEmail);
        System.out.println(employerEmail);
        System.out.println(employerEmail);
        System.out.println(employerEmail);

        newPostBtn.setOnClickListener(view -> {
            startActivity(new Intent(JobPostulationActivity.this, AddOfferActivity.class));
        });

        backBtn.setOnClickListener(view -> {
            startActivity(new Intent(JobPostulationActivity.this, SignInActivity.class));
        });

        signoutBtn.setOnClickListener(view -> {
            startActivity(new Intent(JobPostulationActivity.this, MainActivity.class));
        });


        layout = findViewById(R.id.container);

        DB = FirebaseDatabase.getInstance().getReference();

        DatabaseReference employersRef = DB.child("Employers");

        Query EmpEmail = employersRef.orderByChild("EMAIL").equalTo(employerEmail);


        EmpEmail.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for(DataSnapshot ds : snapshot.getChildren()) {
                    ArrayList<String> employerInfos = new ArrayList<>();
                    employerInfos.add(ds.child("FULL_NAME").getValue().toString());
                    employerInfos.add(ds.child("EMAIL").getValue().toString());
                    employerInfos.add(ds.child("FIRST_NAME").getValue().toString());
                    employerInfos.add(ds.child("LAST_NAME").getValue().toString());
                    employerInfos.add(ds.child("PHONE_NUMBER").getValue().toString());


                    profileBtn.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            System.out.println(employerInfos.get(0));
                            System.out.println(employerInfos.get(1));
                            System.out.println(employerInfos.get(2));
                            System.out.println(employerInfos.get(3));
                            System.out.println(employerInfos.get(4));
                            Intent intent = new Intent(JobPostulationActivity.this, EmployerProfileActivity.class);
                            Bundle args = new Bundle();
                            args.putSerializable("EMPLOYERInformation",(Serializable)employerInfos);
                            intent.putExtra("BUNDLE",args);
                            startActivity(intent);
                        }
                    });


                }


            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

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
            postulationsRef.addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot snapshot) {
                    for(DataSnapshot ds : snapshot.getChildren()) {

                        String position = ds.child("JOB_TITLE").getValue().toString();
                        String name = ds.child("EMPLOYEE_NAME").getValue().toString();
                        String email = ds.child("EMAIL").getValue().toString();
                        String about = ds.child("ABOUT").getValue().toString();
                        String phone = ds.child("PHONE_NUMBER").getValue().toString();
                        String speciality = ds.child("SPECIALITY").getValue().toString();

                        //create card element
                        final View view = getLayoutInflater().inflate(R.layout.cardposts, null);
                        TextView JOBTITLE = view.findViewById(R.id.jobtilePost);
                        TextView NAME = view.findViewById(R.id.NamePost);
                        TextView EMAIL = view.findViewById(R.id.EmailPost);
                        TextView ABOUT = view.findViewById(R.id.AboutPost);
                        Button MORE =  view.findViewById(R.id.morePost);

                        //insert database values
                        JOBTITLE.setText(position);
                        NAME.setText(name);
                        EMAIL.setText(email);
                        ABOUT.setText(about);

                        layout.addView(view);

                        ArrayList<String> employeeInformations = new ArrayList<>();
                        employeeInformations.add(position);
                        employeeInformations.add(name);
                        employeeInformations.add(email);
                        employeeInformations.add(about);
                        employeeInformations.add(phone);
                        employeeInformations.add(speciality);
                        employeeInformations.add(employerEmail);

                        MORE.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {
                                Intent intent = new Intent(JobPostulationActivity.this, EmployeeDetailsActivity.class);
                                Bundle args = new Bundle();
                                args.putSerializable("EmpInformation",(Serializable)employeeInformations);
                                intent.putExtra("BUNDLE",args);
                                startActivity(intent);
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
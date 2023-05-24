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
import com.google.firebase.database.ValueEventListener;

import java.io.Serializable;
import java.util.ArrayList;

public class ArchitectureActivity extends AppCompatActivity {

    LinearLayout layout;
    DatabaseReference DB;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_architecture);

        layout = findViewById(R.id.container);

        DB = FirebaseDatabase.getInstance().getReference();
        DatabaseReference architectureRef = DB.child("OFFERS/Architecture");

        architectureRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for(DataSnapshot ds : snapshot.getChildren()) {

                    String position = ds.child("JOB_TITLE").getValue().toString();
                    String salary = ds.child("SALARY").getValue().toString();
                    String company = ds.child("COMPANY").getValue().toString();
                    String datebegin = ds.child("BEGIN_DATE").getValue().toString();

                    //create card element
                    final View view = getLayoutInflater().inflate(R.layout.card, null);
                    TextView JOBTITLE = view.findViewById(R.id.jobtileEco);
                    TextView COMPANY = view.findViewById(R.id.companyEco);
                    TextView SALARY = view.findViewById(R.id.salaryEco);
                    TextView DATEBEGIN = view.findViewById(R.id.dateEco);
                    Button APPLY =  view.findViewById(R.id.applyEco);

                    //insert database values
                    JOBTITLE.setText(position);
                    COMPANY.setText(company);
                    SALARY.setText(salary);
                    DATEBEGIN.setText(datebegin);

                    layout.addView(view);

                    String employerName = ds.child("EMPLOYER_NAME").getValue().toString();
                    String speciality = ds.child("SPECIALITY").getValue().toString();

                    ArrayList<String> jobInformation = new ArrayList<>();
                    jobInformation.add(position);
                    jobInformation.add(salary);
                    jobInformation.add(company);
                    jobInformation.add(datebegin);
                    jobInformation.add(employerName);
                    jobInformation.add(speciality);

                    APPLY.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            Intent intent = new Intent(ArchitectureActivity.this, JobDetailsActivity.class);
                            Bundle args = new Bundle();
                            args.putSerializable("JobInformation",(Serializable)jobInformation);
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

        Button backBtn;
        Button homeBtn;
        backBtn = findViewById(R.id.backBtn);
        homeBtn = findViewById(R.id.homeBtn);

        Button addApp;
        addApp = findViewById(R.id.AddAppBtn);
        addApp.setOnClickListener(view -> {
            startActivity(new Intent(ArchitectureActivity.this, AddApplicationActivity.class));
        });

        backBtn.setOnClickListener(view -> {
            finish();
        });

        homeBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(ArchitectureActivity.this, CategoriesActivity.class));
            }
        });
    }
}
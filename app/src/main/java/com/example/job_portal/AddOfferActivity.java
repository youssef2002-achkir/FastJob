package com.example.job_portal;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.SetOptions;

import java.util.ArrayList;
import java.util.EventListener;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;

public class AddOfferActivity extends AppCompatActivity implements AdapterView.OnItemSelectedListener{


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
        jobtitle = findViewById(R.id.position);
        salary = findViewById(R.id.salary);
        company = findViewById(R.id.company);
        final Spinner spinner = (Spinner) findViewById(R.id.speciality);
        employerName = findViewById(R.id.employerName);
        beginDate = findViewById(R.id.date);
        submitDataBtn = findViewById(R.id.submitOfferBtn);
        resetDataBtn= findViewById(R.id.resetBtn);
        backBtn = findViewById(R.id.backOfferBtn);
        homeBtn = findViewById(R.id.homeOfferBtn);

        ArrayList<String> categories = new ArrayList<>();
        categories.add("Economics");
        categories.add("Statistics");
        categories.add("Electrical Engineering");
        categories.add("Mechanical Engineering");
        categories.add("Software Engineering");
        categories.add("Architecture");
        categories.add("Admin Assistance");
        categories.add("Journalism");

        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_dropdown_item, categories);

        spinner.setAdapter(adapter);

        spinner.setOnItemSelectedListener(this);

        DB = FirebaseDatabase.getInstance().getReference();

        submitDataBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

//                ArrayList<String> testInputs = new ArrayList<>();
//                testInputs.add(salary.getText().toString());
//                testInputs.add(jobtitle.getText().toString());
//                testInputs.add(beginDate.getText().toString());
//                testInputs.add(company.getText().toString());
//                testInputs.add(employerName.getText().toString());
//                testInputs.add(salary.getText().toString());
//
//                for (int i = 0; i < testInputs.size(); i++)

                if (
                        salary.getText().toString().isEmpty() || jobtitle.getText().toString().isEmpty() ||
                        beginDate.getText().toString().isEmpty() || company.getText().toString().isEmpty() ||
                        employerName.getText().toString().isEmpty()
                ){
                    Toast.makeText(AddOfferActivity.this, "You have empty record", Toast.LENGTH_SHORT).show();
                }else{

                    String value = String.valueOf(spinner.getSelectedItem());
                    Map<String, Object> data = new HashMap<>();
                    data.put("SALARY", salary.getText().toString());
                    data.put("SPECIALITY", String.valueOf(spinner.getSelectedItem()));
                    data.put("JOB_TITLE", jobtitle.getText().toString());
                    data.put("BEGIN_DATE", beginDate.getText().toString());
                    data.put("COMPANY", company.getText().toString());

                    DB = FirebaseDatabase.getInstance().getReference();
                    DatabaseReference employersRef = DB.child("Employers");

                    Query EmployerNameVer = employersRef.orderByChild("FULL_NAME").equalTo(employerName.getText().toString());
                    EmployerNameVer.addValueEventListener(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot snapshot) {
                            if (snapshot.getChildrenCount() > 0){
                                data.put("EMPLOYER_NAME", employerName.getText().toString());

                                DatabaseReference categoryRef = DB.child("OFFERS/" + value);
                                DatabaseReference newRef =  categoryRef.push();
                                newRef.setValue(data);
                                Toast.makeText(AddOfferActivity.this, "Your Offer Successfully Added ",Toast.LENGTH_SHORT).show();
                            }else{
                                Toast.makeText(AddOfferActivity.this, "This Employer:" + employerName.getText().toString()
                                        +"  Does not Exist! Please Enter Your Valid Name", Toast.LENGTH_SHORT).show();
                            }
                        }

                        @Override
                        public void onCancelled(@NonNull DatabaseError error) {

                        }
                    });
                }


            }
        });

        resetDataBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                jobtitle.getText().clear();
                salary.getText().clear();
                beginDate.getText().clear();
                company.getText().clear();
                employerName.getText().clear();
            }
        });

        backBtn.setOnClickListener(view -> {
            finish();
        });

        homeBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(AddOfferActivity.this, JobPostulationActivity.class));
            }
        });

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
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

import java.util.ArrayList;

public class EmployerOffersActivity extends AppCompatActivity {

    LinearLayout layout;

    DatabaseReference DB;
    Button newOfferBtn;

    Button OfferbackBtn;
    Button OfferprofileBtn;
    Button OffersignoutBtn;
    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_employer_offers);

        newOfferBtn = findViewById(R.id.offerAddBtn);
        OfferprofileBtn = findViewById(R.id.employerprobtn);
        OffersignoutBtn = findViewById(R.id.offerlogoutBtn);
        OfferbackBtn = findViewById(R.id.offerbackBtn);

        newOfferBtn.setOnClickListener(view -> {
            startActivity(new Intent(EmployerOffersActivity.this, AddOfferActivity.class));
        });

        OfferbackBtn.setOnClickListener(view -> {
            finish();
        });

        OffersignoutBtn.setOnClickListener(view -> {
            startActivity(new Intent(EmployerOffersActivity.this, MainActivity.class));
        });


        layout = findViewById(R.id.Offercontainer);

        Bundle bundle = getIntent().getExtras();
        String employerName= bundle.getString("EMPLOYER NAME");

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
            DatabaseReference postulationsRef = DB.child("OFFERS/" + categories.get(i));
            Query q = postulationsRef.orderByChild("EMPLOYER_NAME").equalTo(employerName);
            q.addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot snapshot) {
                    for(DataSnapshot ds : snapshot.getChildren()) {

                        String position = ds.child("JOB_TITLE").getValue().toString();
                        String salary = ds.child("SALARY").getValue().toString();
                        String date = ds.child("BEGIN_DATE").getValue().toString();
                        String speciality = ds.child("SPECIALITY").getValue().toString();

                        //create card element
                        final View offers = getLayoutInflater().inflate(R.layout.offerscard, null);
                        TextView JOBTITLE = offers.findViewById(R.id.offerjob);
                        TextView SALARY = offers.findViewById(R.id.offersalary);
                        TextView Date = offers.findViewById(R.id.offerdate);
                        TextView SPECIALITY =  offers.findViewById(R.id.offerspec);
                        Button DELETE = offers.findViewById(R.id.deleteoffer);


                        //insert database values
                        JOBTITLE.setText(position);
                        SALARY.setText(salary);
                        Date.setText(date);
                        SPECIALITY.setText(speciality);


                        layout.addView(offers);

                        ArrayList<String> applicationInformation = new ArrayList<>();
                        applicationInformation.add(position);
                        applicationInformation.add(speciality);
                        applicationInformation.add(date);
                        applicationInformation.add(salary);

                        DELETE.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {
                                ds.getRef().removeValue();
                                layout.removeView(offers);
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
package com.example.job_portal;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import java.util.ArrayList;

/**
 * Activity for displaying and managing the offers made by an employer.
 */
public class EmployerOffersActivity extends AppCompatActivity {

    // UI elements
    LinearLayout layout;
    Button newOfferBtn;
    Button OfferprofileBtn;
    Button OffersignoutBtn;
    Button OfferbackBtn;

    // Firebase Database reference
    DatabaseReference DB;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_employer_offers);

        // Initialize UI elements
        newOfferBtn = findViewById(R.id.offerAddBtn);
        OfferprofileBtn = findViewById(R.id.employerprobtn);
        OffersignoutBtn = findViewById(R.id.offerlogoutBtn);
        OfferbackBtn = findViewById(R.id.offerbackBtn);

        // Set OnClickListener for the "Add Offer" button
        newOfferBtn.setOnClickListener(view -> {
            startActivity(new Intent(EmployerOffersActivity.this, AddOfferActivity.class));
        });

        // Set OnClickListener for the "Back" button
        OfferbackBtn.setOnClickListener(view -> {
            finish();
        });

        // Set OnClickListener for the "Sign Out" button
        OffersignoutBtn.setOnClickListener(view -> {
            startActivity(new Intent(EmployerOffersActivity.this, MainActivity.class));
        });

        // Initialize the layout
        layout = findViewById(R.id.Offercontainer);

        // Retrieve employer name from the intent
        Bundle bundle = getIntent().getExtras();
        String employerName = bundle.getString("EMPLOYER NAME");

        // Initialize Firebase Database reference
        DB = FirebaseDatabase.getInstance().getReference();

        // List of job categories
        ArrayList<String> categories = new ArrayList<>();
        categories.add("Economics");
        categories.add("Statistics");
        categories.add("Electrical Engineering");
        categories.add("Mechanical Engineering");
        categories.add("Software Engineering");
        categories.add("Architecture");
        categories.add("Admin Assistance");
        categories.add("Journalism");

        // Loop through each job category
        for (int i = 0; i < categories.size(); i++) {
            DatabaseReference postulationsRef = DB.child("OFFERS/" + categories.get(i));

            // Query to retrieve offers by the specific employer
            Query q = postulationsRef.orderByChild("EMPLOYER_NAME").equalTo(employerName);

            // ValueEventListener to retrieve data from Firebase
            q.addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot snapshot) {
                    for (DataSnapshot ds : snapshot.getChildren()) {
                        // Extract offer details from the database
                        String position = ds.child("JOB_TITLE").getValue().toString();
                        String salary = ds.child("SALARY").getValue().toString();
                        String date = ds.child("BEGIN_DATE").getValue().toString();
                        String speciality = ds.child("SPECIALITY").getValue().toString();

                        // Create card element
                        final View offers = getLayoutInflater().inflate(R.layout.offerscard, null);
                        TextView JOBTITLE = offers.findViewById(R.id.offerjob);
                        TextView SALARY = offers.findViewById(R.id.offersalary);
                        TextView Date = offers.findViewById(R.id.offerdate);
                        TextView SPECIALITY = offers.findViewById(R.id.offerspec);
                        Button DELETE = offers.findViewById(R.id.deleteoffer);

                        // Insert database values into the UI
                        JOBTITLE.setText(position);
                        SALARY.setText(salary);
                        Date.setText(date);
                        SPECIALITY.setText(speciality);

                        // Add the card to the layout
                        layout.addView(offers);

                        // ArrayList to store offer information
                        ArrayList<String> offerInformation = new ArrayList<>();
                        offerInformation.add(position);
                        offerInformation.add(speciality);
                        offerInformation.add(date);
                        offerInformation.add(salary);

                        // Set OnClickListener for the "Delete" button
                        DELETE.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {
                                // Remove the offer from the database and layout
                                ds.getRef().removeValue();
                                layout.removeView(offers);
                            }
                        });
                    }
                }

                @Override
                public void onCancelled(@NonNull DatabaseError error) {
                    // Handle error, if any
                }
            });
        }
    }
}

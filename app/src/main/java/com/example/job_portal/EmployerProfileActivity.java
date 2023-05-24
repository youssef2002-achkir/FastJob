package com.example.job_portal;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.google.firebase.database.DatabaseReference;

import org.checkerframework.checker.units.qual.A;

import java.io.Serializable;
import java.util.ArrayList;

public class EmployerProfileActivity extends AppCompatActivity {

    TextView FullName;
    TextView Email;
    TextView PhoneNumber;
    TextView FirstName;
    TextView LastName;

    Button BackBtn;
    Button HomeBtn;
    Button EditBtn;
    Button AppsBtn;
    Button LogoutBtn;

    DatabaseReference DB;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_employer_profile);

        FullName = findViewById(R.id.profileEmployerName);
        Email = findViewById(R.id.profileEmployerEmail);
        PhoneNumber = findViewById(R.id.profileEmployerPhone);
        FirstName = findViewById(R.id.profileEmployerFName);
        LastName = findViewById(R.id.profileEmployerLName);
        BackBtn = findViewById(R.id.employerPBack);
        HomeBtn = findViewById(R.id.employerPHome);
        EditBtn = findViewById(R.id.employerPEdit);
        AppsBtn = findViewById(R.id.employerPApps);
        LogoutBtn = findViewById(R.id.employerPLogout);

        Intent intent = getIntent();
        Bundle args = intent.getBundleExtra("BUNDLE");
        ArrayList<String> EMPInformation = (ArrayList<String>) args.getSerializable("EMPLOYERInformation");

        FullName.setText("Hello Dear, " + EMPInformation.get(0));
        Email.setText(EMPInformation.get(1));
        FirstName.setText(EMPInformation.get(2));
        LastName.setText(EMPInformation.get(3));
        PhoneNumber.setText(EMPInformation.get(4));

        LogoutBtn.setOnClickListener(view -> {
            startActivity(new Intent(EmployerProfileActivity.this, MainActivity.class));
        });
        HomeBtn.setOnClickListener(view -> {
            startActivity(new Intent(EmployerProfileActivity.this, JobPostulationActivity.class));
        });
        BackBtn.setOnClickListener(view -> {
            finish();
        });

        AppsBtn.setOnClickListener(view -> {
            Intent offers = new Intent(this, EmployerOffersActivity.class);

            String name = EMPInformation.get(0);
            Bundle bundle = new Bundle();
            bundle.putString("EMPLOYER NAME", name);
            offers.putExtras(bundle);
            startActivity(offers);
            System.out.println(EMPInformation.get(0));
            System.out.println(EMPInformation.get(0));
            System.out.println(EMPInformation.get(0));
            System.out.println(EMPInformation.get(0));
            System.out.println(EMPInformation.get(0));
            System.out.println(EMPInformation.get(0));

        });

        EditBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ArrayList<String> infos = new ArrayList<>();
                infos.add(EMPInformation.get(0));
                infos.add("Employers");
                Intent intent = new Intent(EmployerProfileActivity.this, EvaluateApplicationActivity.class);
                Bundle args = new Bundle();
                args.putSerializable("username",(Serializable)infos);
                intent.putExtra("BUNDLE",args);
                startActivity(intent);

            }
        });
    }
}
package com.example.job_portal;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.io.Serializable;
import java.util.ArrayList;

public class EmployeeProfileActivity extends AppCompatActivity {

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

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_employee_profile);


        FullName = findViewById(R.id.profileEmployeeName);
        Email = findViewById(R.id.profileEmployeeEmail);
        PhoneNumber = findViewById(R.id.profileEmployeePhone);
        FirstName = findViewById(R.id.profileEmployeeFName);
        LastName = findViewById(R.id.profileEmployeeLName);
        BackBtn = findViewById(R.id.employeePBack);
        HomeBtn = findViewById(R.id.employeePHome);
        EditBtn = findViewById(R.id.employeePEdit);
        AppsBtn = findViewById(R.id.employeePApps);
        LogoutBtn = findViewById(R.id.employeePLogout);

        Intent intent = getIntent();
        Bundle args = intent.getBundleExtra("BUNDLE");
        ArrayList<String> EMPInformation = (ArrayList<String>) args.getSerializable("EMPInformation");

        FullName.setText("Hello Dear, " + EMPInformation.get(0));
        Email.setText(EMPInformation.get(1));
        FirstName.setText(EMPInformation.get(2));
        LastName.setText(EMPInformation.get(3));
        PhoneNumber.setText(EMPInformation.get(4));

        DB = FirebaseDatabase.getInstance().getReference();


        LogoutBtn.setOnClickListener(view -> {
            startActivity(new Intent(EmployeeProfileActivity.this, MainActivity.class));
        });
        HomeBtn.setOnClickListener(view -> {
            finish();
        });
        BackBtn.setOnClickListener(view -> {
            finish();
        });

        AppsBtn.setOnClickListener(view -> {
            Intent apps = new Intent(this, EmployeeApplicationsActivity.class);

            String name = EMPInformation.get(0);
            Bundle bundle = new Bundle();
            bundle.putString("EMPLOYEE NAME", name);
            apps.putExtras(bundle);
            startActivity(apps);
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
                infos.add("Employees");
                Intent intent = new Intent(EmployeeProfileActivity.this, EvaluateApplicationActivity.class);
                Bundle args = new Bundle();
                args.putSerializable("username",(Serializable)infos);
                intent.putExtra("BUNDLE",args);
                startActivity(intent);

            }
        });

    }
}
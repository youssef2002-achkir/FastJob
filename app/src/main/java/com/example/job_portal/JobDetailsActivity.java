package com.example.job_portal;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

public class JobDetailsActivity extends AppCompatActivity {

    TextView JobPosition;
    TextView JobCompany;
    TextView JobSalary;
    TextView JobEmployer;
    TextView JobSpeciality;
    TextView JobDate;

    Button applyBtn;
    Button dismissBtn;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_job_details);

        JobPosition = findViewById(R.id.jobdetailstitle);
        JobCompany = findViewById(R.id.jobdetailsCompany);
        JobSalary = findViewById(R.id.jobdetailssalary);
        JobEmployer = findViewById(R.id.jobdetailsName);
        JobSpeciality = findViewById(R.id.jobdetailsspeciality);
        JobDate = findViewById(R.id.jobdetailsdate);
        applyBtn = findViewById(R.id.applyjobdetails);
        dismissBtn = findViewById(R.id.dismissjob);

        Intent intent = getIntent();
        Bundle args = intent.getBundleExtra("BUNDLE");
        ArrayList<String> jobInformation = (ArrayList<String>) args.getSerializable("JobInformation");


        JobPosition.setText(jobInformation.get(0));
        JobCompany.setText(jobInformation.get(2));
        JobSalary.setText(jobInformation.get(1));
        JobEmployer.setText(jobInformation.get(4));
        JobSpeciality.setText(jobInformation.get(5));
        JobDate.setText(jobInformation.get(3));

        dismissBtn.setOnClickListener(view -> {
            finish();
        });

        applyBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                sendEmail("hello", "contact@"+ JobCompany.getText().toString() + ".com");
            }
        });


    }

    public void sendEmail(String employeeEmail, String employerEmail) {

        Intent emailIntent = new Intent(Intent.ACTION_SEND);
        emailIntent.setData(Uri.parse("mailto:"));
        emailIntent.setType("text/plain");
        String position = JobPosition.getText().toString();
        String name = JobEmployer.getText().toString();

        emailIntent.putExtra(Intent.EXTRA_EMAIL, employeeEmail);
        emailIntent.putExtra(Intent.EXTRA_CC, employerEmail);
        emailIntent.putExtra(Intent.EXTRA_SUBJECT, "Applying For:"+ " " + position + " " +"On Fast Job");
        emailIntent.putExtra(Intent.EXTRA_TEXT, "Hello Sir," + name);

        try {
            startActivity(Intent.createChooser(emailIntent, "Send mail..."));
            finish();
        } catch (android.content.ActivityNotFoundException ex) {
            Toast.makeText(JobDetailsActivity.this,
                    "There is no email client installed.", Toast.LENGTH_SHORT).show();
        }
    }
}
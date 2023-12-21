package com.example.job_portal;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import java.util.ArrayList;

/**
 * Activity for displaying details of an employee.
 */
public class EmployeeDetailsActivity extends AppCompatActivity {

    // UI elements
    TextView EmpTitle;
    TextView EmpName;
    TextView EmpEmail;
    TextView EmpSpec;
    TextView EmpPhone;
    TextView EmpAbout;
    Button DMBtn;
    Button DismissEmpBtn;
    Button PhoneBtn;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_employee_details);

        // Initialize UI elements
        EmpTitle = findViewById(R.id.Empdetailstitle);
        EmpName = findViewById(R.id.EMPdetailsName);
        EmpEmail = findViewById(R.id.EMPdetailsEmail);
        EmpSpec = findViewById(R.id.Empdetailsspeciality);
        EmpPhone = findViewById(R.id.EmpdetailsPhone);
        EmpAbout = findViewById(R.id.Empdetailsabout);
        DMBtn = findViewById(R.id.DMdetails);
        DismissEmpBtn = findViewById(R.id.dismissEmp);
        PhoneBtn = findViewById(R.id.phoneEmp);

        // Retrieve employee information from the intent
        Intent intent = getIntent();
        Bundle args = intent.getBundleExtra("BUNDLE");
        ArrayList<String> employeeInformation = (ArrayList<String>) args.getSerializable("EmpInformation");

        // Set employee details in UI elements
        EmpTitle.setText(employeeInformation.get(0));
        EmpName.setText(employeeInformation.get(1));
        EmpEmail.setText(employeeInformation.get(2));
        EmpPhone.setText(employeeInformation.get(4));
        EmpAbout.setText(employeeInformation.get(3));
        EmpSpec.setText(employeeInformation.get(5));

        // Set OnClickListener for the "Dismiss" button
        DismissEmpBtn.setOnClickListener(view -> finish());

        // Set OnClickListener for the "Direct Message" button
        DMBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                sendEmail(EmpEmail.getText().toString(), employeeInformation.get(6));
            }
        });

        // Set OnClickListener for the "Phone" button
        PhoneBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Create an intent to initiate a phone call
                Intent phoneCall = new Intent();
                phoneCall.setAction(Intent.ACTION_DIAL);
                phoneCall.setData(Uri.parse("tel:" + EmpPhone.getText().toString()));
                startActivity(phoneCall);
            }
        });
    }

    /**
     * Method to send an email.
     *
     * @param employeeEmail  Email address of the employee.
     * @param employerEmail  Email address of the employer.
     */
    public void sendEmail(String employeeEmail, String employerEmail) {
        Intent emailIntent = new Intent(Intent.ACTION_SEND);
        emailIntent.setData(Uri.parse("mailto:"));
        emailIntent.setType("text/plain");
        String position = EmpTitle.getText().toString();
        String name = EmpName.getText().toString();

        // Set email details
        emailIntent.putExtra(Intent.EXTRA_EMAIL, employeeEmail);
        emailIntent.putExtra(Intent.EXTRA_CC, employerEmail);
        emailIntent.putExtra(Intent.EXTRA_SUBJECT, "Your Application:" + " " + position + " " + "On Fast Job");
        emailIntent.putExtra(Intent.EXTRA_TEXT, "Hello Dear," + name);

        try {
            // Start an email client chooser
            startActivity(Intent.createChooser(emailIntent, "Send mail..."));
            finish();
        } catch (android.content.ActivityNotFoundException ex) {
            // Handle if there is no email client installed
            Toast.makeText(EmployeeDetailsActivity.this,
                    "There is no email client installed.", Toast.LENGTH_SHORT).show();
        }
    }
}

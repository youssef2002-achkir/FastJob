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
import java.util.HashMap;
import java.util.Map;

public class AddApplicationActivity extends AppCompatActivity implements AdapterView.OnItemSelectedListener{

    EditText Appjobtitle;
    EditText AppName;
    EditText AppEmail;
    EditText AppAbout;
    EditText AppPhone;
    Spinner AppSpeciality;
    Button submitAppBtn;
    Button resetAppBtn;
    Button backBtn;
    Button homeBtn;

    DatabaseReference DB;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_application);

        Appjobtitle = findViewById(R.id.applyJob);
        AppName = findViewById(R.id.applyName);
        AppEmail = findViewById(R.id.applyEmail);
        AppAbout = findViewById(R.id.applyAbout);
        AppPhone = findViewById(R.id.Applyphone);
        AppSpeciality = findViewById(R.id.Applyspeciality);
        submitAppBtn = findViewById(R.id.submitAppBtn);
        resetAppBtn = findViewById(R.id.resetAppBtn);
        backBtn = findViewById(R.id.backAppBtn);
        homeBtn = findViewById(R.id.homeAppBtn);


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

        AppSpeciality.setAdapter(adapter);

        AppSpeciality.setOnItemSelectedListener(this);

        DB = FirebaseDatabase.getInstance().getReference();


        submitAppBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if (
                        AppName.getText().toString().isEmpty() || AppEmail.getText().toString().isEmpty() ||
                                Appjobtitle.getText().toString().isEmpty() || AppPhone.getText().toString().isEmpty() ||
                                AppAbout.getText().toString().isEmpty()
                ){
                    Toast.makeText(AddApplicationActivity.this, "Empty Records ", Toast.LENGTH_SHORT).show();
                }

                else{
                    String value = String.valueOf(AppSpeciality.getSelectedItem());
                    Map<String, Object> data = new HashMap<>();
                    data.put("EMAIL", AppEmail.getText().toString());
                    data.put("SPECIALITY", String.valueOf(AppSpeciality.getSelectedItem()));
                    data.put("JOB_TITLE", Appjobtitle.getText().toString());
                    data.put("PHONE_NUMBER", AppPhone.getText().toString());
                    data.put("ABOUT", AppAbout.getText().toString());

                    DB = FirebaseDatabase.getInstance().getReference();
                    DatabaseReference employersRef = DB.child("Employees");
                    Query EmployeeNameVer = employersRef.orderByChild("FULL_NAME").equalTo(AppName.getText().toString());
                    EmployeeNameVer.addValueEventListener(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot snapshot) {
                            if (snapshot.getChildrenCount() > 0){

                                for(DataSnapshot ds : snapshot.getChildren()) {
                                    String emailVerification = ds.child("EMAIL").getValue().toString();
                                    String phoneVerification = ds.child("PHONE_NUMBER").getValue().toString();
                                    if (emailVerification.equals(AppEmail.getText().toString()) && phoneVerification.equals(AppPhone.getText().toString())){
                                        data.put("EMPLOYEE_NAME", AppName.getText().toString());
                                        DatabaseReference categoryRef = DB.child("POSTULATIONS/" + value );
                                        DatabaseReference newRef =  categoryRef.push();
                                        newRef.setValue(data);
                                        Toast.makeText(AddApplicationActivity.this, "Your Application Successfully Added ",Toast.LENGTH_SHORT).show();
                                    }else{
                                        Toast.makeText(AddApplicationActivity.this, "Invalid Email : Employee " + AppName.getText().toString()
                                                + "  Doesn't have such email or Phone Number", Toast.LENGTH_SHORT).show();
                                    }
                                }

                            }else{
                                Toast.makeText(AddApplicationActivity.this, "This Employee Does not Exist Please Enter Your Valid Name", Toast.LENGTH_SHORT).show();
                            }
                        }

                        @Override
                        public void onCancelled(@NonNull DatabaseError error) {

                        }
                    });
                }




            }
        });

        resetAppBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Appjobtitle.getText().clear();
                AppName.getText().clear();
                AppAbout.getText().clear();
                AppPhone.getText().clear();
                AppEmail.getText().clear();
            }
        });

        homeBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(AddApplicationActivity.this, CategoriesActivity.class));
            }
        });

        backBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
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
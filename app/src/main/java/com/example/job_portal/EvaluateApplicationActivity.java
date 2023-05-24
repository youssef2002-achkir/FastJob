package com.example.job_portal;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class EvaluateApplicationActivity extends AppCompatActivity {

    EditText name;
    EditText comment;

    CheckBox badRate;
    CheckBox goodRate;
    CheckBox easyRate;
    CheckBox hardRate;
    CheckBox oneRate;
    CheckBox threeRate;
    CheckBox fiveRate;

    Button submit;
    Button back;

    DatabaseReference DB;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_evaluate_application);

        name = findViewById(R.id.rateName);
        badRate = findViewById(R.id.badRate);
        goodRate = findViewById(R.id.goodRate);
        easyRate = findViewById(R.id.easyRate);
        hardRate = findViewById(R.id.hardRate);
        oneRate = findViewById(R.id.OneRate);
        threeRate = findViewById(R.id.ThreeRate);
        fiveRate = findViewById(R.id.FiveRate);
        comment = findViewById(R.id.commentRate);
        submit = findViewById(R.id.submitRateBtn);
        back = findViewById(R.id.backRateBtn);

        Intent intent = getIntent();
        Bundle args = intent.getBundleExtra("BUNDLE");
        ArrayList<String> infos = (ArrayList<String>) args.getSerializable("username");

        name.setText(infos.get(0));

        DB = FirebaseDatabase.getInstance().getReference();

        DatabaseReference userRef = DB.child(infos.get(1));

        Query NameVer = userRef.orderByChild("FULL_NAME").equalTo(infos.get(0));

        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                NameVer.addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        if (snapshot.getChildrenCount() > 0){
                            Map<String, Object> data = new HashMap<>();
                            data.put("USERNAME", infos.get(0));
                            data.put("COMMENT", comment.getText().toString());
                            if (goodRate.isChecked() && easyRate.isChecked() && fiveRate.isChecked()){
                                Toast.makeText(EvaluateApplicationActivity.this, "Thanks for your feedback", Toast.LENGTH_SHORT).show();
                                data.put("APP FLOW", "GOOD");
                                data.put("LEARNABILITY", "EASY");
                                data.put("SATISFACTION", "VERY SATISFIED");
                                DatabaseReference Rates = DB.child("Rates");
                                DatabaseReference newRef =  Rates.push();
                                newRef.setValue(data);
                            }else if (goodRate.isChecked() && hardRate.isChecked() && threeRate.isChecked()){
                                Toast.makeText(EvaluateApplicationActivity.this, "Thanks for your feedback", Toast.LENGTH_SHORT).show();
                                data.put("APP FLOW", "GOOD");
                                data.put("LEARNABILITY", "HARD");
                                data.put("SATISFACTION", "WORKING WELL");
                                DatabaseReference Rates = DB.child("Rates");
                                DatabaseReference newRef =  Rates.push();
                                newRef.setValue(data);
                            }
                            else if (badRate.isChecked() && hardRate.isChecked() && oneRate.isChecked()){
                                Toast.makeText(EvaluateApplicationActivity.this, "Thanks for your feedback", Toast.LENGTH_SHORT).show();
                                data.put("APP FLOW", "BAD");
                                data.put("LEARNABILITY", "HARD");
                                data.put("SATISFACTION", "NOT SATISFIED");
                                DatabaseReference Rates = DB.child("Rates");
                                DatabaseReference newRef =  Rates.push();
                                newRef.setValue(data);
                            }else if (badRate.isChecked() && easyRate.isChecked() && oneRate.isChecked()){
                                Toast.makeText(EvaluateApplicationActivity.this, "Thanks for your feedback", Toast.LENGTH_SHORT).show();
                                data.put("APP FLOW", "BAD");
                                data.put("LEARNABILITY", "EASY");
                                data.put("SATISFACTION", "NOT SATISFIED");
                                DatabaseReference Rates = DB.child("Rates");
                                DatabaseReference newRef =  Rates.push();
                                newRef.setValue(data);
                            }else {
                                Toast.makeText(EvaluateApplicationActivity.this, "Enter Logical Rating Please", Toast.LENGTH_SHORT).show();
                            }
                        }
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {

                    }
                });
            }
        });
        back.setOnClickListener(view -> {
            finish();
        });





    }
}
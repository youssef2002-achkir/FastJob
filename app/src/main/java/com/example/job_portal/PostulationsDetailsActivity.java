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

public class PostulationsDetailsActivity extends AppCompatActivity {

    LinearLayout layout;

    DatabaseReference DB;

    Button back;
    Button cancel;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_postulations_details);

        layout = findViewById(R.id.AdminAppscontainer);
        back = findViewById(R.id.backadminbtn);
        cancel = findViewById(R.id.logoutbtnadmin);

        DB = FirebaseDatabase.getInstance().getReference();

        Intent intent = getIntent();
        Bundle args = intent.getBundleExtra("BUNDLE");
        String category = (String) args.getSerializable("CATEGORY");

        DatabaseReference categoryRef = DB.child("POSTULATIONS/" + category);

        categoryRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for(DataSnapshot ds : snapshot.getChildren()) {

                    String position = ds.child("JOB_TITLE").getValue().toString();
                    String name = ds.child("EMPLOYEE_NAME").getValue().toString();
                    String email = ds.child("EMAIL").getValue().toString();
                    String speciality = ds.child("SPECIALITY").getValue().toString();

                    //create card element
                    final View view = getLayoutInflater().inflate(R.layout.offerscardadmin, null);
                    TextView JOBTITLE = view.findViewById(R.id.offerjobadmin);
                    TextView NAME = view.findViewById(R.id.offerspecadmin);
                    TextView EMAIL = view.findViewById(R.id.offerdateadmin);
                    TextView SPECIALITY = view.findViewById(R.id.offersalaryadmin);
                    Button DELETE = view.findViewById(R.id.deleteofferadmin);

                    //insert database values
                    JOBTITLE.setText(position);
                    NAME.setText(name);
                    EMAIL.setText(email);
                    SPECIALITY.setText(speciality);

                    layout.addView(view);

                    DELETE.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            ds.getRef().removeValue();
                            layout.removeView(view);
                        }
                    });


                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        back.setOnClickListener(view -> {
            finish();
        });

        cancel.setOnClickListener(view -> {
            startActivity(new Intent(PostulationsDetailsActivity.this, MainActivity.class));
        });


    }
}
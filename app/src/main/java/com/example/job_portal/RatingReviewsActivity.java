package com.example.job_portal;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
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

public class RatingReviewsActivity extends AppCompatActivity {

    LinearLayout layout;

    DatabaseReference DB;

    Button back;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_rating_reviews);

        layout = findViewById(R.id.Ratingcontainer);
        back = findViewById(R.id.backRatingBtn);

        DB = FirebaseDatabase.getInstance().getReference();

        DatabaseReference ratings = DB.child("Rates");

        ratings.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for(DataSnapshot ds : snapshot.getChildren()) {

                    String name = ds.child("USERNAME").getValue().toString();
                    String appflowd = ds.child("APP FLOW").getValue().toString();
                    String learnablity = ds.child("LEARNABILITY").getValue().toString();
                    String satisfactiond = ds.child("SATISFACTION").getValue().toString();
                    final View ratingCard = getLayoutInflater().inflate(R.layout.cardrating, null);

                    TextView username = ratingCard.findViewById(R.id.ratinguser);
                    TextView appflow = ratingCard.findViewById(R.id.appflow);
                    TextView learnable = ratingCard.findViewById(R.id.learnability);
                    TextView satisfaction = ratingCard.findViewById(R.id.satisfaction);
                    Button delete = ratingCard.findViewById(R.id.deleterating);

                    username.setText(name);
                    appflow.setText("AppFlow:" + " " +appflowd);
                    learnable.setText("Learnability:" + " " +learnablity);
                    satisfaction.setText("Satisfaction:" + " " +satisfactiond);

                    layout.addView(ratingCard);
                    delete.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            layout.removeView(ratingCard);
                            ds.getRef().removeValue();
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
    }
}
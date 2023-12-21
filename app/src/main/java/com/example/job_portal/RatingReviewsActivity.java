package com.example.job_portal;

import android.annotation.SuppressLint;
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
import com.google.firebase.database.ValueEventListener;

/**
 * Activity for displaying and managing user ratings and reviews.
 */
public class RatingReviewsActivity extends AppCompatActivity {

    // UI elements
    LinearLayout layout;
    DatabaseReference DB;
    Button back;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_rating_reviews);

        // Initialization of UI elements
        layout = findViewById(R.id.Ratingcontainer);
        back = findViewById(R.id.backRatingBtn);

        // Initialization of Firebase Database reference
        DB = FirebaseDatabase.getInstance().getReference();

        // Set up a ValueEventListener to retrieve rating data from the database
        DatabaseReference ratings = DB.child("Rates");
        ratings.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for (DataSnapshot ds : snapshot.getChildren()) {
                    // Extract rating details from the database
                    String name = ds.child("USERNAME").getValue().toString();
                    String appFlow = ds.child("APP FLOW").getValue().toString();
                    String learnability = ds.child("LEARNABILITY").getValue().toString();
                    String satisfaction = ds.child("SATISFACTION").getValue().toString();

                    // Create a rating card element
                    final View ratingCard = getLayoutInflater().inflate(R.layout.cardrating, null);
                    TextView username = ratingCard.findViewById(R.id.ratinguser);
                    TextView appflowView = ratingCard.findViewById(R.id.appflow);
                    TextView learnabilityView = ratingCard.findViewById(R.id.learnability);
                    TextView satisfactionView = ratingCard.findViewById(R.id.satisfaction);
                    Button deleteButton = ratingCard.findViewById(R.id.deleterating);

                    // Insert rating details into the UI
                    username.setText(name);
                    appflowView.setText("AppFlow: " + appFlow);
                    learnabilityView.setText("Learnability: " + learnability);
                    satisfactionView.setText("Satisfaction: " + satisfaction);

                    // Add the rating card element to the layout
                    layout.addView(ratingCard);

                    // Set click listener for the "Delete" button
                    deleteButton.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            // Remove the rating from the database and the layout
                            layout.removeView(ratingCard);
                            ds.getRef().removeValue();
                        }
                    });
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                // Handle database error
            }
        });

        // Set click listener for the "Back" button
        back.setOnClickListener(view -> {
            finish();
        });
    }
}

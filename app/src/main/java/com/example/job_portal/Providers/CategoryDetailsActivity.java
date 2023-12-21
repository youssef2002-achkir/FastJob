package com.example.job_portal;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;
import com.google.firebase.database.DatabaseReference;
import java.util.ArrayList;

/**
 * Activity for displaying details of a specific job category.
 */
public class CategoryDetailsActivity extends AppCompatActivity {

    // UI elements
    LinearLayout layout;
    Button backBtn;
    Button outBtn;

    // Firebase Database reference
    DatabaseReference DB;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_category_details);

        // Initialize UI elements
        backBtn = findViewById(R.id.backadmion);
        outBtn = findViewById(R.id.logoutadmin);
        layout = findViewById(R.id.Admincontainer);

        // Get category information from the intent
        Intent intent = getIntent();
        Bundle args = intent.getBundleExtra("BUNDLE");
        ArrayList<String> catInfos = (ArrayList<String>) args.getSerializable("CATINFORMATION");

        System.out.println(catInfos);

        // Inflate the layout for category details
        final View detailsCat = getLayoutInflater().inflate(R.layout.categorydetailscard, null);
        TextView TITLE = detailsCat.findViewById(R.id.cattitle);
        TextView OFFERS = detailsCat.findViewById(R.id.catoffers);
        TextView APPS = detailsCat.findViewById(R.id.catapps);

        // Set values for the category details
        TITLE.setText(catInfos.get(0));
        OFFERS.setText("OFFERS :" + " " + catInfos.get(2) + " " + "OFFER.");
        APPS.setText("APPLICATIONS :" + " " + catInfos.get(1) + " " + "APPLICATION.");

        // Add the category details to the layout
        layout.addView(detailsCat);

        // Set click listeners for buttons
        backBtn.setOnClickListener(view -> finish());
        outBtn.setOnClickListener(view -> startActivity(new Intent(CategoryDetailsActivity.this, MainActivity.class)));
    }
}

package com.example.job_portal;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.google.firebase.database.DatabaseReference;

import java.util.ArrayList;

public class CategoryDetailsActivity extends AppCompatActivity {

    LinearLayout layout;
    DatabaseReference DB;

    Button backBtn;
    Button outBtn;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_category_details);

        backBtn = findViewById(R.id.backadmion);
        outBtn = findViewById(R.id.logoutadmin);
        layout = findViewById(R.id.Admincontainer);

        Intent intent = getIntent();
        Bundle args = intent.getBundleExtra("BUNDLE");
        ArrayList<String> catInfos = (ArrayList<String>) args.getSerializable("CATINFORMATION");

        System.out.println(catInfos);

        final View detailsCat = getLayoutInflater().inflate(R.layout.categorydetailscard, null);
        TextView TITLE = detailsCat.findViewById(R.id.cattitle);
        TextView OFFERS = detailsCat.findViewById(R.id.catoffers);
        TextView APPS = detailsCat.findViewById(R.id.catapps);

        //insert database values
        TITLE.setText(catInfos.get(0));
        OFFERS.setText("OFFERS :" + " " +  catInfos.get(2) + " " + "OFFER.");
        APPS.setText( "PPLICATIONS :" + " " +  catInfos.get(1) + " " + "APPLICATION.");

        layout.addView(detailsCat);

        backBtn.setOnClickListener(view -> {
            finish();
        });
        outBtn.setOnClickListener(view -> {
            startActivity(new Intent(CategoryDetailsActivity.this, MainActivity.class));
        });
    }
}
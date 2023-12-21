package com.example.job_portal;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import java.io.Serializable;
import java.util.ArrayList;

/**
 * Main activity for the administrator's dashboard.
 */
public class AdminDashbordActivity extends AppCompatActivity {

    // General buttons
    Button ecoBtn, softBtn, statBtn, elecBtn, mechBtn, archBtn, assistBtn, journBtn, back, rate;

    // Category buttons for applications and offers
    Button offerEco, appEco, offerSoft, appSoft, offerStat, appStat, offerElect, appElect,
           offerMech, appMech, offerArch, appArch, offerAssist, appAssist, offerJour, appJour;

    // Firebase Database reference
    DatabaseReference db;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_dashbord);

        // Initialize buttons
        initializeButtons();

        // Initialize category buttons
        initializeCategoryButtons();

        // Initialize Firebase Database reference
        initializeDatabaseReferences();

        // Set click listeners
        setButtonClickListeners();

        // Set event listeners for the database
        setDatabaseValueEventListeners();

        // Set click listener for the rating button
        rate.setOnClickListener(view -> startActivity(new Intent(AdminDashbordActivity.this, RatingReviewsActivity.class)));

        // Set click listener for the back button
        back.setOnClickListener(view -> finish());
    }

    // Method to initialize general buttons
    private void initializeButtons() {
        ecoBtn = findViewById(R.id.manageeconomics);
        softBtn = findViewById(R.id.managesoftware);
        statBtn = findViewById(R.id.managestat);
        elecBtn = findViewById(R.id.manageelect);
        mechBtn = findViewById(R.id.managemechanics);
        archBtn = findViewById(R.id.manageearchitecture);
        assistBtn = findViewById(R.id.manageassistant);
        journBtn = findViewById(R.id.manageejournal);
        rate = findViewById(R.id.DASHRATE);
        back = findViewById(R.id.dashBACK);
    }

    // Method to initialize category buttons
    private void initializeCategoryButtons() {
        offerEco = findViewById(R.id.offereconomics);
        appEco = findViewById(R.id.appeconomics);
        offerStat = findViewById(R.id.offerstat);
        appStat = findViewById(R.id.appstat);
        offerSoft = findViewById(R.id.offersoft);
        appSoft = findViewById(R.id.appsoft);
        offerElect = findViewById(R.id.offerelect);
        appElect = findViewById(R.id.appelect);
        offerMech = findViewById(R.id.offermechanics);
        appMech = findViewById(R.id.appmechanics);
        offerArch = findViewById(R.id.offerarchitec);
        appArch = findViewById(R.id.apparchitect);
        offerAssist = findViewById(R.id.offerassist);
        appAssist = findViewById(R.id.appassist);
        offerJour = findViewById(R.id.offerjournal);
        appJour = findViewById(R.id.appjournal);
    }

    // Method to initialize Firebase Database reference
    private void initializeDatabaseReferences() {
        db = FirebaseDatabase.getInstance().getReference();
    }

    // Method to set click listeners for category buttons
    private void setButtonClickListeners() {
        ecoBtn.setOnClickListener(view -> handleCategoryButtonClick("Economics"));
        softBtn.setOnClickListener(view -> handleCategoryButtonClick("Software Engineering"));
        statBtn.setOnClickListener(view -> handleCategoryButtonClick("Statistics"));
        elecBtn.setOnClickListener(view -> handleCategoryButtonClick("Electrical Engineering"));
        mechBtn.setOnClickListener(view -> handleCategoryButtonClick("Mechanical Engineering"));
        archBtn.setOnClickListener(view -> handleCategoryButtonClick("Architecture"));
        assistBtn.setOnClickListener(view -> handleCategoryButtonClick("Admin Assistance"));
        journBtn.setOnClickListener(view -> handleCategoryButtonClick("Journalism"));
    }

    // Method to set event listeners for the database
    private void setDatabaseValueEventListeners() {
        ArrayList<String> categories = new ArrayList<>();
        categories.add("Economics");
        categories.add("Software Engineering");
        categories.add("Statistics");
        categories.add("Electrical Engineering");
        categories.add("Mechanical Engineering");
        categories.add("Architecture");
        categories.add("Admin Assistance");
        categories.add("Journalism");

        for (String category : categories) {
            handleValueEventListener(category, "POSTULATIONS");
            handleValueEventListener(category, "OFFERS");
        }
    }

    // Method to handle event listeners for applications and offers
    private void handleValueEventListener(String category, String type) {
        DatabaseReference dataRef = db.child(type + "/" + category);

        dataRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                updateCount(category, type, snapshot.getChildrenCount());
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                // Handle database error...
            }
        });
    }

    // Method to update the count on the UI
    private void updateCount(String category, String type, long count) {
        Button button = findButton(category, type);
        if (button != null) {
            button.setText(String.valueOf(count));
        }
    }

    // Method to find the button associated with a category and type (applications or offers)
    private Button findButton(String category, String type) {
        switch (type) {
            case "POSTULATIONS":
                return findAppButton(category);
            case "OFFERS":
                return findOfferButton(category);
            default:
                return null;
        }
    }

    // Method to find the application button associated with a category
    private Button findAppButton(String category) {
        return findButtonByCategory(category, appEco, appSoft, appStat, appElect, appMech, appArch, appAssist, appJour);
    }

    // Method to find the offer button associated with a category
    private Button findOfferButton(String category) {
        return findButtonByCategory(category, offerEco, offerSoft, offerStat, offerElect, offerMech, offerArch, offerAssist, offerJour);
    }

    // Method to find the button associated with a category in the list of buttons
    private Button findButtonByCategory(String category, Button... buttons) {
        for (Button button : buttons) {
            String buttonText = button.getText().toString().toLowerCase();
            if (buttonText.contains(category.toLowerCase())) {
                return button;
            }
        }
        return null;
    }
}

package com.example.job_portal;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Toast;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import java.util.HashMap;
import java.util.Map;

/**
 * Activity class for evaluating job applications.
 */
public class EvaluateApplicationActivity extends AppCompatActivity {

    // UI elements
    private EditText name;
    private CheckBox badRate;
    private CheckBox goodRate;
    private CheckBox easyRate;
    private CheckBox hardRate;
    private CheckBox oneRate;
    private CheckBox threeRate;
    private CheckBox fiveRate;
    private EditText comment;
    private Button submit;
    private Button back;

    // Firebase Database reference
    private DatabaseReference DB;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_evaluate_application);

        // Initialization of UI elements
        initializeUI();

        // Get data passed from the previous activity
        Intent intent = getIntent();
        Bundle args = intent.getBundleExtra("BUNDLE");
        assert args != null;
        ArrayList<String> infos = args.getStringArrayList("username");

        // Set the username in the name EditText
        assert infos != null;
        name.setText(infos.get(0));

        // Initialization of Firebase Database reference
        DB = FirebaseDatabase.getInstance().getReference();

        // Set up the reference to the user in the database
        DatabaseReference userRef = DB.child(infos.get(1));

        // Set click listeners for buttons
        setButtonClickListeners(userRef);
    }

    // Method to initialize UI elements
    private void initializeUI() {
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
    }

    // Method to set click listeners for buttons
    private void setButtonClickListeners(DatabaseReference userRef) {
        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Validate and submit the application evaluation
                validateAndSubmitEvaluation(userRef);
            }
        });

        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
    }

    // Method to validate and submit the application evaluation
    private void validateAndSubmitEvaluation(DatabaseReference userRef) {
        Query nameVerification = userRef.orderByChild("FULL_NAME").equalTo(name.getText().toString());
        nameVerification.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if (snapshot.getChildrenCount() > 0) {
                    Map<String, Object> evaluationData = new HashMap<>();
                    evaluationData.put("USERNAME", name.getText().toString());
                    evaluationData.put("COMMENT", comment.getText().toString());
                    evaluateAndSubmit(evaluationData);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
            }
        });
    }

    // Method to evaluate and submit the application
    private void evaluateAndSubmit(Map<String, Object> data) {
        if (isGood() && isEasy() && isFive()) {
            submitEvaluation(data, "GOOD", "EASY", "VERY SATISFIED");
        } else if (isGood() && isHard() && isThree()) {
            submitEvaluation(data, "GOOD", "HARD", "WORKING WELL");
        } else if (isBad() && isHard() && isOne()) {
            submitEvaluation(data, "BAD", "HARD", "NOT SATISFIED");
        } else if (isBad() && isEasy() && isOne()) {
            submitEvaluation(data, "BAD", "EASY", "NOT SATISFIED");
        } else {
            Toast.makeText(EvaluateApplicationActivity.this, "Enter Logical Rating Please", Toast.LENGTH_SHORT).show();
        }
    }

    // Helper methods for readability

    // Method to check if the 'Good' rating is selected
    private boolean isGood() {
        return goodRate.isChecked();
    }

    // Method to check if the 'Bad' rating is selected
    private boolean isBad() {
        return badRate.isChecked();
    }

    // Method to check if the 'Easy' rating is selected
    private boolean isEasy() {
        return easyRate.isChecked();
    }

    // Method to check if the 'Hard' rating is selected
    private boolean isHard() {
        return hardRate.isChecked();
    }

    // Method to check if the 'One' rating is selected
    private boolean isOne() {
        return oneRate.isChecked();
    }

    // Method to check if the 'Three' rating is selected
    private boolean isThree() {
        return threeRate.isChecked();
    }

    // Method to check if the 'Five' rating is selected
    private boolean isFive() {
        return fiveRate.isChecked();
    }

    // Method to submit the evaluation to the database
    private void submitEvaluation(Map<String, Object> data, String appFlow, String learnability, String satisfaction) {
        Toast.makeText(EvaluateApplicationActivity.this, "Thanks for your feedback", Toast.LENGTH_SHORT).show();
        data.put("APP FLOW", appFlow);
        data.put("LEARNABILITY", learnability);
        data.put("SATISFACTION", satisfaction);
        DatabaseReference ratesRef = DB.child("Rates");
        DatabaseReference newRef = ratesRef.push();
        newRef.setValue(data);
    }
}

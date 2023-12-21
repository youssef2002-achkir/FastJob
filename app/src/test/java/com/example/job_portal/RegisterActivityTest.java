package com.example.job_portal;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.robolectric.Robolectric;
import org.robolectric.RobolectricTestRunner;
import org.robolectric.annotation.Config;

import static org.junit.Assert.assertNotNull;

@RunWith(RobolectricTestRunner.class)
@Config(sdk = 28)
public class RegisterActivityTest {

    private RegisterActivity activity;

    @Before
    public void setUp() {
        activity = Robolectric.buildActivity(RegisterActivity.class).create().get();
    }

    @Test
    public void testActivityNotNull() {
        assertNotNull(activity);
    }

    @Test
    public void testInitializeUI() {
        activity.initializeUI();

        TextView regEmail = activity.findViewById(R.id.RegEmail);
        TextView regPassword = activity.findViewById(R.id.RegPassword);
        TextView regFirstName = activity.findViewById(R.id.RegFName);
        TextView regLastName = activity.findViewById(R.id.RegLName);
        TextView regPhoneNumber = activity.findViewById(R.id.RegPhoneNum);
        TextView loginHere = activity.findViewById(R.id.LoginLink);
        Button btnRegister = activity.findViewById(R.id.RegButton);
        CheckBox roleEmployee = activity.findViewById(R.id.employeeRole);
        CheckBox roleEmployer = activity.findViewById(R.id.employerRole);

        assertNotNull(regEmail);
        assertNotNull(regPassword);
        assertNotNull(regFirstName);
        assertNotNull(regLastName);
        assertNotNull(regPhoneNumber);
        assertNotNull(loginHere);
        assertNotNull(btnRegister);
        assertNotNull(roleEmployee);
        assertNotNull(roleEmployer);
    }

}

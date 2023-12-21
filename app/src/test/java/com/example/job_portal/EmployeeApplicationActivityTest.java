package com.example.job_portal;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.robolectric.Robolectric;
import org.robolectric.RobolectricTestRunner;
import org.robolectric.annotation.Config;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.mockito.Mockito.mock;

@RunWith(RobolectricTestRunner.class)
@Config(sdk = 28)
public class EmployeeApplicationsActivityTest {

    private EmployeeApplicationsActivity activity;
    private DatabaseReference mockDatabaseReference;

    @Before
    public void setUp() {
        activity = Robolectric.buildActivity(EmployeeApplicationsActivity.class).create().get();
        mockDatabaseReference = mock(DatabaseReference.class);
        activity.DB = mockDatabaseReference;
    }

    @Test
    public void testActivityNotNull() {
        assertNotNull(activity);
    }

    @Test
    public void testInitializeUI() {
        activity.initializeUI();

        Button newPostBtn = activity.findViewById(R.id.appAddPostBtn);
        Button appProfileBtn = activity.findViewById(R.id.employeeprofilebtn);
        Button appSignoutBtn = activity.findViewById(R.id.applogoutBtn);
        Button appBackBtn = activity.findViewById(R.id.appbackBtn);
        LinearLayout layout = activity.findViewById(R.id.Appcontainer);

        assertNotNull(newPostBtn);
        assertNotNull(appProfileBtn);
        assertNotNull(appSignoutBtn);
        assertNotNull(appBackBtn);
        assertNotNull(layout);
    }

    @Test
    public void testOnClickListeners() {
        activity.initializeUI();
        activity.setButtonClickListeners();

        activity.newPostBtn.performClick();


        activity.appbackBtn.performClick();

        activity.appsignoutBtn.performClick();
    }


}

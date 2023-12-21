package com.example.job_portal;

import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.robolectric.Robolectric;
import org.robolectric.RobolectricTestRunner;
import org.robolectric.annotation.Config;

import static org.junit.Assert.assertNotNull;

@RunWith(RobolectricTestRunner.class)
@Config(sdk = 28)
public class AddOfferActivityTest {

    private AddOfferActivity activity;

    @Before
    public void setUp() {
        activity = Robolectric.buildActivity(AddOfferActivity.class).create().get();
    }

    @Test
    public void testActivityNotNull() {
        assertNotNull(activity);
    }

    @Test
    public void testInitializeUI() {
        activity.initializeUI();

        EditText jobTitle = activity.findViewById(R.id.position);
        EditText salary = activity.findViewById(R.id.salary);
        EditText company = activity.findViewById(R.id.company);
        EditText employerName = activity.findViewById(R.id.employerName);
        EditText beginDate = activity.findViewById(R.id.date);
        Button submitOfferBtn = activity.findViewById(R.id.submitOfferBtn);
        Button resetBtn = activity.findViewById(R.id.resetBtn);
        Button backOfferBtn = activity.findViewById(R.id.backOfferBtn);
        Button homeOfferBtn = activity.findViewById(R.id.homeOfferBtn);

        assertNotNull(jobTitle);
        assertNotNull(salary);
        assertNotNull(company);
        assertNotNull(employerName);
        assertNotNull(beginDate);
        assertNotNull(submitOfferBtn);
        assertNotNull(resetBtn);
        assertNotNull(backOfferBtn);
        assertNotNull(homeOfferBtn);
    }


}

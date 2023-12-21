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
import static org.junit.Assert.assertNull;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;

@RunWith(RobolectricTestRunner.class)
@Config(sdk = 28)
public class AddApplicationActivityTest {

    private AddApplicationActivity activity;

    @Before
    public void setUp() {
        activity = Robolectric.buildActivity(AddApplicationActivity.class).create().get();
    }

    @Test
    public void testActivityNotNull() {
        assertNotNull(activity);
    }

    @Test
    public void testInitializeUI() {
        activity.initializeUI();

        EditText appJobTitle = activity.findViewById(R.id.applyJob);
        EditText appName = activity.findViewById(R.id.applyName);
        EditText appEmail = activity.findViewById(R.id.applyEmail);
        EditText appAbout = activity.findViewById(R.id.applyAbout);
        EditText appPhone = activity.findViewById(R.id.Applyphone);
        Spinner appSpeciality = activity.findViewById(R.id.Applyspeciality);
        Button submitAppBtn = activity.findViewById(R.id.submitAppBtn);
        Button resetAppBtn = activity.findViewById(R.id.resetAppBtn);
        Button backBtn = activity.findViewById(R.id.backAppBtn);
        Button homeBtn = activity.findViewById(R.id.homeAppBtn);

        assertNotNull(appJobTitle);
        assertNotNull(appName);
        assertNotNull(appEmail);
        assertNotNull(appAbout);
        assertNotNull(appPhone);
        assertNotNull(appSpeciality);
        assertNotNull(submitAppBtn);
        assertNotNull(resetAppBtn);
        assertNotNull(backBtn);
        assertNotNull(homeBtn);
    }


}

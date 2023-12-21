package com.example.job_portal;
import android.content.Intent;
import android.widget.Button;
import android.widget.TextView;
import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.robolectric.Robolectric;
import org.robolectric.RobolectricTestRunner;
import org.robolectric.annotation.Config;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.verify;
import static org.robolectric.Shadows.shadowOf;

@RunWith(RobolectricTestRunner.class)
@Config(sdk = 28)
public class SignInActivityTest {

    private SignInActivity activity;

    @Mock
    private TextInputEditText mockLoginEmail;

    @Mock
    private TextInputEditText mockLoginPassword;

    @Mock
    private TextView mockRegister;

    @Mock
    private Button mockBtnLogin;

    @Mock
    private FirebaseAuth mockUserLogin;

    @Mock
    private DatabaseReference mockDB;

    @Before
    public void setUp() {
        MockitoAnnotations.initMocks(this);

        activity = Robolectric.buildActivity(SignInActivity.class).create().get();
        activity.LoginEmail = mockLoginEmail;
        activity.LoginPassword = mockLoginPassword;
        activity.Register = mockRegister;
        activity.btnLogin = mockBtnLogin;
        activity.UserLogin = mockUserLogin;
        activity.DB = mockDB;
    }

    @Test
    public void testLoginUserWithEmptyEmail() {
        Mockito.when(mockLoginEmail.getText()).thenReturn(null);
        activity.loginUser();
        verify(mockLoginEmail).setError("Email can't be empty");
    }

    @Test
    public void testLoginUserWithEmptyPassword() {
        Mockito.when(mockLoginEmail.getText()).thenReturn("test@example.com");
        Mockito.when(mockLoginPassword.getText()).thenReturn(null);
        activity.loginUser();
        verify(mockLoginPassword).setError("Password can't be empty");
    }

    @Test
    public void testLoginUserWithValidCredentials() {
        Mockito.when(mockLoginEmail.getText()).thenReturn("test@example.com");
        Mockito.when(mockLoginPassword.getText()).thenReturn("password");

        FirebaseAuth.AuthResult mockAuthResult = Mockito.mock(FirebaseAuth.AuthResult.class);
        Mockito.when(mockUserLogin.signInWithEmailAndPassword(anyString(), anyString()))
                .thenReturn(Mockito.mock(Task.class));
        Mockito.when(mockUserLogin.signInWithEmailAndPassword(anyString(), anyString()).addOnCompleteListener(any()))
                .thenReturn(Mockito.mock(Task.class));
        Mockito.when(mockUserLogin.signInWithEmailAndPassword(anyString(), anyString()).addOnCompleteListener(any()).isSuccessful())
                .thenReturn(true);
        Mockito.when(mockUserLogin.signInWithEmailAndPassword(anyString(), anyString()).addOnCompleteListener(any()).getResult())
                .thenReturn(mockAuthResult);

        DatabaseReference mockEmployeesRef = Mockito.mock(DatabaseReference.class);
        DatabaseReference mockEmployersRef = Mockito.mock(DatabaseReference.class);
        Query mockQuery = Mockito.mock(Query.class);

        Mockito.when(mockDB.child("Employees")).thenReturn(mockEmployeesRef);
        Mockito.when(mockDB.child("Employers")).thenReturn(mockEmployersRef);
        Mockito.when(mockEmployeesRef.orderByChild("EMAIL")).thenReturn(mockQuery);
        Mockito.when(mockQuery.equalTo("test@example.com")).thenReturn(mockQuery);
        Mockito.when(mockQuery.orderByChild("EMAIL")).thenReturn(mockQuery);
        Mockito.when(mockQuery.equalTo("test@example.com")).thenReturn(mockQuery);

        activity.loginUser();

        Intent expectedIntent = new Intent(activity, CategoriesActivity.class);
        expectedIntent.putExtra("BUNDLE", Mockito.any(Bundle.class));

        Intent actualIntent = shadowOf(activity).getNextStartedActivity();
        assertEquals(expectedIntent.getComponent(), actualIntent.getComponent());
    }
}

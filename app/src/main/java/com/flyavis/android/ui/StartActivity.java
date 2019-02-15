package com.flyavis.android.ui;

import android.content.Intent;
import android.os.Bundle;

import com.firebase.ui.auth.AuthUI;
import com.firebase.ui.auth.IdpResponse;
import com.flyavis.android.BuildConfig;
import com.flyavis.android.R;
import com.google.firebase.FirebaseApp;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import java.util.Arrays;
import java.util.List;

import androidx.appcompat.app.AppCompatActivity;

import static com.flyavis.android.Constants.RC_SIGN_IN;

public class StartActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        //For login check & onboarding experience
        super.onCreate(savedInstanceState);
        checkLoginStatus();
    }

    private void checkLoginStatus() {
        FirebaseApp.initializeApp(this);
        FirebaseAuth auth = FirebaseAuth.getInstance();

        if (auth.getCurrentUser() != null) {
            // already signed in
            launchMainActivity();
        } else {
            // not signed in
            List<AuthUI.IdpConfig> providers = Arrays.asList(
                    new AuthUI.IdpConfig.GoogleBuilder().build(),
                    new AuthUI.IdpConfig.FacebookBuilder().build()
            );

            // Create and launchMainActivity sign-in intent
            startActivityForResult(
                    AuthUI.getInstance()
                            .createSignInIntentBuilder()
                            .setAvailableProviders(providers)
                            .setIsSmartLockEnabled(!BuildConfig.DEBUG /* credentials */, true /* hints */)
                            .setTheme(R.style.AppTheme)
                            .build(),
                    RC_SIGN_IN);
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == RC_SIGN_IN) {
            IdpResponse response = IdpResponse.fromResultIntent(data);

            if (resultCode == RESULT_OK) {
                // Successfully signed in
                FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
                launchMainActivity();

            } else {
                // Sign in failed. If response is null the user canceled the
                // sign-in flow using the back button. Otherwise check
                // response.getError().getErrorCode() and handle the error.
                // ...
                finish();
            }
        }
    }

    private void launchMainActivity() {
        Intent activityIntent = new Intent(this, MainActivity.class);
        startActivity(activityIntent);
        finish();
    }

}

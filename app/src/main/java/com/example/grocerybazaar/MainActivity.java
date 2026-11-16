package com.example.grocerybazaar;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.util.Patterns;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.auth.FirebaseAuth;

public class MainActivity extends AppCompatActivity {

    private static final int MAX_ATTEMPTS = 5;
    private static final long LOCK_DURATION = 30000L;

    private FirebaseAuth auth;
    private EditText emailInput, passwordInput;
    private Button loginButton;

    private SharedPreferences preferences;
    private CountDownTimer lockTimer;

    private int failedAttempts;
    private long lockedUntil;
    private boolean loginInProgress = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        auth = FirebaseAuth.getInstance();

        preferences = getSharedPreferences(
                "grocery_login_security", MODE_PRIVATE);

        failedAttempts = preferences.getInt("failed_attempts", 0);
        lockedUntil = preferences.getLong("locked_until", 0L);

        emailInput = findViewById(R.id.emailInput);
        passwordInput = findViewById(R.id.passwordInput);
        loginButton = findViewById(R.id.loginButton);

        loginButton.setOnClickListener(v -> loginUser());

        findViewById(R.id.registerButton).setOnClickListener(v ->
                startActivity(new Intent(this, RegisterActivity.class)));

        findViewById(R.id.forgotPasswordButton)
                .setOnClickListener(v -> resetPassword());

        updateLockState();
    }

    private void loginUser() {
        if (loginInProgress) {
            return;
        }

        if (System.currentTimeMillis() < lockedUntil) {
            updateLockState();
            return;
        }

        String email = emailInput.getText().toString().trim();
        String password = passwordInput.getText().toString();

        if (!Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            emailInput.setError("Enter a valid email");
            return;
        }

        if (password.isEmpty()) {
            passwordInput.setError("Enter password");
            return;
        }

        loginInProgress = true;
        loginButton.setEnabled(false);

        auth.signInWithEmailAndPassword(email, password)
                .addOnCompleteListener(this, task -> {
                    loginInProgress = false;

                    if (task.isSuccessful()) {
                        failedAttempts = 0;
                        lockedUntil = 0L;
                        saveSecurityState();

                        Toast.makeText(this,
                                "Login successful",
                                Toast.LENGTH_SHORT).show();

                        Intent intent = new Intent(
                                this, HomeActivity.class);

                        intent.addFlags(
                                Intent.FLAG_ACTIVITY_NEW_TASK |
                                Intent.FLAG_ACTIVITY_CLEAR_TASK
                        );

                        startActivity(intent);
                        finish();
                        return;
                    }

                    failedAttempts++;

                    if (failedAttempts >= MAX_ATTEMPTS) {
                        failedAttempts = MAX_ATTEMPTS;
                        lockedUntil =
                                System.currentTimeMillis() + LOCK_DURATION;

                        saveSecurityState();

                        Toast.makeText(this,
                                "Too many failed attempts. Wait 30 seconds.",
                                Toast.LENGTH_LONG).show();

                        updateLockState();
                        return;
                    }

                    saveSecurityState();

                    int remaining = MAX_ATTEMPTS - failedAttempts;

                    Toast.makeText(this,
                            "Login failed. " + remaining
                                    + " attempts remaining.",
                            Toast.LENGTH_LONG).show();

                    loginButton.setEnabled(true);
                });
    }

    private void updateLockState() {
        if (lockTimer != null) {
            lockTimer.cancel();
            lockTimer = null;
        }

        long remaining = lockedUntil - System.currentTimeMillis();

        if (remaining <= 0) {
            if (lockedUntil != 0L) {
                lockedUntil = 0L;
                failedAttempts = 0;
                saveSecurityState();
            }

            loginButton.setEnabled(!loginInProgress);
            loginButton.setText("Login");
            return;
        }

        loginButton.setEnabled(false);

        lockTimer = new CountDownTimer(remaining, 1000L) {

            @Override
            public void onTick(long millisUntilFinished) {
                long seconds = (millisUntilFinished + 999L) / 1000L;
                loginButton.setText("Wait " + seconds + "s");
            }

            @Override
            public void onFinish() {
                lockedUntil = 0L;
                failedAttempts = 0;
                saveSecurityState();

                loginButton.setEnabled(true);
                loginButton.setText("Login");
            }
        }.start();
    }

    private void saveSecurityState() {
        preferences.edit()
                .putInt("failed_attempts", failedAttempts)
                .putLong("locked_until", lockedUntil)
                .apply();
    }

    private void resetPassword() {
        String email = emailInput.getText().toString().trim();

        if (!Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            emailInput.setError("Enter your email first");
            return;
        }

        auth.sendPasswordResetEmail(email)
                .addOnCompleteListener(this, task -> {
                    Toast.makeText(this,
                            task.isSuccessful()
                                    ? "If the account exists, check your email."
                                    : "Unable to process reset. Try again later.",
                            Toast.LENGTH_LONG).show();
                });
    }

    @Override
    protected void onDestroy() {
        if (lockTimer != null) {
            lockTimer.cancel();
        }
        super.onDestroy();
    }
}
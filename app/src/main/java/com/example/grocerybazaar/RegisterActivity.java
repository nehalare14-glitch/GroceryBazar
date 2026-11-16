package com.example.grocerybazaar;

import android.os.Bundle;
import android.content.Intent;
import android.util.Patterns;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import com.google.firebase.auth.FirebaseAuth;

public class RegisterActivity extends AppCompatActivity {

    private FirebaseAuth auth;
    private EditText emailInput, passwordInput, confirmInput;
    private Button registerButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        auth = FirebaseAuth.getInstance();

        emailInput = findViewById(R.id.registerEmail);
        passwordInput = findViewById(R.id.registerPassword);
        confirmInput = findViewById(R.id.confirmPassword);
        registerButton = findViewById(R.id.createAccountButton);

        registerButton.setOnClickListener(v -> registerUser());

        findViewById(R.id.backToLogin)
                .setOnClickListener(v -> finish());
    }

    private void registerUser() {
        String email = emailInput.getText().toString().trim();
        String password = passwordInput.getText().toString();
        String confirm = confirmInput.getText().toString();

        if (!Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            emailInput.setError("Enter a valid email");
            return;
        }

        if (password.length() < 6) {
            passwordInput.setError("Use at least 6 characters");
            return;
        }

        if (!password.equals(confirm)) {
            confirmInput.setError("Passwords do not match");
            return;
        }

        registerButton.setEnabled(false);

        auth.createUserWithEmailAndPassword(email, password)
            .addOnCompleteListener(this, task -> {
                registerButton.setEnabled(true);

                if (task.isSuccessful()) {
                    auth.signOut();
                    Toast.makeText(this,
                        "Account created. Please log in.",
                        Toast.LENGTH_LONG).show();
                    finish();
                } else {
                    String message = task.getException() != null
                        ? task.getException().getLocalizedMessage()
                        : "Registration failed";
                    Toast.makeText(this, message,
                        Toast.LENGTH_LONG).show();
                }
            });
    }
}
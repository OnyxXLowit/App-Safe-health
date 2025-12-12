package com.example.myappchronic;

import android.content.Intent;
import android.os.Bundle;
import android.widget.EditText;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.button.MaterialButton;

public class LoginActivity extends AppCompatActivity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_login);

        EditText inputEmail = findViewById(R.id.inputEmail);
        EditText inputPassword = findViewById(R.id.inputPassword);
        EditText inputName = findViewById(R.id.inputName);
        MaterialButton btnLogin = findViewById(R.id.btnLogin);
        MaterialButton btnRegister = findViewById(R.id.btnRegister);

        btnLogin.setOnClickListener(v -> {
            if (inputEmail.getText().toString().trim().isEmpty()) {
                Toast.makeText(this, "Введите email", Toast.LENGTH_SHORT).show();
                return;
            }
            goToMain("Вход выполнен");
        });

        btnRegister.setOnClickListener(v -> {
            if (inputName.getText().toString().trim().isEmpty()) {
                Toast.makeText(this, "Введите имя", Toast.LENGTH_SHORT).show();
                return;
            }
            goToMain("Профиль создан");
        });
    }

    private void goToMain(String message) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
        startActivity(new Intent(this, MainActivity.class));
        finish();
    }
}




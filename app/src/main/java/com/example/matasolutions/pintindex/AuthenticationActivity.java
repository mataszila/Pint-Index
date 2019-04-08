package com.example.matasolutions.pintindex;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;

public class AuthenticationActivity extends AppCompatActivity {
    private FirebaseAuth mAuth;
    private EditText password;
    private EditText email;
    private Button button_register;
    private Button button_login;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_authentication);

            email = (EditText) findViewById(R.id.signup_email_input);
            password =(EditText) findViewById(R.id.signup_password_input);
            button_register = (Button)findViewById(R.id.button_register);
            button_login = (Button)findViewById(R.id.button_login);
            mAuth = FirebaseAuth.getInstance();

        }
}

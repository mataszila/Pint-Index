package com.example.matasolutions.pintindex;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class FeedbackActivity extends AppCompatActivity {

    com.google.android.material.textfield.TextInputEditText input_name;
    com.google.android.material.textfield.TextInputEditText input_email;
    com.google.android.material.textfield.TextInputEditText input_query;

    String name;
    String email;
    String query;

    Button actionButton;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_feedback);

        input_name = findViewById(R.id.feedback_input_name);
        input_email = findViewById(R.id.feedback_input_email);
        input_query = findViewById(R.id.feedback_input_query);

        actionButton = findViewById(R.id.feedback_actionbutton);

        actionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                 name = input_name.getText().toString();
                 email = input_email.getText().toString();
                 query = input_query.getText().toString();

                Intent email_intent = new Intent(Intent.ACTION_SEND);
                email_intent.putExtra(Intent.EXTRA_EMAIL, new String[]{ "pintindex@gmail.com"});
                email_intent.putExtra(Intent.EXTRA_SUBJECT, "subject");
                email_intent.putExtra(Intent.EXTRA_TEXT, query);
                email_intent.setType("message/rfc822");
                startActivity(Intent.createChooser(email_intent, "Choose an email client"));

            }
        });




    }
}

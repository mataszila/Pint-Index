package com.example.matasolutions.pintindex;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.TextView;

public class PubCompareActivity extends AppCompatActivity {

    String pub1_name;
    TextView pub1_name_textview;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pub_compare);

        pub1_name = getIntent().getStringExtra("pubName");

        pub1_name_textview = findViewById(R.id.pub1_name_textview);

        pub1_name_textview.setText(pub1_name);


    }
}

package com.example.matasolutions.pintindex;

import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.os.Bundle;
import android.util.Log;
import android.view.Display;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.app.AlertDialog.Builder;
import android.app.AlertDialog;




public class PubCompareActivity extends AppCompatActivity {

    String pub1_name;
    TextView pub1_name_textview;
    TextView pub1_avg_rating;


    TextView pub2_name_textview;
    TextView pub2_avg_rating;

    Pub pub1;
    Pub pub2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pub_compare);

        SetupAlertDialog();

        // Pub 1

        pub1_name = getIntent().getStringExtra("pubName");
        SetupFirstPub();




    }

    private void SetupFirstPub(){
        pub1 = new PubSetup().returnPubByName(pub1_name);

        pub1_name_textview = findViewById(R.id.pub1_name_textview);

        pub1_name_textview.setText(pub1_name);

        pub1_avg_rating = findViewById(R.id.pub1_avg_rating);
        pub1_avg_rating.setText(String.valueOf(pub1.ratings.averageRating));
    }



    private void SetupSecondPub(String name) {

        PubSetup setup = new PubSetup();
        pub2 = setup.returnPubByName(name);

        pub2_name_textview = findViewById(R.id.pub2_name_textview);
        pub2_avg_rating = findViewById(R.id.pub2_avg_rating);

        pub2_name_textview.setText(pub2.name);
        pub2_avg_rating.setText(String.valueOf(pub2.ratings.averageRating));

    }

    private ArrayAdapter<String> SetupArrayAdapter(){

        PubSetup setup = new PubSetup();

        final ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(PubCompareActivity.this, android.R.layout.select_dialog_singlechoice);


        for(int i=0;i<setup.pubs.size();i++){

            arrayAdapter.add(setup.pubs.get(i).name);

        }

        return arrayAdapter;

    }


    private synchronized void SetupAlertDialog(){

        AlertDialog.Builder builderSingle = new AlertDialog.Builder(PubCompareActivity.this);
        // builderSingle.setIcon(R.drawable.ic_launcher);
        builderSingle.setTitle("Select One Name:-");

        final ArrayAdapter<String> arrayAdapter = SetupArrayAdapter();

        builderSingle.setNegativeButton("cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

                dialog.dismiss();
            }
        });

        builderSingle.setAdapter(arrayAdapter, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                String strName = arrayAdapter.getItem(which);
                SetupSecondPub(strName);


                AlertDialog.Builder builderInner = new AlertDialog.Builder(PubCompareActivity.this);
                builderInner.setMessage(strName);
                builderInner.setTitle("Your Selected Item is");
                builderInner.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog,int which) {

                        dialog.dismiss();
                    }
                });
                builderInner.show();
            }
        });
        builderSingle.show();


    }






}

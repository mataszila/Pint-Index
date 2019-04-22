package com.example.matasolutions.pintindex;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.text.InputType;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;

import androidx.recyclerview.widget.RecyclerView;

public  class MyPricesAdapter extends RecyclerView.Adapter<MyPricesAdapter.MyPricesViewHolder> {
    private ArrayList<Price> mDataset;
    private String pubID;
    private Context context;

    // Provide a reference to the views for each data item
    // Complex data items may need more than one view per item, and
    // you provide access to all the views for a data item in a view holder
    public static class MyPricesViewHolder extends RecyclerView.ViewHolder {
        // each data item is just a string in this case

        public TextView brand;
        public TextView price;


        public MyPricesViewHolder(View v) {
            super(v);

            brand =  v.findViewById(R.id.pub_activity_prices_brand);
            price =  v.findViewById(R.id.pub_activity_prices_price);
        }
    }

    // Provide a suitable constructor (depends on the kind of dataset)
    public MyPricesAdapter(ArrayList<Price> myDataset, String pubID,Context context) {
        mDataset = myDataset;
        this.pubID = pubID;
        this.context = context;
    }

    // Create new views (invoked by the layout manager)
    @Override
    public MyPricesAdapter.MyPricesViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        // create a new view
        Context context = parent.getContext();
        LayoutInflater inflater = LayoutInflater.from(context);

        View v = inflater.inflate(R.layout.pub_activity_prices_recycler_view, parent, false);

        MyPricesAdapter.MyPricesViewHolder vh = new MyPricesAdapter.MyPricesViewHolder(v);
        return vh;
    }


    // Replace the contents of a view (invoked by the layout manager)
    @Override
    public void onBindViewHolder(MyPricesAdapter.MyPricesViewHolder holder, int position) {
        // - get element from your dataset at this position
        // - replace the contents of the view with that element
        Price thisPrice = mDataset.get(position);

        // Set item views based on your views and data model
        TextView brand = holder.brand;
        final TextView price  = holder.price;


        final int arrPos = position;

        brand.setText(String.valueOf(thisPrice.product.brand));
        price.setText(String.valueOf(thisPrice.price));


        price.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                FirebaseDatabase database = FirebaseDatabase.getInstance();
                final  DatabaseReference myRef = database.getReference("pubsList");
                AlertDialog.Builder builder = new AlertDialog.Builder(context);
                builder.setTitle("Please enter a new price for selected product");

                final EditText input = new EditText(context);

                input.setInputType(InputType.TYPE_CLASS_TEXT);
                builder.setView(input);

                builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        String newPrice = input.getText().toString();
                        myRef.child("list").child(pubID).child("prices").child("priceList").child(String.valueOf(arrPos)).child("price").setValue(Double.parseDouble(newPrice));
                        price.setText(newPrice);
                    }
                });
                builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.cancel();
                    }
                });

                builder.show();


            }
        });



    }


    // Return the size of your dataset (invoked by the layout manager)
    @Override
    public int getItemCount() {
        return mDataset.size();
    }
}
package com.example.matasolutions.pintindex;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;

import com.example.matasolutions.pintindex.Pub_Data.RatingEntry;

import org.w3c.dom.Text;

import java.util.ArrayList;

public class RateActivity extends AppCompatActivity {

    Pub pub;

    Spinner DrinkTypeSpinner;
    Spinner BrandSpinner;
    Spinner AmountSpinner;

    private RecyclerView recyclerView;
    private RecyclerView.Adapter mAdapter;
    private RecyclerView.LayoutManager layoutManager;

    DrinkType drinkType;
    Brand brand;
    Amount amount;

    ArrayList<Pub> pubsWithProduct;

    Button actionButton;

    Button sort_high_to_low;
    Button sort_low_to_high;




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_rate);


            ArrayList<RatingEntry> ratingEntries = new ArrayList<>();

            ratingEntries.add(new RatingEntry(RatingType.ATMOSPHERE));
            ratingEntries.add(new RatingEntry(RatingType.HYGIENE));
            ratingEntries.add(new RatingEntry(RatingType.SERVICE));
            ratingEntries.add(new RatingEntry(RatingType.VALUE_FOR_PRICE));


            recyclerView =  findViewById(R.id.my_recycler_view);

            recyclerView.setHasFixedSize(true);

            layoutManager = new LinearLayoutManager(this);
            recyclerView.setLayoutManager(layoutManager);

            mAdapter = new MyAdapter(ratingEntries);
            recyclerView.setAdapter(mAdapter);






    }

    public static class MyAdapter extends RecyclerView.Adapter<MyAdapter.MyViewHolder> {
        private ArrayList<RatingEntry> mDataset;
        private Product product;


        // Provide a reference to the views for each data item
        // Complex data items may need more than one view per item, and
        // you provide access to all the views for a data item in a view holder
        public static class MyViewHolder extends RecyclerView.ViewHolder {
            // each data item is just a string in this case


            public TextView ratingType;

            public ImageView star_1;
            public ImageView star_2 ;
            public ImageView star_3;
            public ImageView star_4;
            public ImageView star_5;


            public MyViewHolder(View v) {
                super(v);

                star_1 = v.findViewById(R.id.star_1);
                star_2 = v.findViewById(R.id.star_2);
                star_3 =  v.findViewById(R.id.star_3);
                star_4 =  v.findViewById(R.id.star_4);
                star_5 = v.findViewById(R.id.star_5);

                ratingType = v.findViewById(R.id.rating_type);

            }
        }

        // Provide a suitable constructor (depends on the kind of dataset)
        public MyAdapter(ArrayList<RatingEntry> myDataset) {
            mDataset = myDataset;
        }

        // Create new views (invoked by the layout manager)
        @Override
        public MyAdapter.MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            // create a new view
            Context context = parent.getContext();
            LayoutInflater inflater = LayoutInflater.from(context);

            View v = inflater.inflate(R.layout.ratingcategory, parent, false);

            MyAdapter.MyViewHolder vh = new MyAdapter.MyViewHolder(v);
            return vh;
        }


        // Replace the contents of a view (invoked by the layout manager)
        @Override
        public void onBindViewHolder(MyAdapter.MyViewHolder holder, int position) {
            // - get element from your dataset at this position
            // - replace the contents of the view with that element
            RatingEntry rating = mDataset.get(position);

            // Set item views based on your views and data model
            ImageView star_1 = holder.star_1;
            ImageView star_2 = holder.star_2;
            ImageView star_3 = holder.star_3;
            ImageView star_4 = holder.star_4;
            ImageView star_5 = holder.star_5;

            TextView ratingType = holder.ratingType;


            star_1.setImageResource(R.drawable.ic_twotone_star_rate_18px);
            star_2.setImageResource(R.drawable.ic_twotone_star_rate_18px);
            star_3.setImageResource(R.drawable.ic_twotone_star_rate_18px);
            star_4.setImageResource(R.drawable.ic_twotone_star_rate_18px);
            star_5.setImageResource(R.drawable.ic_twotone_star_rate_18px);

            ratingType.setText(rating.ratingType.toString());


        }

        // Return the size of your dataset (invoked by the layout manager)
        @Override
        public int getItemCount() {
            return mDataset.size();
        }
    }

}

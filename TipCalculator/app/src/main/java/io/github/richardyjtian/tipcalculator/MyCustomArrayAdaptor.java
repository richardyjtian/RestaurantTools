package io.github.richardyjtian.tipcalculator;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;


public class MyCustomArrayAdaptor extends ArrayAdapter<Restaurant> {
    // my new class variables, copies of constructor params, but add more if required
    private Context context;
    private ArrayList<Restaurant> theRestaurantArray;

    // constructor
    public MyCustomArrayAdaptor (Context _context, int textViewResourceId, ArrayList<Restaurant> _theRestaurantArray)
    {
        // call base class constructor
        super(_context, textViewResourceId, _theRestaurantArray);

        // save the context and the array of strings we were given
        context = _context;
        theRestaurantArray = _theRestaurantArray;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent)
    {
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE );
        View row = inflater.inflate (R.layout.row, parent, false );
        final int pos = position;

        final Restaurant restaurant = theRestaurantArray.get(position);
        final String rest_name = restaurant.getName();
        final int rest_rating = restaurant.getRating();

        // Set left icon
        ImageView restaurant_img = (ImageView) row.findViewById (R.id.restaurant_img);
        restaurant_img.setImageResource(R.drawable.restaurant);
        restaurant_img.setVisibility(View.VISIBLE);

        // Set text
        TextView label = (TextView) row.findViewById(R.id.restaurant_name);
        label.setText(rest_name);

        // Set delete button on click
        Button delete_button = (Button) row.findViewById(R.id.delete_button);
        delete_button.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                theRestaurantArray.remove(pos);
                notifyDataSetChanged();
                // Save to the save file
                FileIO.saveToFile(context, CustomActivity.myRestaurantArray);
            }
        });

        // Set star_1
        ImageView star_1 = (ImageView) row.findViewById (R.id.star_1);
        if(rest_rating >= 1)
            star_1.setImageResource(android.R.drawable.btn_star_big_on);
        else
            star_1.setImageResource(android.R.drawable.btn_star_big_off);
        star_1.setVisibility(View.VISIBLE);
        star_1.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                if(rest_rating == 1)
                    restaurant.setRating(0);
                else
                    restaurant.setRating(1);
                theRestaurantArray.set(pos, restaurant);
                notifyDataSetChanged();
                // Save to the save file
                FileIO.saveToFile(context, CustomActivity.myRestaurantArray);
            }
        });

        // Set star_2
        ImageView star_2 = (ImageView) row.findViewById (R.id.star_2);
        if(rest_rating >= 2)
            star_2.setImageResource(android.R.drawable.btn_star_big_on);
        else
            star_2.setImageResource(android.R.drawable.btn_star_big_off);
        star_2.setVisibility(View.VISIBLE);
        star_2.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                if(rest_rating == 2)
                    restaurant.setRating(1);
                else
                    restaurant.setRating(2);
                theRestaurantArray.set(pos, restaurant);
                notifyDataSetChanged();
                // Save to the save file
                FileIO.saveToFile(context, CustomActivity.myRestaurantArray);
            }
        });

        // Set star_3
        ImageView star_3 = (ImageView) row.findViewById (R.id.star_3);
        if(rest_rating >= 3)
            star_3.setImageResource(android.R.drawable.btn_star_big_on);
        else
            star_3.setImageResource(android.R.drawable.btn_star_big_off);
        star_3.setVisibility(View.VISIBLE);
        star_3.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                if(rest_rating == 3)
                    restaurant.setRating(2);
                else
                    restaurant.setRating(3);
                theRestaurantArray.set(pos, restaurant);
                notifyDataSetChanged();
                // Save to the save file
                FileIO.saveToFile(context, CustomActivity.myRestaurantArray);
            }
        });

        // Set star_4
        ImageView star_4 = (ImageView) row.findViewById (R.id.star_4);
        if(rest_rating >= 4)
            star_4.setImageResource(android.R.drawable.btn_star_big_on);
        else
            star_4.setImageResource(android.R.drawable.btn_star_big_off);
        star_4.setVisibility(View.VISIBLE);
        star_4.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                if(rest_rating == 4)
                    restaurant.setRating(3);
                else
                    restaurant.setRating(4);
                theRestaurantArray.set(pos, restaurant);
                notifyDataSetChanged();
                // Save to the save file
                FileIO.saveToFile(context, CustomActivity.myRestaurantArray);
            }
        });

        // Set star_5
        ImageView star_5 = (ImageView) row.findViewById (R.id.star_5);
        if(rest_rating >= 5)
            star_5.setImageResource(android.R.drawable.btn_star_big_on);
        else
            star_5.setImageResource(android.R.drawable.btn_star_big_off);
        star_5.setVisibility(View.VISIBLE);
        star_5.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                if(rest_rating == 5)
                    restaurant.setRating(4);
                else
                    restaurant.setRating(5);
                theRestaurantArray.set(pos, restaurant);
                notifyDataSetChanged();
                // Save to the save file
                FileIO.saveToFile(context, CustomActivity.myRestaurantArray);
            }
        });


        return row;
    }

}

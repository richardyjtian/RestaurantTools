package io.github.richardyjtian.tipcalculator;

import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.constraint.ConstraintLayout;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Collections;


public class RestaurantArrayAdaptor extends ArrayAdapter<Restaurant> {
    // my new class variables, copies of constructor params, but add more if required
    private Context context;
    private Activity activity;
    private ArrayList<Restaurant> theRestaurantArray;

    // constructor
    public RestaurantArrayAdaptor (Context _context, int textViewResourceId, ArrayList<Restaurant> _theRestaurantArray)
    {
        // call base class constructor
        super(_context, textViewResourceId, _theRestaurantArray);

        // save the context and the array of strings we were given
        context = _context;
        activity = (Activity) _context;
        theRestaurantArray = _theRestaurantArray;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent)
    {
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE );
        View row = inflater.inflate (R.layout.restaurant_list_row, parent, false );
        final int pos = position;
        final ViewGroup par = parent;

        final Restaurant restaurant = theRestaurantArray.get(position);
        String rest_name = restaurant.getName();
        String rest_loc = restaurant.getLocation();
        String rest_type = restaurant.getType();
        final int rest_rating = restaurant.getRating();
        String rest_note = restaurant.getNote();

        // Set layout on click
        ConstraintLayout layout = (ConstraintLayout) row.findViewById(R.id.layout);
        layout.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                // Start a new activity with the Restaurant
                Intent intent = new Intent(context, DishListActivity.class);
                intent.putExtra("Restaurant", restaurant);
                intent.putExtra("Position", pos);
                context.startActivity(intent);
            }
        });

        // Set left icon
//        ImageView restaurant_img = (ImageView) row.findViewById(R.id.l_rest_img);
//        restaurant_img.setImageResource(R.drawable.restaurant);
//        restaurant_img.setVisibility(View.VISIBLE);

        // Set name
        TextView name = (TextView) row.findViewById(R.id.l_rest_name);
        name.setText(rest_name);

        // Set location
        TextView location = (TextView) row.findViewById(R.id.l_rest_loc);
        location.setText(rest_loc);

        // Set type
        TextView type = (TextView) row.findViewById(R.id.l_rest_type);
        type.setText(rest_type);

        // Set note
        TextView note = (TextView) row.findViewById(R.id.l_rest_note);
        note.setText(rest_note);

        // Set delete img on click
        ImageView delete = row.findViewById(R.id.bin);
        delete.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                theRestaurantArray.remove(pos);
                notifyDataSetChanged();
                // Save to the save file
                FileIO.saveToFile(context, RestaurantListActivity.myRestaurantArray);
            }
        });

        // Set edit img on click
        ImageView edit = row.findViewById(R.id.edit);
        edit.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                // Start a dialogbox to edit restaurant

                // create a new AlertDialog Builder object
                AlertDialog.Builder builder = new AlertDialog.Builder(context);

                // set the message and the Title
                builder.setTitle("Edit Restaurant");

                // setup the dialog so that it cannot be cancelled by the back key (optional)
                builder.setCancelable(true);

                // We need a layout inflater to read our XML file and construct the layout
                LayoutInflater inflater = activity.getLayoutInflater();

                // Inflate the restaurant_dialogbox.xml layout file
                View theDialog = inflater.inflate (R.layout.restaurant_dialogbox, null);

                EditText name = theDialog.findViewById(R.id.d_rest_name);
                name.setText(restaurant.getName());
                EditText location = theDialog.findViewById(R.id.d_rest_loc);
                location.setText(restaurant.getLocation());
                EditText type = theDialog.findViewById(R.id.d_rest_type);
                type.setText(restaurant.getType());
                EditText rating = theDialog.findViewById(R.id.d_rest_rating);
                rating.setText(String.valueOf(restaurant.getRating()));
                EditText note = theDialog.findViewById(R.id.d_rest_note);
                note.setText(restaurant.getNote());

                // Set as dialog view
                builder.setView(theDialog);

                // Set up the buttons
                builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        AlertDialog theDialog = (AlertDialog)(dialog);
                        EditText name = theDialog.findViewById(R.id.d_rest_name);
                        EditText location = theDialog.findViewById(R.id.d_rest_loc);
                        EditText type = theDialog.findViewById(R.id.d_rest_type);
                        EditText rating = theDialog.findViewById(R.id.d_rest_rating);
                        EditText note = theDialog.findViewById(R.id.d_rest_note);

                        String r_name = name.getText().toString();
                        String r_rating_str = rating.getText().toString();
                        if(!r_name.isEmpty() && !r_rating_str.isEmpty()) {
                            String r_location = location.getText().toString();
                            String r_type = type.getText().toString();
                            int r_rating = Integer.parseInt(r_rating_str);
                            String r_note = note.getText().toString();

                            restaurant.setFields(r_name, r_location, r_type, r_rating, r_note);
                            Collections.sort(theRestaurantArray);

                            // notify the array adaptor that the array contents have changed (redraw)       
                            notifyDataSetChanged();

                            // Save to the save file
                            FileIO.saveToFile(context, RestaurantListActivity.myRestaurantArray);
                        }
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
                FileIO.saveToFile(context, RestaurantListActivity.myRestaurantArray);
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
                FileIO.saveToFile(context, RestaurantListActivity.myRestaurantArray);
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
                FileIO.saveToFile(context, RestaurantListActivity.myRestaurantArray);
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
                FileIO.saveToFile(context, RestaurantListActivity.myRestaurantArray);
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
                FileIO.saveToFile(context, RestaurantListActivity.myRestaurantArray);
            }
        });

        return row;
    }
}

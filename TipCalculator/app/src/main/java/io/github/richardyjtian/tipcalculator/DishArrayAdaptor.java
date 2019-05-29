package io.github.richardyjtian.tipcalculator;

import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
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


public class DishArrayAdaptor extends ArrayAdapter<Dish> {
    // my new class variables, copies of constructor params, but add more if required
    private Context context;
    private Activity activity;
    private ArrayList<Dish> theDishArray;

    // constructor
    public DishArrayAdaptor(Context _context, int textViewResourceId, ArrayList<Dish> _theDishArray)
    {
        // call base class constructor
        super(_context, textViewResourceId, _theDishArray);

        // save the context and the array of strings we were given
        context = _context;
        activity = (Activity) _context;
        theDishArray = _theDishArray;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent)
    {
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE );
        View row = inflater.inflate (R.layout.dish_list_row, parent, false );
        final int pos = position;

        final Dish dish = theDishArray.get(position);
        String dish_name = dish.getName();
        String dish_price = Double.toString(dish.getPrice());
        final int rest_rating = dish.getRating();
        String dish_note = dish.getNote();

        // Set name
        TextView name = (TextView) row.findViewById(R.id.l_dish_name);
        name.setText(dish_name);

        // Set price
        TextView price = (TextView) row.findViewById(R.id.l_dish_price);
        price.setText("$" + dish_price);

        // Set note
        TextView note = (TextView) row.findViewById(R.id.l_dish_note);
        note.setText(dish_note);

        // Set delete img on click
        ImageView delete = row.findViewById(R.id.bin);
        delete.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                theDishArray.remove(pos);
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
                builder.setTitle("Edit Dish");

                // setup the dialog so that it cannot be cancelled by the back key (optional)
                builder.setCancelable(true);

                // We need a layout inflater to read our XML file and construct the layout
                LayoutInflater inflater = activity.getLayoutInflater();

                // Inflate the restaurant_dialogbox.xml layout file
                View theDialog = inflater.inflate (R.layout.dish_dialogbox, null);

                EditText name = theDialog.findViewById(R.id.d_dish_name);
                name.setText(dish.getName());
                EditText price = theDialog.findViewById(R.id.d_dish_price);
                price.setText(String.valueOf(dish.getPrice()));
                EditText rating = theDialog.findViewById(R.id.d_dish_rating);
                rating.setText(String.valueOf(dish.getRating()));
                EditText note = theDialog.findViewById(R.id.d_dish_note);
                note.setText(dish.getNote());

                // Set as dialog view
                builder.setView(theDialog);

                // Set up the buttons
                builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        AlertDialog theDialog = (AlertDialog)(dialog);
                        EditText name = theDialog.findViewById(R.id.d_dish_name);
                        EditText price = theDialog.findViewById(R.id.d_dish_price);
                        EditText rating = theDialog.findViewById(R.id.d_dish_rating);
                        EditText note = theDialog.findViewById(R.id.d_dish_note);

                        String d_name = name.getText().toString();
                        String d_rating_str = rating.getText().toString();
                        if(!d_name.isEmpty() && !d_rating_str.isEmpty()) {
                            double d_price = Double.parseDouble(price.getText().toString());
                            int d_rating = Integer.parseInt(d_rating_str);
                            String d_note = note.getText().toString();

                            Dish dish = new Dish(d_name, d_price, d_rating, d_note);
                            Collections.sort(theDishArray);

                            notifyDataSetChanged();

                            // Save restaurant array to the save file
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
                    dish.setRating(0);
                else
                    dish.setRating(1);
                theDishArray.set(pos, dish);
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
                    dish.setRating(1);
                else
                    dish.setRating(2);
                theDishArray.set(pos, dish);
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
                    dish.setRating(2);
                else
                    dish.setRating(3);
                theDishArray.set(pos, dish);
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
                    dish.setRating(3);
                else
                    dish.setRating(4);
                theDishArray.set(pos, dish);
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
                    dish.setRating(4);
                else
                    dish.setRating(5);
                theDishArray.set(pos, dish);
                notifyDataSetChanged();
                // Save to the save file
                FileIO.saveToFile(context, RestaurantListActivity.myRestaurantArray);
            }
        });

        return row;
    }
}

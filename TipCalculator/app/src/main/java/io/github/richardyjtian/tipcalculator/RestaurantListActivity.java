package io.github.richardyjtian.tipcalculator;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.Collections;

public class RestaurantListActivity extends AppCompatActivity {

    // instance of our new custom array adaptor
    public static RestaurantArrayAdaptor myArrayAdapter;

    // dynamic array of Restaurants (populate at run time)
    public static ArrayList<Restaurant> myRestaurantArray = new ArrayList<Restaurant>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_restaurant_list);

        // Read from the save file
        myRestaurantArray = FileIO.readFromFile(this);

        // create the new adaptors passing important params, such
        // as context, android restaurant_list_row style and the array of strings to display
        myArrayAdapter = new RestaurantArrayAdaptor(this, android.R.layout.simple_list_item_1, myRestaurantArray);

        // get handles to the list view in the Custom Activity layout
        ListView myListView = (ListView) findViewById(R.id.rest_listView);

        // set the adaptor view
        myListView.setAdapter(myArrayAdapter);

        // Update the list with previously saved data
        myArrayAdapter.notifyDataSetChanged();
    }

    public void plusButtonClicked(View view){
        addRestaurantEntry(view);
    }


    AlertDialog alertDialog;
    public void addRestaurantEntry(View view){
        // create a new AlertDialog Builder object
        AlertDialog.Builder builder = new AlertDialog.Builder(this);

        // set the message and the Title
        builder.setTitle("Add Restaurant");

        // setup the dialog so that it cannot be cancelled by the back key (optional)
        builder.setCancelable(true);

        // We need a layout inflater to read our XML file and construct the layout
        LayoutInflater inflater = getLayoutInflater();

        // Inflate the restaurant_dialogbox.xml layout file
        View theDialog = inflater.inflate(R.layout.restaurant_dialogbox, null);

        // Focus keyboard on restaurant name
        EditText name = theDialog.findViewById(R.id.d_rest_name);
        name.requestFocus();
        showKeyboard();

        // On finish of typing note
        EditText note = theDialog.findViewById(R.id.d_rest_note);
        note.setOnKeyListener(new View.OnKeyListener() {
            public boolean onKey(View v, int keyCode, KeyEvent event) {
                // Done button pressed on keyboard
                if ((event.getAction() == KeyEvent.ACTION_DOWN) && (keyCode == KeyEvent.KEYCODE_ENTER)) {
                    createNewRestaurant();
                    closeKeyboard();
                    alertDialog.dismiss();
                    return true;
                }
                return false;
            }
        });

        // Set as dialog view
        builder.setView(theDialog);

        // Set up the buttons
        builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                createNewRestaurant();
                closeKeyboard();
            }
        });
        builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.cancel();
            }
        });
        alertDialog = builder.create();
        alertDialog.show();
    }

    private void createNewRestaurant() {
        EditText name = alertDialog.findViewById(R.id.d_rest_name);
        EditText location = alertDialog.findViewById(R.id.d_rest_loc);
        EditText type = alertDialog.findViewById(R.id.d_rest_type);
        EditText rating = alertDialog.findViewById(R.id.d_rest_rating);
        EditText note = alertDialog.findViewById(R.id.d_rest_note);

        String r_name = name.getText().toString();
        String r_rating_str = rating.getText().toString();
        if(!r_name.isEmpty() && !r_rating_str.isEmpty()) {
            String r_location = location.getText().toString();
            String r_type = type.getText().toString();
            int r_rating = Integer.parseInt(r_rating_str);
            String r_note = note.getText().toString();
            Restaurant restaurant = new Restaurant(r_name, r_location, r_type, r_rating, r_note);

            myRestaurantArray.add(restaurant);
            Collections.sort(myRestaurantArray);

            // notify the array adaptor that the array contents have changed (redraw)       
            myArrayAdapter.notifyDataSetChanged();

            // Save to the save file
            FileIO.saveToFile(getApplicationContext(), myRestaurantArray);

            // Start a new activity with the restaurant
            Intent intent = new Intent(RestaurantListActivity.this, DishListActivity.class);
            intent.putExtra("Position", myRestaurantArray.indexOf(restaurant));
            startActivity(intent);
        }
    }

    public void showKeyboard(){
        InputMethodManager inputMethodManager = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
        inputMethodManager.toggleSoftInput(InputMethodManager.SHOW_FORCED, 0);
    }

    public void closeKeyboard(){
        InputMethodManager inputMethodManager = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
        inputMethodManager.toggleSoftInput(InputMethodManager.HIDE_IMPLICIT_ONLY, 0);
    }
}


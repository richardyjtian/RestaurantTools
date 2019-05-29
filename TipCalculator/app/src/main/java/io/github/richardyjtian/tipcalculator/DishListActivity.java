package io.github.richardyjtian.tipcalculator;

import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.text.InputFilter;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.Collections;

public class DishListActivity extends AppCompatActivity {

    // instance of our new custom array adaptor
    private DishArrayAdaptor myArrayAdapter;

    // dynamic array of dishes (populate at run time)
    public static ArrayList<Dish> myDishArray = new ArrayList<>();

    // current restaurant's dishes
    private Restaurant restaurant;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dish_list);

        // Set back img on click
        ImageView back = (ImageView) findViewById(R.id.back);
        back.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                finish();
            }
        });

        int position = getIntent().getIntExtra("Position", 0);
        restaurant = RestaurantListActivity.myRestaurantArray.get(position);

        // get restaurant's dishes
        myDishArray = restaurant.getDishes();

        // create the new adaptors passing important params, such
        // as context, android restaurant_list_row style and the array of strings to display
        myArrayAdapter = new DishArrayAdaptor(this, android.R.layout.simple_list_item_1, myDishArray);

        // get handles to the list view in the Custom Activity layout
        ListView myListView = (ListView) findViewById(R.id.dish_listView);

        // set the adaptor view
        myListView.setAdapter(myArrayAdapter);

        // Update the list with previously saved data
        myArrayAdapter.notifyDataSetChanged();
    }

    public void plusButtonClicked(View view){
        addDishEntry(view);
    }

    AlertDialog alertDialog;
    public void addDishEntry(View view){
        // create a new AlertDialog Builder object
        AlertDialog.Builder builder = new AlertDialog.Builder(this);

        // set the message and the title
        builder.setTitle("Add Dish");

        // setup the dialog so that it cannot be cancelled by the back key (optional)
        builder.setCancelable(true);

        // We need a layout inflater to read our XML file and construct the layout
        LayoutInflater inflater = getLayoutInflater();

        // Inflate the dish_dialogbox.xml layout file
        View theDialog = inflater.inflate(R.layout.dish_dialogbox, null);

        // Focus keyboard on dish name
        EditText name = theDialog.findViewById(R.id.d_dish_name);
        name.requestFocus();
        showKeyboard();

        // Limit price to 2 decimal places
        EditText price = theDialog.findViewById(R.id.d_dish_price);
        price.setFilters(new InputFilter[] {new DecimalDigitsInputFilter(2)});

        // On finish of typing note
        EditText note = theDialog.findViewById(R.id.d_dish_note);
        note.setOnKeyListener(new View.OnKeyListener() {
            public boolean onKey(View v, int keyCode, KeyEvent event) {
                // Done button pressed on keyboard
                if ((event.getAction() == KeyEvent.ACTION_DOWN) && (keyCode == KeyEvent.KEYCODE_ENTER)) {
                    createNewDish();
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
                createNewDish();
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

    public void createNewDish() {
        EditText name = alertDialog.findViewById(R.id.d_dish_name);
        EditText price = alertDialog.findViewById(R.id.d_dish_price);
        EditText rating = alertDialog.findViewById(R.id.d_dish_rating);
        EditText note = alertDialog.findViewById(R.id.d_dish_note);

        String d_name = name.getText().toString();
        String d_rating_str = rating.getText().toString();
        if(!d_name.isEmpty() && !d_rating_str.isEmpty()) {
            double d_price = Double.parseDouble(price.getText().toString());
            int d_rating = Integer.parseInt(d_rating_str);
            String d_note = note.getText().toString();
            Dish dish = new Dish(d_name, d_price, d_rating, d_note);

            myDishArray.add(dish);
            Collections.sort(myDishArray);
            myArrayAdapter.notifyDataSetChanged();

            restaurant.setDishes(myDishArray);
            RestaurantListActivity.myArrayAdapter.notifyDataSetChanged();

            // Save restaurant array to the save file
            FileIO.saveToFile(getApplicationContext(), RestaurantListActivity.myRestaurantArray);
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

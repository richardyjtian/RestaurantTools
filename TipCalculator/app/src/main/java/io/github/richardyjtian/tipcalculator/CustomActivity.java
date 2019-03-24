package io.github.richardyjtian.tipcalculator;

import android.content.DialogInterface;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.InputType;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

public class CustomActivity extends AppCompatActivity {

    // instance of our new custom array adaptor
    private static MyCustomArrayAdaptor myArrayAdapter;

    // dynamic array of Restaurants (populate at run time)
    public static ArrayList<Restaurant> myRestaurantArray = new ArrayList<Restaurant>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_custom);

        // Read from the save file
        myRestaurantArray = FileIO.readFromFile(this);

        // create the new adaptors passing important params, such
        // as context, android row style and the array of strings to display
        myArrayAdapter = new MyCustomArrayAdaptor(this, android.R.layout.simple_list_item_1, myRestaurantArray);

        // get handles to the list view in the Custom Activity layout
        ListView myListView = (ListView) findViewById( R.id.listView2 );

        // set the adaptor view
        myListView.setAdapter(myArrayAdapter);

        // Update the list with previously saved data
        myArrayAdapter.notifyDataSetChanged();
    }

    public void plusButtonClicked(View view){
        addRestaurantEntry(view);
    }

    public void addRestaurantEntry(View view){
        // create a new AlertDialog Builder object
        AlertDialog.Builder builder = new AlertDialog.Builder(this);

        // set the message and the Title
        builder.setTitle("Add Entry");
        //builder.setMessage("Add Entry");

        // setup the dialog so that it cannot be cancelled by the back key (optional)
        builder.setCancelable(true);

        // We need a layout inflater to read our XML file and construct the layout
        LayoutInflater inflater = getLayoutInflater();

        // Inflate the dialogbox_view.xml layout file and set as dialog view
        builder.setView( inflater.inflate ( R.layout.dialogbox_view, null ));


        // Set up the buttons
        builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                AlertDialog theDialog = (AlertDialog)(dialog);
                EditText name = theDialog.findViewById(R.id.name);
                EditText rating = theDialog.findViewById(R.id.rating);
                if(!MainActivity.EditText_isEmpty(name) && !MainActivity.EditText_isEmpty(rating)) {
                    String r_name = name.getText().toString();
                    int r_rating = Integer.parseInt(rating.getText().toString());
                    Restaurant restaurant = new Restaurant(r_name, r_rating);
                    myRestaurantArray.add(restaurant);
                    // notify the array adaptor that the array contents have changed (redraw)       
                    myArrayAdapter.notifyDataSetChanged();

                    // Save to the save file
                    FileIO.saveToFile(getApplicationContext(), myRestaurantArray);
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
}

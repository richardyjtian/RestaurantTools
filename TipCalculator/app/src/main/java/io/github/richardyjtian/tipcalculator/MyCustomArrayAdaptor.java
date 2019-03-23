package io.github.richardyjtian.tipcalculator;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;


public class MyCustomArrayAdaptor extends ArrayAdapter<Restaurant> {
    // my new class variables, copies of constructor params, but add more if required
    private Context context;
    private ArrayList<Restaurant> theRestaurantArray;

    // Used to determine what star to show
    public final int numRows = 500 ;
    private boolean [] RowValidity = new boolean [numRows];

    // constructor
    public MyCustomArrayAdaptor (Context _context, int textViewResourceId, ArrayList<Restaurant> _theRestaurantArray)
    {
        // call base class constructor
        super(_context, textViewResourceId, _theRestaurantArray);

        // save the context and the array of strings we were given
        context = _context;
        theRestaurantArray = _theRestaurantArray;

        clearValidity();
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent)
    {
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE );
        View row = inflater.inflate (R.layout.row, parent, false );

        // Set left icon
        ImageView icon = (ImageView) row.findViewById (R.id.BTicon);
        icon.setImageResource(R.drawable.restaurant);
        icon.setVisibility(View.VISIBLE);

        // Set text
        TextView label = (TextView) row.findViewById( R.id.BTdeviceText);
        label.setText(theRestaurantArray.get(position).getName() + "\nRating: " + Integer.toString(theRestaurantArray.get(position).getRating()));

        // Set right icon
        icon = (ImageView) row.findViewById (R.id.Selected);
        if(RowValidity [position] == false)
            icon.setImageResource (android.R.drawable.btn_star_big_off);
        else
            icon.setImageResource (android.R.drawable.btn_star_big_on);
        icon.setVisibility (View.VISIBLE);

        return row;
    }

    public void changeValidity(int position) { RowValidity[position] = !RowValidity[position]; }
    public void clearValidity() {
        for(int i = 0; i < numRows; i++)
            RowValidity[i] = false;
    }

}

package io.github.richardyjtian.tipcalculator;

import android.content.Context;
import android.widget.Toast;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;

public class FileIO {
    private static String FileName = "savefile";

    public static void saveToFile(Context context, ArrayList<Restaurant> RestaurantArray) {
        // open/create the file
        try {
            FileOutputStream fos = context.openFileOutput(FileName, Context.MODE_PRIVATE);
            ObjectOutputStream os = new ObjectOutputStream(fos);

            // write/overwrite to the file
            os.writeObject(RestaurantArray);

            os.close();
            fos.close();

        } catch (FileNotFoundException e) {
//            Toast.makeText(context, "Error Save 1: " + FileName, Toast.LENGTH_LONG).show();
        } catch (IOException e) {
            e.printStackTrace();
            Toast.makeText(context, "Error Save 2: " + FileName, Toast.LENGTH_LONG).show();
        }
    }

    public static ArrayList<Restaurant> readFromFile(Context context) {
        ArrayList<Restaurant> RestaurantArray = new ArrayList<Restaurant>();

        // open the file for reading
        try {
            FileInputStream fis = context.openFileInput(FileName);
            ObjectInputStream is = new ObjectInputStream(fis);

            RestaurantArray = (ArrayList<Restaurant>) is.readObject();

            is.close();
            fis.close();

        } catch (FileNotFoundException e) {
//            Toast.makeText(context, "Error Read 1: " + FileName, Toast.LENGTH_LONG).show();
        } catch (IOException e) {
            e.printStackTrace();
            Toast.makeText(context, "Error Read 2: " + FileName, Toast.LENGTH_LONG).show();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
            Toast.makeText(context, "Error Read 3: " + FileName, Toast.LENGTH_LONG).show();
        }

        return RestaurantArray;
    }
}

package io.github.richardyjtian.tipcalculator;

import android.content.Context;
import android.widget.Toast;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;

public class FileIO {
    // Overrides the file
    public static void saveToPrivateFile(Context context, ArrayList<Restaurant> myRestaurantArray) {
        FileOutputStream fos;
        ObjectOutputStream os;

        String FileName = "savefile";

        // open/create the file
        try {
            fos = context.openFileOutput(FileName, Context.MODE_PRIVATE);
            os = new ObjectOutputStream(fos);

            // write to the file
            os.writeObject(myRestaurantArray);

            os.close();
            fos.close();
        } catch (FileNotFoundException e) {
            Toast.makeText(context, "Error File: " + FileName, Toast.LENGTH_LONG).show();
            return;
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static ArrayList<Restaurant> readFromPrivateFile(Context context) {
        FileInputStream fis;
        ObjectInputStream is;
        ArrayList<Restaurant> restaurants = new ArrayList<Restaurant>();

        String FileName = "savefile";

        // open the file for reading
        try {
            fis = context.openFileInput(FileName);
            is = new ObjectInputStream(fis);

            restaurants = (ArrayList<Restaurant>) is.readObject();

            is.close();
            fis.close();

        } catch (FileNotFoundException e) {
            Toast.makeText(context, "Error File: " + FileName, Toast.LENGTH_LONG).show();
            return null;
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }

        return restaurants;
    }
}
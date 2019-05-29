package io.github.richardyjtian.tipcalculator;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.Switch;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {
    static MainActivity MainActivityInstance; // Instance of MainActivity created upon opening of app

    EditText total_bill;
    EditText tip;
    EditText split_bill;
    TextView total_to_pay;
    TextView total_tip;
    TextView total_per_person;

    Switch custom_layout_switch;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        MainActivityInstance = this;

        total_bill = (EditText) findViewById(R.id.bill);
        tip = (EditText) findViewById(R.id.tip);
        split_bill = (EditText) findViewById(R.id.split_bill);
        total_to_pay = findViewById(R.id.total_to_pay);
        total_tip = findViewById(R.id.total_tip);
        total_per_person = findViewById(R.id.total_per_person);

        custom_layout_switch = findViewById(R.id.custom_layout_switch);
        custom_layout_switch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                startCustomActivity(isChecked);
            }
        });
        // Start the restaurantlistactivity immediately
        startCustomActivity(true);
    }

    public static MainActivity getInstance() {
        return MainActivityInstance;
    }

    public void startCustomActivity(boolean start) {
        if(start){
            Intent intent = new Intent(this, RestaurantListActivity.class);
            startActivity(intent);
        }
    }

    // Update the display with new information
    public void updateDisplay(View view) {
        if(!EditText_isEmpty(total_bill) && !EditText_isEmpty(tip)) {
            float total_bill_value = Float.valueOf(total_bill.getText().toString());
            float tip_value = Float.valueOf(tip.getText().toString());
            float total_to_pay_value = total_bill_value * (1+tip_value/100);
            total_to_pay.setText("$" + String.format("%.2f", total_to_pay_value));

            float total_tip_value = total_bill_value * (tip_value/100);
            total_tip.setText("$" + String.format("%.2f", total_tip_value));

            if(!EditText_isEmpty(split_bill)) {
                float split_bill_value = Float.valueOf(split_bill.getText().toString());
                float total_per_person_value = total_to_pay_value/split_bill_value;
                total_per_person.setText("$" + String.format("%.2f", total_per_person_value));
            }
        }
        else {
            total_to_pay.setText("");
            total_tip.setText("");
            total_per_person.setText("");
        }
    }

    public void tipMinusButtonClicked(View view){
        if(!EditText_isEmpty(tip)) {
            int tip_value = Integer.parseInt(tip.getText().toString());
            if(tip_value > 0)
                tip.setText(Integer.toString(tip_value-1));
        }
        updateDisplay(view);
    }
    public void tipPlusButtonClicked(View view){
        if(!EditText_isEmpty(tip)) {
            int tip_value = Integer.parseInt(tip.getText().toString());
            tip.setText(Integer.toString(tip_value+1));
        }
        updateDisplay(view);
    }
    public void splitBillMinusButtonClicked(View view){
        if(!EditText_isEmpty(split_bill)) {
            int split_bill_value = Integer.parseInt(split_bill.getText().toString());
            if(split_bill_value > 1)
                split_bill.setText(Integer.toString(split_bill_value-1));
        }
        updateDisplay(view);
    }
    public void splitBillPlusButtonClicked(View view){
        if(!EditText_isEmpty(split_bill)) {
            int split_bill_value = Integer.parseInt(split_bill.getText().toString());
            split_bill.setText(Integer.toString(split_bill_value+1));
        }
        updateDisplay(view);
    }

    // Clear the display
    public void clear(){
        total_bill.setText("");
        tip.setText("");
        split_bill.setText("");
        updateDisplay(findViewById(android.R.id.content));
    }


    public static boolean EditText_isEmpty(EditText etText) {
        if(etText.getText().toString().trim().length() > 0)
            return false;
        return true;
    }
}

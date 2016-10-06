package com.example.coffeordering;

import android.content.Intent;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.text.NumberFormat;
import java.util.Locale;

public class  MainActivity extends AppCompatActivity {
    static final String STATE_QUANTITY = "quantity";
    private int quantity = 1;

    private static final double pricePerCoffee = 1.5;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    @Override
    public void onSaveInstanceState(Bundle savedInstanceState) {
        // Save the app state
        savedInstanceState.putInt(STATE_QUANTITY, quantity);

        // Always call the superclass so it can save the view hierarchy state
        super.onSaveInstanceState(savedInstanceState);
    }

    public void onRestoreInstanceState(Bundle savedInstanceState) {
        // Always call the superclass so it can restore the view hierarchy
        super.onRestoreInstanceState(savedInstanceState);

        // Restore state members from saved instance
        quantity = savedInstanceState.getInt(STATE_QUANTITY);
        updateScreen();
    }
    //*/

    public void order(View view) {
        Intent intent = new Intent(Intent.ACTION_SENDTO);
        intent.setData(Uri.parse("mailto:")); // only email apps should handle this
        intent.putExtra(Intent.EXTRA_EMAIL, new String[]{"greg@csd.auth.gr"});
        intent.putExtra(Intent.EXTRA_SUBJECT, "new order");
        EditText et = (EditText) findViewById(R.id.name);
        intent.putExtra(Intent.EXTRA_TEXT, et.getText());
        if (intent.resolveActivity(getPackageManager()) != null) {
            startActivity(intent);
        }

    }

    public void increase(View view) {
        quantity++;
        updateScreen();
    }

    public void decrease(View view) {
        if (quantity > 1) {
            quantity--;
            updateScreen();
        } else {
            Toast.makeText(this, "cannot have less that 1 coffee", Toast.LENGTH_SHORT).show();
        }
    }

    public void chocolate(View view) {
        updateScreen();
    }

    public void cream(View view) {
        updateScreen();
    }

    private void updateScreen() {
        double extras = 0;
        CheckBox cb;
        cb = (CheckBox) findViewById(R.id.cbCream);
        if (cb.isChecked()) {
            extras += 0.5;
        }
        cb = (CheckBox) findViewById(R.id.cbChocolate);
        if (cb.isChecked()) {
            extras += 0.3;
        }
        double price = quantity*(pricePerCoffee+extras);
        TextView tv = (TextView) findViewById(R.id.quantityValue);
        tv.setText(String.valueOf(quantity));
        TextView pv = (TextView) findViewById(R.id.price);
        pv.setText(NumberFormat.getCurrencyInstance().format(price));
    }
}

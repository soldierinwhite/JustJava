/**
 * Add your package below. Package name can be found in the project's AndroidManifest.xml file.
 * This is the package name our example uses:
 * <p>
 * package com.example.android.justjava;
 */
package com.example.android.justjava;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.text.NumberFormat;

/**
 * This app displays an order form to order coffee.
 */
public class MainActivity extends AppCompatActivity {

    private int numberOfCoffees = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }


    public void decrement(View view) {
        if (numberOfCoffees == 1) {
            Toast.makeText(this, "At least 1 coffee, thank you!", Toast.LENGTH_SHORT).show();
            return;
        }
        numberOfCoffees--;
        display(numberOfCoffees);
    }

    public void increment(View view) {
        if (numberOfCoffees == 100) {
            Toast.makeText(this, "No more than 100 orders, thank you!", Toast.LENGTH_SHORT).show();
            return;
        }

        numberOfCoffees++;
        display(numberOfCoffees);
    }

    /**
     * This method is called when the order button is clicked.
     */
    public void submitOrder(View view) {
        CheckBox whippedCream = (CheckBox) findViewById(R.id.whipped_cream_checkbox);
        CheckBox chocolate = (CheckBox) findViewById(R.id.chocolate_checkbox);
        EditText name = (EditText) findViewById(R.id.name_edittext);
        boolean hasWhippedCream = whippedCream.isChecked();
        boolean hasChocolate = chocolate.isChecked();
        int total = calculatePrice(hasWhippedCream, hasChocolate);
        String clientName = name.getText().toString();
        String message = createOrderMessage(clientName, total, hasWhippedCream, hasChocolate);
        composeEmail(message, getString(R.string.order_summary_email_subject, clientName) + clientName);
    }

    private int calculatePrice(boolean hasCream, boolean hasChocolate) {
        int pricePerCup = 5;
        if (hasCream) {
            pricePerCup++;
        }
        if (hasChocolate) {
            pricePerCup += 2;
        }
        return pricePerCup * numberOfCoffees;
    }

    private String createOrderMessage(String name, int priceValue, boolean hasCream, boolean hasChoc) {
        return getString(R.string.order_summary_name, name)
                + "\n" + getString(R.string.order_summary_whipped_cream, hasCream)
                + "\n" + getString(R.string.order_summary_chocolate, hasChoc)
                + "\n" + getString(R.string.order_summary_quantity, numberOfCoffees)
                + "\n" + getString(R.string.order_summary_price,
                NumberFormat.getCurrencyInstance().format(priceValue))
                + "\n" + getString(R.string.thank__you);
    }

    /**
     * This method displays the given quantity value on the screen.
     */
    private void display(int number) {
        TextView quantityTextView = (TextView) findViewById(R.id.quantity_text_view);
        quantityTextView.setText(String.valueOf(number));
    }

    public void composeEmail(String text, String subject) {
        Intent intent = new Intent(Intent.ACTION_SEND);
        intent.setType("*/*");
        intent.putExtra(Intent.EXTRA_TEXT, text);
        intent.putExtra(Intent.EXTRA_SUBJECT, subject);
        if (intent.resolveActivity(getPackageManager()) != null) {
            startActivity(intent);
        }
    }

}

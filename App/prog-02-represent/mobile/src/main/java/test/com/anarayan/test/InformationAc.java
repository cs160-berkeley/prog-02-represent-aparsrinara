package test.com.anarayan.test;


import android.content.Context;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.content.Intent;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Button;
import android.graphics.Color;
import android.widget.TextView;
import android.widget.Toast;

import org.w3c.dom.Text;


public class InformationAc extends AppCompatActivity {

    public TextView moreDetails1;
    public TextView moreDetails2;
    public TextView moreDetails3;
    public TextView back;
    public TextView locationDisplay;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.information);
        Intent intent = getIntent();

        back = (TextView) findViewById(R.id.textView);
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                goBack(v);
            }
        });

        locationDisplay = (TextView) findViewById(R.id.loc);

        String text = intent.getExtras().getString("test.com.anarayan.test.loc");
        String n = intent.getExtras().getString("test.com.anarayan.test.shake");
        if (n != null) {
            locationDisplay.setText(n);
            //Context context = getApplicationContext();
            //int duration = Toast.LENGTH_SHORT;
            //Toast toast = Toast.makeText(getApplicationContext(), "Random zipcode initiated: " + n, duration);
            //toast.show();
        }
        else {
            locationDisplay.setText(text);
        }


        moreDetails1 = (TextView) findViewById(R.id.button);
        moreDetails2 = (TextView) findViewById(R.id.button2);
        moreDetails3 = (TextView) findViewById(R.id.button3);
        moreDetails1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sendMessage(v);
            }
        });
        moreDetails2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sendMessage(v);
            }
        });
        moreDetails3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sendMessage(v);
            }
        });
    }

    /** Called when user presses back arrow */
    public void goBack(View view) {
        Intent intent = new Intent(this, InputActivity.class);
        startActivity(intent);
    }

    /** Called when the user clicks the Go button */
    public void sendMessage(View view) {
        // Do something in response to button
        Intent intent = new Intent(this, Details.class);
        intent.putExtra("test.com.anarayan.test.loc", locationDisplay.getText().toString());
        startActivity(intent);
    }
}

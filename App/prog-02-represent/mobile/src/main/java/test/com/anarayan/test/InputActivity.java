package test.com.anarayan.test;


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


public class InputActivity extends AppCompatActivity {

    public Spinner location;
    public EditText zipcode;
    public Button go;
    public TextView back;

    class MyOnItemSelectedListener implements AdapterView.OnItemSelectedListener {
        @Override
        public void onItemSelected(AdapterView parent, View view, int position, long id) {
            // On selecting a spinner item
            String locationString = location.getSelectedItem().toString();
            if (locationString.equals("Current Location")) {
                sendMessage(view);
            }
            if (locationString.equals("Zipcode")) {
                zipcode.setVisibility(View.VISIBLE);
                go.setVisibility(View.VISIBLE);
            }

        }

        @Override
        public void onNothingSelected(AdapterView arg0) {
            // TODO Auto-generated method stub
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.input);

        zipcode = (EditText) findViewById(R.id.editText);
        zipcode.setVisibility(View.GONE);
        location = (Spinner) findViewById(R.id.spinner);
        location.setOnItemSelectedListener(new MyOnItemSelectedListener());
        back = (TextView) findViewById(R.id.textView);
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                goBack(v);
            }
        });

        go = (Button) findViewById(R.id.button);
        go.setVisibility(View.GONE);
        go.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                boolean b = false;
                if (location.getSelectedItem().toString().equals("Select a Location")) {
                    TextView errorText = (TextView) location.getSelectedView();
                    errorText.setTextColor(Color.RED);//just to highlight that this is an error
                    errorText.setText("Oops! Field is Empty!");//changes the selected item text to this
                    b = true;
                }
                if (location.getSelectedItem().toString().equals("Zipcode") && zipcode.getText().toString().length() == 0) {
                    zipcode.setError("Oops! Field is Empty!");
                    b = true;
                }
                else {
                    if (!b)
                        sendMessage(v);
                }
            }
        });
    }

    /** Called when user presses back arrow */
    public void goBack(View view) {
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
    }

    /** Called when the user clicks the Go button */
    public void sendMessage(View view) {
        // Do something in response to button
        Intent intent = new Intent(this, InformationAc.class);
        Intent intent2 = new Intent(this, PhoneToWatchService.class);
        String loc = "";
        if (location.getSelectedItem().toString().equals("Current Location")) {
            loc = "Berkeley, CA 94704";
        }
        else {
            loc = zipcode.getText().toString();
        }
        intent.putExtra("test.com.anarayan.test.loc", loc);
        intent2.putExtra("test.com.anarayan.test.name", "Senator Loni Hancock");
        intent2.putExtra("test.com.anarayan.test.party", "Democratic Party");
        intent2.putExtra("test.com.anarayan.test.loc", loc);
        startActivity(intent);
        startService(intent2);
    }
}

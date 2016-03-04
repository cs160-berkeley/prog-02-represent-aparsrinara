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


public class MainActivity extends AppCompatActivity {


    public TextView go;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        go = (TextView) findViewById(R.id.textView3);
        go.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sendMessage(v);
            }
        });
    }

    /** Called when the user clicks the Go button */
    public void sendMessage(View view) {
        // Do something in response to button
        Intent intent = new Intent(this, InputActivity.class);
        startActivity(intent);
    }
}

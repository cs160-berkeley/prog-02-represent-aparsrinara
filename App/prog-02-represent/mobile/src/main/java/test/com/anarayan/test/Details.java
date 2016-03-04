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

import org.w3c.dom.Text;


public class Details extends AppCompatActivity {

    public TextView back;
    String text;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.moredetails);
        Intent intent = getIntent();
        text = intent.getStringExtra("test.com.anarayan.test.loc");

        back = (TextView) findViewById(R.id.textView);
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                goBack(v);
            }
        });
    }

    /** Called when user presses back arrow */
    public void goBack(View view) {
        Intent intent = new Intent(this, InformationAc.class);
        intent.putExtra("test.com.anarayan.test.loc", text);
        startActivity(intent);
    }
}

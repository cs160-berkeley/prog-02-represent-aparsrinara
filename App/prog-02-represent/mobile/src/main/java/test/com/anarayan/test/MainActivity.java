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
import com.twitter.sdk.android.Twitter;
import com.twitter.sdk.android.core.TwitterAuthConfig;

import java.io.IOException;
import java.io.InputStream;

import io.fabric.sdk.android.Fabric;


public class MainActivity extends AppCompatActivity {

    // Note: Your consumer key and secret should be obfuscated in your source code before shipping.
    private static final String TWITTER_KEY = "fihex75INpYAmlcyaIzrcA491";
    private static final String TWITTER_SECRET = "m8wU7xI18i1RUXbHcpGshRVAQkJAMIDxpk1M6WcKktkVOf5LxK";


    public TextView go;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        TwitterAuthConfig authConfig = new TwitterAuthConfig(TWITTER_KEY, TWITTER_SECRET);
        Fabric.with(this, new Twitter(authConfig));
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

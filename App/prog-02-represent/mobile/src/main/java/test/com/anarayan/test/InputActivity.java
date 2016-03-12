package test.com.anarayan.test;


import android.Manifest;
import android.content.Context;
import android.content.pm.PackageManager;
import android.location.Location;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.content.Intent;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.Spinner;
import android.widget.Button;
import android.graphics.Color;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.ResultCallback;
import com.google.android.gms.common.api.Status;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.location.LocationListener;
import com.google.android.gms.wearable.Wearable;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.twitter.sdk.android.core.Callback;
import com.twitter.sdk.android.core.Result;
import com.twitter.sdk.android.core.TwitterApiClient;
import com.twitter.sdk.android.core.TwitterCore;
import com.twitter.sdk.android.core.TwitterException;
import com.twitter.sdk.android.core.TwitterSession;
import com.twitter.sdk.android.core.identity.TwitterLoginButton;
import com.twitter.sdk.android.core.models.Tweet;
import com.twitter.sdk.android.core.services.StatusesService;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.lang.reflect.Array;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;


public class InputActivity extends AppCompatActivity implements

        GoogleApiClient.ConnectionCallbacks,
        GoogleApiClient.OnConnectionFailedListener,
        LocationListener {

    public Spinner location;
    public EditText zipcode;
    public Button go;
    public TextView back;
    public ArrayList<Message> messages = new ArrayList<Message>();
    public int count;
    public int in = 0;
    private TwitterLoginButton loginButton;
    public String county;
    public String state;
    public String usualzip = "94704";
    public double LAT;
    public double LONG;
    String API_URL ="http://congress.api.sunlightfoundation.com/legislators/locate?";
    String API_KEY = "3d4edffb86e547eabb5402be5f52e315";
//    String GEO_URL = "https://maps.googleapis.com/maps/api/geocode/json?";
//    String GEO_KEY = "AIzaSyB2Wp_6QOUFGe9hXHVjuJVLYdzOZo9bMVU";

    String GEO_URL = "https://maps.googleapis.com/maps/api/geocode/json?";
    String GEO_KEY = "AIzaSyBrLfnEsTZzo5iy9U3r9dbrrtbCQ0DNM_E";
    public String lat_random;
    public String long_random;
    public String lolo;

    private GoogleApiClient mGoogleApiClient;
    public static String TAG = "GPSActivity";
    public static int UPDATE_INTERVAL_MS = 2000;
    public static int FASTEST_INTERVAL_MS = 1000;
    public boolean isZipCode = false;
    public boolean isRandom = false;


    private class Message {
        String name;
        String party;
        String memberId;
        String endOfDate;
        private Message (int index, String name, String party, String id, String endOfDate) {
            this.name = name;
            this.party = party;
            this.memberId = id;
            this.endOfDate = endOfDate;
        }
    }

    private class DownloadWebpageTask extends AsyncTask<String, Void, String> {
        @Override
        protected String doInBackground(String... urls) {
            StringBuilder s = new StringBuilder();
            // params comes from the execute() call: params[0] is the url.
            try {
                s.append(downloadUrl(urls[0]));
                String second_url;

                if (isZipCode == true){
                    second_url = GEO_URL + "&address=" + usualzip + "&key=" + GEO_KEY;
                }
                else{
                    if (isRandom)
                        second_url = GEO_URL + "latlng=" + lat_random + "," + long_random + "&key=" + GEO_KEY;
                    else
                        second_url = GEO_URL + "latlng=" + String.valueOf(LAT) + "," + String.valueOf(LONG) + "&key=" + GEO_KEY;
                    Log.d("T", "SECOND URL: " + second_url);
                }
                try{
                    String results = downloadUrl(second_url);
                    JsonElement jObject = new JsonParser().parse(results);
                    JsonObject j = jObject.getAsJsonObject();
                    JsonArray arr= j.get("results").getAsJsonArray();
                    JsonObject t = arr.get(0).getAsJsonObject();
                    JsonArray several = t.get("address_components").getAsJsonArray();
                    for (int q = 0; q < several.size(); q++) {
                        JsonObject temp = several.get(q).getAsJsonObject();
                        String strr = temp.get("types").getAsJsonArray().get(0).toString();
                        if (strr.toString().equals("\"administrative_area_level_2\"")) {
                            county = temp.get("long_name").getAsString();
                            Log.d("T", "COUNTY IS: " + county);
                        }
                        if (strr.toString().equals("\"administrative_area_level_1\"")) {
                            state = temp.get("short_name").getAsString();
                            Log.d("T", "STATE IS: " + state);
                        }
                    }

                } catch(Exception e){
                    Log.d("T", "Exception!");
                }
            } catch (IOException e) {
                return "Unable to retrieve web page. URL may be invalid.";
            }
            return s.toString();
        }

        // onPostExecute displays the results of the AsyncTask.
        @Override
        protected void onPostExecute(String result) {
//            try {
            Log.d("T", "STRING RESULT IS: " + result);
            //InputStream stream = new ByteArrayInputStream(result.getBytes(StandardCharsets.UTF_8));
            try {
                JSONObject jObject = new JSONObject(result);
                Iterator<?> keys = jObject.keys();
                Log.d("T", "RIGHT HERE");
                count = jObject.getInt("count");
                String[] names = new String[count];
                String[] parties = new String[count];
                String[] memberIds = new String[count];
                String[] endOfDates = new String[count];
                String[] websites = new String[count];
                String[] emails = new String[count];
                String[] twitters = new String[count];
                while( keys.hasNext() ) {
                    String key = (String) keys.next();
                    Log.d("T", "RIGHT HERE2: " + key);
                    if ( key.equals("results")) {
                        Log.d("T", "IT THINKS IT IS THE KEY: " + key);

                        Log.d("T", "DID I MAKE IT OVER HERE");
                        JSONArray j = jObject.getJSONArray(key);
                        for (int i = 0; i < count; i++) {
                            in = i;
                            String title = "";
                            String party = "";
                            Log.d("T", "DID I MAKE IT OVER HERE2");
                            JSONObject objects = j.getJSONObject(i);
                            Log.d("T", "DID I MAKE IT OVER HERE3");
                            if (objects.getString("title").equals("Sen")) {
                                title = "Senator";
                            }
                            else {
                                title = "Rep.";
                            }
                            String endOfDate = objects.getString("term_end");
                            String name = title + " " + objects.getString("first_name") + " " + objects.getString("last_name");
                            Log.d("T", "DID I MAKE IT OVER HERE4");
                            if (objects.getString("party").equals("D")) {
                                party = "Democratic Party";
                            }
                            else if (objects.getString("party").equals("R")) {
                                party = "Republican Party";
                            }
                            else {
                                party = objects.getString("party");
                            }
                            Log.d("T", "DID I GET HERE5");
                            String email = "Email: " + objects.getString("oc_email");
                            String website = "Website: " + objects.getString("website");
                            String memberId = objects.getString("bioguide_id");
                            String twitter_id = objects.getString("twitter_id");
                            Log.d("T", "DID I GET HERE6");
                            names[i] = name;
                            parties[i] = party;
                            memberIds[i] = memberId;
                            endOfDates[i] = endOfDate;
                            websites[i] = website;
                            emails[i] = email;
                            twitters[i] = twitter_id;
                        }
                    }

                }
                Intent intent = new Intent(InputActivity.this, InformationAc.class);
                Intent intent2 = new Intent(InputActivity.this, PhoneToWatchService.class);
                String loc = " ";
                if (!isRandom) {
                    if (location.getSelectedItem().toString().equals("Current Location")) {
                        loc = "Current Location";
                    } else {
                        loc = zipcode.getText().toString();
                    }
                    intent.putExtra("test.com.anarayan.test.loc", loc);
                }
                else {
                    lolo = county + ", " + state;
                    intent.putExtra("test.com.anarayan.test.loc", lolo);
                }

                intent.putExtra("test.com.anarayan.test.name", names);
                intent.putExtra("test.com.anarayan.test.party", parties);
                intent.putExtra("test.com.anarayan.test.memberid", memberIds);
                intent.putExtra("test.com.anarayan.test.endofdate", endOfDates);
                intent.putExtra("test.com.anarayan.test.email", emails);
                intent.putExtra("test.com.anarayan.test.website", websites);
                intent.putExtra("test.com.anarayan.test.twitter", twitters);


                String s = loadJSONFromAsset();
                StringBuilder str = new StringBuilder();
                try {
                    JSONObject jObject2 = new JSONObject(s);
                    Iterator<?> keys2 = jObject2.keys();
                    while (keys2.hasNext()) {
                        String key = (String) keys2.next();
                        Log.d("T", "RIGHT HERE2: " + key);
                        String[] kys = key.split(", ");
                        if (kys[0].equals(county) && kys[1].equals(state)) {
                            str.append(kys[0] + ";" + kys[1] + ";");
                            JSONObject val = jObject2.getJSONObject(key);
                            str.append(val.getString("romney") +"% for Romney"+ ";" + val.getString("obama") +"% for Obama");
                            break;
                        }
                    }
                } catch (JSONException e) {
                    Log.d("T", "JSON exception");
                }


                intent2.putExtra("test.com.anarayan.test.name", names);
                intent2.putExtra("test.com.anarayan.test.party", parties);
                intent2.putExtra("test.com.anarayan.test.memberid", memberIds);
                intent2.putExtra("test.com.anarayan.test.endofdate", endOfDates);
                if (isRandom) {
                    intent2.putExtra("random", "random");
                }
                Log.d("T", "STRING IS FOR RANDOM: " + str.toString() + "BLAH");
                intent2.putExtra("test.com.anarayan.test.presview", str.toString());
                startActivity(intent);
                startService(intent2);


            }
            catch (JSONException e) {
                Log.d("T", "JSONException found and caught");
            }

        }





        private String downloadUrl(String myurl) throws IOException {
            // Do some validation here

            try {
                URL url = new URL(myurl);
                HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection();
                try {
                    BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(urlConnection.getInputStream()));
                    StringBuilder stringBuilder = new StringBuilder();
                    String line;
//                    ImageView pic = (ImageView) findViewById(R.id.picture);
//                    String urldisplay = urlImage;
//                    try {
//                        InputStream in = urlConnection.getInputStream();
//                        mIcon11 = BitmapFactory.decodeStream(in);
//                    } catch (Exception e) {
//                        Log.e("Error", "Cannot put in image");
//                        e.printStackTrace();
//                    }
                    while ((line = bufferedReader.readLine()) != null) {
                        stringBuilder.append(line).append("\n");
                    }
                    bufferedReader.close();
                    Log.d("T", "PLEASE WORK" + stringBuilder.toString());
                    return stringBuilder.toString();
                }
                finally{
                    urlConnection.disconnect();
                }
            }
            catch(Exception e) {
                Log.e("ERROR", e.getMessage(), e);
                return null;
            }
        }
    }

    class MyOnItemSelectedListener implements AdapterView.OnItemSelectedListener {
        @Override
        public void onItemSelected(AdapterView parent, View view, int position, long id) {
            // On selecting a spinner item
            String locationString = location.getSelectedItem().toString();
            if (locationString.equals("Current Location")) {
                forLoc(view);
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
    public void onLocationChanged(Location location) {
        // Do some work here with the location you have received
        LAT = location.getLatitude();
        LONG = location.getLongitude();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        Intent intent = getIntent();
        mGoogleApiClient = new GoogleApiClient.Builder(this)
                .addApi(LocationServices.API)
                .addApi(Wearable.API) //used for data layer API
                .addConnectionCallbacks(this)
                .addOnConnectionFailedListener(this)
                .build();
        super.onCreate(savedInstanceState);
        if (intent.getStringExtra("random") == null) {
            setContentView(R.layout.input);
            loginButton = (TwitterLoginButton) findViewById(R.id.twitter_login_button);
            loginButton.setCallback(new Callback<TwitterSession>() {
                @Override
                public void success(Result<TwitterSession> result) {
                    // The TwitterSession is also available through:
                    // Twitter.getInstance().core.getSessionManager().getActiveSession()
                    TwitterSession session = result.data;
                    // TODO: Remove toast and use the TwitterSession's userID
                    // with your app's user model

                    String msg = "@" + session.getUserName() + " logged in! (#" + session.getUserId() + ")";
                }

                @Override
                public void failure(TwitterException exception) {
                    Log.d("TwitterKit", "Login with Twitter failure", exception);
                }
            });

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
                    } else {
                        if (!b) {
                            forZip(v);
                        }
                    }
                }
            });
        }



        if (intent.getStringExtra("random") != null) {
            String s = intent.getStringExtra("random");
            String[] arr = s.split("/");
            lat_random = arr[0];
            long_random = arr[1];
            isRandom = true;
            String urlAry = API_URL + "latitude=" + arr[0] + "&longitude=" + arr[1]+ "&apikey=" + API_KEY;
            new DownloadWebpageTask().execute(urlAry);
        }



    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        // Make sure that the loginButton hears the result from any
        // Activity that it triggered.
        loginButton.onActivityResult(requestCode, resultCode, data);
    }

    @Override
    protected void onStart() {
        mGoogleApiClient.connect();
        super.onStart();
    }

    @Override
    protected void onStop() {
        mGoogleApiClient.disconnect();
        super.onStop();
    }

    @Override
    protected void onResume() {
        super.onResume();
        mGoogleApiClient.connect();
    }

    @Override
    protected void onPause() {
        super.onPause();
        mGoogleApiClient.disconnect();
    }

    @Override
    public void onConnected(Bundle bundle) {
        LocationRequest locationRequest = LocationRequest.create()
                .setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY)
                .setInterval(UPDATE_INTERVAL_MS)
                .setFastestInterval(FASTEST_INTERVAL_MS);

        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED
                && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            return;
        }
        LocationServices.FusedLocationApi
                .requestLocationUpdates(mGoogleApiClient, locationRequest, this)
                .setResultCallback(new ResultCallback<Status>() {

                    @Override
                    public void onResult(Status status) {
                        if (status.getStatus().isSuccess()) {
                            Log.d(TAG, "Successfully requested");
                        } else {
                            Log.e(TAG, status.getStatusMessage());
                        }
                    }
                });
    }

    @Override
    public void onConnectionSuspended(int i) {}

    @Override
    public void onConnectionFailed(ConnectionResult connResult) {}
    /** Called when user presses back arrow */

    public void goBack(View view) {
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
    }


    public void forZip(View view){

        EditText zipText = (EditText) findViewById(R.id.editText);
        usualzip = zipText.getText().toString();
        isZipCode = true;

        ConnectivityManager connMgr = (ConnectivityManager)
                getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = connMgr.getActiveNetworkInfo();
        if (networkInfo != null && networkInfo.isConnected()) {
            Log.d("T", "IN DOWNLOADWEBPAGETASK");
            String[] urlAry = new String[1];
            urlAry[0] = API_URL + "zip=" + usualzip + "&apikey=" + API_KEY;
            Log.d("T", "URL IS ZIP:  " + urlAry[0]);
            new DownloadWebpageTask().execute(urlAry);
        } else {
            Log.d("No","NETWORK CONNECTION AVAILABLE");
        }
    }

    public void forLoc(View view){

        ConnectivityManager connMgr = (ConnectivityManager)
                getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = connMgr.getActiveNetworkInfo();
        if (networkInfo != null && networkInfo.isConnected()) {
            Log.d("T", "IN DOWNLOADWEBPAGETASK");

            String[] urlAry = new String[1];

            urlAry[0] = API_URL + "latitude=" + String.valueOf(LAT) + "&longitude=" + String.valueOf(LONG)+ "&apikey=" + API_KEY;
            Log.d("T", "URL IN LOCATION: " + urlAry[0]);

            new DownloadWebpageTask().execute(urlAry);
        } else {
            Log.d("No","NETWORK CONNECTION AVAILABLE");
        }

    }


    public String loadJSONFromAsset() {
        String json = null;
        try {

            InputStream is = getAssets().open("election_results_2012.json");

            int size = is.available();

            byte[] buffer = new byte[size];

            is.read(buffer);

            is.close();

            json = new String(buffer, "UTF-8");


        } catch (IOException ex) {
            ex.printStackTrace();
            return null;
        }
        return json;

    }


}

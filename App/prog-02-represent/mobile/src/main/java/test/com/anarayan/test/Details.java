package test.com.anarayan.test;


import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.media.Image;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Base64;
import android.util.Log;
import android.view.View;
import android.content.Intent;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.Button;
import android.graphics.Color;
import android.widget.TextView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.w3c.dom.Text;

import java.io.BufferedReader;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.Iterator;


public class Details extends AppCompatActivity {

    public TextView back;
    String text;
    String[] images;
    String[] twitterQuotes;
    public String name;
    public String party;
    public String endOfDate;
    public String urlImage;
    public Bitmap mIcon11;
    //String myurl = "http://congress.api.sunlightfoundation.com/legislators/locate?zip=94704&apikey=3d4edffb86e547eabb5402be5f52e315";

    private class Message {
        String name;
        String party;
        String title;
        private Message (String name, String party, String title) {
            this.name = name;
            this.party = party;
            this.title = title;
        }
    }

    private class DownloadWebpageTask extends AsyncTask<String, Void, String> {

        @Override
        protected String doInBackground(String[] urls) {

            // params comes from the execute() call: params[0] is the url.
            try {
                StringBuilder join = new StringBuilder();
                for (int i = 0; i < urls.length; i++) {
                    join.append(downloadUrl(urls[i], i));
                }
                return join.toString();

            } catch (IOException e) {
                return "Unable to retrieve web page. URL may be invalid.";
            }
        }
        // onPostExecute displays the results of the AsyncTask.
        @Override
        protected void onPostExecute(String result) {
//            try {
            Log.d("T", "STRING RESULT IS: " + result);
            String[] twoJson = result.split("\n");
            for (int i = 0; i < twoJson.length; i++) {
                Log.d("T", "JSON YOU MORON I HATE YOU DIE: " + i + " BITCH " + twoJson[i]);
            }
            Log.d("T", "JSON ARRAY LENGTH: " + twoJson.length);
            try {
                StringBuilder committee = new StringBuilder();
                StringBuilder bill = new StringBuilder();
                committee.append("Committees: \n\n");
                bill.append("Recently Sponsored Bills: \n\n");
                for (int k = 0; k < twoJson.length; k++) {
                    Log.d("T", "WHAT IS K: " + k);
                    JSONObject jObject = new JSONObject(twoJson[k]);
                    Iterator<?> keys = jObject.keys();
                    Log.d("T", "RIGHT HERE");
                    while (keys.hasNext()) {
                        String key = (String) keys.next();
                        Log.d("T", "RIGHT HERE2: " + key);
                        if (key.equals("results")) {
                            Log.d("T", "IT THINKS IT IS THE KEY: " + key);
                            JSONArray j = jObject.getJSONArray(key);
                            for (int i = 0; i < j.length(); i++) {
                                Log.d("T", "DID I MAKE IT OVER HERE2");
                                JSONObject objects = j.getJSONObject(i);
                                if (k == 0) {
                                    Log.d("T", "IN HERE IN COMMITTEE");
                                    committee.append((i + 1) + ") " + objects.getString("name") + "\n");
                                }
                                else if (k== 1) {
                                    Log.d("T", "BEFORE");
                                    bill.append(objects.getString("introduced_on") + ": " +
                                                objects.getString("official_title") + "\n\n");
                                    Log.d("T", "AFTER");
                                }
                                else  {
                                    String title = "";
                                    if (objects.getString("title").equals("Sen")) {
                                        title = "Senator";
                                    } else {
                                        title = "Rep.";
                                    }
                                    //name = title + " " + objects.getString("first_name") + " " + objects.getString("last_name");
//                                    if (objects.getString("party").equals("D")) {
//                                        party = "Democratic Party";
//                                    } else if (objects.getString("party").equals("R")) {
//                                        party = "Republican Party";
//                                    }
//                                    endOfDate = objects.getString("term_end");
                                }
                            }

                        }
                    }


                }

                Log.d("T", "OUT OF LOOP: " + bill.toString());
                TextView nme = (TextView) findViewById(R.id.name);
                TextView prty = (TextView) findViewById(R.id.party);
                TextView endDate = (TextView) findViewById(R.id.enddate);
                TextView committees = (TextView) findViewById(R.id.committee);
                TextView bills = (TextView) findViewById(R.id.bills);
                ImageView pic = (ImageView) findViewById(R.id.picture);
                Log.d("T", "WHAT");
                committees.setText(committee);

                Log.d("T", "BILL IS: " + bill);
                bills.setText(bill);

                nme.setText(name);
                prty.setText(party);
                endDate.setText("End date of term: " + endOfDate);
                pic.setImageBitmap(mIcon11);

            } catch (JSONException e) {
                Log.d("T", "JSONException found and caught");
            }
        }


        private String downloadUrl(String myurl, int k) throws IOException {
            // Do some validation here

            try {
                URL url = new URL(myurl);

                HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection();
                try {
                    BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(urlConnection.getInputStream()));
                    if (k == 2) {
                        ImageView pic = (ImageView) findViewById(R.id.picture);
                        String urldisplay = urlImage;
                        try {
                            InputStream in = urlConnection.getInputStream();
                            mIcon11 = BitmapFactory.decodeStream(in);
                            Log.d("T", "SET BITMAP");
                        } catch (Exception e) {
                            Log.e("Error", "Cannot put in image");
                            e.printStackTrace();
                        }
                    }
                    StringBuilder stringBuilder = new StringBuilder();
                    String line;
                    while ((line = bufferedReader.readLine()) != null) {
                        stringBuilder.append(line).append("\n");
                    }
                    bufferedReader.close();
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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.moredetails);
        Intent intent = getIntent();
        Bundle b = intent.getExtras();
        text = b.getString("test.com.anarayan.test.loc");
        images = b.getStringArray("test.com.anarayan.test.images");
        twitterQuotes = b.getStringArray("test.com.anarayan.test.twitterQuotes");
        back = (TextView) findViewById(R.id.textView);
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                goBack(v);
            }
        });

        Log.d("T", "BEFORE CONNECTIVITY CHECK");
        ConnectivityManager connMgr = (ConnectivityManager)
                getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = connMgr.getActiveNetworkInfo();
        if (networkInfo != null && networkInfo.isConnected()) {
            Log.d("T", "IN DOWNLOADWEBPAGETASK");
            //urlArray[0] = "http://congress.api.sunlightfoundation.com/legislators/locate?zip=94704&apikey=3d4edffb86e547eabb5402be5f52e315";

            String memberId = b.getString("test.com.anarayan.test.memberId");
            name = b.getString("test.com.anarayan.test.name");
            party = b.getString("test.com.anarayan.test.party");
            endOfDate = b.getString("test.com.anarayan.test.endofdate");
            String[] urlArray = new String[3];
            String url = "http://congress.api.sunlightfoundation.com/committees?member_ids="+memberId+"&apikey=3d4edffb86e547eabb5402be5f52e315";
            urlArray[0] = url;
            url = "http://congress.api.sunlightfoundation.com/bills?sponsor_id="+memberId+"&apikey=3d4edffb86e547eabb5402be5f52e315";
            urlArray[1] = url;
            urlImage = "https://theunitedstates.io/images/congress/225x275/"+memberId+".jpg";
            urlArray[2] = urlImage;
//            name = b.getString("test.com.anarayan.test.name");
//            party = b.getString("test.com.anarayan.test.party");
//            endOfDate = b.getString("test.com.anarayan.test.endOfDate");
            new DownloadWebpageTask().execute(urlArray);
        } else {
            Log.d("T", "No network connection available.");
        }
    }

    /** Called when user presses back arrow */
    public void goBack(View view) {
        Intent intent = new Intent(this, InformationAc.class);
        intent.putExtra("test.com.anarayan.test.loc", text);
        intent.putExtra("test.com.anarayan.test.images", images);
        intent.putExtra("test.com.anarayan.test.twitterQuotes", twitterQuotes);
        startActivity(intent);
    }
}

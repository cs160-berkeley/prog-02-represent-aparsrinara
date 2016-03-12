package test.com.anarayan.test;

import com.squareup.picasso.Picasso;
import com.twitter.sdk.android.Twitter;
import com.twitter.sdk.android.core.*;
import com.twitter.sdk.android.core.models.Tweet;
import com.twitter.sdk.android.core.models.User;
import com.twitter.sdk.android.core.services.StatusesService;
import com.twitter.sdk.android.tweetui.*;
import io.fabric.sdk.android.Fabric;
import retrofit.http.GET;
import retrofit.http.Query;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.JsonReader;
import android.util.JsonToken;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.TextureView;
import android.view.View;
import android.content.Intent;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.Spinner;
import android.widget.Button;
import android.graphics.Color;
import android.widget.TextView;
import android.widget.Toast;

import com.twitter.sdk.android.core.Result;
import com.twitter.sdk.android.core.TwitterException;
import com.twitter.sdk.android.core.models.Tweet;
import com.twitter.sdk.android.tweetui.TweetUtils;
import com.twitter.sdk.android.tweetui.TweetView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.w3c.dom.Text;

import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;


public class InformationAc extends AppCompatActivity {

    public TextView moreDetails1;
    public TextView moreDetails2;
    public TextView moreDetails3;
    public TextView moreDetails4;
    public TextView moreDetails5;
    public TextView moreDetails6;
    public TextView moreDetails7;
    public TextView back;
    public TextView locationDisplay;
    public ArrayList<Message> messages = new ArrayList<Message>();
    public String urlImage;
//    public Bitmap mIcon12;
//    public Bitmap mIcon13;
//    public Bitmap mIcon14;
    public ArrayList<Bitmap> imageIcons = new ArrayList<Bitmap>();
    public int count;
    public boolean toImages = false;
    public ArrayList<String> urlImages = new ArrayList<String>();
    public String[] twitterQuotes;
    public String[] images;

    private class Message {
        String name;
        String party;
        String title;
        String memberId;
        String endOfDate;
        private Message (String name, String party, String memberId, String endOfDate) {
            this.name = name;
            this.party = party;
            this.title = title;
            this.memberId = memberId;
            this.endOfDate = endOfDate;
        }
    }



    public void setImages(String id, int i) {
        ImageView[] arr = getImages();
        ImageView ii = arr[i];
        String url = "https://theunitedstates.io/images/congress/225x275/"+id+".jpg";
        Picasso.with(getApplicationContext()).load(url).into(ii);
    }

    public ImageView[] getImages() {
        ImageView[] images =
                {(ImageView) findViewById(R.id.imageView),
                        (ImageView) findViewById(R.id.senator2),
                        (ImageView) findViewById(R.id.rep1),
                        (ImageView) findViewById(R.id.rep2),
                        (ImageView) findViewById(R.id.rep3), (ImageView) findViewById(R.id.rep4), (ImageView) findViewById(R.id.rep5)};
        return images;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        Log.d("T", "IN ONCREATE");
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
        String[] names = intent.getStringArrayExtra("test.com.anarayan.test.name");
        String[] parties = intent.getStringArrayExtra("test.com.anarayan.test.party");
        String[] memberIds = intent.getStringArrayExtra("test.com.anarayan.test.memberid");
        String[] emails = intent.getStringArrayExtra("test.com.anarayan.test.email");
        String[] endOfDates = intent.getStringArrayExtra("test.com.anarayan.test.endofdate");
        String[] websites = intent.getStringArrayExtra("test.com.anarayan.test.website");
        String[] twitters = intent.getStringArrayExtra("test.com.anarayan.test.twitter");
        if (n != null) {
            locationDisplay.setText(n);
        }
        else {
            locationDisplay.setText(text);
        }

        LinearLayout fr = (LinearLayout) findViewById(R.id.frame1);
        for (int i = 0; i < names.length; i++) {

                                        if (i == 0) {
                                            LinearLayout f = (LinearLayout) findViewById(R.id.frame1);
                                            fr = f;
                                            TextView senatorName = (TextView) findViewById(R.id.senatorName);
                                            TextView senatorParty = (TextView) findViewById(R.id.senatorParty);
                                            TextView senatorEmail = (TextView) findViewById(R.id.senatorEmail);
                                            TextView senatorWebsite = (TextView) findViewById(R.id.senatorWebsite);
                                            ImageView pic = (ImageView) findViewById(R.id.imageView);
                                            senatorName.setText(names[i]);
                                            senatorParty.setText(parties[i]);
                                            senatorEmail.setText(emails[i]);
                                            senatorWebsite.setText(websites[i]);

                                        } else if (i == 1) {
                                            LinearLayout f = (LinearLayout) findViewById(R.id.frame2);
                                            fr = f;
                                            TextView senatorName = (TextView) findViewById(R.id.senatorName2);
                                            TextView senatorParty = (TextView) findViewById(R.id.senatorParty2);
                                            TextView senatorEmail = (TextView) findViewById(R.id.senatorEmail2);
                                            TextView senatorWebsite = (TextView) findViewById(R.id.senatorWebsite2);
                                            ImageView pic = (ImageView) findViewById(R.id.senator2);
                                            senatorName.setText(names[i]);
                                            senatorParty.setText(parties[i]);
                                            senatorEmail.setText(emails[i]);
                                            senatorWebsite.setText(websites[i]);
                                        } else if (i == 2) {
                                            LinearLayout f = (LinearLayout) findViewById(R.id.frame3);
                                            fr = f;
                                            TextView senatorName = (TextView) findViewById(R.id.repName1);
                                            TextView senatorParty = (TextView) findViewById(R.id.repParty1);
                                            TextView senatorEmail = (TextView) findViewById(R.id.repEmail1);
                                            TextView senatorWebsite = (TextView) findViewById(R.id.repWebsite1);
                                            ImageView pic = (ImageView) findViewById(R.id.rep1);
                                            senatorName.setText(names[i]);
                                            senatorParty.setText(parties[i]);
                                            senatorEmail.setText(emails[i]);
                                            senatorWebsite.setText(websites[i]);
                                        } else if (i == 3) {
                                            LinearLayout f = (LinearLayout) findViewById(R.id.frame4);
                                            fr = f;
                                            f.setVisibility(View.VISIBLE);
                                            TextView senatorName = (TextView) findViewById(R.id.repName2);
                                            TextView senatorParty = (TextView) findViewById(R.id.repParty2);
                                            TextView senatorEmail = (TextView) findViewById(R.id.repEmail2);
                                            TextView senatorWebsite = (TextView) findViewById(R.id.repWebsite2);
                                            ImageView pic = (ImageView) findViewById(R.id.rep2);
                                            senatorName.setText(names[i]);
                                            senatorParty.setText(parties[i]);
                                            senatorEmail.setText(emails[i]);
                                            senatorWebsite.setText(websites[i]);
                                        } else if (i == 4) {
                                            LinearLayout f = (LinearLayout) findViewById(R.id.frame5);
                                            f.setVisibility(View.VISIBLE);
                                            fr = f;
                                            TextView senatorName = (TextView) findViewById(R.id.repName3);
                                            TextView senatorParty = (TextView) findViewById(R.id.repParty3);
                                            TextView senatorEmail = (TextView) findViewById(R.id.repEmail3);
                                            TextView senatorWebsite = (TextView) findViewById(R.id.repWebsite3);
                                            ImageView pic = (ImageView) findViewById(R.id.rep3);
                                            senatorName.setText(names[i]);
                                            senatorParty.setText(parties[i]);
                                            senatorEmail.setText(emails[i]);
                                            senatorWebsite.setText(websites[i]);
                                        } else if (i == 5) {
                                            LinearLayout f = (LinearLayout) findViewById(R.id.frame6);
                                            f.setVisibility(View.VISIBLE);
                                            fr = f;
                                            TextView senatorName = (TextView) findViewById(R.id.repName4);
                                            TextView senatorParty = (TextView) findViewById(R.id.repParty4);
                                            TextView senatorEmail = (TextView) findViewById(R.id.repEmail4);
                                            TextView senatorWebsite = (TextView) findViewById(R.id.repWebsite4);
                                            ImageView pic = (ImageView) findViewById(R.id.rep4);
                                            senatorName.setText(names[i]);
                                            senatorParty.setText(parties[i]);
                                            senatorEmail.setText(emails[i]);
                                            senatorWebsite.setText(websites[i]);
                                        } else {
                                            LinearLayout f = (LinearLayout) findViewById(R.id.frame7);
                                            f.setVisibility(View.VISIBLE);
                                            fr = f;
                                            TextView senatorName = (TextView) findViewById(R.id.repName5);
                                            TextView senatorParty = (TextView) findViewById(R.id.repParty5);
                                            TextView senatorEmail = (TextView) findViewById(R.id.repEmail5);
                                            TextView senatorWebsite = (TextView) findViewById(R.id.repWebsite5);
                                            ImageView pic = (ImageView) findViewById(R.id.rep5);
                                            senatorName.setText(names[i]);
                                            senatorParty.setText(parties[i]);
                                            senatorEmail.setText(emails[i]);
                                            senatorWebsite.setText(websites[i]);
                                        }
                                        messages.add(new Message(names[i], parties[i], memberIds[i], endOfDates[i]));
                                        tweetStuff(twitters[i], i, fr);
                                        setImages(memberIds[i], i);
                                    }

        moreDetails1 = (TextView) findViewById(R.id.button);
        moreDetails2 = (TextView) findViewById(R.id.button2);
        moreDetails3 = (TextView) findViewById(R.id.button3);
        moreDetails4 = (TextView) findViewById(R.id.button4);
        moreDetails5 = (TextView) findViewById(R.id.button5);
        moreDetails6 = (TextView) findViewById(R.id.button6);
        moreDetails7 = (TextView) findViewById(R.id.button7);

        moreDetails1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sendMessage(v, 1);
            }
        });
        moreDetails2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sendMessage(v, 2);
            }
        });
        moreDetails3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sendMessage(v, 3);
            }
        });
        moreDetails4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sendMessage(v, 4);
            }
        });
        moreDetails5.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sendMessage(v, 5);
            }
        });
        moreDetails6.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sendMessage(v, 6);
            }
        });
        moreDetails7.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sendMessage(v, 7);
            }
        });
        }





    public void tweetStuff(String tweetId, int i, LinearLayout fr) {

            TwitterApiClient twitterApiClient = TwitterCore.getInstance().getApiClient();
            StatusesService search = twitterApiClient.getStatusesService();
            final LinearLayout frr = fr;
            search.userTimeline(null, tweetId, null, null, null, null, null, null, null, new Callback<List<Tweet>>() {

                public void success(Result<List<Tweet>> result) {
                    CompactTweetView tweetView = new CompactTweetView(InformationAc.this, result.data.get(0));
                    frr.addView(tweetView);
                }

                @Override
                public void failure(TwitterException e) {
                    Log.v("Error", e.getLocalizedMessage());
                }
            });
    }




    /** Called when user presses back arrow */
    public void goBack(View view) {
        Intent intent = new Intent(this, InputActivity.class);
        startActivity(intent);
    }

    /** Called when the user clicks the Go button */
    public void sendMessage(View view, int id) {
        // Do something in response to button
        Intent intent = new Intent(this, Details.class);
        Intent intent2 = new Intent(this, PhoneToWatchService.class);
        Bundle b = new Bundle();
        b.putString("test.com.anarayan.test.memberId", messages.get(id - 1).memberId);
        b.putString("test.com.anarayan.test.name", messages.get(id - 1).name);
        b.putString("test.com.anarayan.test.party", messages.get(id - 1).party);
        b.putString("test.com.anarayan.test.endofdate", messages.get(id - 1).endOfDate);
//        b.putStringArray("test.com.anarayan.test.images", images);
//        b.putStringArray("test.com.anarayan.test.twitterQuotes", twitterQuotes);
        intent.putExtras(b);
        startActivity(intent);
    }
}

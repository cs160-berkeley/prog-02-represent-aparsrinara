package test.com.anarayan.test;

import android.app.Activity;
import android.content.Intent;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.support.wearable.view.CardFragment;
import android.support.wearable.view.GridViewPager;
import android.support.wearable.view.WatchViewStub;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import 	android.support.wearable.view.FragmentGridPagerAdapter;
import android.app.Fragment;
import android.content.Context;

import java.math.RoundingMode;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import android.app.FragmentManager;
import android.view.LayoutInflater;
import android.hardware.Sensor;

import android.hardware.SensorEventListener;
import android.hardware.SensorEvent;
import android.widget.Toast;




public class MainActivity extends Activity {
    public String s;
    public int count;
    public Page[][] PAGES;
    public String[] pres;
    // A simple container for static data in each page
    private class Page {
        // static resources
        String name;
        String party;
        String memberid;
        String endOfDate;
        private Page(String n, String p, String m, String e) {
            name = n;
            party = p;
            memberid = m;
            endOfDate = e;
        }

    }
    /* Skeleton taken from Android docs*/
    class SampleGridPagerAdapter extends FragmentGridPagerAdapter {

        private final Context mContext;
        private List mRows;

        public SampleGridPagerAdapter(Context ctx, FragmentManager fm) {
            super(fm);
            mContext = ctx;
        }


        // Override methods in FragmentGridPagerAdapter

        @Override
        public Fragment getFragment(int row, int col) {
            if (PAGES[row][col] == null) {
                Log.d("T", "PRINTING PAGES");
            }
            Log.d("T", "ROW AND COL: " + row + col);
            Page page = PAGES[row][col];
            String name = page.name;
            String party = page.party;
            String memberid = page.memberid;
            String endOfDate = page.endOfDate;
            Log.d("T", "HELLO IT'S ME");
            Log.d("T", "PAGE IS: " + name + "/" + party);
            Fragment fragment;
            //CardFragment fragment = CardFragment.create(name, party);
            if (page.name.equals("shake")) {
                Bundle b = new Bundle();
//                Random rnd = new Random();
//                int n = rnd.nextInt(8);
                //String[] arr = {"Alameda County", "Santa Clara County", "Alpine County", "Amador County", "Butte County", "Calaveras County",
                //"Colusa County"};
//                //String newLoc = arr[n];
//                if (s == null)
                    b.putStringArray("test.com.anarayan.test.pres", pres);
//                else { //random stuff
//                    b.putString("county", );
//                }
                Log.d("T", "IN SHAKE FRAGMENT CONDITION");
                fragment = new ShakeFragment();
                fragment.setArguments(b);
            } else {
                Bundle b = new Bundle();
                b.putString("test.com.anarayan.test.name", name);
                b.putString("test.com.anarayan.test.party", party);
                b.putString("test.com.anarayan.test.memberid", memberid);
                b.putString("test.com.anarayan.test.endofdate", endOfDate);
                fragment = new CustomFragment();
                fragment.setArguments(b);
            }

            // Advanced settings (card gravity, card expansion/scrolling)
            //fragment.setCardGravity(page.cardGravity);
            //fragment.setExpansionEnabled(page.expansionEnabled);
            //fragment.setExpansionDirection(page.expansionDirection);
            //fragment.setExpansionFactor(page.expansionFactor);
            return fragment;
        }

        // Obtain the number of pages (vertical)
        @Override
        public int getRowCount() {
            return PAGES.length;
        }

        // Obtain the number of pages (horizontal)
        @Override
        public int getColumnCount(int rowNum) {
            return PAGES[rowNum].length;
        }

    }
    private SensorManager mSensorManager;

    private ShakeEventListener mSensorListener;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
        Intent intent = getIntent();
        String[] name = intent.getStringArrayExtra("test.com.anarayan.test.name");
        String[] party = intent.getStringArrayExtra("test.com.anarayan.test.party");
        String[] memberId = intent.getStringArrayExtra("test.com.anarayan.test.memberid");
        count = Integer.parseInt(intent.getStringExtra("test.com.anarayan.test.count"));
        String[] endOfDate = intent.getStringArrayExtra("test.com.anarayan.test.endofdate");
        pres = intent.getStringArrayExtra("test.com.anarayan.test.pres");
        Log.d("T", "COUNT IS IN MAIN: " + count + name.length + party.length);
        PAGES = new Page[count+1][1];
        int i = 0;
        for (i = 0; i < count; i++) {
            Log.d("T", "Name is: " + name[i]);
            if (name[i] != null)
                PAGES[i][0] = new Page(name[i], party[i], memberId[i], endOfDate[i]);
            else
                break;
        }
        PAGES[i][0] = new Page("shake", "shake", "shake", "shake");
        s = intent.getStringExtra("random");
        Log.d("T", "IN MAIN ACTIVITY: " + name);
        final GridViewPager pager = (GridViewPager) findViewById(R.id.pager);
        pager.setAdapter(new SampleGridPagerAdapter(this, getFragmentManager()));
        // ShakeDetector initialization

    /*http://stackoverflow.com/questions/2317428/android-i-want-to-shake-it*/
        mSensorManager = (SensorManager) getSystemService(Context.SENSOR_SERVICE);
        mSensorListener = new ShakeEventListener();

        mSensorListener.setOnShakeListener(new ShakeEventListener.OnShakeListener() {

            public void onShake() {
//                Intent intent = new Intent(getBaseContext(), WatchToPhoneService.class);
//                Random rnd = new Random();
//                int n = rnd.nextInt(8);
//                String[] arr = {"Alameda County", "Santa Clara County", "Alpine County", "Amador County", "Butte County", "Calaveras County",
//                    "Colusa County"};
//                String newLoc = arr[n];
//                intent.putExtra("random", newLoc);
//                startService(intent);
                Intent intent = new Intent(getBaseContext(), WatchToPhoneService.class);
                Random rand = new Random();
                int minLat = 35;
                int maxLat = 45;
                int minLong = -115;
                int maxLong = -85;
                Random r = new Random();
                double randomLat = minLat + (maxLat - minLat) * r.nextDouble();
                double randomLon = minLong + (maxLong - minLong) * r.nextDouble();
                DecimalFormat df = new DecimalFormat("0.####");
                df.setRoundingMode(RoundingMode.DOWN);
                intent.putExtra("random", "random");
                intent.putExtra("latitude", Double.toString(randomLat));
                intent.putExtra("longitude", Double.toString(randomLon));
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startService(intent);
//
            }
        });
    }
//
//    public String getName() {
//        Intent intent = getIntent();
//        return intent.getStringExtra("test.com.anarayan.test.name");
//    }
//
//    public String getParty() {
//        Intent intent = getIntent();
//        return intent.getStringExtra("test.com.anarayan.test.party");

    @Override
    protected void onResume() {
        super.onResume();
        mSensorManager.registerListener(mSensorListener,
                mSensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER),
                SensorManager.SENSOR_DELAY_UI);
    }

    @Override
    protected void onPause() {
        mSensorManager.unregisterListener(mSensorListener);
        super.onPause();
    }
}

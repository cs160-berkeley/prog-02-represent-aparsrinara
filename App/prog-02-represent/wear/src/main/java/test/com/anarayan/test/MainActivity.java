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

    /* Skeleton taken from Android docs*/
    class SampleGridPagerAdapter extends FragmentGridPagerAdapter {

        private final Context mContext;
        private List mRows;

        public SampleGridPagerAdapter(Context ctx, FragmentManager fm) {
            super(fm);
            mContext = ctx;
            PAGES = createPageArray();
        }

        // A simple container for static data in each page
        private class Page {
            // static resources
            String name;
            String party;

            private Page(String n, String p) {
                name = n;
                party = p;
            }

        }

        Page a = new Page("Senator Loni Hancock", "Democratic Party");
        Page b = new Page("Senator Mary Mcilroy", "Peace and Freedom Party");
        Page c = new Page("Rep. Jerry McNerney", "Democratic Party");
        Page d = new Page(" ", " ");
        Page[] temp = {a, b, c, d};
        private Page[][] PAGES = new Page[4][1];


        public Page[][] createPageArray() {
            ArrayList<Integer> beenUsed = new ArrayList<Integer>();
            Random rand = new Random();
            for (int i = 0; i <= 3; i++) {
                int n = rand.nextInt(4);
                while (beenUsed.contains(n)) {
                    n = rand.nextInt(4);
                }
                beenUsed.add(n);
                PAGES[i][0] = temp[n];
            }
            return PAGES;
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
            Fragment fragment;
            //CardFragment fragment = CardFragment.create(name, party);
            if (page.name.equals(" ") & page.party.equals(" ")) {
                Bundle b = new Bundle();
//                Random rnd = new Random();
//                int n = rnd.nextInt(8);
                //String[] arr = {"Alameda County", "Santa Clara County", "Alpine County", "Amador County", "Butte County", "Calaveras County",
                //"Colusa County"};
                //String newLoc = arr[n];
                if (s == null)
                    b.putString("county", "Alameda County");
                else {
                    b.putString("county", s);
                }

                Log.d("T", "IN SHAKE FRAGMENT CONDITION");
                fragment = new ShakeFragment();
                fragment.setArguments(b);
            } else {
                Bundle b = new Bundle();
                b.putString("test.com.anarayan.test.name", name);
                b.putString("test.com.anarayan.test.party", party);
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
        String name = intent.getStringExtra("test.com.anarayan.test.name");
        String party = intent.getStringExtra("test.com.anarayan.test.party");
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
                Intent intent = new Intent(getBaseContext(), WatchToPhoneService.class);
                Random rnd = new Random();
                int n = rnd.nextInt(8);
                String[] arr = {"Alameda County", "Santa Clara County", "Alpine County", "Amador County", "Butte County", "Calaveras County",
                    "Colusa County"};
                String newLoc = arr[n];
                intent.putExtra("random", newLoc);
                startService(intent);
            }
        });
    }

    public String getName() {
        Intent intent = getIntent();
        String name = intent.getStringExtra("test.com.anarayan.test.name");
        return name;
    }

    public String getParty() {
        Intent intent = getIntent();
        String party = intent.getStringExtra("test.com.anarayan.test.party");
        return party;
    }
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

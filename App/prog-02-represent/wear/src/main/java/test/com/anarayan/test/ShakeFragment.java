package test.com.anarayan.test;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.wearable.view.CardFragment;
import android.support.wearable.view.GridViewPager;
import android.support.wearable.view.WatchViewStub;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import 	android.support.wearable.view.FragmentGridPagerAdapter;
import android.app.Fragment;
import android.content.Context;
import java.util.List;
import android.app.FragmentManager;
import android.view.LayoutInflater;

public class ShakeFragment extends CardFragment{

    @Override
    public View onCreateContentView (LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        Log.d("T", "IN ONCREATE: ");
        String county = getArguments().getString("county");
        View myView = inflater.inflate(R.layout.shake, container, false);
        //Fragment n = getFragmentManager().findFragmentById(R.id.name);

        //Fragment p = getFragmentManager().findFragmentById(R.id.party);
        //TextView button = (TextView) myView.findViewById(R.id.textView);
        TextView p = (TextView) myView.findViewById(R.id.county);
        p.setText(county);
        return myView;
    }



}
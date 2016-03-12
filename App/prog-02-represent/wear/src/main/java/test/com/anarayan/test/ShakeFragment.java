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
        String[] pres = getArguments().getStringArray("test.com.anarayan.test.pres");
        View myView = inflater.inflate(R.layout.shake, container, false);
        //Fragment n = getFragmentManager().findFragmentById(R.id.name);

        //Fragment p = getFragmentManager().findFragmentById(R.id.party);
        //TextView button = (TextView) myView.findViewById(R.id.textView);
        TextView p = (TextView) myView.findViewById(R.id.county);
        TextView q = (TextView) myView.findViewById(R.id.state);
        TextView obama = (TextView) myView.findViewById(R.id.obama);
        TextView romney = (TextView) myView.findViewById(R.id.romney);
        p.setText(pres[0]);
        q.setText(pres[1]);
        romney.setText(pres[2]);
        obama.setText(pres[3]);
        return myView;
    }



}
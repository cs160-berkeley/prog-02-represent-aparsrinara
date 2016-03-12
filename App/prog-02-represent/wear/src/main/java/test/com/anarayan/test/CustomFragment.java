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

public class CustomFragment extends CardFragment implements View.OnClickListener{

    public String name;
    public String party;
    public String memberid;
    public String endOfDate;
        @Override
    public View onCreateContentView (LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
            Log.d("T", "IN ONCREATE: ");
        name = getArguments().getString("test.com.anarayan.test.name");
        party = getArguments().getString("test.com.anarayan.test.party");
        memberid = getArguments().getString("test.com.anarayan.test.memberid");
        endOfDate = getArguments().getString("test.com.anarayan.test.endofdate");
        View myView = inflater.inflate(R.layout.activity_main, container, false);
        //Fragment n = getFragmentManager().findFragmentById(R.id.name);

        //Fragment p = getFragmentManager().findFragmentById(R.id.party);
        //TextView button = (TextView) myView.findViewById(R.id.textView);
        TextView n = (TextView) myView.findViewById(R.id.name);
        TextView p = (TextView) myView.findViewById(R.id.party);
        n.setText(name);
        p.setText(party);
        myView.setOnClickListener(this);
        return myView;
    }

    @Override
    public void onClick(View v) {
        sendMessage(v);
    }

    public void sendMessage(View v) {
        Intent intent = new Intent(getActivity(), WatchToPhoneService.class);
        Log.d("T", "SEND MESSAGE" + getActivity().toString());
        Log.d("T", "SEND MESSAGE " + name);
        intent.putExtra("test.com.anarayan.test.name", name);
        intent.putExtra("test.com.anarayan.test.party", party);
        intent.putExtra("test.com.anarayan.test.memberid", memberid);
        intent.putExtra("test.com.anarayan.test.endofdate", endOfDate);
        Log.d("T", "WHAT IN THE WORLD IS DATE: " + endOfDate);
        getActivity().startService(intent);
    }

}
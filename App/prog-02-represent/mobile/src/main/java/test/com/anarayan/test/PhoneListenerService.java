package test.com.anarayan.test;

import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.IBinder;
import android.util.Log;
import android.widget.Toast;

import com.google.android.gms.wearable.MessageEvent;
import com.google.android.gms.wearable.WearableListenerService;

import java.nio.charset.StandardCharsets;
import java.util.Random;

/**
 * Created by joleary and noon on 2/19/16 at very late in the night. (early in the morning?)
 */
public class PhoneListenerService extends WearableListenerService {

    //   WearableListenerServices don't need an iBinder or an onStartCommand: they just need an onMessageReceieved.
    private static final String NAME = "/name";

    @Override
    public void onMessageReceived(MessageEvent messageEvent) {
        Log.d("T", "in PhoneListenerService, got: " + messageEvent.getPath());
        if( messageEvent.getPath().equalsIgnoreCase("/1") ) {

            // Value contains the String we sent over in WatchToPhoneService, "good job"
            //String value = new String(messageEvent.getData(), StandardCharsets.UTF_8);
            String name = "Senator Loni Hancock";

            // Make a toast with the String
            //Context context = getApplicationContext();
            //int duration = Toast.LENGTH_SHORT;

            Intent intent = new Intent(this, Details.class);
            intent.putExtra("test.com.anarayan.test.name", name);
            intent.putExtra("test.com.anarayan.test.party", "Democratic Party");
            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            startActivity(intent);
            //Toast toast = Toast.makeText(context, value, duration);
           // toast.show();

            // so you may notice this crashes the phone because it's
            //''sending message to a Handler on a dead thread''... that's okay. but don't do this.
            // replace sending a toast with, like, starting a new activity or something.
            // who said skeleton code is untouchable? #breakCSconceptions

        }
        else if( messageEvent.getPath().equalsIgnoreCase("/2") ) {

            // Value contains the String we sent over in WatchToPhoneService, "good job"
            //String value = new String(messageEvent.getData(), StandardCharsets.UTF_8);
            String name = "Senator Mary Mcilroy";

            // Make a toast with the String
            //Context context = getApplicationContext();
            //int duration = Toast.LENGTH_SHORT;

            Intent intent = new Intent(this, Details.class);
            intent.putExtra("test.com.anarayan.test.name", name);
            intent.putExtra("test.com.anarayan.test.party", "Peace and Freedom Party");
            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            startActivity(intent);
            //Toast toast = Toast.makeText(context, value, duration);
            //toast.show();

            // so you may notice this crashes the phone because it's
            //''sending message to a Handler on a dead thread''... that's okay. but don't do this.
        }
        else if (messageEvent.getPath().equalsIgnoreCase("/3")) {
            // Value contains the String we sent over in WatchToPhoneService, "good job"
            //String value = new String(messageEvent.getData(), StandardCharsets.UTF_8);
            String name = "Rep. Jerry McNerney";

            // Make a toast with the String
            //Context context = getApplicationContext();
            //int duration = Toast.LENGTH_SHORT;

            Intent intent = new Intent(this, Details.class);
            intent.putExtra("test.com.anarayan.test.name", name);
            intent.putExtra("test.com.anarayan.test.party", "Democratic Party");
            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            startActivity(intent);
            //Toast toast = Toast.makeText(context, value, duration);
            //toast.show();

            // so you may notice this crashes the phone because it's
            //''sending message to a Handler on a dead thread''... that's okay. but don't do this.
        }
        else if (messageEvent.getPath().charAt(1) == '4') {
            Random rnd = new Random();
            int n = rnd.nextInt(8);
            Intent intent = new Intent(this, InformationAc.class);
            Intent intent2 = new Intent(this, PhoneToWatchService.class);
            String newLoc = messageEvent.getPath().substring(2);
            //String[] arr = {"Alameda County", "Santa Clara County", "Alpine County", "Amador County", "Butte County", "Calaveras County",
                    //"Colusa County"};
            //String newLoc = arr[n];
            intent.putExtra("test.com.anarayan.test.shake", newLoc);
            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            intent2.putExtra("random", newLoc);
            startActivity(intent);
            startService(intent2);
        }
        else {
            super.onMessageReceived( messageEvent );
        }

    }
}

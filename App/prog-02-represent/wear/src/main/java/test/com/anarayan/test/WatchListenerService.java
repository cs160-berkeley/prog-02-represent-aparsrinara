package test.com.anarayan.test;

import android.content.Intent;
import android.util.Log;

import com.google.android.gms.wearable.MessageEvent;
import com.google.android.gms.wearable.WearableListenerService;

import java.nio.charset.StandardCharsets;

/**
 * Created by joleary and noon on 2/19/16 at very late in the night. (early in the morning?)
 */
public class WatchListenerService extends WearableListenerService {
    // In PhoneToWatchService, we passed in a path, either "/FRED" or "/LEXY"
    // These paths serve to differentiate different phone-to-watch messages
    private static final String NAME1 = "/1";
    private static final String NAME2 = "/2";
    private static final String NAME3 = "/3";

    @Override
    public void onMessageReceived(MessageEvent messageEvent) {
        Log.d("T", "in WatchListenerService, got: " + messageEvent.getPath());
        Log.d("T", "in WatchListenerService, got: " + new String(messageEvent.getData(), StandardCharsets.UTF_8));
        //use the 'path' field in sendmessage to differentiate use cases
        //(here, fred vs lexy)

        if( messageEvent.getPath().equalsIgnoreCase( NAME1 ) ) {
            //String value = new String(messageEvent.getData(), StandardCharsets.UTF_8);
            String name = "Senator Loni Hancock";
            String party = "Democratic Party";
            Intent intent = new Intent(this, MainActivity.class);
            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            //you need to add this flag since you're starting a new activity from a service
            intent.putExtra("test.com.anarayan.test.name", name);
            intent.putExtra("test.com.anarayan.test.party", party);
            Log.d("T", "about to start watch MainActivity");
            startActivity(intent);
        }
        else if (messageEvent.getPath().equalsIgnoreCase( NAME2 )) {
            String name = "Senator Catherine Mcilroy";
            String party = "Peace and Freedom Party";
            Intent intent = new Intent(this, MainActivity.class);
            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            //you need to add this flag since you're starting a new activity from a service
            intent.putExtra("test.com.anarayan.test.name", name);
            intent.putExtra("test.com.anarayan.test.party", party);
            Log.d("T", "about to start watch MainActivity");
            startActivity(intent);
        }
        else if ((messageEvent.getPath().equalsIgnoreCase( NAME3 ))) {
            String name = "Representative Jerry McNerney";
            String party = "Democratic Party";
            Intent intent = new Intent(this, MainActivity.class);
            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            //you need to add this flag since you're starting a new activity from a service
            intent.putExtra("test.com.anarayan.test.name", name);
            intent.putExtra("test.com.anarayan.test.party", party);
            Log.d("T", "about to start watch MainActivity");
            startActivity(intent);
        }
        else if ((messageEvent.getPath().charAt(1) == '4')) {
            String zip = messageEvent.getPath().substring(2);
            Intent intent = new Intent(this, MainActivity.class);
            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            //you need to add this flag since you're starting a new activity from a service
            intent.putExtra("random", zip);
            Log.d("T", "about to start watch MainActivity");
            startActivity(intent);
        }
//        } else if (messageEvent.getPath().equalsIgnoreCase(PARTY)) {
//            String value = new String(messageEvent.getData(), StandardCharsets.UTF_8);
//            Intent intent = new Intent(this, MainActivity.class );
//            intent.addFlags( Intent.FLAG_ACTIVITY_NEW_TASK );
//            //you need to add this flag since you're starting a new activity from a service
//            intent.putExtra("test.com.anarayan.test.party", value);
//            Log.d("T", "about to start watch MainActivity");
//            startActivity(intent);
        else {
            super.onMessageReceived( messageEvent );
        }

    }
}
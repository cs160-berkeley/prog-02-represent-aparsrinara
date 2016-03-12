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
    private static final String NAME = "/name";
    private static final String TYPE = "/type";

    @Override
    public void onMessageReceived(MessageEvent messageEvent) {
        Log.d("T", "in WatchListenerService, got: " + messageEvent.getPath());
        Log.d("T", "in WatchListenerService, got: " + new String(messageEvent.getData(), StandardCharsets.UTF_8));
        //use the 'path' field in sendmessage to differentiate use cases
        //(here, fred vs lexy)

        if( messageEvent.getPath().equalsIgnoreCase( NAME ) ) {
            //String value = new String(messageEvent.getData(), StandardCharsets.UTF_8);
            String s = new String(messageEvent.getData(), StandardCharsets.UTF_8);
            String[] array = s.split("/");
            Log.d("T", "ARRAY LENGTH IS: " + array.length);
            int count = 0;
            String[] names = new String[(array.length-1)/4];
            String[] parties = new String[(array.length-1)/4];
            String[] memberIds = new String[(array.length-1)/4];
            String[] endOfDate = new String[(array.length-1)/4];
            String[] pres = (array[array.length-1]).split(";");
            int j = 0;
            int k = 0;
            int l = 0;
            int m = 0;
            for (int i = 0; i < array.length-1; i++) {
                if (i % 4 == 0) {
                    names[j] = array[i];
                    j++;
                    Log.d("T", "HI I AM IN HERE");
                    count++;
                }
                else if (i % 4 == 1) {
                    parties[k] = array[i];
                    k++;
                }
                else if (i % 4 == 2){
                    memberIds[l] = array[i];
                    l++;
                }
                else {
                    endOfDate[m] = array[i];
                    m++;
                }
            }
            Intent intent = new Intent(this, MainActivity.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TOP);
            //you need to add this flag since you're starting a new activity from a service
            intent.putExtra("test.com.anarayan.test.name", names);
            intent.putExtra("test.com.anarayan.test.party", parties);
            intent.putExtra("test.com.anarayan.test.memberid", memberIds);
            Log.d("T", "COUNT IS: " + count);
            intent.putExtra("test.com.anarayan.test.count", Integer.toString(count));
            intent.putExtra("test.com.anarayan.test.endofdate", endOfDate);
            intent.putExtra("test.com.anarayan.test.pres", pres);
            for (int i = 0; i < pres.length; i++) {
                Log.d("T", "PLEASE PRINT PRES: " + pres[i]);
            }
            Log.d("T", "about to start watch MainActivity");
            startActivity(intent);
        }
//        else if (messageEvent.getPath().equalsIgnoreCase( NAME2 )) {
//            String name = "Senator Catherine Mcilroy";
//            String party = "Peace and Freedom Party";
//            Intent intent = new Intent(this, MainActivity.class);
//            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
//            //you need to add this flag since you're starting a new activity from a service
//            intent.putExtra("test.com.anarayan.test.name", name);
//            intent.putExtra("test.com.anarayan.test.party", party);
//            Log.d("T", "about to start watch MainActivity");
//            startActivity(intent);
//        }
//        else if ((messageEvent.getPath().equalsIgnoreCase( NAME3 ))) {
//            String name = "Representative Jerry McNerney";
//            String party = "Democratic Party";
//            Intent intent = new Intent(this, MainActivity.class);
//            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
//            //you need to add this flag since you're starting a new activity from a service
//            intent.putExtra("test.com.anarayan.test.name", name);
//            intent.putExtra("test.com.anarayan.test.party", party);
//            Log.d("T", "about to start watch MainActivity");
//            startActivity(intent);
//        }
        else if ((messageEvent.getPath().equalsIgnoreCase(TYPE))) {
            String s = new String(messageEvent.getData(), StandardCharsets.UTF_8);
            String[] array = s.split("/");
            Log.d("T", "ARRAY LENGTH IS: " + array.length);
            int count = 0;
            String[] names = new String[(array.length-1)/4];
            String[] parties = new String[(array.length-1)/4];
            String[] memberIds = new String[(array.length-1)/4];
            String[] endOfDate = new String[(array.length-1)/4];
            String[] pres = (array[array.length-1]).split(";");
            int j = 0;
            int k = 0;
            int l = 0;
            int m = 0;
            for (int i = 0; i < array.length-1; i++) {
                if (i % 4 == 0) {
                    names[j] = array[i];
                    j++;
                    Log.d("T", "HI I AM IN HERE");
                    count++;
                }
                else if (i % 4 == 1) {
                    parties[k] = array[i];
                    k++;
                }
                else if (i % 4 == 2){
                    memberIds[l] = array[i];
                    l++;
                }
                else {
                    endOfDate[m] = array[i];
                    m++;
                }
            }
            Intent intent = new Intent(this, MainActivity.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TOP);
            //you need to add this flag since you're starting a new activity from a service
            intent.putExtra("test.com.anarayan.test.name", names);
            intent.putExtra("test.com.anarayan.test.party", parties);
            intent.putExtra("test.com.anarayan.test.memberid", memberIds);
            Log.d("T", "COUNT IS: " + count);
            intent.putExtra("test.com.anarayan.test.count", Integer.toString(count));
            intent.putExtra("test.com.anarayan.test.endofdate", endOfDate);
            intent.putExtra("test.com.anarayan.test.pres", pres);
            for (int i = 0; i < pres.length; i++) {
                Log.d("T", "PLEASE PRINT PRES: " + pres[i]);
            }
            //you need to add this flag since you're starting a new activity from a service
            intent.putExtra("random", "random");
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
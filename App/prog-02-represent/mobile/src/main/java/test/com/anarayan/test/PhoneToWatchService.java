package test.com.anarayan.test;

import android.app.Service;
import android.content.Intent;
import android.os.Bundle;
import android.os.IBinder;
import android.util.Log;
import android.widget.ImageView;

import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.wearable.MessageApi;
import com.google.android.gms.wearable.Node;
import com.google.android.gms.wearable.NodeApi;
import com.google.android.gms.wearable.Wearable;

/**
 * Created by joleary on 2/19/16.
 */
public class PhoneToWatchService extends Service {

    private GoogleApiClient mApiClient;
    @Override
    public void onCreate() {
        super.onCreate();
        //initialize the googleAPIClient for message passing
        mApiClient = new GoogleApiClient.Builder( this )
                .addApi( Wearable.API )
                .addConnectionCallbacks(new GoogleApiClient.ConnectionCallbacks() {
                    @Override
                    public void onConnected(Bundle connectionHint) {
                    }

                    @Override
                    public void onConnectionSuspended(int cause) {
                    }
                })
                .build();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        mApiClient.disconnect();
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        // Which cat do we want to feed? Grab this info from INTENT
        // which was passed over when we called startService
        Log.d("T", "I AM IN HERE IN ONSTARTCOMMAND");
        Bundle extras = intent.getExtras();
        int val = 0;
        String newLoc;
        String[] names = extras.getStringArray("test.com.anarayan.test.name");
        String[] parties = extras.getStringArray("test.com.anarayan.test.party");
        String[] memberids = extras.getStringArray("test.com.anarayan.test.memberid");
        String[] endOfDate = extras.getStringArray("test.com.anarayan.test.endofdate");
        String pres = extras.getString("test.com.anarayan.test.presview");
        final StringBuilder s = new StringBuilder();
        final String t;
        if (extras.getString("random") != null) {
            t = "/type";
        }
        else {
            Log.d("T", "NAME IS: " + names[0]);
//            if (name != null && name.equals("Senator Loni Hancock")) {
//                val = 1;
//            } else if (name != null && name.equals("Senator Mary Mcilroy")) {
//                val = 2;
//            } else {
//                val = 3;
//            }
//            v = "/"+val;


            t = "/name";
        }
        for (int i = 0; i < names.length; i++) {
            s.append(names[i] + "/" + parties[i] + "/" + memberids[i] + "/" + endOfDate[i] + "/");
        }
        s.append(pres);
        // Send the message with the cat name
        new Thread(new Runnable() {
            @Override
            public void run() {
                //first, connect to the apiclient
                mApiClient.connect();
                //now that you're connected, send a massage with the cat name
                sendMessage(t, s.toString());
            }
        }).start();

        return START_STICKY;
    }

    @Override //remember, all services need to implement an IBiner
    public IBinder onBind(Intent intent) {
        return null;
    }

    private void sendMessage( final String path, final String text ) {
        //one way to send message: start a new thread and call .await()
        //see watchtophoneservice for another way to send a message
        new Thread( new Runnable() {
            @Override
            public void run() {
                NodeApi.GetConnectedNodesResult nodes = Wearable.NodeApi.getConnectedNodes( mApiClient ).await();
                for(Node node : nodes.getNodes()) {
                    //we find 'nodes', which are nearby bluetooth devices (aka emulators)
                    //send a message for each of these nodes (just one, for an emulator)
                    MessageApi.SendMessageResult result = Wearable.MessageApi.sendMessage(
                            mApiClient, node.getId(), path, text.getBytes() ).await();
                    //4 arguments: api client, the node ID, the path (for the listener to parse),
                    //and the message itself (you need to convert it to bytes.)
                }
            }
        }).start();
    }

}

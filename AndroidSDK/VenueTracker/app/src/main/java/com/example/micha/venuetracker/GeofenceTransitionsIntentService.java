package com.example.micha.venuetracker;

import android.app.IntentService;
import android.app.Notification;
import android.app.NotificationManager;
import android.content.Context;
import android.content.Intent;
import android.util.Log;

import com.google.android.gms.location.Geofence;
import com.google.android.gms.location.GeofenceStatusCodes;
import com.google.android.gms.location.GeofencingEvent;

import java.util.List;

/**
 * Created by micha on 26.01.2018.
 */

public class GeofenceTransitionsIntentService extends IntentService {
    protected static final String TAG = GeofenceTransitionsIntentService.class.getSimpleName();

    GeofenceTransitionsIntentService() {
        super(TAG);
    }

    protected void onHandleIntent(Intent intent) {
        GeofencingEvent geofencingEvent = GeofencingEvent.fromIntent(intent);
        if (geofencingEvent.hasError()) {
            Log.e("ERROR", "ERROR");
            return;
        }
        int transition = geofencingEvent.getGeofenceTransition();
        if(transition == Geofence.GEOFENCE_TRANSITION_EXIT || transition == Geofence.GEOFENCE_TRANSITION_ENTER){
            //List<Geofence> triggeringGeofences = geofencingEvent.getTriggeringGeofences();
            notification(this, "Hello", 0);
        }
    }
    public void notification(Context context, String msg, int id)
    {
        Notification.Builder nbuilder = new Notification.Builder(context)
                .setContentTitle("Masz nową wiadomość!")
                .setContentText(msg)
                .setSmallIcon(R.drawable.gps_icon);
        NotificationManager nman = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
        nman.notify(id, nbuilder.build());
    }

}

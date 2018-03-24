package com.example.micha.messagerecive;

import android.app.Notification;
import android.app.NotificationManager;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

/**
 * Created by micha on 25.11.2017.
 */

public class Reciever extends BroadcastReceiver {
    int i = 0;
    @Override
    public void onReceive(Context context, Intent intent) {
        try {
            String action = intent.getAction(); //to prevent spoofing
            if(action.equals("com.example.micha.venuetracker.ProximityAlert")) {
                String msg = "NALKSNL";

                notification(context, msg, i);
                i++;
            }
        }
        catch (NullPointerException e)
        {
            Log.d("DEBUG:", e.toString());
            Toast.makeText(context, "Pusto...", Toast.LENGTH_LONG).show();
        }
    }
    public void notification(Context context, String msg, int id)
    {
        Notification.Builder nbuilder = new Notification.Builder(context)
                .setContentTitle("Masz nową wiadomość!")
                .setContentText(msg)
                .setSmallIcon(R.drawable.letter);
        NotificationManager nman = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
        nman.notify(id, nbuilder.build());
    }
}

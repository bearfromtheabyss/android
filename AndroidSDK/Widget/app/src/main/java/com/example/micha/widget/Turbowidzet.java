package com.example.micha.widget;

import android.app.ActivityManager;
import android.app.PendingIntent;
import android.appwidget.AppWidgetManager;
import android.appwidget.AppWidgetProvider;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.util.Log;
import android.view.View;
import android.widget.RemoteViews;

/**
 * Implementation of App Widget functionality.
 */
public class Turbowidzet extends AppWidgetProvider {
    public static final String image_click = "image_click";
    public static final String start_click = "StartClick";
    public static final String stop_click = "StopClick";
    public static final String pause_click = "PauseClick";
    public static final String next_click = "NextClick";
    public static final String open_browser = "open_browser";
    public static int counter = 0;
    static void updateAppWidget(Context context, AppWidgetManager appWidgetManager,
                                int appWidgetId) {
        RemoteViews remoteViews = new RemoteViews(context.getPackageName(), R.layout.turbowidzet);
        appWidgetManager.updateAppWidget(appWidgetId, remoteViews);

    }
    @Override
    public void onReceive(Context context, Intent intent) {
        super.onReceive(context, intent);
        Log.d("LOG:", intent.getAction());
        AppWidgetManager awm = AppWidgetManager.getInstance(context);
        ComponentName COMPONENT_NAME = new ComponentName(context, Turbowidzet.class);
        if(intent.getAction() == image_click && awm != null && counter % 2 == 0){
            RemoteViews rv = new RemoteViews(context.getPackageName(), R.layout.turbowidzet);
            rv.setImageViewResource(R.id.imageView, R.drawable.ferrari2);
            Log.d("Image:", "Zolty");
            awm.getInstance(context).updateAppWidget(COMPONENT_NAME, rv);
            counter++;
        }
        else if(intent.getAction() == image_click && awm != null && counter % 2 == 1){
            RemoteViews rv = new RemoteViews(context.getPackageName(), R.layout.turbowidzet);
            rv.setImageViewResource(R.id.imageView, R.drawable.ferrari1);
            Log.d("Image:", "Czeerwony");
            awm.getInstance(context).updateAppWidget(COMPONENT_NAME, rv);
            counter++;
        }
        Log.d("COUNTER", String.valueOf(counter));
    }
    @Override
    public void onUpdate(Context context, AppWidgetManager appWidgetManager, int[] appWidgetIds) {
        boolean ktoraFura = true;
        for (int appWidgetId : appWidgetIds) {
            RemoteViews remoteViews = new RemoteViews(context.getPackageName(), R.layout.turbowidzet);
            remoteViews.setTextViewText(R.id.appwidget_text, "WIDGET");
            //STRONA
            Intent intent = new Intent(Intent.ACTION_VIEW);
            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            intent.setData(Uri.parse("http://www.google.com"));
            PendingIntent pending = PendingIntent.getActivity(context,0,intent, 0);
            remoteViews.setOnClickPendingIntent(R.id.default_page, pending);
            //IMAGEVIEW
            remoteViews.setOnClickPendingIntent(R.id.change_picture, getPendingSelfIntent(context, image_click));
            appWidgetManager.updateAppWidget(appWidgetId, remoteViews);
        }
    }

//    @Override
//    public void onEnabled(Context context) {
//        // Enter relevant functionality for when the first widget is created
//    }
//
//    @Override
//    public void onDisabled(Context context) {
//        // Enter relevant functionality for when the last widget is disabled
//    }

    protected PendingIntent getPendingSelfIntent(Context context, String action){
        Intent intent = new Intent(context, getClass());
        intent.setAction(action);
        return PendingIntent.getBroadcast(context, 0, intent, 0);
    }
}


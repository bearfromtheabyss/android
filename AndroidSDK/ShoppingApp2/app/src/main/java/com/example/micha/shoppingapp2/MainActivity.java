package com.example.micha.shoppingapp2;

import android.app.Activity;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.content.Intent;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.TextView;

import static android.graphics.Color.parseColor;
import static java.lang.Float.parseFloat;

public class MainActivity extends Activity {
    SharedPreferences settings = null;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        settings = getSharedPreferences("settings", 0);
        boolean firstrun = settings.getBoolean("firstrun", true);
        Log.i(Boolean.toString(firstrun), "msg");
        if(firstrun){
            setDefaults();
        }
        else
            getSettings();
    }
    public void click_lista(View v){
        Intent ilista = new Intent(this, listActivity.class);
        startActivity((Intent)ilista);
    }
    public void setDefaults()
    {
        SharedPreferences.Editor editor = settings.edit();
        editor.putString("fontsize", "18");
        editor.putString("fontcolor", "BLACK");
        editor.commit();
    }
    public void click_opcje(View v)
    {
        Intent iopcje;
        iopcje = new Intent(this, settingsActivity.class);
        startActivity((Intent)iopcje);
    }
    public void getSettings(){
        String fontcolor = settings.getString("fontcolor", "");
        String fontsize = settings.getString("fontsize", "");
        RelativeLayout rl = (RelativeLayout)findViewById(R.id.main_layout);
        for(int i = 0; i<rl.getChildCount(); i++)
        {
            View v = rl.getChildAt(i);
            if(v instanceof TextView)
            {
                TextView text = (TextView)v;
                text.setTextColor(parseColor(fontcolor));
                text.setTextSize(parseFloat(fontsize));
            }
            else if(v instanceof EditText){
                EditText et = (EditText)v;
                et.setTextSize(parseFloat(fontsize));
                et.setTextColor(parseColor(fontcolor));
            }
            else if(v instanceof Button)
            {
                Button b = (Button)v;
                b.setTextSize(parseFloat(fontsize));
                b.setTextColor(parseColor(fontcolor));
            }
        }
    }
    //pierwsze uruchomienie aplikacji i wprowadzenie domyslnych ustawien
    protected void onResume() {
        super.onResume();
        if (settings.getBoolean("firstrun", true)) {
            Log.i("uruchomienie firstrun", "msg");
            settings.edit().putBoolean("firstrun", false).commit();
        }
    }
}

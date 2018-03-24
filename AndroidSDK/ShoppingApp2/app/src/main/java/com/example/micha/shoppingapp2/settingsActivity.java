package com.example.micha.shoppingapp2;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import static android.graphics.Color.parseColor;
import static java.lang.Float.parseFloat;
import static java.lang.Integer.parseInt;

public class settingsActivity extends Activity {
    EditText fontsize;
    EditText fontcolor;
    String fontsize_text;
    String fontcolor_text;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);
        getSettings();
    }
    public void save(View v){
        Context cont = getApplicationContext();
        fontsize  = (EditText)findViewById(R.id.edit_size);
        fontcolor = (EditText)findViewById(R.id.edit_color);
        fontsize_text = fontsize.getText().toString();
        fontcolor_text = fontcolor.getText().toString();
        if(fontcolor_text.length() == 0)
        {
            fontcolor_text = "BLACK";
        }
        if(fontsize_text.length() == 0 || parseInt(fontsize_text) > 24){
            fontsize_text = "14";
            Toast.makeText(getApplicationContext(), "Wprowadzono zbyt dużą czcionkę. Zmieniam na domyślne 14px",Toast.LENGTH_LONG ).show();
        }
        SharedPreferences settings = cont.getSharedPreferences("settings", 0);
        SharedPreferences.Editor editor = settings.edit();
        editor.putString("fontsize", fontsize_text);
        editor.putString("fontcolor", fontcolor_text);
        editor.commit();
        Toast.makeText(getApplicationContext(), "Zapisano!",Toast.LENGTH_LONG ).show();
    }
    public void getSettings(){
        SharedPreferences settings = this.getSharedPreferences("settings", 0);
        String fontcolor = settings.getString("fontcolor", "");
        String fontsize = settings.getString("fontsize", "");
        RelativeLayout rl = (RelativeLayout)findViewById(R.id.settings_layout);
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

}

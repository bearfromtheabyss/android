package com.example.micha.shoppingapp;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import static android.graphics.Color.parseColor;
import static java.lang.Float.parseFloat;

public class Config extends Activity {
    Database db = new Database(this);
    Boolean option = true;
    String name_mod;
    ArrayList<ListObject> list = null;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_config);
        getSettings();
        Intent i = getIntent();
        Bundle get = i.getExtras();
        if(get!=null) {
            option = get.getBoolean("option");
            name_mod = get.getString("name_mod");
        }
        db.open();
        list = getProducts(db);
        db.close();
        if(!option) {
            EditText et = findViewById(R.id.edit_name);
            et.setText(name_mod);
            et.setEnabled(false);
        }
    }
    public void getSettings(){
        SharedPreferences settings = this.getSharedPreferences("settings", 0);
        String fontcolor = settings.getString("fontcolor", "");
        String fontsize = settings.getString("fontsize", "");
        RelativeLayout rl = (RelativeLayout)findViewById(R.id.config_layout);
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
            else if(v instanceof Button) {
                Button b = (Button) v;
                b.setTextSize(parseFloat(fontsize));
                b.setTextColor(parseColor(fontcolor));
            }
        }
    }
    public ArrayList<ListObject> getProducts(Database db)
    {
        db.open();
        ArrayList<ListObject> products = db.getAllProducts();
        db.close();
        return products;
    }
    public void insert(View v)
    {

        db.open();
        EditText nazwa = findViewById(R.id.edit_name);
        EditText count = findViewById(R.id.edit_count);
        EditText price = findViewById(R.id.edit_price);
        if (nazwa.getText().length() > 0 && count.getText().length() > 0 && price.getText().length() > 0) {
            if (option) {
                Boolean found = false;
                for (int i = 0; i<list.size(); i++) {
                    Log.d("DEBUG:", list.get(i).getNazwa());
                    if (list.get(i).getNazwa().trim().equals(nazwa.getText().toString().trim())) {
                        found = true;
                    }
                }
                if (!found) {
                    db.insert(nazwa.getText().toString(), count.getText().toString(), price.getText().toString());
                    Toast.makeText(getApplicationContext(), "Produkt został dodany do listy.", Toast.LENGTH_LONG).show();
                } else {
                    Toast.makeText(getApplicationContext(), "Nie mogę dodać produktu, ponieważ taki już znajduje się na liście.", Toast.LENGTH_LONG).show();
                }
            } else {
                db.update(db.getIdfromName(nazwa.getText().toString()), name_mod, count.getText().toString(), price.getText().toString(), false);
                Toast.makeText(getApplicationContext(), "Produkt został zmodyfikowany!", Toast.LENGTH_LONG).show();
            }
        }
        else
        {
            Toast.makeText(getApplicationContext(), "Znaleziono puste pola.", Toast.LENGTH_LONG).show();

        }
        db.close();

    }
}

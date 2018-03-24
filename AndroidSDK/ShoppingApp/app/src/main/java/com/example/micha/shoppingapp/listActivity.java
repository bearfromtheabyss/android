package com.example.micha.shoppingapp;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import static android.graphics.Color.parseColor;
import static java.lang.Float.parseFloat;

public class listActivity extends Activity {
    Database db = null;
    private customAdapter listazakupow = null;
    ListView list = null;
    Integer pos;
    String name_to_modify;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        db = new Database(this);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list);
        list = findViewById(R.id.list);
        list.invalidateViews();
        list.setClickable(true);
        listazakupow = new customAdapter(this, getProducts(db));
        list.setAdapter(listazakupow);
        getSettings();
        longClickSettings(list);
        saveCheckBoxState(list);
    }
    public void longClickSettings(ListView list)
    {
        list.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> adapterView, View view, int pos, long id) {

                ListObject o = listazakupow.getItem(pos);
                String productToDelete = o.getNazwa();
                db.open();
                long pid = db.getIdfromName(productToDelete); // ID from database, not listview item id...
                db.delete(pid);
                db.close();
                listazakupow.updateList(getProducts(db));
                listazakupow.notifyDataSetChanged();
                Toast.makeText(getApplicationContext(), "Produkt został usunięty z listy.",Toast.LENGTH_LONG ).show();
                return false;
            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
        listazakupow.updateList(getProducts(db));
        listazakupow.notifyDataSetChanged();
    }
    public void setPosition(int pos)
    {
        this.pos = pos;
    }
    public void saveCheckBoxState(ListView list)
    {
        list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                CheckBox box = view.findViewById(R.id.buy_check);

            }
        });
    }

    public void getSettings(){
        SharedPreferences settings = this.getSharedPreferences("settings", 0);
        String fontcolor = settings.getString("fontcolor", "");
        String fontsize = settings.getString("fontsize", "");
        RelativeLayout rl = (RelativeLayout)findViewById(R.id.list_layout);
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
    //Pobierz produkty z Bazy
    public ArrayList<ListObject> getProducts(Database db)
    {
        db.open();
        ArrayList<ListObject> products = db.getAllProducts();
        db.close();
        return products;
    }
    //Dodawanie produktow
    public void add(View v)
    {
        Intent i = new Intent(this, Config.class);
        i.putExtra("option", true);
        startActivity(i);
    }
    public void edit(View v)
    {
        ListObject o = listazakupow.getItem(pos);
        String nazwa = o.getNazwa();
        Intent i = new Intent(this, Config.class);
        i.putExtra("option", false);
        i.putExtra("name_mod", nazwa);
        startActivity(i);
    }

    protected void onDestroy() {
        if(db != null)
           db.close();
        super.onDestroy();
    }
}
class customAdapter extends BaseAdapter {

    Context context;
    ArrayList <ListObject> data;
    private static LayoutInflater inflater = null;

    public customAdapter(Context context, ArrayList<ListObject> data) {
        // TODO Auto-generated constructor stub
        this.context = context;
        this.data = data;
        inflater = (LayoutInflater) context
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    @Override
    public int getCount() {
        // TODO Auto-generated method stub
        return data.size();
    }

    @Override
    public ListObject getItem(int position) {
        // TODO Auto-generated method stub
        return data.get(position);
    }

    @Override
    public long getItemId(int position) {
        // TODO Auto-generated method stub
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        // TODO Auto-generated method stub
        SharedPreferences settings = context.getSharedPreferences("settings", 0);
        String fontcolor = settings.getString("fontcolor", "");
        String fontsize = settings.getString("fontsize", "");
        final Database db = new Database(context);
        View vi = convertView;
        if (vi == null)
            vi = inflater.inflate(R.layout.listview_item, null);
        TextView text = (TextView) vi.findViewById(R.id.item_desc);
        final CheckBox box = (CheckBox)vi.findViewById(R.id.buy_check);
        Button btn = vi.findViewById(R.id.edit_product);
        final ListObject obj = data.get(position);
        Boolean checked = obj.isZapisane();
        //settings
        text.setTextSize(parseFloat(fontsize));
        text.setTextColor(parseColor(fontcolor));
        btn.setTextSize(parseFloat(fontsize));
        btn.setTextColor(parseColor(fontcolor));
        String stxt = "Nazwa: " + obj.getNazwa() + " Ile: " + obj.getIle() + " Cena: " + obj.getCena();
        text.setText(stxt);
        if(checked){
            box.setChecked(true);
        }
            else
                box.setChecked(false);
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String nazwa = obj.getNazwa();
                Intent i = new Intent(context, Config.class);
                i.putExtra("option", false);
                i.putExtra("name_mod", nazwa);
                context.startActivity(i);
            }
        });
        box.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                db.open();
                String pid = obj.getId(); // ID from database, not listview item id...
                db.update(Long.parseLong(pid), obj.getNazwa(), obj.getIle(), obj.getCena(), b);
                db.close();
            }
        });
        return vi;
    }
    //odswiez liste
    public void updateList(ArrayList<ListObject> products) {
        this.data.clear();
        this.data.addAll(products);
    }

}

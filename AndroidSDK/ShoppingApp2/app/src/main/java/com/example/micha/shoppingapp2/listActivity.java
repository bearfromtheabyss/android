package com.example.micha.shoppingapp2;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
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

import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import static android.graphics.Color.parseColor;
import static java.lang.Float.parseFloat;
//podmienic baze na chmure
public class listActivity extends Activity {
    Cloud db = null;
    public customAdapter listazakupow = null;
    ListView list = null;
    //ArrayList<ListObject> firebaselist = null;
    Integer pos;
    FirebaseDatabase database;
    ArrayList<ListObject> products = null;
    String name_to_modify;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        db = new Cloud();
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list);
        //firebaselist = db.getProducts();
        list = findViewById(R.id.list);
        list.invalidateViews();
        list.setClickable(true);
        products = new ArrayList<>();
        database = FirebaseDatabase.getInstance();
        final DatabaseReference ref = database.getReference("products");
        ref.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                products.clear();
                for(DataSnapshot d : dataSnapshot.getChildren()){
                    products.add(d.getValue(ListObject.class));
                }
                listazakupow = new customAdapter(getBaseContext(), products);
                list.setAdapter(listazakupow);
                listazakupow.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                Log.e("ERROR", "Connection failed");
            }
        });
        ref.addChildEventListener(new ChildEventListener() {
            ArrayList<String> keys = new ArrayList<>();
            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                keys.add(dataSnapshot.getKey());
            }

            @Override
            public void onChildChanged(DataSnapshot dataSnapshot, String s) {

            }

            @Override
            public void onChildRemoved(DataSnapshot dataSnapshot) {
//                String key = dataSnapshot.getKey();
//                int idx = keys.indexOf(key);
////                if(products.size() > 1) {
////                    products.remove(idx - 1);
////                }
////                else
////                    products.clear();
//                products.remove(idx);
//                keys.remove(idx);
                ListObject o = dataSnapshot.getValue(ListObject.class);
                products.remove(o);
                listazakupow.notifyDataSetChanged();
            }

            @Override
            public void onChildMoved(DataSnapshot dataSnapshot, String s) {

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
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
                String idn = o.getNazwa();
                db.delete(idn);
                Toast.makeText(getApplicationContext(), "Produkt został usunięty z listy.",Toast.LENGTH_LONG ).show();
                return false;
            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
        final DatabaseReference ref = database.getReference("products");
        ref.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                products.clear();
                for(DataSnapshot d : dataSnapshot.getChildren()){
                    products.add(d.getValue(ListObject.class));
                }
                listazakupow = new customAdapter(getBaseContext(), products);
                list.setAdapter(listazakupow);
                listazakupow.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                Log.e("ERROR", "Connection failed");
            }
        });

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
        final Cloud db = new Cloud();
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
                db.update(obj.getNazwa(), obj.getIle(), obj.getCena(), b);
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

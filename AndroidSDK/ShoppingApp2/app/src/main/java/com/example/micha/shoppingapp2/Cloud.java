package com.example.micha.shoppingapp2;

import android.util.Log;
import android.widget.ArrayAdapter;

import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * Created by micha on 09.12.2017.
 */

public class Cloud {
    FirebaseDatabase database;
    public ArrayList<ListObject> lo = null;
    String id = null;
    public Cloud() {
        database = FirebaseDatabase.getInstance();
        lo = new ArrayList<>();
    }
    public void insert(String name, String count, String price)
    {
        ListObject l = new ListObject(name, count, price, false);
        DatabaseReference prod = database.getReference("products");
        prod.child(name).setValue(l);
    }
    public void update(String name, String count, String price, Boolean checked)
    {
        DatabaseReference ref = database.getReference("products");
        ListObject l = new ListObject(name, count, price, checked);
        ref.child(name).setValue(l);
    }
    public void delete(String id)
    {
        final String deleteid = id;
        DatabaseReference ref = database.getReference().child("products").child(id);
        ref.removeValue();
    }

}

package com.example.micha.venuetracker;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;

/**
 * Created by micha on 13.01.2018.
 */

public class Cloud {
    FirebaseDatabase database;
    String id = null;
    public Cloud() {
        database = FirebaseDatabase.getInstance();
    }
    public void insert(LocationObject lo)
    {
        String name = lo.getName();
        DatabaseReference prod = database.getReference("venues");
        prod.child(name).setValue(lo);
    }
    public void update(String name, String desc, float rad, double loong, double lat)
    {
        DatabaseReference ref = database.getReference("venue");
        LocationObject l = new LocationObject(name, desc, rad, loong, lat);
        ref.child(name).setValue(l);
    }
    public void delete(String id)
    {
        final String deleteid = id;
        DatabaseReference ref = database.getReference().child("products").child(id);
        ref.removeValue();
    }
}

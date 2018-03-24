package com.example.micha.shoppingapp;

import android.content.ContentProvider;
import android.content.ContentResolver;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.net.Uri;
import android.util.Log;

import java.util.ArrayList;

/**
 * Created by micha on 22.11.2017.
 */

public class Database {
    private static final String DEBUG_TAG = "ProductsDB";
    private static final int DB_VERSION = 1;
    private static final String DB_NAME = "shopping.db";
    private static final String DB_TABLE = "products";
    public static final String ID = "id";
    public static final String ID_OPTIONS = "INTEGER PRIMARY KEY AUTOINCREMENT";
    public static final int ID_COLUMN = 0;
    public static final String NAME = "name";
    public static final String NAME_OPTIONS = "TEXT NOT NULL";
    public static final int NAME_COLUMN = 1;
    public static final String COUNT = "count";
    public static final String COUNT_OPTIONS = "TEXT NOT NULL";
    public static final int COUNT_COLUMN = 2;
    public static final String PRICE = "price";
    public static final String PRICE_OPTIONS = "TEXT NOT NULL";
    public static final int PRICE_COLUMN = 3;
    public static final String CHECKBOX = "checkbox";
    public static final String CHECKBOX_OPTIONS = "INTEGER DEFAULT 0";
    public static final int CHECKBOX_COLUMN = 4;

    private static final String DB_CREATE =
            "CREATE TABLE " + DB_TABLE + "( " + ID + " " + ID_OPTIONS + ", " +
                    NAME + " " + NAME_OPTIONS + ", " +
                    COUNT + " " + COUNT_OPTIONS + ", " +
                    PRICE + " " + PRICE_OPTIONS + ", " +
                    CHECKBOX + " " + CHECKBOX_OPTIONS +" );";
    private static final String DB_DROP =
            "DROP TABLE IF EXIST " + DB_TABLE;

    private SQLiteDatabase db;
    private Context context;
    private DatabaseHelper dbHelper;
    private static class DatabaseHelper extends SQLiteOpenHelper {
        public DatabaseHelper(Context context, String name,
                              SQLiteDatabase.CursorFactory factory, int version) {
            super(context, name, factory, version);
        }

        @Override
        public void onCreate(SQLiteDatabase db) {
            db.execSQL(DB_CREATE);
        }

        @Override
        public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
            db.execSQL(DB_CREATE);
            onCreate(db);
        }
    }
    public Database(Context context) {
        this.context = context;
    }

    public Database open()
    {
        dbHelper = new DatabaseHelper(context, DB_NAME, null, DB_VERSION);
        try{
            db=dbHelper.getWritableDatabase();
        } catch(SQLException e){
            db = dbHelper.getReadableDatabase();
    }
        return this;
    }
    public void close() {
        dbHelper.close();
    }
    public long insert(String name, String count, String price) {
        ContentValues newProductValues = new ContentValues();
        newProductValues.put(NAME, name);
        newProductValues.put(COUNT, count);
        newProductValues.put(PRICE, price);
        return db.insert(DB_TABLE, null, newProductValues);
    }
    public boolean delete(long id){
        String where = ID + "=" + id;
        return db.delete(DB_TABLE, where, null) > 0;
    }
    public long getIdfromName(String name)
    {
        long found = 0;
        Cursor c = db.rawQuery("SELECT id FROM " + DB_TABLE + " WHERE name='" + name + "'", null);
        if(c.moveToFirst()){
            do
            {
                found = c.getLong(c.getColumnIndex(ID));
                Log.i(Long.toString(found), "msg");
            }while(c.moveToNext());
        }
        return found;
    }
    public ArrayList<ListObject> getAllProducts() {
        String[] columns = {ID, NAME, COUNT, PRICE, CHECKBOX};
        ArrayList <ListObject> products = new ArrayList<>();
        ContentValues cV = new ContentValues();
        Cursor cursor = db.query(DB_TABLE, columns, null, null, null, null, null);
        if(cursor!=null && cursor.getCount() > 0) {
            Log.i(Integer.toString(cursor.getCount()), "COUNT:");
            if (cursor.moveToFirst()) {
               do{
                    boolean ischecked = false;
                    String id = cursor.getString(cursor.getColumnIndex(ID));
                    String name = cursor.getString(cursor.getColumnIndex(NAME));
                    String count = cursor.getString(cursor.getColumnIndex(COUNT));
                    String price = cursor.getString(cursor.getColumnIndex(PRICE));
                    Integer checked = cursor.getInt(cursor.getColumnIndex(CHECKBOX));
                    cV.put(ID, id);
                    cV.put(NAME, name);
                    cV.put(COUNT, count);
                    cV.put(PRICE, price);
                    if(checked == 1) ischecked = true;
                    cV.put(CHECKBOX, ischecked);
                    Log.i("OBJECT:", id + " " + name + " " + count + " " + String.valueOf(checked));
                    ListObject obj = new ListObject(id, name, count, price, ischecked);
                    products.add(obj);
                } while (cursor.moveToNext());
            }
            else Log.i(DEBUG_TAG, "Database is empty :C");
        }
        //Uri uri = getContentResolver()
        return products;
    }
    public boolean update(long id, String name, String count, String price, boolean checked) {
        String where = ID + "=" + id;
        int numcheck = checked ? 1 : 0; //sqlite nie obsluguje wartosci logicznych
        ContentValues updateProductValues = new ContentValues();
        updateProductValues.put(NAME, name);
        updateProductValues.put(COUNT, count);
        updateProductValues.put(PRICE, price);
        updateProductValues.put(CHECKBOX, numcheck);
        Log.d("MSG", String.valueOf(numcheck));
        return db.update(DB_TABLE, updateProductValues, where, null) > 0;
    }
}

//package com.example.micha.shoppingapp2;
//
//import android.content.ContentProvider;
//import android.content.ContentValues;
//import android.database.Cursor;
//import android.net.Uri;
//import android.support.annotation.NonNull;
//import android.support.annotation.Nullable;
//
///**
// * Created by micha on 24.11.2017.
// */
//
//public class ProductsProvider extends ContentProvider {
//    Database db;
//    public static final String AUTHORITY = "com.example.micha.shoppingapp";
//    private static final String PATH = "products";
//    public static final Uri CONTENT_URI = Uri.parse("content://" + AUTHORITY + "/" + PATH);
//
//
//    @Override
//    public boolean onCreate() {
//        db = new Database(getContext());
//        return false;
//    }
//
//    @Nullable
//    @Override
//    public Cursor query(@NonNull Uri uri, @Nullable String[] strings, @Nullable String s, @Nullable String[] strings1, @Nullable String s1) {
//        return null;
//    }
//
//    @Nullable
//    @Override
//    public String getType(@NonNull Uri uri) {
//        return null;
//    }
//
//    @Nullable
//    @Override
//    public Uri insert(@NonNull Uri uri, @Nullable ContentValues contentValues) {
//        return null;
//    }
//
//    @Override
//    public int delete(@NonNull Uri uri, @Nullable String s, @Nullable String[] strings) {
//        return 0;
//    }
//
//    @Override
//    public int update(@NonNull Uri uri, @Nullable ContentValues contentValues, @Nullable String s, @Nullable String[] strings) {
//        return 0;
//    }
//}

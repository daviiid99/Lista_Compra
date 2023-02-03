package com.daviiid99.basededatos;

import android.app.Activity;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.provider.BaseColumns;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class accederBaseDatos extends Activity {

    final Context context;
    final GestorBaseDatos db;

    final SQLiteDatabase sql;

    protected ArrayList<String> productNames = new ArrayList<>();

    public accederBaseDatos(Context context){
        this.context = context;
        this.db = new GestorBaseDatos(this.context);
        this.sql = this.db.getWritableDatabase();
    }

    public void addValues(String producto, Integer precio) {
        ContentValues values = new ContentValues();
        values.put(GestorBaseDatos.FeedEntry.PRODUCT_NAME, producto);
        values.put(GestorBaseDatos.FeedEntry.PRODUCT_PRICE, precio);
        insertRowDataBase(values);
    }

    public void insertRowDataBase(ContentValues values){
        this.sql.insert(GestorBaseDatos.FeedEntry.TABLE_NAME, null, values);
    }

    public ArrayList<String> fetchRows(){
        String[] projection = {
                GestorBaseDatos.FeedEntry.PRODUCT_NAME
        };

        String sortOrder = GestorBaseDatos.FeedEntry.PRODUCT_NAME + " DESC";

        Cursor cursor = sql.query(
                GestorBaseDatos.FeedEntry.TABLE_NAME ,
                projection,
                null,
                null,
                null,
                null,
                sortOrder
        );

        List itemIds = new ArrayList<>();
        while(cursor.moveToNext()) {
            String itemId = cursor.getString(
                    cursor.getColumnIndexOrThrow(GestorBaseDatos.FeedEntry.PRODUCT_NAME));
            itemIds.add(itemId);
        }
        cursor.close();

        return (ArrayList<String>) itemIds;


    }
    public void deleteItem(String producto){
        String selection = GestorBaseDatos.FeedEntry.PRODUCT_NAME + " LIKE ?";
        String[] selectionArgs = { producto };
        int deletedRows = sql.delete(GestorBaseDatos.FeedEntry.TABLE_NAME, selection, selectionArgs);
    }

    public void deleteAll(){
        sql.execSQL("DELETE FROM lista WHERE _ID >=0");
    }


}

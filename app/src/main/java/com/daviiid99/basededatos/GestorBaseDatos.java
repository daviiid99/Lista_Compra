package com.daviiid99.basededatos;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.provider.BaseColumns;

import androidx.annotation.Nullable;

public class GestorBaseDatos extends SQLiteOpenHelper{

    public static final String DATABASE_NAME = "listacompra.db";
    public static final int  DATABASE_VERSION = 1;
    public GestorBaseDatos( @Nullable  Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION );
    }

    public static class FeedEntry implements BaseColumns{
        public static final String TABLE_NAME = "lista";
        public static final String PRODUCT_NAME = "producto";
        public static final String PRODUCT_PRICE = "precio";
    }

    protected static final String SQL_CREATE_ENTRIES =
            "CREATE TABLE " + FeedEntry.TABLE_NAME + " (" +
                    FeedEntry._ID + " INTEGER PRIMARY KEY AUTOINCREMENT," +
                    FeedEntry.PRODUCT_NAME + " TEXT," +
                    FeedEntry.PRODUCT_PRICE + " INTEGER)";

    protected static final String SQL_DELETE_ENTRIES =
            "DROP TABLE IF EXISTS " + FeedEntry.TABLE_NAME;


    @Override
    public void onCreate(SQLiteDatabase db) {
        // El método onCreate() recibe como argumento la base de datos
        // Ejecuta una sentencia con la base de datos recibida
        // Crea la tabla con la intruccion SQL recibida
        db.execSQL(SQL_CREATE_ENTRIES);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // El método onUpgrade() recibe como parametro la base de datos y la version anterior
        // Usa la base de datos y ejecuta la sentencia que borra la tabla
        // Vuelve a crear la tabla llamando al onCreate()
        db.execSQL(SQL_CREATE_ENTRIES);
        onCreate(db);
    }

    @Override
    public void onDowngrade(SQLiteDatabase db, int oldVersion, int newVersion){
        // El método onDowngrade() recibe como parametro la base de datos, la version anterior de esta y la nueva version
        // Llama entonces al método onUpgrade() que se encarga de borrar la version anterior y restaurar la nueva, en este caso una versión aún más anterior
        onUpgrade(db, oldVersion, newVersion);
    }
}
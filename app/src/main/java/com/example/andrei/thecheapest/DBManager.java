package com.example.andrei.thecheapest;

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.widget.Toast;

/**
 * Created by Andrei on 10/21/2017.
 */

public class DBManager {

    private SQLiteDatabase sqlDB;

    static final String DBName="Market";
    static final String TableProduse="produse";
    static final String TableAdrese="adrese";
    static final String TableMagazine="magazine";
    static final String TableAdreseProduse="adrese_produse";
    static final String ColImagine="imagine";
    static final String ColNume="nume";
    static final String ColAdresa="adresa";
    static final String ColLat="latitudine";
    static final String ColLong="longitudine";
    static final String ColIdMagazin="id_magazin";
    static final String ColIdProdus="id_produs";
    static final String ColIdAdresa="id_adresa";
    static final String ColPret = "pret";
    static final int DBVersion=1;

    static final String CreateTable1 = "Create table IF NOT EXISTS " + TableProduse + "(ID integer PRIMARY KEY AUTOINCREMENT," + ColImagine + " text," + ColNume + " text)";
    static final String CreateTable2 = "Create table IF NOT EXISTS " + TableAdrese + "(ID integer PRIMARY KEY AUTOINCREMENT," + ColAdresa + " text," + ColLat + " double" + ColLong + " double" + ColIdMagazin + " integer)";
    static final String CreateTable3 = "Create table IF NOT EXISTS " + TableMagazine + "(ID integer PRIMARY KEY AUTOINCREMENT," + ColNume + " text)";
    static final String CreateTable4 = "Create table IF NOT EXISTS " + TableAdreseProduse + "(ID integer PRIMARY KEY AUTOINCREMENT," + ColIdProdus + " integer" + ColIdAdresa + " integer," + ColPret + " double)";


    private static class DatabaseHelperMarket extends SQLiteOpenHelper{

        Context context;
        DatabaseHelperMarket(Context context){
            super(context, DBName, null, DBVersion);
            this.context = context;
        }

        @Override
        public void onCreate(SQLiteDatabase db) {
            db.execSQL(CreateTable1);
            db.execSQL(CreateTable2);
            db.execSQL(CreateTable3);
            db.execSQL(CreateTable4);
            Toast.makeText(context, "Tables are created", Toast.LENGTH_LONG).show();
        }

        @Override
        public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
            db.execSQL("Drop table IF EXISTS " + TableProduse);
            db.execSQL("Drop table IF EXISTS " + TableAdrese);
            db.execSQL("Drop table IF EXISTS " + TableMagazine);
            db.execSQL("Drop table IF EXISTS " + TableAdreseProduse);
            onCreate(db);
        }
    }

    public DBManager(Context context)
    {
        DatabaseHelperMarket db = new DatabaseHelperMarket(context);
        sqlDB = db.getWritableDatabase();
    }
    public long InsertProduse(ContentValues values){
        long ID = sqlDB.insert(TableProduse,"",values);
        return ID;
    }
    public long InsertAdrese(ContentValues values){
        long ID = sqlDB.insert(TableAdrese,"",values);
        return ID;
    }
    public long InsertMagazine(ContentValues values){
        long ID = sqlDB.insert(TableMagazine,"",values);
        return ID;
    }
    public long InsertAdreseProduse(ContentValues values){
        long ID = sqlDB.insert(TableAdreseProduse,"",values);
        return ID;
    }
}

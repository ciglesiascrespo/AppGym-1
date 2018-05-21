package com.iglesias.c.appgym.Db;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

/**
 * Created by Ciglesias on 18/02/2018.
 */

public class DbHelper extends SQLiteOpenHelper {
    private static final int DATABASE_VERSION = 1;
    private static final String DATABASE_NAME = "data";
    private final String TAG = getClass().getName();

    public DbHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
        Log.e(TAG, "Creo DbHelper");
    }

    @Override
    public void onCreate(SQLiteDatabase db) {

        db.execSQL(UserDb.getCreateTable());
        insertData(db);
    }

    private void insertData(SQLiteDatabase db) {
        ContentValues cv = new ContentValues();
        cv.put(UserDb.KEY_DIAS, 25);
        cv.put(UserDb.KEY_NRO_DOCUMENTO, "1234");
        cv.put(UserDb.KEY_ID,"3,84,131,255,255,255,255,252,248,240,248,248,248,248,248,248,248,248,248,248,0,0,0,0,0,0,0,0,58,105,86,192,72,168,107,6,101,135,84,13,89,14,92,136,89,139,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,232,0,198,193,195,1,33,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,48,48,48,48,48,48,48,48,52,56,44,52,56,44,52,44,255,0,1,255,0,255,255,255,255,255,255,255,255,0,255,4,0,0,0,0,109,0,53,91,155,67,207,246,241,231,175,11,184,60,252,132,248,255,250,78,90,229,55,247,184,19,244,187,151,52,173,99,227,40,197,184,247,233,168,102,110,243,250,204,190,252,127,247,81,94,39,88,238,187,38,125,242,127,84,202,88,250,43,133,36,0,0,0,0,0,1,27,0,232,0,0,0,197,196,192,193,194,198,1,1,23,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,48,48,48,48,48,48,48,48,48,48,48,48,52,44,56,52,44,56,52,44,56,52,44,56,52,44,56,52,44,56,53,44,255,255,0,0,3,1,255,255,0,0,255,255,255,255,255,255,255,255,255,255,255,255,255,255,255,255,255,0,0,255,255,4,1,0,3,0,0,0,4,0,43,109,4,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,1,0,3,0,0,0,0,0,0,0,0,0,0,42,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,58,49,56,10,101,100,97,121,99,97,97,116,114,116,32,111,116,110,101,46,13,121,32,48,48,10,97,107,116,108,110,116,255,255,0,128,232,239,255,255,7,19,0,0,3,0,255,255,0,0,255,255,255,");
        db.insert(UserDb.TABLE, null, cv);
    }


    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

        db.execSQL("DROP TABLE IF EXISTS " + UserDb.TABLE);

        onCreate(db);
    }

    public long insert(String tableName, ContentValues cv) {
        long i = 0;
        SQLiteDatabase db = this.getWritableDatabase();
        try {
            i = db.insert(tableName, null, cv);
            //if (db.isOpen()) db.close();
        } catch (Exception e) {
            //db.close();
            Log.e(TAG, "Error insertando en la base de datos: " + e.getMessage());
            e.printStackTrace();

        }
        return i;

    }

    public Cursor execSql(String query) {
        Cursor c = null;
        SQLiteDatabase db = this.getWritableDatabase();

        c = db.rawQuery(query, null);
        // db.close();


        return c;
    }

    public void update(String tableName, ContentValues cv, String condition) {
        SQLiteDatabase db = this.getWritableDatabase();
        try {
            db.update(tableName, cv, condition, null);
            //  db.close();
        } catch (Exception e) {
            // if (db.isOpen()) db.close();
            Log.e(TAG, "Error update: " + e.getMessage());
            e.printStackTrace();
        }
    }

    public void delete(String tableName, String condition) {
        SQLiteDatabase db = this.getWritableDatabase();

        try {
            db.delete(tableName, condition, null);
            //  db.close();
        } catch (Exception e) {
            //  if (db.isOpen()) db.close();
            Log.e(TAG, "Error delete: " + e.getMessage());
            e.printStackTrace();
        }
    }
}

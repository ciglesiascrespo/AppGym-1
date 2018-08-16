package com.iglesias.c.appgym.Db;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.util.Log;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.iglesias.c.appgym.Models.User;
import com.iglesias.c.appgym.RestApi.ConstantesRestApi;
import com.iglesias.c.appgym.RestApi.Model.InfoLogin;
import com.iglesias.c.appgym.RestApi.Model.ResultLogin;

/**
 * Created by Ciglesias on 18/02/2018.
 */

public class DbHandler {

    private DbHelper dbHelper;
    private static DbHandler instance = null;
    public String TAG = getClass().getName();
    private DatabaseReference databaseReference;

    protected DbHandler(Context context) {
        Log.e("DbHandler", "creo instancia handler");
        dbHelper = new DbHelper(context);
    }

    public static DbHandler getInstance(Context context) {
        if (instance == null) {
            instance = new DbHandler(context);
        }
        Log.e("DbHandler", "uso instancia handler");
        return instance;
    }

    public Cursor execSql(String sql) {
        Log.e(TAG, sql);
        return dbHelper.execSql(sql);
    }

    public int actualizaUsuario(String nro, String id) {
        ContentValues cv = new ContentValues();
        cv.put(UserDb.KEY_ID, id);

        databaseReference = FirebaseDatabase.getInstance().getReference();
        User user = new User(nro, id);
        databaseReference.child("Users/" + user.getId()).setValue(user);

        return dbHelper.update(UserDb.TABLE, cv, UserDb.KEY_NRO_DOCUMENTO + " = " + nro);

    }

    public boolean verificaUsuario(String nro, String id) {
        int cnt = 0;
        Cursor c = null;
        boolean result = true;

        try {

            String query = "Select count(*)  as cnt From " + UserDb.TABLE +
                    " where " + UserDb.KEY_NRO_DOCUMENTO + " = '" + nro + "' or " + UserDb.KEY_ID + " = '" + id + "' ;";
            c = dbHelper.execSql(query);

            if (c.moveToFirst()) {
                if (!c.isNull(c.getColumnIndex("cnt"))) {
                    cnt = c.getInt(c.getColumnIndex("cnt"));
                }
            }

            result = cnt > 0;
        } catch (Exception e) {
            if (c != null && !c.isClosed()) {
                c.close();
            }
            Log.e(TAG, "Error validando usuario: " + e.getMessage());
            e.printStackTrace();
        } finally {
            if (c != null && !c.isClosed()) {
                c.close();
            }
        }


        return result;
    }

    public Long registraUsuario(String nro, String id) {
        ContentValues cv = new ContentValues();
        cv.put(UserDb.KEY_ID, id);
        cv.put(UserDb.KEY_NRO_DOCUMENTO, nro);
        cv.put(UserDb.KEY_DIAS, 1);

        databaseReference = FirebaseDatabase.getInstance().getReference();
        User user = new User(nro, id);
        databaseReference.child("Users/" + user.getId()).setValue(user);

        return dbHelper.insert(UserDb.TABLE, cv);
    }

    public ResultLogin verificaUsuarioDb(String nro) {
        int cnt = 0;
        String id = "";
        Cursor c = null;
        ResultLogin result = new ResultLogin();
        result.setErrorCode(ConstantesRestApi.CODE_ERROR);

        try {

            String query = "Select " + UserDb.KEY_DIAS + "   as cnt, " + UserDb.KEY_ID + " From " + UserDb.TABLE +
                    " where " + UserDb.KEY_NRO_DOCUMENTO + " = '" + nro + "';";
            c = dbHelper.execSql(query);

            if (c.moveToFirst()) {
                if (!c.isNull(c.getColumnIndex("cnt"))) {
                    cnt = c.getInt(c.getColumnIndex("cnt"));
                }

                if (!c.isNull(c.getColumnIndex(UserDb.KEY_ID))) {
                    id = c.getString(c.getColumnIndex(UserDb.KEY_ID));
                }

                result.setErrorCode(0);
            }


            InfoLogin infoLogin = new InfoLogin();
            infoLogin.setNombre("No registra");
            infoLogin.setDias(cnt);
            infoLogin.setNroDocumento(nro);
            infoLogin.setIdHuella(id);

            result.setInfo(infoLogin);
        } catch (Exception e) {
            if (c != null && !c.isClosed()) {
                c.close();
            }
            Log.e(TAG, "Error validando usuario: " + e.getMessage());
            e.printStackTrace();
        } finally {
            if (c != null && !c.isClosed()) {
                c.close();
            }
        }


        return result;
    }


}

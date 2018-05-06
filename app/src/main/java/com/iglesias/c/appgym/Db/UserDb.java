package com.iglesias.c.appgym.Db;

/**
 * Created by Ciglesias on 18/02/2018.
 */

public class UserDb {
    public static final String TABLE = "usuarios";

    public static final String KEY_ID = "id_huella";
    public static final String KEY_NRO_DOCUMENTO = "nro_documento";
    public static final String KEY_DIAS = "dias";


    public static final int ID_USUARIO_CyM = 1;

    public static String getCreateTable() {
        String query = "CREATE TABLE `" + TABLE + "` " +
                " (`" + KEY_ID + "` TEXT, `" + KEY_NRO_DOCUMENTO + "` TEXT, `" + KEY_DIAS + "` INTEGER,  PRIMARY KEY(`" + KEY_NRO_DOCUMENTO + "`) )";

        return query;
    }
}

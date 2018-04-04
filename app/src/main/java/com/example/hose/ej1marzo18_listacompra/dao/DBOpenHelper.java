package com.example.hose.ej1marzo18_listacompra.dao;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by hose on 07/03/2018.
 */

public class DBOpenHelper extends SQLiteOpenHelper {
    private static DBOpenHelper dbOpen;
    private static final String BASEDATOS_NOMBRE = "productos";
    private static final int BASEDATOS_VERSION = 1;

    private DBOpenHelper(Context context) {
        super(context, BASEDATOS_NOMBRE, null, BASEDATOS_VERSION);
    }

    public static DBOpenHelper getInstance(Context context){
        if (dbOpen == null){
            dbOpen = new DBOpenHelper(context);
        }
        return dbOpen;
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        // TODO Auto-generated method stub
        try {
            // Crear la tabla
            db.execSQL("CREATE TABLE productos(id INTEGER NOT NULL"
                    + ",nombre NVARCHAR(40) NOT NULL"
                    + ",cantidad NVARCHAR(40) NOT NULL"
                    + ",comprado INTEGER NOT NULL);");
            db.execSQL("INSERT INTO productos (id,nombre,cantidad,comprado) VALUES (0,'Probando','1 kg.',0);");
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    @Override
    public void onUpgrade(SQLiteDatabase arg0, int arg1, int arg2) {
        // TODO Auto-generated method stub

    }

}

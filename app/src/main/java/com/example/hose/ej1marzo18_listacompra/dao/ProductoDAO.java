package com.example.hose.ej1marzo18_listacompra.dao;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.example.hose.ej1marzo18_listacompra.model.Producto;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by hose on 07/03/2018.
 */

public class ProductoDAO {

    public ProductoDAO() {
    }

    public List<Producto> cargar_lista(Context context) {
        List<Producto> res = new ArrayList<Producto>();
        Cursor c = null;

        try {
            // Obtener acceso de solo lectura
            SQLiteDatabase db = DBOpenHelper.getInstance(context).getReadableDatabase();
            String sql = "SELECT * FROM productos;";
            c = db.rawQuery(sql, null);
            // Leer resultados del cursor e insertarlos en la lista
            while (c.moveToNext()) {
                Producto nuevo = new Producto();
                nuevo.setIdProducto(c.getInt(0));
                nuevo.setNombre(c.getString(1));
                nuevo.setCantidad(c.getString(2));
                boolean comprado = (c.getInt(3)==0?false:true);
                nuevo.setComprado(comprado);
                res.add(nuevo);
            }
        } finally {
            if (c != null) {
                c.close();
            }
        }
        return res;
    }

    public void actualizarBBDD(Context context, Producto p){
        SQLiteDatabase db = DBOpenHelper.getInstance(context).getWritableDatabase();
        db.execSQL("UPDATE productos "
                + "SET nombre='"+p.getNombre()+"', " +
                " cantidad='"+p.getCantidad()+"',"+
                " comprado="+(p.isComprado()?1:0)+
                " WHERE id="+p.getIdProducto()+";");
        db.close();
    }

    public void insertarBBDD(Context context, Producto p){
        SQLiteDatabase db = DBOpenHelper.getInstance(context).getWritableDatabase();
        db.execSQL("INSERT INTO productos (id,nombre,cantidad,comprado) VALUES ("+p.getIdProducto()+",'"
                + p.getNombre()+"','"+p.getCantidad()+"',"+(p.isComprado()?1:0)+");");
        db.close();
    }

    public void eliminarBBDD(Context context){
        SQLiteDatabase db = DBOpenHelper.getInstance(context).getWritableDatabase();
        db.execSQL("DELETE FROM productos;");
        db.close();
    }

    public void eliminar(Context context, Producto producto){
        SQLiteDatabase db = DBOpenHelper.getInstance(context).getWritableDatabase();
        db.execSQL("DELETE FROM productos WHERE id="+producto.getIdProducto()+";");
        db.close();
    }

    public int getId(Context context){
        Cursor c = null;
        int max=0;
        try {
            // Obtener acceso de solo lectura
            SQLiteDatabase db = DBOpenHelper.getInstance(context).getReadableDatabase();
            String sql = "SELECT MAX(id) FROM productos;";
            c = db.rawQuery(sql, null);
            // Leer resultados del cursor e insertarlos en la lista
            while (c.moveToNext()) {
                max = c.getInt(0);
            }
        } finally {
            if (c != null) {
                c.close();
            }
        }
        return max+1;
    }

}

package com.example.hose.ej1marzo18_listacompra.activity;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.ContextMenu;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.example.hose.ej1marzo18_listacompra.R;
import com.example.hose.ej1marzo18_listacompra.adapter.ProductoAdapter;
import com.example.hose.ej1marzo18_listacompra.dao.ProductoDAO;
import com.example.hose.ej1marzo18_listacompra.model.Producto;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity implements AdapterView.OnItemClickListener, View.OnClickListener{
    private List<Producto> lista = new ArrayList<Producto>();
    private TextView tvNombre;
    private TextView tvCantidad;
    private ImageView ivCheck;
    private Button btnAnadir;
    private Button btnEliminar;
    private static final int GET_LIST = 0;
    private ProductoDAO productoDAO = new ProductoDAO();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        btnAnadir = findViewById(R.id.btnAnadir);
        btnAnadir.setOnClickListener(this);
        lista = productoDAO.cargar_lista(this);
        this.recarga();
    }

    @Override
    public void onClick(View view) {
            Intent intent = new Intent(this, ProductoActivity.class);
            this.startActivityForResult(intent, GET_LIST);
    }

    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == GET_LIST) {
            if (resultCode == RESULT_OK) {
                String nombre = data.getStringExtra("nombre");
                String cantidad = data.getStringExtra("cantidad");
                int posicion = data.getIntExtra("posicion",-1);
                boolean comprado = data.getBooleanExtra("comprado",false);
                Producto producto = new Producto();
                producto.setNombre(nombre);
                producto.setCantidad(cantidad);
                producto.setComprado(comprado);
                if (posicion<0) {
                    ProductoDAO productoDAO = new ProductoDAO();
                    producto.setIdProducto(productoDAO.getId(this));
                    productoDAO.insertarBBDD(this, producto);
                }
                else{
                    producto.setIdProducto(data.getIntExtra("id",-1));
                    productoDAO.actualizarBBDD(this,producto);
                }

                lista = productoDAO.cargar_lista(this);
                this.recarga();
            }
        }
    }

    @Override
    public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
        Producto producto = (Producto) adapterView.getAdapter().getItem(i);
        producto.setComprado(!producto.isComprado());
        productoDAO.actualizarBBDD(this,producto);
        this.recarga();
    }

    public void recarga() {
        ProductoAdapter adapter = new ProductoAdapter(this, R.layout.list_item, lista);
        ListView listView1 =  findViewById(R.id.listView1);
        listView1.setCacheColorHint(0);
        // Cargar el adaptador de listas
        listView1.setAdapter(adapter);
        listView1.setOnItemClickListener(this);
        this.registerForContextMenu(listView1);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_eliminar_lista:
                AlertDialog.Builder builder = new AlertDialog.Builder(this);
                String cadena = this.getResources().getString(R.string.desea)+" "+this.getResources().getString(R.string.lalista);
                builder.setMessage(cadena)
                        .setCancelable(false)
                        .setPositiveButton(R.string.si,
                                new DialogInterface.OnClickListener() {
                                    public void onClick(DialogInterface dialog,
                                                        int id) {
                                        lista.clear();
                                        productoDAO.eliminarBBDD(MainActivity.this);
                                        MainActivity.this.recarga();
                                    }
                                })
                        .setNegativeButton(R.string.no,
                                new DialogInterface.OnClickListener() {
                                    public void onClick(DialogInterface dialog,
                                                        int id) {
                                        dialog.cancel();
                                    }
                                });
                AlertDialog alert = builder.create();
                alert.show();
        }
        return true;
    }

    @Override
    public void onCreateContextMenu(ContextMenu menu, View v,
                                    ContextMenu.ContextMenuInfo menuInfo) {
        AdapterView.AdapterContextMenuInfo info = (AdapterView.AdapterContextMenuInfo) menuInfo;
        ListView lv = (ListView) v;
        Producto p = ((ProductoAdapter) lv.getAdapter())
                .getItem(info.position);
        menu.setHeaderTitle(p.getNombre());
        getMenuInflater().inflate(R.menu.contextual, menu);
    }

    @Override
    public boolean onContextItemSelected(MenuItem item) {
        AdapterView.AdapterContextMenuInfo info = (AdapterView.AdapterContextMenuInfo) item
                .getMenuInfo();
        final Producto p = lista.get(info.position);

        switch (item.getItemId()) {
            case R.id.action_eliminar_producto:
                AlertDialog.Builder builder = new AlertDialog.Builder(this);
                String cadena = this.getResources().getString(R.string.desea)+" "+this.getResources().getString(R.string.elproducto);
                cadena += " "+p.getNombre() + "?";
                builder.setMessage(cadena)
                        .setCancelable(false)
                        .setPositiveButton(R.string.si,
                                new DialogInterface.OnClickListener() {
                                    public void onClick(DialogInterface dialog,
                                                        int id) {
                                        lista.remove(p);
                                        ProductoDAO productoDAO = new ProductoDAO();
                                        productoDAO.eliminar(MainActivity.this,p);
                                        MainActivity.this.recarga();
                                    }
                                })
                        .setNegativeButton(R.string.no,
                                new DialogInterface.OnClickListener() {
                                    public void onClick(DialogInterface dialog,
                                                        int id) {
                                        dialog.cancel();
                                        // se puede poner dialog.dismiss()
                                    }
                                });
                AlertDialog alert = builder.create();
                alert.show();
                break;
            case R.id.action_editar_producto:
                Intent intent = new Intent(this, ProductoActivity.class);
                intent.putExtra("id",p.getIdProducto());
                intent.putExtra("nombre", p.getNombre());
                intent.putExtra("cantidad", p.getCantidad());
                intent.putExtra("posicion", info.position);
                intent.putExtra("comprado", p.isComprado());
                this.startActivityForResult(intent, GET_LIST);
        }
        return true;
    }
}

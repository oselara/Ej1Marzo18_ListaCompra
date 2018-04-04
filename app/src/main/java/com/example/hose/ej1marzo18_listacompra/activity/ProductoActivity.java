package com.example.hose.ej1marzo18_listacompra.activity;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.example.hose.ej1marzo18_listacompra.R;
import com.example.hose.ej1marzo18_listacompra.dao.ProductoDAO;
import com.example.hose.ej1marzo18_listacompra.model.Producto;

public class ProductoActivity extends AppCompatActivity implements View.OnClickListener{
    private EditText etnombre;
    private EditText etcantidad;
    private Button btnAnadir;
    private int posicion;
    private int id;
    private boolean comprado;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_producto);
        etnombre = findViewById(R.id.etproducto);
        etcantidad = findViewById(R.id.etcantidad);
        btnAnadir = findViewById(R.id.btnAnadirProducto);
        btnAnadir.setOnClickListener(this);
        id = this.getIntent().getIntExtra("id",-1);
        String nombre = this.getIntent().getStringExtra("nombre");
        String cantidad = this.getIntent().getStringExtra("cantidad");
        posicion = this.getIntent().getIntExtra("posicion",-1);
        comprado = this.getIntent().getBooleanExtra("comprado",false);
        if ((nombre!=null)&&(cantidad!=null)&&(posicion>-1)){
            etnombre.setText(nombre);
            etcantidad.setText(cantidad);
        }

    }

    @Override
    public void onClick(View view) {
        Intent intent = new Intent();
        intent.putExtra("nombre",etnombre.getText().toString());
        intent.putExtra("cantidad",etcantidad.getText().toString());
        intent.putExtra("posicion",posicion);
        intent.putExtra("id", id);
        intent.putExtra("comprado",comprado);
        setResult(RESULT_OK,intent);
        this.finish();
    }
}

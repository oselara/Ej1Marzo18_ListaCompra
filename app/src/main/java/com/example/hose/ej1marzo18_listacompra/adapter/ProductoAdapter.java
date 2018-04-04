package com.example.hose.ej1marzo18_listacompra.adapter;

/**
 * Created by hose on 07/03/2018.
 */

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.hose.ej1marzo18_listacompra.R;
import com.example.hose.ej1marzo18_listacompra.model.Producto;

import java.util.List;

/**
 * Created by hose on 07/03/2018.
 */

public class ProductoAdapter extends ArrayAdapter<Producto> {
    private List<Producto> elementos;
    private Context context;

    public ProductoAdapter(Context context, int textViewResourceId, List<Producto> elementos) {
        super(context, textViewResourceId, elementos);
        this.elementos = elementos;
        this.context = context;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        // Evita llamadas innecesarias a findViewById()
        ViewElemento holder;
        // Evitar reinflar los componentes
        if (convertView == null) {
            LayoutInflater vi = (LayoutInflater) context
                    .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = vi.inflate(R.layout.list_item, null);
            holder = new ViewElemento();
            holder.texto = (TextView) convertView.findViewById(R.id.tvNombre);
            holder.texto2 = (TextView) convertView.findViewById(R.id.tvCantidad);
            holder.foto = (ImageView) convertView.findViewById(R.id.ivImagen);
            convertView.setTag(holder);
        } else {
            holder = (ViewElemento) convertView.getTag();
        }
        Producto producto = elementos.get(position);
        if (producto != null) {
            // Construir el elemento
            holder.texto.setText(producto.getNombre());
            holder.texto2.setText(producto.getCantidad());
            boolean comprado=elementos.get(position).isComprado();
            int imagen = (comprado?R.drawable.chkok:R.drawable.chkko);
            holder.foto.setImageResource(imagen);
//            holder.foto.setImageDrawable(context.getResources().getDrawable());
        }
        return convertView;
    }
    public int getCount() {
        return elementos.size();
    }
    public Producto getItem(int position) {
        return elementos.get(position);
    }
    public long getItemId(int position) {
        return position;
    }
    static class ViewElemento {
        ImageView foto;
        TextView texto;
        TextView texto2;
    }
}


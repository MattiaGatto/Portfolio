package com.example.tesi;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.LinkedList;

public class CustomAdapter extends ArrayAdapter<Persona>{

    public CustomAdapter(Context context, int textViewResourceId, LinkedList objects) {
        super(context, textViewResourceId, objects);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        LayoutInflater inflater = (LayoutInflater) getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        convertView = inflater.inflate(R.layout.lista, null);
        TextView nome = (TextView)convertView.findViewById(R.id.textViewName);
        TextView numero = (TextView)convertView.findViewById(R.id.textViewNumber);
        TextView data = (TextView)convertView.findViewById(R.id.textViewData);
        Persona c = getItem(position);
        nome.setText(c.getNome()+" "+c.getCognome());
        numero.setText(c.getNumero());
        data.setText(c.getDataN());
        return convertView;
    }

}
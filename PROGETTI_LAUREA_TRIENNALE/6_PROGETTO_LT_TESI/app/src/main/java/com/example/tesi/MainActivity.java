package com.example.tesi;


import android.content.Intent;
import android.os.Build;
import android.support.annotation.RequiresApi;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;

import java.util.Comparator;
import java.util.LinkedList;
import java.util.StringTokenizer;

import android.database.Cursor;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {
    @RequiresApi(api = Build.VERSION_CODES.N)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ListView listView = findViewById(R.id.listView);
        final LinkedList <Persona>list = new LinkedList<>();

        GestioneDB db = new GestioneDB(this);
        db.open();
        Cursor c = db.ottieniTuttiClienti();

        if (c.moveToFirst()) {
            do {
                list.add(new Persona(c.getString(1),c.getString(2),c.getString(3),c.getString(4)));//+"   "+c.getString(0)));
            } while (c.moveToNext());
        }
        db.close();
        list.sort(new Comparator<Persona>() {
            @Override
            public int compare(Persona o1, Persona o2) {
                if(o1.getNome().charAt(0)>o2.getNome().charAt(0))return 1;
                if(o1.getNome().charAt(0)<o2.getNome().charAt(0))return -1;
                return 0;
            }
        });
        CustomAdapter adapter = new CustomAdapter(this, R.layout.lista, list);
        listView.setAdapter(adapter);
        Button cerca=findViewById(R.id.Cerca);
        cerca.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i=new Intent(MainActivity.this, Cerca.class);
                startActivity(i);
            }
        });

        Button btnHome=findViewById(R.id.aggiungi);
        btnHome.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainActivity.this, ActivityAgg.class));
            }
        });
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                //Log.i("HelloListView", "You clicked Item: " + id + " at position:" + position);
                // Then you start a new Activity via Intent
                final Intent intent = new Intent();
                intent.setClass(MainActivity.this, Modifica.class);
                intent.putExtra("position", position);
                intent.putExtra("id", id);

                TextView myTextView = (TextView) view.findViewById(R.id.textViewName);
                String textN = myTextView.getText().toString();

                StringTokenizer st=new StringTokenizer(textN);
                String no="",co="";
                int i=0;
                while(st.hasMoreElements()){
                    if(i==0){no=st.nextToken();i++;}
                    else {
                        if(i==1)co=co+st.nextToken();
                        else co=co+" "+st.nextToken();
                        ++i;
                    }
                }
                TextView myTextView4 = (TextView) view.findViewById(R.id.textViewNumber);
                String textNUM = myTextView4.getText().toString();

                TextView myTextView3 = (TextView) view.findViewById(R.id.textViewData);
                String textD = myTextView3.getText().toString();

                Persona p=new Persona(no,co,textD,textNUM);
                Bundle b=new Bundle();
                b.putParcelable("persona",p);
                intent.putExtras(b);
                startActivity(intent);
            }
        });

    }

}

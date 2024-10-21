package com.example.tesi;

import android.content.Intent;
import android.database.Cursor;
import android.os.Build;
import android.os.Bundle;
import android.app.Activity;
import android.support.annotation.RequiresApi;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import java.util.Comparator;
import java.util.LinkedList;
import java.util.StringTokenizer;

public class CercaA extends Activity {

    @RequiresApi(api = Build.VERSION_CODES.N)
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.cercafine);
        Bundle ex=getIntent().getExtras();
        ListView listView = findViewById(R.id.listViewC);
        LinkedList <Persona>list = new LinkedList<>();
        if(ex!=null) {
            Persona p =ex.getParcelable("persona");

            GestioneDB db = new GestioneDB(this);
            db.open();
            Cursor c = db.ottieniTuttiClienti();
            String regexN="[a-zA-Z0-9]*"+p.getNome()+"[a-zA-Z0-9]*";
            String regexC="[a-zA-Z0-9]*"+p.getCognome()+"[a-zA-Z0-9]*";
            if (c.moveToFirst()) {
                do {
                    if((!p.getNome().equals(""))&&p.getCognome().equals("")){
                        if(p.getNome().matches(regexN)&&c.getString(1).matches(regexN)){//if(c.getString(1).equals(p.getNome())){
                            list.add(new Persona(c.getString(1),c.getString(2),c.getString(3),c.getString(4)));
                        }
                    }
                    else if((p.getNome().equals(""))&&!p.getCognome().equals("")){
                        if(p.getCognome().matches(regexC)&&c.getString(2).matches(regexC)){//if(c.getString(2).equals(p.getCognome())){
                            list.add(new Persona(c.getString(1),c.getString(2),c.getString(3),c.getString(4)));
                        }
                    }
                    else if((!p.getNome().equals(""))&&(!p.getCognome().equals(""))){
                        if(p.getNome().matches(regexN)&&p.getCognome().matches(regexC)&&c.getString(1).matches(regexN)&&c.getString(2).matches(regexC)){//if((c.getString(1).equals(p.getNome())&&c.getString(2).equals(p.getCognome()))
                            list.add(new Persona(c.getString(1),c.getString(2),c.getString(3),c.getString(4)));
                        }
                    }

                } while (c.moveToNext());
            }
            db.close();
        }
        list.sort(new Comparator<Persona>() {
            @Override
            public int compare(Persona o1, Persona o2) {
                if(o1.getNome().charAt(0)>o2.getNome().charAt(0))return 1;
                if(o1.getNome().charAt(0)<o2.getNome().charAt(0))return -1;
                return 0;
            }
        });
        CustomAdapter adapter = new CustomAdapter(this, R.layout.cercafine, list);
        listView.setAdapter(adapter);
        Button fine=findViewById(R.id.fine);
        fine.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(CercaA.this, MainActivity.class));
            }
        });
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                //Log.i("HelloListView", "You clicked Item: " + id + " at position:" + position);
                // Then you start a new Activity via Intent
                final Intent intent = new Intent();
                intent.setClass(CercaA.this, Modifica.class);
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
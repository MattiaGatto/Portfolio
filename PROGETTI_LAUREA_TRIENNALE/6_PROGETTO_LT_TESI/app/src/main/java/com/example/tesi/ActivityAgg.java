package com.example.tesi;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.app.Activity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class ActivityAgg extends Activity {
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.aggiungi);
        Button annulla=findViewById(R.id.Annulla);
        Button ok=findViewById(R.id.Ok);
        annulla.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(ActivityAgg.this, MainActivity.class));
            }
        });
        final GestioneDB db = new GestioneDB(this);
        db.open();

        ok.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                EditText nome=(EditText)findViewById(R.id.editText);
                EditText numero=(EditText)findViewById(R.id.editText2);
                EditText cognome=(EditText)findViewById(R.id.editText3);
                EditText data=(EditText)findViewById(R.id.editText4);
                String n=nome.getText().toString().trim();
                String c=cognome.getText().toString().trim();
                String d=data.getText().toString().trim();
                String num=numero.getText().toString().trim();
                if (!(n.equals(""))&&(d.length()==8||d.length()==0)){
                    db.inserisciCliente(n, c, d, num);
                    db.close();
                    startActivity(new Intent(ActivityAgg.this, MainActivity.class));
                }else if(n.equals("")){
                    AlertDialog a= new AlertDialog.Builder(ActivityAgg.this).create();
                    a.setTitle("Errore!");
                    a.setMessage("Non hai inserito nessun nome al tuo contatto! ");
                    a.setButton("OK", new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int which) {
                        }
                    });
                    a.show();
                }
                else{
                    AlertDialog a= new AlertDialog.Builder(ActivityAgg.this).create();
                    a.setTitle("Errore!");
                    a.setMessage("Non hai inserito una data di nascita corretta al tuo contatto! ");
                    a.setButton("OK", new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int which) {
                        }
                    });
                    a.show();
                }
            }
        });

    }
}

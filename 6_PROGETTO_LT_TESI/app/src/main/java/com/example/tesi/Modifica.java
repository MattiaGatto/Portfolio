package com.example.tesi;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class Modifica extends Activity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.mod);
        Button annulla = findViewById(R.id.AnnullaM);
        Button salva = findViewById(R.id.Salva);
        Button cancella = findViewById(R.id.cancella);
        Button chiama = findViewById(R.id.chiama);


        Bundle ex = getIntent().getExtras();
        Persona p = null;
        if (ex != null) {
            p = ex.getParcelable("persona");
        }
        final String nomeK = p.getNome();
        final String cognomeK = p.getCognome();
        final EditText nome = (EditText) findViewById(R.id.editTextM);
        final EditText cognome = (EditText) findViewById(R.id.editTextM2);
        final EditText numero = (EditText) findViewById(R.id.editTextM3);
        final EditText data = (EditText) findViewById(R.id.editTextM4);
        nome.setText(p.getNome());
        cognome.setText(p.getCognome());
        numero.setText(p.getNumero());
        data.setText(p.getDataN());

        chiama.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String tel=numero.getText().toString().trim();
                if(!tel.equals(""))startActivity(new Intent(Intent.ACTION_DIAL, Uri.parse("tel:"+tel)));
                else{
                    AlertDialog a= new AlertDialog.Builder(Modifica.this).create();
                    a.setTitle("Errore");
                    a.setMessage("Non è stato inserito nessun numero al tuo contatto!");
                    a.setButton("OK", new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int which) {
                        }
                    });
                    a.show();
                }
            }
        });

        cancella.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try{
                    final String n=nome.getText().toString().trim();
                    final String c=cognome.getText().toString().trim();
                    final String num=numero.getText().toString().trim();
                    final String d=data.getText().toString().trim();

                    GestioneDB db = new GestioneDB(Modifica.this);
                    db.open();
                    Persona p=new Persona(n,c,d,num);
                    db.close();
                    AlertDialog a= new AlertDialog.Builder(Modifica.this).create();
                    a.setTitle("Rimuovere? ");
                    a.setMessage("Sei sicuro di voler rimuovere il contatto: "+"\n"+"Nome: "+p.getNome()+"\n" +
                            "Cognome: "+p.getCognome()+"\n"+
                            "DataNascita: "+p.getDataN()+"\n" +
                            "Numero: "+p.getNumero()+"\n");
                    a.setButton("OK", new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int which) {
                            GestioneDB db = new GestioneDB(Modifica.this);
                            db.open();
                            db.aggiornaCliente(nomeK,cognomeK,n,c,d,num);
                            db.cancellaCliente(n,c,d,num);
                            db.close();
                            startActivity(new Intent(Modifica.this, MainActivity.class));
                        }
                    });
                    a.show();
                }catch(Exception e){
                    AlertDialog a= new AlertDialog.Builder(Modifica.this).create();
                    a.setTitle("Errore!");
                    a.setMessage("Non esiste nessun contatto con gli attributi da te indicati! ");
                    a.setButton("OK", new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int which) {
                        }
                    });
                    a.show();

                }


            }
        });
        annulla.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(Modifica.this, MainActivity.class));
            }
        });

        salva.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                EditText nome = (EditText) findViewById(R.id.editTextM);
                EditText cognome = (EditText) findViewById(R.id.editTextM2);
                EditText numero = (EditText) findViewById(R.id.editTextM3);
                EditText data = (EditText) findViewById(R.id.editTextM4);
                String n = nome.getText().toString().trim();
                String c = cognome.getText().toString().trim();
                String num = numero.getText().toString().trim();
                String d = data.getText().toString().trim();
                GestioneDB db = new GestioneDB(Modifica.this);
                db.open();
                if (!(n.equals(""))&&(d.length()==8||d.length()==0)) {
                    db.aggiornaCliente(nomeK, cognomeK, n, c, d,num);
                    db.close();
                    startActivity(new Intent(Modifica.this, MainActivity.class));
                }else if(n.equals("")){
                    AlertDialog a= new AlertDialog.Builder(Modifica.this).create();
                    a.setTitle("Errore!");
                    a.setMessage("Non hai inserito nessun nome al tuo contatto! ");
                    a.setButton("OK", new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int which) {
                        }
                    });
                    a.show();
                }
                else{
                    AlertDialog a= new AlertDialog.Builder(Modifica.this).create();
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

package com.example.tesi;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class Cerca extends Activity {
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.cercael);
        Button annulla=findViewById(R.id.buttonC2);
        Button si=findViewById(R.id.buttonC1);
        annulla.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(Cerca.this, MainActivity.class));
            }
        });
        si.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try{
                    EditText nome=(EditText)findViewById(R.id.editTextC6);
                    EditText cognome=(EditText)findViewById(R.id.editTextC7);
                    String n=nome.getText().toString().trim();
                    String c=cognome.getText().toString().trim();
                    GestioneDB db = new GestioneDB(Cerca.this);
                    db.open();
                    /*Cursor l;
                    if(c.equals("")) l =db.ottieniClienteN(n);
                    else if (n.equals("")) l =db.ottieniClienteC(c);
                    else l =db.ottieniCliente(n,c);*/
                    Persona p=new Persona(n,c,"","");//l.getString(3),l.getString(4)); //meglio lasciarli così lancia eccezione se inserisco nome e cognome che non esistono nel db, altrimenti se ins "" "" mi rest lista nulla
                    db.close();
                    Intent i=new Intent(Cerca.this, CercaA.class);
                    Bundle b=new Bundle();
                    b.putParcelable("persona",p);
                    i.putExtras(b);
                    startActivity(i);
                }catch(Exception e){
                    AlertDialog a= new AlertDialog.Builder(Cerca.this).create();
                    a.setTitle("Errore!");
                    a.setMessage("Non esiste nessun contatto con gli attributi da te indicati!\nDevi Specificare almeno uno dei due campi ");
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

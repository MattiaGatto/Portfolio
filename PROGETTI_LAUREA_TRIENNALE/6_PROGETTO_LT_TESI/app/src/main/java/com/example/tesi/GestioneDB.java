package com.example.tesi;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

public class GestioneDB {

    static final String KEY_RIGAID = "id";
    static final String KEY_NOME = "nome";
    static final String KEY_COGNOME = "cognome";
    static final String KEY_NUMERO = "numero";
    static final String KEY_DATAN = "dataN";
    static final String TAG = "GestioneDB";
    static final String DATABASE_NOME = "TestDB";
    static final String DATABASE_TABELLA = "persone";
    static final int DATABASE_VERSIONE = 1;

    static final String DATABASE_CREAZIONE =
            "CREATE TABLE persone (id integer primary key autoincrement, "
                    + "nome text not null,cognome text not null,numero text not null,dataN text not null);";

    final Context context;
    DatabaseHelper DBHelper;
    SQLiteDatabase db;

    public GestioneDB(Context ctx){
        this.context = ctx;
        DBHelper = new DatabaseHelper(context);
    }

    private static class DatabaseHelper extends SQLiteOpenHelper{
        DatabaseHelper(Context context){
            super(context, DATABASE_NOME, null, DATABASE_VERSIONE);
        }

        @Override
        public void onCreate(SQLiteDatabase db){
            try {
                db.execSQL(DATABASE_CREAZIONE);
            }
            catch (SQLException e) {
                e.printStackTrace();
            }
        }

        @Override
        public void onUpgrade(SQLiteDatabase db,int oldVersion,int newVersion){
            Log.w(DatabaseHelper.class.getName(),"Aggiornamento database dalla versione " + oldVersion + " alla "
                    + newVersion + ". I dati esistenti verranno eliminati.");
            db.execSQL("DROP TABLE IF EXISTS persone");
            onCreate(db);
        }

    }


    public GestioneDB open() throws SQLException{
        db = DBHelper.getWritableDatabase();
        return this;
    }


    public void close(){
        DBHelper.close();
    }


    public long inserisciCliente(String nome, String cognome,String dataN,String numero){
        ContentValues initialValues = new ContentValues();
        initialValues.put(KEY_NOME, nome);
        initialValues.put(KEY_COGNOME ,cognome);
        initialValues.put(KEY_NUMERO, numero);
        initialValues.put(KEY_DATAN, dataN);
        return db.insert(DATABASE_TABELLA, null, initialValues);
    }



    public boolean cancellaCliente(String n,String c,String d,String num){
        Cursor s=ottieniTuttiClienti();
        String l=null;
        boolean t=false;
        if (s.moveToFirst()) {
            do {
                if(s.getString(1).equals(n)&&s.getString(2).equals(c)&&s.getString(3).equals(d)&&s.getString(4).equals(num)){
                    l=s.getString(0);
                    t=true;
                }
            } while (s.moveToNext()&&t==false);
        }
        return db.delete(DATABASE_TABELLA, KEY_RIGAID + "=" + l,  null) > 0;
    }


    public Cursor ottieniTuttiClienti(){
        return db.query(DATABASE_TABELLA, new String[] {KEY_RIGAID, KEY_NOME, KEY_COGNOME,  KEY_DATAN, KEY_NUMERO}, null, null, null, null, null);
    }



    public Cursor ottieniCliente(String n,String c) throws SQLException{
        Cursor s=ottieniTuttiClienti();
        String l=null;
        boolean t=false;
        if (s.moveToFirst()) {
            do {
                if(s.getString(1).equals(n)&&s.getString(2).equals(c)){
                    l=s.getString(0);
                    t=true;
                }
            } while (s.moveToNext()&&t==false);
        }
        Cursor mCursore = db.query(true, DATABASE_TABELLA, new String[] {KEY_RIGAID, KEY_NOME, KEY_COGNOME,  KEY_DATAN,KEY_NUMERO}, KEY_RIGAID+"="+l, null, null, null, null, null);
        if (mCursore != null) {
            mCursore.moveToFirst();
        }
        return mCursore;
    }


    public Cursor ottieniClienteN(String n) throws SQLException{
        Cursor s=ottieniTuttiClienti();
        String l=null;
        boolean t=false;
        if (s.moveToFirst()) {
            do {
                if(s.getString(1).equals(n)){
                    l=s.getString(0);
                    t=true;
                }
            } while (s.moveToNext()&&t==false);
        }
        Cursor mCursore = db.query(true, DATABASE_TABELLA, new String[] {KEY_RIGAID, KEY_NOME, KEY_COGNOME,  KEY_DATAN,KEY_NUMERO}, KEY_RIGAID+"="+l, null, null, null, null, null);
        if (mCursore != null) {
            mCursore.moveToFirst();
        }
        return mCursore;
    }
    public Cursor ottieniClienteC(String c) throws SQLException{
        Cursor s=ottieniTuttiClienti();
        String l=null;
        boolean t=false;
        if (s.moveToFirst()) {
            do {
                if(s.getString(2).equals(c)){
                    l=s.getString(0);
                    t=true;
                }
            } while (s.moveToNext()&&t==false);
        }
        Cursor mCursore = db.query(true, DATABASE_TABELLA, new String[] {KEY_RIGAID, KEY_NOME, KEY_COGNOME,  KEY_DATAN,KEY_NUMERO}, KEY_RIGAID+"="+l, null, null, null, null, null);
        if (mCursore != null) {
            mCursore.moveToFirst();
        }
        return mCursore;
    }


    public boolean aggiornaCliente( String n, String c,String nome,String cognome,String dataN,String numero){
        ContentValues args = new ContentValues();
        args.put(KEY_NOME, nome);
        args.put(KEY_COGNOME, cognome);
        args.put(KEY_DATAN, dataN);
        args.put(KEY_NUMERO, numero);
        Cursor s=ottieniTuttiClienti();
        String l=null;
        boolean t=false;
        if (s.moveToFirst()) {
            do {
                if(s.getString(1).equals(n)&&s.getString(2).equals(c)){
                    l=s.getString(0);
                    t=true;
                }
            } while (s.moveToNext()&&t==false);
        }

        return db.update(DATABASE_TABELLA, args, KEY_RIGAID + "=" +l, null) > 0;
    }

}
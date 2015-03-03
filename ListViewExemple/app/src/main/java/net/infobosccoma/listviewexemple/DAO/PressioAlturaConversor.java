package net.infobosccoma.listviewexemple.DAO;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import java.util.ArrayList;


/**
 * Classe conversora de les dades a BD
 * Created by PC on 20/02/2015.
 */
public class PressioAlturaConversor {

    private AdminSQLiteOpenHelper helper;

    /**
     * Constructor per defecte
     */
    public PressioAlturaConversor(){}

    /**
     * Constructor amb paràmetres
     * @param helper, l'ajudant de la BD
     */
    public PressioAlturaConversor(AdminSQLiteOpenHelper helper){
        this.helper = helper;
    }

    /**
     * Mètode que desa dades a la taula
     * retorna l'index o id
     * @param hora
     * @param pressio
     * @param altura
     * @return
     */
    public long save(String hora, float pressio, float altura) {
        long index = -1;
        // s'agafa l'objecte base de dades en mode escriptura
        SQLiteDatabase db = helper.getWritableDatabase();
        // es crea un objecte de diccionari (clau,valor) per indicar els valors a afegir
        ContentValues registre = new ContentValues();
        registre.put("hora", hora);
        registre.put("pressio", pressio);
        registre.put("altura", altura);
        try{
            //l'id o index és autoincrement
            index = db.insertOrThrow("pressioaltura", null, registre);
            // mostro en el log el que passa
            Log.i("pressioaltura",registre.toString()+" afegit amb codi " + index);
            //tanco la BD
            db.close();
        }
        catch (Exception e){
            Log.e("pressioaltura", e.getMessage());
        }
        return index;

        //Toast.makeText(this, "Has afegit dades.", Toast.LENGTH_SHORT).show();//dona error amb el context (this)

    }

    /**
     * Retorna un cursor amb totes les dades de la taula
     * @return
     */
    public Cursor getAll(){
        SQLiteDatabase db = helper.getReadableDatabase();
        return db.query(true,"pressioaltura",new String[] {"_id","hora","pressio","altura"},null,null,null,null,null,null);
    }

    /**
     * Obting totes les dades de la taula en forma de llista
     * més fàcil per passar-ho a l'adapter
     * @return
     */

    public ArrayList<String> getAllAsList() {
        ArrayList<String> llista = new ArrayList<String>();
        Cursor c = getAll();
        String t = null;

        while(c.moveToNext()) {
            t =  c.getInt(0)+" "
                    + "\t"+ "Dia "
                    + c.getString(1)
                    + "\n" + "Pressió "
                    + c.getString(2)
                    + " mbar, "+ "altura "
                    + c.getString(3) + " m";
            llista.add(t);
        }

        return llista;
    }



    /**
     * Esborra la dada que te l'id passat per paràmetre
     * @param id el codi de la dada o string a esborrar
     * @return la quantitat strings eliminats
     *
     */
    public boolean remove(int id){
        SQLiteDatabase db = helper.getWritableDatabase();
        return db.delete("pressioaltura","_id=" + id, null) > 0;

    }

    /**
     * Elimina totes les dades de la taula
     * @return
     */
    public boolean removeAll(){
        SQLiteDatabase db = helper.getWritableDatabase();
        return db.delete("pressioaltura",null, null) > 0;
    }


}

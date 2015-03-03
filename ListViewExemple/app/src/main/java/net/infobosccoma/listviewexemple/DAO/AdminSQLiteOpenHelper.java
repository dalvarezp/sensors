package net.infobosccoma.listviewexemple.DAO;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteDatabase.CursorFactory;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Classe que estén les funcionalitats de SQLiteOpenHelper
 * Crea la BD
 * Created by PC on 20/02/2015.
 */
public class AdminSQLiteOpenHelper extends SQLiteOpenHelper{


    /**
     * Constructor amb paràmetres
     * @param context, context de l'apliació
     * @param nom, nom de la base de dades a crear
     * @param factory, s'utilitza per crear els objectes cursor
     * @param versio, número de versió de la BD. Si és més gran que la versió actual, es farà un
     * 				 Upgrade; si és menor es farà un Downgrade
     */
    public AdminSQLiteOpenHelper(Context context, String nom, CursorFactory factory, int versio){
        // es guarda la BD a /data/data/DAO/databases/nom
        super(context, nom, factory, versio);
    }

    /**
     * Event que es produeix quan s'ha de crear la BD
     * @param db
     */
    @Override
    public void onCreate(SQLiteDatabase db) {
        // Sentència SQL per crear la taula pressioaltura
        // és indispensable que la columna primary key es digui _id si es vol utilitzar un
        // SimpleCursor Adapter
        // Deso el dia i l'hora, la pressió i l'altura, com a clau primaria hi poso un enter per facilitar
        // la gestió de la BD
        db.execSQL("create table pressioaltura(_id integer primary key, hora string, pressio float, altura float)");
    }

    /**
     * Event que es produeix quan s'ha d'actualitzar la BD a una versió superior
     * @param db
     * @param oldVersion
     * @param newVersion
     */
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // S'elimina la versió anterior de la taula
        db.execSQL("drop table if exists pressioaltura");
        // Es crea la nova versió de la taula
        db.execSQL("create table pressioaltura(_id integer primary key, hora string, pressio float, altura float)");
    }
}

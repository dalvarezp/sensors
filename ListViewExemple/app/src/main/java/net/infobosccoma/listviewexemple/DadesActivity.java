package net.infobosccoma.listviewexemple;

import android.database.Cursor;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.ContextMenu;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import net.infobosccoma.listviewexemple.DAO.AdminSQLiteOpenHelper;
import net.infobosccoma.listviewexemple.DAO.PressioAlturaConversor;

import java.util.ArrayList;


public class DadesActivity extends ActionBarActivity {
    private Cursor dades;
    //private PressioAlturaAdapter adapter;
    private AdminSQLiteOpenHelper dadesHelper;
    private PressioAlturaConversor dadesConv;
    private ListView llista;
    private ArrayList stringId;
    ArrayAdapter<String> adapter;




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dades);
        llista = (ListView) findViewById(R.id.listViewDades);
        //registro el control llista per al menú contextual
        registerForContextMenu(llista);
        //referència a la BD
        dadesHelper = new AdminSQLiteOpenHelper(this,
                "administracio", null, 1);
        //referència al conversor
        dadesConv = new PressioAlturaConversor(dadesHelper);
        //carrego les dades actuals de la BD
        refrescaDades();

        /*
        llista.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
                //obting la primera part de l'string que és l'id, el passo al conversor per eliminar-lo de la BD.
                String[] s =((String) stringId.get(position)).split(" ");
                int i = Integer.parseInt(s[0]);
                dadesConv.remove(i);
                Toast.makeText(getBaseContext(), "Eliminat " + " " + stringId.get(position),
                        Toast.LENGTH_SHORT).show();
                refrescaDades();
                return false;
            }
        });*/

    }

    /**
     * Mètode associat a fer aparèixer el menú contextual
     * @param menu
     * @param v
     * @param menuInfo
     */
    @Override
    public void onCreateContextMenu(ContextMenu menu, View v,
                                    ContextMenu.ContextMenuInfo menuInfo) {
        super.onCreateContextMenu(menu, v, menuInfo);
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_contextual, menu);
    }

    /**
     * Mètode que s'exuta al premer sobre el menú contextual
     * @param item
     * @return
     */
    @Override
    public boolean onContextItemSelected(MenuItem item) {
        AdapterView.AdapterContextMenuInfo info = (AdapterView.AdapterContextMenuInfo) item.getMenuInfo();
        int i = info.position;
        switch (item.getItemId()) {
            case R.id.ctxeliminar:
                eliminaSeleccionat(i);
                return true;
            case R.id.ctxeliminartots:
                eliminaTot();
                return true;
        }
        return super.onContextItemSelected(item);
    }


    /**
     * Mètode que s'executa al premer sobre eliminar dada actual del menú contextual
     * Elimina la dada seleccionada de la BD
     * @param position
     */
    public void eliminaSeleccionat(int position){
        String[] s =((String) stringId.get(position)).split(" ");
        int i = Integer.parseInt(s[0]);
        dadesConv.remove(i);
        Toast.makeText(getBaseContext(), "Eliminat " + " " + stringId.get(position),
                Toast.LENGTH_SHORT).show();
        refrescaDades();
    }

    /**
     * Mètode que s'executa al premer sobre eliminar totes les dades del menú contextual
     * Elimina totes les dades de la BD
     */
    public void eliminaTot(){
        dadesConv.removeAll();
        Toast.makeText(getBaseContext(), "Eliminades totes les dades ",
                Toast.LENGTH_SHORT).show();
        refrescaDades();
    }

    /**
     * Mètode que carrega les dades actuals a l'activity de mostrar dades de la BD
     */
    public void refrescaDades(){
        //obting les dades en forma de llista
        stringId = dadesConv.getAllAsList();
        //li passo a l'adapter la referència de l'activity actaul,
        // la referència al tipus de dades a mostrar, la referència al nom del camp
        // i la llista a mostrar
        adapter = new ArrayAdapter<String>(this,
                R.layout.list_singledades, R.id.textView, stringId);
        llista.setAdapter(adapter);
        if(stringId.size()== 0){
            Toast.makeText(getBaseContext(), "No hi ha dades per mostrar" ,
                    Toast.LENGTH_SHORT).show();
        }
    }




    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_dades, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}

package net.infobosccoma.listviewexemple;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;


public class MainActivity extends ActionBarActivity {
    ListView list;
    String[] stringId = {
            "Sensors disponibles en aquest mòbil",
            "Baròmetre",
            "Lluminositat",
            "Proximitat",
            "Nivell",
            "Moviment",
            "Llanterna"
    } ;
    Integer[] imageId = {
            R.drawable.imatgeeines,
            R.drawable.imatgebarometre,
            R.drawable.imatgellum,
            R.drawable.imatgedistancia,
            R.drawable.imatgenivell,
            R.drawable.imatgemoviment,
            R.drawable.imatgellanterna
    };
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        CustomList adapter = new
                CustomList(MainActivity.this, stringId, imageId);
        list=(ListView)findViewById(R.id.llista);
        list.setAdapter(adapter);
        list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view,
                                    int position, long id) {
                //Toast.makeText(MainActivity.this, "Has clicat a: " + stringId[+position], Toast.LENGTH_SHORT).show();
                canvi(position);
            }
        });
    }

    public void canvi(int position){
        if(position == 6){//la llanterna te una activity diferent
            Intent j = new Intent(this,ActivityLlanterna.class);
            startActivity(j);
        }else{//Utilitzo la mateixa activity per tots els sensors
            Intent i = new Intent(this,SensorActivity.class);
            i.putExtra("posicio",position);//passo a SensorActivity el valor de la posició clicada
            startActivity(i);
        }
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
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

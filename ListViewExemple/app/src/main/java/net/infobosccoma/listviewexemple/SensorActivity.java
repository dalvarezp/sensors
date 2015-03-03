package net.infobosccoma.listviewexemple;

import android.content.Context;
import android.content.Intent;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.os.Vibrator;
import android.support.v4.view.MenuItemCompat;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.widget.ShareActionProvider;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import net.infobosccoma.listviewexemple.DAO.AdminSQLiteOpenHelper;
import net.infobosccoma.listviewexemple.DAO.PressioAlturaConversor;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.List;

import static android.hardware.SensorManager.getAltitude;


public class SensorActivity extends ActionBarActivity implements SensorEventListener {
    private SensorManager mSensorManager;
    private Sensor mProximity;
    private Sensor mLight;
    private Sensor mAccelerometer;
    private Sensor mGyroscope;
    private Sensor mPressure;
    private Sensor mOrientacio;
    private Sensor mSensor;
    private TextView textTitol, textLlista, textSensors1, textSensors2;
    private TextView textSensors3, textSensors4, textSensors5, textSensors6;
    List<Sensor> deviceSensors;
    private String llSensors="";
    private int pos;
    private float[] gravity;
    private float[] linear_acceleration;
    private Boolean llum = false;
    private Boolean proximitat = false;
    private float distance = 0;
    private float lux = 0;
    private float x,y,z;
    private float alpha = (float) 0.8;
    private ShareActionProvider mShareActionProvider;
    private Vibrator vibrar;
    private float pressio;
    private float altura;



    @Override
    public final void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sensor);
        textTitol= (TextView) findViewById(R.id.textTitol);
        textLlista= (TextView) findViewById(R.id.textLlista);
        textSensors1= (TextView) findViewById(R.id.textSensors1);
        textSensors2 = (TextView) findViewById(R.id.textSensors2);
        textSensors3 = (TextView) findViewById(R.id.textSensors3);
        textSensors4 = (TextView) findViewById(R.id.textSensors4);
        textSensors5 = (TextView) findViewById(R.id.textSensors6);
        textSensors6 = (TextView) findViewById(R.id.textSensors5);
        pos = (int) getIntent().getExtras().getSerializable("posicio");//rebo valor passat desde MainActivity
        // Get an instance of the sensor service, and use that to get an instance of
        // a particular sensor.
        mSensorManager = (SensorManager) getSystemService(Context.SENSOR_SERVICE);
        deviceSensors = mSensorManager.getSensorList(Sensor.TYPE_ALL);
        mProximity = mSensorManager.getDefaultSensor(Sensor.TYPE_PROXIMITY);
        mLight = mSensorManager.getDefaultSensor(Sensor.TYPE_LIGHT);
        mAccelerometer = mSensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);
        mGyroscope = mSensorManager.getDefaultSensor(Sensor.TYPE_GYROSCOPE);
        mPressure = mSensorManager.getDefaultSensor(Sensor.TYPE_PRESSURE);
        mOrientacio = mSensorManager.getDefaultSensor(Sensor.TYPE_ORIENTATION);
        mSensor = mSensorManager.getDefaultSensor(Sensor.TYPE_GAME_ROTATION_VECTOR);
        vibrar = (Vibrator) getSystemService(Context.VIBRATOR_SERVICE);

        gestioActivities(pos);//passo el valor rebut de l'activity anterior per activar unes opcions o altres

    }

    @Override
    public final void onAccuracyChanged(Sensor sensor, int accuracy) {
        // Do something here if sensor accuracy changes.
        //textNumAlt.setText(" " + accuracy);
        //textNumAlt.setText("x: " + gravity[0] + ", y: " + gravity[1] + ", z: " + gravity[2] );
    }

    @Override
    public final void onSensorChanged(SensorEvent event) {

        //Cada vegada que un sensor modifica el seu valor entrem en aquest mètode
        // detectem quin és i el modifiquem
        if (event.sensor.getType() == Sensor.TYPE_LIGHT) {
            lux = event.values[0];
            textSensors1.setText("Sensor de llum " + lux + " lux");
        }

        if (event.sensor.getType() == Sensor.TYPE_PROXIMITY) {

            distance = event.values[0];
            if(distance > 1) distance = 1;
           textSensors2.setText("Proximitat " + distance);
            if(distance == 0){
                vibrar.vibrate(800);
            }
        }

        if (event.sensor.getType() == Sensor.TYPE_ACCELEROMETER) {
            x = event.values[0];
            y = event.values[1];
            z =event.values[2];
            textSensors3.setText("x: " + x + "\n" + "y: " + y + "\n" + "z: " + z);
        }
        /*
        if (event.sensor.getType() == Sensor.TYPE_GAME_ROTATION_VECTOR) {

            //x = alpha * x + (1 - alpha) * event.values[0];
            //y = alpha * y + (1 - alpha) * event.values[1];
            //z = alpha * z + (1 - alpha) * event.values[2];
            x = event.values[0];
            y = event.values[1];
            z =event.values[2];
            textSensors3.setText("x: " + x + "\n" + "y: " + y + "\n" + "z: " + z);
        }*/

        if (event.sensor.getType() == Sensor.TYPE_ORIENTATION) {
            x = event.values[0];
            y = event.values[1];
            z = event.values[2];
            textSensors4.setText("AZIMUTH: " + x + "\n" + "PITCH: " + y + "\n" + "ROLL: " + z);

        }
        /*
        if (event.sensor.getType() == Sensor.TYPE_GYROSCOPE) {
            float xg = event.values[0];
            float yg = event.values[1];
            float zg = event.values[2];
            textSensors4.setText("x: " + xg + "\n" + "y: " + yg + "\n" + "z: " + zg);
        }*/

        if (event.sensor.getType() == Sensor.TYPE_PRESSURE) {
            setPressio(event.values[0]);
            textSensors5.setText("Pressió: " + getPressio() + " mbar");
            setAltura(getAltitude(SensorManager.PRESSURE_STANDARD_ATMOSPHERE, getPressio()));
            textSensors6.setText("Altura: " + getAltura()+ " metres");
        }

    }

    @Override
    protected void onResume() {

        //Registro un listener per cada sensor
        super.onResume();
        mSensorManager.registerListener(this, mAccelerometer, SensorManager.SENSOR_DELAY_NORMAL);
        mSensorManager.registerListener(this, mLight, SensorManager.SENSOR_DELAY_NORMAL);
        mSensorManager.registerListener(this, mProximity, SensorManager.SENSOR_DELAY_NORMAL);
        mSensorManager.registerListener(this, mGyroscope, SensorManager.SENSOR_DELAY_NORMAL);
        mSensorManager.registerListener(this, mPressure, SensorManager.SENSOR_DELAY_NORMAL);
        mSensorManager.registerListener(this,  mOrientacio, SensorManager.SENSOR_DELAY_NORMAL);
        mSensorManager.registerListener(this,  mSensor, SensorManager.SENSOR_DELAY_NORMAL);
    }

    @Override
    protected void onPause() {
        // Be sure to unregister the sensor when the activity pauses.
        super.onPause();
        mSensorManager.unregisterListener(this);
    }

    /**
     * Mètode que segons que hagin clicat a l'activitat principal
     * carrega uns valors o uns altres.
     * @param pos
     */
    public void gestioActivities(int pos){
        switch (pos){
            case 0: llistatdeSensors();
                break;
            case 1:barometre();
                break;
            case 2:lluminositat();
                break;
            case 3: nivellProximitat();
                break;
            case 4: accelerometre();
                break;
            case 5: magnetic();
                break;
        }
    }

    /**
     * Mètode que mostra tots els sensors dels que disposa el mòbil
     */
    public void llistatdeSensors(){

        for(int i = 0; i < deviceSensors.size(); i++) {
            llSensors = llSensors + (i + 1) + ": " + deviceSensors.get(i).getName() + "\n";
        }

        textTitol.setText("Sensors");
        textLlista.setText(llSensors);
        textSensors1.setVisibility(View.GONE);
        textSensors2.setVisibility(View.GONE);
        textSensors3.setVisibility(View.GONE);
        textSensors4.setVisibility(View.GONE);
        textSensors5.setVisibility(View.GONE);
        textSensors6.setVisibility(View.GONE);
    }

    /**
     * Mètode que mostra el valor del sensor de llum
     */
    public void lluminositat(){
        textTitol.setText("Sensor de lluminositat");
        textLlista.setVisibility(View.GONE);
        textSensors1.setVisibility(View.VISIBLE);
        textSensors2.setVisibility(View.GONE);
        textSensors3.setVisibility(View.GONE);
        textSensors4.setVisibility(View.GONE);
        textSensors5.setVisibility(View.GONE);
        textSensors6.setVisibility(View.GONE);
    }

    /**
     * Mètode que mostra el valor del sensor de proximitat
     */
    public void nivellProximitat(){
        textTitol.setText("Proximitat");
        textLlista.setVisibility(View.GONE);
        textSensors1.setVisibility(View.GONE);
        textSensors2.setVisibility(View.VISIBLE);
        textSensors3.setVisibility(View.GONE);
        textSensors4.setVisibility(View.GONE);
        textSensors5.setVisibility(View.GONE);
        textSensors6.setVisibility(View.GONE);

    }

    public void accelerometre(){
        textTitol.setText("Accelerometre");
        textLlista.setVisibility(View.GONE);
        textSensors1.setVisibility(View.GONE);
        textSensors2.setVisibility(View.GONE);
        textSensors3.setVisibility(View.VISIBLE);
        textSensors4.setVisibility(View.GONE);
        textSensors5.setVisibility(View.GONE);
        textSensors6.setVisibility(View.GONE);
    }

    public void barometre(){
        textTitol.setText("Barometre");
        textLlista.setVisibility(View.GONE);
        textSensors1.setVisibility(View.GONE);
        textSensors2.setVisibility(View.GONE);
        textSensors3.setVisibility(View.GONE);
        textSensors4.setVisibility(View.GONE);
        textSensors5.setVisibility(View.VISIBLE);
        textSensors6.setVisibility(View.VISIBLE);
    }

    public void magnetic(){
        textTitol.setText("Magnetism");
        textLlista.setVisibility(View.GONE);
        textSensors1.setVisibility(View.INVISIBLE);
        textSensors2.setVisibility(View.GONE);
        textSensors3.setVisibility(View.GONE);
        textSensors4.setVisibility(View.VISIBLE);
        textSensors5.setVisibility(View.GONE);
        textSensors6.setVisibility(View.GONE);
    }



    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_sensor, menu);
        // Locate MenuItem with ShareActionProvider
        MenuItem menuItem = menu.findItem(R.id.action_settings);
        // Fetch and store ShareActionProvider
        mShareActionProvider = (ShareActionProvider) MenuItemCompat.getActionProvider(menuItem);
        mShareActionProvider.setShareIntent(compartir());
        // Return true to display menu
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


    private Intent compartir() {
        String missatge="";
        Intent intent = new Intent(Intent.ACTION_SEND);
        intent.setType("text/plain");
        //CREAR MISSATGE SEGONS EL SENSOR QUE ESTIGUEM PER COMPARTIR

        switch(pos){
            case 1: missatge = "En la meva situació l'alçada és: " + getAltura() + " metres." ;
                break;
            case 2: missatge = "En la meva situació la lluminositat és: " + lux + " lux." ;
                break;
            default:
                Toast.makeText(SensorActivity.this, "Aquests sensor no disposa de compartiment de dades.", Toast.LENGTH_LONG).show();

        }

        intent.putExtra(Intent.EXTRA_TEXT, missatge);

        return intent;
    }

    /**
     * Mètode que guarda les dades a la BD
     * @param v
     */
    public void guardar(View v) {
        //referència a la BD
        AdminSQLiteOpenHelper admin = new AdminSQLiteOpenHelper(this,
                "administracio", null, 1);//nom BD
        //referència al conversor
        PressioAlturaConversor pac = new PressioAlturaConversor(admin);
        //capturo les dades a guardar
        String hora = new SimpleDateFormat("dd-MM-yyyy HH:mm:ss").format(Calendar.getInstance().getTime());
        float pres = getPressio();
        float alt = getAltura();
        //Deso les dades a la BD
        pac.save(hora,pres,alt);
        //mostro missatge que s'han afegit dades
        Toast.makeText(this, "Has afegit dades.",
                Toast.LENGTH_SHORT).show();
    }


    /**
     * Metode que obra d'activity on es mostren les dades de la BD
     * @param v
     */
    public void mostrar(View v) {
        Intent j = new Intent(this,DadesActivity.class);
        startActivity(j);
    }


    public float getPressio() {
        return pressio;
    }

    public void setPressio(float pressio) {
        this.pressio = pressio;
    }


    public float getAltura() {
        return altura;
    }

    public void setAltura(float altura) {
        this.altura = altura;
    }
}
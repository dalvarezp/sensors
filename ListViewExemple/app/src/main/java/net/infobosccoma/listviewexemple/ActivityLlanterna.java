package net.infobosccoma.listviewexemple;

import android.hardware.Camera;
import android.hardware.Camera.Parameters;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;

public class ActivityLlanterna extends ActionBarActivity {

    Camera camera = null;
    Parameters parameters;
    Button FlashLightControl;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_llanterna);
        FlashLightControl = (Button)findViewById(R.id.flashcontrol);
        FlashLightControl.setText("ENCEN LA LLUM");
    }

    public void onClickLED(View v) {
        try{
            // Al premer si el led està encés es para i viceversa
            if(camera == null){
                camera = Camera.open();
                parameters = camera.getParameters();
                parameters.setFlashMode(Parameters.FLASH_MODE_TORCH);
                camera.setParameters(parameters);
                FlashLightControl.setText("APAGAR LLUM");
            }else{
                parameters.setFlashMode(Parameters.FLASH_MODE_OFF);
                camera.setParameters(parameters);
                camera.release();
                camera = null;
                FlashLightControl.setText("ENCENDRE LLUM");
            }
        }catch(Exception e){
            //Control errors
        }
    }

    /**
     * Si al sortir el LED és encés, l'apaguem
     * Aquesta funció es crida al tancar l'aplicació
     */
    public void finish(){
        if (camera != null){
            parameters.setFlashMode(Parameters.FLASH_MODE_OFF);
            camera.setParameters(parameters);
            camera.release();
            camera = null;
        }
        super.finish();
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

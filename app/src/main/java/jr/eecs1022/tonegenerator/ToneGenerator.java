package jr.eecs1022.tonegenerator;

import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.SeekBar;
import android.widget.TextView;

import com.google.android.gms.appindexing.Action;
import com.google.android.gms.appindexing.AppIndex;
import com.google.android.gms.common.api.GoogleApiClient;

import java.text.DecimalFormat;

public class ToneGenerator extends AppCompatActivity implements SeekBar.OnSeekBarChangeListener {
    private ToneEmitter toneEmitter = null;
    //private double freqChange = 0;
    /**
     * ATTENTION: This was auto-generated to implement the App Indexing API.
     * See https://g.co/AppIndexing/AndroidStudio for more information.
     */
    private GoogleApiClient client;

    public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
        double freqChange = (((double)progress/100)*( (this.toneEmitter.NOTE_C_ABOVE_MIDDLE_C)-(this.toneEmitter.NOTE_C))+this.toneEmitter.NOTE_C);

        if (toneEmitter != null) {

            if (seekBar == findViewById(R.id.changeAmplitude)) {
                if (progress == 0) {
                    // this.setAmplitude(0);
                    this.setAmplitude(0);
                    System.out.println("Amplitude has been set to 0");
                    System.out.println(R.id.amplitudeValue);
                }
                if (progress == 100) {
                    //this.setAmplitude(1);
                    this.setAmplitude(1);
                    //System.out.println("Amplitude has been set to 1");
                }
                // System.out.println("Amplitude bar has been moved");

            } else {
                //System.out.println(freqChange);
                //System.out.println(progress);

                if(freqChange == this.toneEmitter.NOTE_C){
                    this.setFrequency(this.toneEmitter.NOTE_C);
                }else if(this.toneEmitter.NOTE_C <= freqChange && freqChange <= this.toneEmitter.NOTE_C_SHARP ){
                    this.setFrequency(this.toneEmitter.NOTE_C);
                }else if(this.toneEmitter.NOTE_C_SHARP <= freqChange && freqChange <= this.toneEmitter.NOTE_D){
                    this.setFrequency(this.toneEmitter.NOTE_C_SHARP);
                }else if(this.toneEmitter.NOTE_D <= freqChange && freqChange <= this.toneEmitter.NOTE_D_SHARP){
                    this.setFrequency(this.toneEmitter.NOTE_D);
                }else if(this.toneEmitter.NOTE_D_SHARP <= freqChange && freqChange <= this.toneEmitter.NOTE_E ){
                    this.setFrequency(this.toneEmitter.NOTE_D_SHARP);
                }else if(this.toneEmitter.NOTE_E <= freqChange && freqChange <= this.toneEmitter.NOTE_F){
                    this.setFrequency(this.toneEmitter.NOTE_E);
                }else if(this.toneEmitter.NOTE_F <= freqChange && freqChange <= this.toneEmitter.NOTE_F_SHARP ) {
                    this.setFrequency(this.toneEmitter.NOTE_F);
                }else if(this.toneEmitter.NOTE_F_SHARP <= freqChange && freqChange <= this.toneEmitter.NOTE_G ){
                    this.setFrequency(this.toneEmitter.NOTE_F_SHARP);
                }else if(this.toneEmitter.NOTE_G <= freqChange && freqChange <= this.toneEmitter.NOTE_G_SHARP){
                    this.setFrequency(this.toneEmitter.NOTE_G);
                }else if(this.toneEmitter.NOTE_G_SHARP <= freqChange && freqChange <= this.toneEmitter.NOTE_A_ABOVE_MIDDLE_C ){
                    this.setFrequency(this.toneEmitter.NOTE_G_SHARP);
                }else if(this.toneEmitter.NOTE_A_ABOVE_MIDDLE_C <= freqChange && freqChange <= this.toneEmitter.NOTE_B_ABOVE_MIDDLE_C){
                    this.setFrequency(this.toneEmitter.NOTE_A_ABOVE_MIDDLE_C);
                }else if(this.toneEmitter.NOTE_B_ABOVE_MIDDLE_C <= freqChange && freqChange < this.toneEmitter.NOTE_C_ABOVE_MIDDLE_C ) {
                    this.setFrequency(this.toneEmitter.NOTE_B_ABOVE_MIDDLE_C);
                }
                else if(freqChange == this.toneEmitter.NOTE_C_ABOVE_MIDDLE_C){
                    this.setFrequency(this.toneEmitter.NOTE_C_ABOVE_MIDDLE_C);
                }

                /*
                if (progress == 0) {
                    // this.setFrequency(this.toneEmitter.NOTE_C);
                    this.setFrequency(this.toneEmitter.NOTE_C);
                    System.out.println("Frequency has been set to Note_C");
                }
                if (progress == 100) {
                    // this.setFrequency(this.toneEmitter.NOTE_C_ABOVE_MIDDLE_C);
                    this.setFrequency(this.toneEmitter.NOTE_C_ABOVE_MIDDLE_C);
                    System.out.println("Frequency has been set to Note_C_Above_Middle_C");
                }
                */
                //System.out.println("Frequency bar has been moved");
            }
            //System.out.println("onPress called with " + progress);
        }
    }

    public void onStartTrackingTouch(SeekBar seekBar) {
        System.out.println(seekBar);
    }

    public void onStopTrackingTouch(SeekBar seekBar) {
    }

    private void setFrequency(double frequency) {
        toneEmitter.setFrequency(frequency);
        TextView freqText = (TextView) findViewById(R.id.frequencyValue);
        DecimalFormat decFrequency = new DecimalFormat();
        freqText.setText(decFrequency.format(frequency));
        System.out.println("setFrequency called with " + frequency);
    }

    private void setAmplitude(double amplitude) {
        toneEmitter.setAmplitude(amplitude);
        TextView ampText = (TextView) findViewById(R.id.amplitudeValue);
        DecimalFormat decAmp = new DecimalFormat();
        ampText.setText(decAmp.format(amplitude));
        System.out.println("setAmplitude called with " + amplitude);
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tone_generator);

        SeekBar seekbar = (SeekBar) findViewById(R.id.changeAmplitude);
        seekbar.setOnSeekBarChangeListener(this);
        seekbar = (SeekBar) findViewById(R.id.changeFrequency);
        seekbar.setOnSeekBarChangeListener(this);
        // ATTENTION: This was auto-generated to implement the App Indexing API.
        // See https://g.co/AppIndexing/AndroidStudio for more information.
        client = new GoogleApiClient.Builder(this).addApi(AppIndex.API).build();
    }

    @Override
    public void onResume() {
        super.onResume();
        toneEmitter = new ToneEmitter();
        this.setAmplitude(0.0);
        this.setFrequency(ToneEmitter.NOTE_C);
        SeekBar seekBar = (SeekBar) findViewById(R.id.changeAmplitude);
        seekBar.setProgress(0);
        seekBar = (SeekBar) findViewById(R.id.changeFrequency);
        seekBar.setProgress(0);
    }

    @Override
    public void onPause() {
        toneEmitter.shutdown();
        toneEmitter = null;
        super.onPause();
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_tone_generator, menu);
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

    @Override
    public void onStart() {
        super.onStart();

        // ATTENTION: This was auto-generated to implement the App Indexing API.
        // See https://g.co/AppIndexing/AndroidStudio for more information.
        client.connect();
        Action viewAction = Action.newAction(
                Action.TYPE_VIEW, // TODO: choose an action type.
                "ToneGenerator Page", // TODO: Define a title for the content shown.
                // TODO: If you have web page content that matches this app activity's content,
                // make sure this auto-generated web page URL is correct.
                // Otherwise, set the URL to null.
                Uri.parse("http://host/path"),
                // TODO: Make sure this auto-generated app deep link URI is correct.
                Uri.parse("android-app://jr.eecs1022.tonegenerator/http/host/path")
        );
        AppIndex.AppIndexApi.start(client, viewAction);
    }

    @Override
    public void onStop() {
        super.onStop();

        // ATTENTION: This was auto-generated to implement the App Indexing API.
        // See https://g.co/AppIndexing/AndroidStudio for more information.
        Action viewAction = Action.newAction(
                Action.TYPE_VIEW, // TODO: choose an action type.
                "ToneGenerator Page", // TODO: Define a title for the content shown.
                // TODO: If you have web page content that matches this app activity's content,
                // make sure this auto-generated web page URL is correct.
                // Otherwise, set the URL to null.
                Uri.parse("http://host/path"),
                // TODO: Make sure this auto-generated app deep link URI is correct.
                Uri.parse("android-app://jr.eecs1022.tonegenerator/http/host/path")
        );
        AppIndex.AppIndexApi.end(client, viewAction);
        client.disconnect();
    }
}

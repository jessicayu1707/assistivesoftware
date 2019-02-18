package com.example.assistivesoftware;

import android.content.Context;
import android.content.Intent;
import android.speech.tts.TextToSpeech;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import java.util.Locale;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    static int MY_DATA_CHECK_CODE = 1; //from example
    public void checkTTS(View view){
        Context context = getApplicationContext();
        CharSequence text = "Hello Check!";
        int duration = Toast.LENGTH_SHORT;

        Toast toast = Toast.makeText(context, text, duration);
        toast.show();

        Intent checkIntent = new Intent();
        checkIntent.setAction(TextToSpeech.Engine.ACTION_CHECK_TTS_DATA);
        startActivityForResult(checkIntent, MY_DATA_CHECK_CODE);
        //onActivityResult(MY_DATA_CHECK_CODE,resultCode,checkIntent);
    }

    private TextToSpeech mTts;
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        Context context = getApplicationContext();
        CharSequence text = "Hello! Im on activity result now!";
        int duration = Toast.LENGTH_SHORT;

        Toast toast = Toast.makeText(context, text, duration);
        toast.show();

        if (requestCode == MY_DATA_CHECK_CODE) {
            if (resultCode == TextToSpeech.Engine.CHECK_VOICE_DATA_PASS) {
                // success, create the TTS instance
                mTts = new TextToSpeech(MainActivity.this, new TextToSpeech.OnInitListener() {
                    @Override
                    public void onInit(int status) {
                        mTts.setLanguage(Locale.UK);
                    }
                });
            } else {
                // missing data, install it
                Intent installIntent = new Intent();
                installIntent.setAction(TextToSpeech.Engine.ACTION_INSTALL_TTS_DATA);
                startActivity(installIntent);
            }
        }
    }

    //called when user taps on the button
    public void speakTTS(View view){
        //do something in response to clicking send button
        Context context = getApplicationContext();
        CharSequence text = "Hello speak tts!";
        int duration = Toast.LENGTH_SHORT;

        Toast toast = Toast.makeText(context, text, duration);
        toast.show();
        String toSpeak = "Hello I am working.";
        mTts.speak(toSpeak, TextToSpeech.QUEUE_FLUSH, null);

        //make intent (where does it go?)
        //Intent intent = new Intent(this, Accessibility.class);
        //start the intent
        //startActivity(intent);
    }
}

package com.example.assistivesoftware;


import android.Manifest;
import android.annotation.TargetApi;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.provider.Settings;
import android.speech.tts.TextToSpeech;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;
import java.util.Locale;

import static android.provider.Settings.canDrawOverlays;

public class MainActivity extends AppCompatActivity implements AdapterView.OnItemSelectedListener {

    public static int ACTION_MANAGE_OVERLAY_PERMISSION_REQUEST_CODE= 789;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //////Language Dropdown Menu///////
        //Getting the instance of Spinner and applying OnItemSelectedListener on it
        Spinner spinner = findViewById(R.id.spinner);
        spinner.setOnItemSelectedListener(this);

        //Creating the ArrayAdapter instance
        ArrayAdapter aa = ArrayAdapter.createFromResource(this,
                          R.array.language_array, android.R.layout.simple_spinner_item);
        aa.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        //Setting the ArrayAdapter data on the Spinner
        spinner.setAdapter(aa);

        ///////Overlay Permission///////
        testPermission();

        ///////Start Service Button///////
        Button button;
        button = findViewById(R.id.button_start);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startService(new Intent(MainActivity.this, FloatingWindow.class));
            }
        });


    }

    //occurs in onCreate()
    //code to text the permission to draw over other apps
    @TargetApi(23)
    public void testPermission() {
        String text = "I'm on test permission method.";
        TextView textView = findViewById(R.id.text);
        TextView textView2 = findViewById(R.id.text2);
        textView.setText(text);
        if (!canDrawOverlays(this)) {
            text = "CANNOT draw overlay";
            textView2.setText(text);
            Intent intent = new Intent(Settings.ACTION_MANAGE_OVERLAY_PERMISSION,
                    Uri.parse("package:" + getPackageName()));
            startActivityForResult(intent, ACTION_MANAGE_OVERLAY_PERMISSION_REQUEST_CODE);
        }
        else {
        text = "CAN draw overlay";
        textView2.setText(text);
        }
    }
//    @Override
//    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
//        if (requestCode == ACTION_MANAGE_OVERLAY_PERMISSION_REQUEST_CODE) {
//            if (Settings.canDrawOverlays(this)) {
//                // You have permission
//            }
//        }
//    }

    //Performing action onItemSelected and onNothing selected
    @Override
    public void onItemSelected(AdapterView<?> arg0, View arg1, int position,long id) {
        //make array from language array in resources
        String[] language_array = getResources().getStringArray(R.array.language_array);
        String text = language_array[position];
        TextView textView = findViewById(R.id.textView);
        textView.setText(text); //set text for text view

    }

    @Override
    public void onNothingSelected(AdapterView<?> arg0) {
// TODO Auto-generated method stub

    }

    //called when "Check" is clicked
    //check for the presence of the TTS resources with the corresponding intent
    static int MY_DATA_CHECK_CODE = 1; //from example
    public void checkTTS(View view) {
        Context context = getApplicationContext();
        CharSequence text = "Hello Check!";
        int duration = Toast.LENGTH_SHORT;

        Toast toast = Toast.makeText(context, text, duration);
        toast.show();

        Intent checkIntent = new Intent();
        checkIntent.setAction(TextToSpeech.Engine.ACTION_CHECK_TTS_DATA);
        startActivityForResult(checkIntent, MY_DATA_CHECK_CODE);
    }

    //initialising tts (to do) with required language from dropdown
    private TextToSpeech mTts;
    @Override
    @TargetApi(23)
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {

        //overlay permission activity result
        if (requestCode == ACTION_MANAGE_OVERLAY_PERMISSION_REQUEST_CODE) {
            if (canDrawOverlays(this)) {
                // You have permission
                Context context = getApplicationContext();
                CharSequence text = "Hello! You have permission!";
                int duration = Toast.LENGTH_SHORT;

                Toast toast = Toast.makeText(context, text, duration);
                toast.show();
            }
        }



        //tts activity result
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

    //called when user taps on the "Speak" button
    //pops up toast and speaks text
    public void speakTTS(View view) {
        //do something in response to clicking send button
        Context context = getApplicationContext();
        CharSequence text = "Hello I am talking. Can you hear me?";
        int duration = Toast.LENGTH_SHORT;

        Toast toast = Toast.makeText(context, text, duration);
        toast.show();
        String toSpeak = "Hello I am talking. Can you hear me?";
        mTts.speak(toSpeak, TextToSpeech.QUEUE_FLUSH, null);

    }
}

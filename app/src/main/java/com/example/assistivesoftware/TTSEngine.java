package com.example.assistivesoftware;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.LocaleList;
import android.speech.tts.TextToSpeech;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.Toast;

import java.util.Locale;

public class TTSEngine {

//    @Override
//    protected void onCreate(Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//
//        checkTTS();
//    }



    private TextToSpeech tts = null;
    private boolean isLoaded = false;


    private Locale loc = Locale.UK;

    public void init(Context context) {
        try {
            tts = new TextToSpeech(context, onInitListener);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private TextToSpeech.OnInitListener onInitListener = new TextToSpeech.OnInitListener() {
        @Override
        public void onInit(int status) {
            if (status == TextToSpeech.SUCCESS) {
                int result = tts.setLanguage(loc);
                isLoaded = true;

                if (result == TextToSpeech.LANG_MISSING_DATA || result == TextToSpeech.LANG_NOT_SUPPORTED) {
                    Log.e("error", "This Language is not supported");
                }
            } else {
                Log.e("error", "Initialization Failed!");
            }
        }
    };

    public void shutDown() {
        tts.shutdown();
    }

    public void addTalk(String text) {
        if (isLoaded)
            tts.speak(text, TextToSpeech.QUEUE_ADD, null);
        else
            Log.e("error", "TTS Not Initialized");
    }

    public void talk(String text) {
        if (isLoaded)
            tts.speak(text, TextToSpeech.QUEUE_FLUSH, null);
        else
            Log.e("error", "TTS Not Initialized");
    }


//    static int MY_DATA_CHECK_CODE = 123;
//    public void checkTTS() {
//        Intent checkIntent = new Intent();
//        checkIntent.setAction(TextToSpeech.Engine.ACTION_CHECK_TTS_DATA);
//        startActivityForResult(checkIntent, MY_DATA_CHECK_CODE);
//    }
//
//    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
//
//        if (requestCode == MY_DATA_CHECK_CODE) {
//            if (resultCode == TextToSpeech.Engine.CHECK_VOICE_DATA_PASS) {
//                // success, create the TTS instance
//                tts = new TextToSpeech(context, new TextToSpeech.OnInitListener() {
//                    @Override
//                    public void onInit(int status) {
//                        tts.setLanguage(loc);
//                    }
//                });
//
//            } else {
//                // missing data, install it
//                Intent installIntent = new Intent();
//                installIntent.setAction(TextToSpeech.Engine.ACTION_INSTALL_TTS_DATA);
//                startActivity(installIntent);
//            }
//        }
//    }
//
//    //initialising the tts
//    public void onInit(int status) {
//        if (status == TextToSpeech.SUCCESS) {
//            if (tts != null) {
//                int result = tts.setLanguage(Locale.US);
//                if (result == TextToSpeech.LANG_MISSING_DATA ||
//                        result == TextToSpeech.LANG_NOT_SUPPORTED) {
//                    Toast.makeText(this, "TTS language is not supported", Toast.LENGTH_LONG).show();
//                } else {
//                    talk("TTS is ready");
//                }
//            }
//        } else {
//            Toast.makeText(this, "TTS initialization failed",
//                    Toast.LENGTH_LONG).show();
//        }
//    }
//
//
//    //speaks string given to method
//    public void talk(String toSpeak) {
//        tts.speak(toSpeak, TextToSpeech.QUEUE_FLUSH, null);
//    }
}

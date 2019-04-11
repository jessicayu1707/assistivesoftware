package com.example.assistivesoftware;

import android.content.Context;
import android.speech.tts.TextToSpeech;
import android.util.Log;
import java.util.Locale;

//from TTS tutorial on below website, used to store the methods of the TTS engine in one place
// ***
// * Created by Nilanchala
// * http://www.stacktips.com
// ***
public class TTSEngine {


    private TextToSpeech tts = null;
    private boolean isLoaded = false;


    private Locale loc = Locale.UK;

    public void changeLocale(String languageCode){
        Locale locale = new Locale(languageCode);
        loc = locale;
    }

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


}

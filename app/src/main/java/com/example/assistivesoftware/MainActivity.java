package com.example.assistivesoftware;


import android.annotation.TargetApi;
import android.content.Context;
import android.content.Intent;
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

import com.squareup.okhttp.MediaType;
import com.squareup.okhttp.RequestBody;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;


public class MainActivity extends AppCompatActivity implements AdapterView.OnItemSelectedListener {


    private TTSEngine tts = null;
    private TranslateAPI translateAPI;
    private TextView translatedText;

    private String languageCode = "yue-Hant-HK";

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



        ///////TTSEngine/////////
        tts = new TTSEngine();
        tts.changeLocale(languageCode);
        tts.init(this);
        Button testButton = findViewById(R.id.btnTTSEngine);
        testButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                tts.talk("你好吗？我是余君儿!");
            }
        });


        //////////Translation Test//////////

        translatedText = findViewById(R.id.translatedText);

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("https://api.cognitive.microsofttranslator.com/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        translateAPI = retrofit.create(TranslateAPI.class);

        Button translateBtn = findViewById(R.id.translateBtn);

        translateBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                createTranslation("three eggs", "zh-Hans");
            }
        });



        //settingLanguage(languageCode);
        Intent intent = new Intent(MainActivity.this, MyAccessibilityService.class);
        intent.putExtra("language_code", languageCode);
        startService(intent);

    }

    //saving the string languageCode for intended language to use across activities
    public void settingLanguage(String languageCode){

    }

    private void createTranslation(String text, String languageCode) {

        final InputText inputText = new InputText(text);
        List<InputText> inputTextList = new ArrayList<>();
        inputTextList.add(inputText);

        Call<List<TranslationResponse>> call = translateAPI.createTranslation(3.0, languageCode, inputTextList);

        call.enqueue(new Callback<List<TranslationResponse>>() {
            @Override
            public void onResponse(Call<List<TranslationResponse>> call, Response<List<TranslationResponse>> response) {

                if(!response.isSuccessful()){
                    translatedText.setText("Failed :( Code: " + response.code());
                    return;
                }

                List<TranslationResponse> translationResponse = response.body();

                String output = translationResponse.get(0).getTranslations().get(0).getText();

                String content = "";
                content += "Code: " + response.code() + "\n";
                content += "Original Text:" + inputText.getText() + "\n";
                content += "Translated to:" + translationResponse.get(0).getTranslations().get(0).getTo() + "\n";
                content += "Translated Text:" + output + "\n\n";

                translatedText.setText(content);

            }

            @Override
            public void onFailure(Call<List<TranslationResponse>> call, Throwable t) {
                translatedText.setText(t.getMessage());
            }
        });

    }


    @Override
    public void onDestroy() {
        super.onDestroy();
        tts.shutDown();
    }


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
    static int MY_DATA_CHECK_CODE = 123; //from example
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

    //testing overlay permission
    //initialising tts (to do) with required language from dropdown
    private TextToSpeech mTts;
    @Override
    @TargetApi(23)
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {



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
        Context context = getApplicationContext();
        CharSequence text = "Hello I am talking. Can you hear me?";
        int duration = Toast.LENGTH_SHORT;
        Toast toast = Toast.makeText(context, text, duration);
        toast.show();

        String toSpeak = "Hello I am talking. Can you hear me?";
        mTts.speak(toSpeak, TextToSpeech.QUEUE_FLUSH, null);

    }
}

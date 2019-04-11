package com.example.assistivesoftware;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.TextView;


public class MainActivity extends AppCompatActivity implements AdapterView.OnItemSelectedListener {


    private String googleCode = "en-GB";
    private String bingCode = "en";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        TextView description = findViewById(R.id.description);
        description.setText("Hello! This is an Accessibility Service.\n" +
                "\n" +
                "This is a screen reader which will translate what is on the screen and read it out loud.\n" +
                "\n" +
                "To get started you can pick an output language from the dropdown menu.\n" +
                "Then go to Settings > Accessibility > Assistive Software > Turn ON.\n" +
                "Now you can click and your actions will be read out to you."); //set text for text view


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

    }

    //saving the string languageCode for intended language to use across activities
    public void settingLanguage(String googleCode, String bingCode){
        Intent intent = new Intent(MainActivity.this, MyAccessibilityService.class);
        intent.putExtra("google_code", googleCode);
        intent.putExtra("bing_code", bingCode);
        startService(intent);
    }

    //Performing action onItemSelected and onNothing selected
    String selectedLanguage = "English UK";
    @Override
    public void onItemSelected(AdapterView<?> arg0, View arg1, int position,long id) {
        //make array from language array in resources
        String[] language_array = getResources().getStringArray(R.array.language_array);
        selectedLanguage = language_array[position];
        LanguageCode languageCode = new LanguageCode(selectedLanguage);
        googleCode = languageCode.getGoogleCode();
        bingCode = languageCode.getBingCode();
        settingLanguage(googleCode, bingCode);
        TextView textView = findViewById(R.id.textView);
        textView.setText(selectedLanguage); //set text for text view
    }

    @Override
    public void onNothingSelected(AdapterView<?> arg0) {
// TODO Auto-generated method stub

    }

    @Override
    public void onDestroy() {
        super.onDestroy();
    }

}

//Some code referenced from Youtube, Stack Overflow, Supporting Documentation, etc.
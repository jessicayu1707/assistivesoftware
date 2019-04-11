package com.example.assistivesoftware;

import android.content.Context;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class Translator {

    private TranslateAPI translateAPI;

    public void init(){
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("https://api.cognitive.microsofttranslator.com/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        translateAPI = retrofit.create(TranslateAPI.class);
    }

    private InputText translatedText;
    public void doTranslation(String text, String languageCode){

        final InputText inputText = new InputText(text);
        List<InputText> inputTextList = new ArrayList<>();
        inputTextList.add(inputText);

        Call<List<TranslationResponse>> call = translateAPI.createTranslation(3.0, languageCode, inputTextList);

        call.enqueue(new Callback<List<TranslationResponse>>() {

            String ttt ;
            @Override
            public void onResponse(Call<List<TranslationResponse>> call, Response<List<TranslationResponse>> response) {

                if(!response.isSuccessful()){
                    ttt = "Failed :( Code: " + response.code();
                    translatedText.setText(ttt);
                    return;
                }

                List<TranslationResponse> translationResponse = response.body();

                String output = translationResponse.get(0).getTranslations().get(0).getText();

                String content = "";
                content += "Code: " + response.code() + "\n";
                content += "Original Text:" + inputText.getText() + "\n";
                content += "Translated to:" + translationResponse.get(0).getTranslations().get(0).getTo() + "\n";
                content += "Translated Text:" + output + "\n\n";

                ttt = content;
                translatedText.setText(ttt);

            }

            @Override
            public void onFailure(Call<List<TranslationResponse>> call, Throwable t) {
                ttt = t.getMessage();
                translatedText.setText(ttt);
            }

        });


    }

    public String doTest(){
        return translatedText.getText();
    }


}

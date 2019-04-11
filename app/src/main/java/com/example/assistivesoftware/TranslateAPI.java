package com.example.assistivesoftware;

import com.squareup.okhttp.RequestBody;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.Headers;
import retrofit2.http.POST;
import retrofit2.http.Query;

//interface to make POST calls to the Microsoft API using Retrofit
public interface TranslateAPI {

    String apikey = BuildConfig.api_key;
    @Headers({
           "Ocp-Apim-Subscription-Key: " + apikey,
            "Content-type: application/json"
    })
    @POST("translate")
    Call<List<TranslationResponse>> createTranslation(@Query("api-version") double version,
                                                      @Query("to") String languageCode,
                                                      @Body List<InputText> textToTranslate);


}

//Some code referenced from Youtube, Stack Overflow, Supporting Documentation, etc.
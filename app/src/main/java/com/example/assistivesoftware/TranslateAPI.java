package com.example.assistivesoftware;

import com.squareup.okhttp.RequestBody;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.Headers;
import retrofit2.http.POST;
import retrofit2.http.Query;

public interface TranslateAPI {

    //Microsoft Translate API  using Retrofit

//    @GET("translate")
//    Call<List<Translation>> getTranslation(@Query("api-version")double version,
//                                             @Query("to") String languageCode);
//

//    @Query("api-version") String version,
//    @Query("to") String languageCode,

    String apikey = BuildConfig.api_key;
    @Headers({
           "Ocp-Apim-Subscription-Key: " + apikey,
            "Content-type: application/json"
    })
    @POST("translate")
    Call<List<TranslationResponse>> createTranslation(    @Query("api-version") double version,
                                                      @Query("to") String languageCode,
                                                      @Body List<InputText> textToTranslate);


}

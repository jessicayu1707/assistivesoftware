package com.example.assistivesoftware;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

//Java object representation of JSON response from Microsoft Translate
//helps to easier parse response with retrofit
public class TranslationResponse {

    @SerializedName("detectedLanguages")
    @Expose
    private DetectedLanguage detectedLanguage;
    @SerializedName("translations")
    @Expose
    private List<Translation> translations = null;

    public TranslationResponse(DetectedLanguage detectedLanguage, List<Translation> translations) {
        this.detectedLanguage = detectedLanguage;
        this.translations = translations;
    }

    public DetectedLanguage getDetectedLanguage() {
        return detectedLanguage;
    }

    public List<Translation> getTranslations() {
        return translations;
    }

}

//Some code referenced from Youtube, Stack Overflow, Documentation, etc.

package com.example.assistivesoftware;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

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

package com.example.assistivesoftware;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

//Java object representation for detected language object
class DetectedLanguage {

    @SerializedName("language")
    @Expose
    private String language;
    @SerializedName("score")
    @Expose
    private float score;

    public DetectedLanguage(String language, float score) {
        this.language = language;
        this.score = score;
    }

    public String getLanguage() {
        return language;
    }

    public float getScore() {
        return score;
    }

}

//Some code referenced from Youtube, Stack Overflow, Supporting Documentation, etc.

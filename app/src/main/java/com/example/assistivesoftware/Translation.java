package com.example.assistivesoftware;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

//Java object to represent a translation object
public class Translation {

    //A string giving the translated text.
    @SerializedName("text")
    @Expose
    private String text;
    //A string representing the language code of the target language.
    @SerializedName("to")
    @Expose
    private String to;

    public Translation(String text, String to) {
        this.text = text;
        this.to = to;
    }

    public String getText() {
        return text;
    }

    public String getTo() {
        return to;
    }

}


//Some code referenced from Youtube, Stack Overflow, Documentation, etc.
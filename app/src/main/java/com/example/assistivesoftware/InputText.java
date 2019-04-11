package com.example.assistivesoftware;

import com.google.gson.annotations.SerializedName;

//Java object representation of input for translation api
public class InputText {

    @SerializedName("Text")
    private String text;

    public InputText(String text) {
        this.text = text;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) { this.text = text; }
}

//Some code referenced from Youtube, Stack Overflow, Documentation, etc.
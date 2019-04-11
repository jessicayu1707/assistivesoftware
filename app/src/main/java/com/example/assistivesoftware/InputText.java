package com.example.assistivesoftware;

import com.google.gson.annotations.SerializedName;

public class InputText {

    @SerializedName("Text")
    private String text;

    public InputText(String text) {
        this.text = text;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }
}

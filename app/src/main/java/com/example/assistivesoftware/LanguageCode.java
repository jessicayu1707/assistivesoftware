package com.example.assistivesoftware;


//to return the required language codes
public class LanguageCode {

    private String language;
    public LanguageCode(String language) {
        this.language = language;
    }

    private String googleCode;
    //codes used for google/android voice TTS
    public String getGoogleCode() {

        switch(language) {

            case "English UK":
                googleCode = "en-GB";
                break;
            case "English USA":
                googleCode = "en-US";
                break;
            case "Chinese Mandarin 中文":
                googleCode = "zh";
                break;
            case "Cantonese 粤语":
                googleCode = "yue-Hant-HK";
                break;
            case "French":
                googleCode = "fr-FR";
                break;
            case "German":
                googleCode = "de-DE";
                break;
            case "Japanese":
                googleCode = "ja-JP";
                break;
            case "Korean":
                googleCode = "ko-KR";
                break;
            case "Spanish":
                googleCode = "es-ES";
                break;

        }

        return googleCode;
    }

    private String bingCode;
    //codes used for microsoft/bing translate
    public String getBingCode() {

        switch(language) {

            case "English UK":
                bingCode = "en";
                break;
            case "English USA":
                bingCode = "en";
                break;
            case "Chinese Mandarin 中文":
                bingCode = "zh-Hans";
                break;
            case "Cantonese 粤语":
                bingCode = "yue";
                break;
            case "French":
                bingCode = "fr";
                break;
            case "German":
                bingCode = "de";
                break;
            case "Japanese":
                bingCode = "ja";
                break;
            case "Korean":
                bingCode = "ko";
                break;
            case "Spanish":
                bingCode = "es";
                break;

        }

        return bingCode;
    }

}

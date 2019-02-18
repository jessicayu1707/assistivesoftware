package com.example.assistivesoftware;

import android.accessibilityservice.AccessibilityService;
import android.speech.tts.TextToSpeech;
import android.view.accessibility.AccessibilityEvent;

import java.util.Locale;

public class MyAccessibilityService extends AccessibilityService {

    @Override
    public void onAccessibilityEvent(AccessibilityEvent event) {
        final int eventType = event.getEventType();
        String eventText = null;
        switch(eventType) {
            case AccessibilityEvent.TYPE_VIEW_CLICKED:
                eventText = "Clicked: ";
                break;
            case AccessibilityEvent.TYPE_VIEW_FOCUSED:
                eventText = "Focused: ";
                break;
        }

        eventText = eventText + event.getContentDescription();

        // Do something nifty with this text, like speak the composed string
        // back to the user.
        //speakToUser(eventText);
    }

    @Override
    public void onInterrupt() {
    }

}
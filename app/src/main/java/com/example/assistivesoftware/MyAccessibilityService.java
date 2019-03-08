package com.example.assistivesoftware;

import android.accessibilityservice.AccessibilityService;
import android.accessibilityservice.AccessibilityServiceInfo;
import android.annotation.TargetApi;
import android.view.accessibility.AccessibilityEvent;
import android.view.accessibility.AccessibilityNodeInfo;
import android.widget.Toast;

public class MyAccessibilityService extends AccessibilityService {

    @TargetApi(21)
    @Override public void onServiceConnected() {

        AccessibilityServiceInfo info = new AccessibilityServiceInfo();
        // Set the type of events that this service wants to listen to. Others won't be passed to this service.
        // We are only considering windows state changed event.
        info.eventTypes = AccessibilityEvent.TYPE_VIEW_CLICKED | AccessibilityEvent.TYPE_VIEW_FOCUSED;

        // If you only want this service to work with specific applications, set their package names here.
        // Otherwise, when the service is activated, it will listen to events from all applications.
        //info.packageNames = new String[] {"com.example.android.myFirstApp", "com.example.android.mySecondApp"};

        // Set to spoken feedback. (type of feedback my service provides)
        info.feedbackType = AccessibilityServiceInfo.FEEDBACK_SPOKEN;

        // Default services are invoked only if no package-specific ones are present for the type of AccessibilityEvent generated.
        // This is a general-purpose service, so we will set some flags
        info.flags = AccessibilityServiceInfo.DEFAULT;
        info.flags = AccessibilityServiceInfo.FLAG_INCLUDE_NOT_IMPORTANT_VIEWS;
        info.flags = AccessibilityServiceInfo.FLAG_REPORT_VIEW_IDS;
        info.flags = AccessibilityServiceInfo.FLAG_REQUEST_ENHANCED_WEB_ACCESSIBILITY;
        info.flags = AccessibilityServiceInfo.FLAG_RETRIEVE_INTERACTIVE_WINDOWS;
        //info.flags = AccessibilityServiceInfo.FLAG_REQUEST_TOUCH_EXPLORATION_MODE;
        //info.flags = AccessibilityServiceInfo.FLAG_REQUEST_ACCESSIBILITY_BUTTON;
        // We are keeping the timeout to 0 as we don’t need any delay or to pause our accessibility events
        info.notificationTimeout = 0;
        this.setServiceInfo(info);
    }

    @Override
    public void onAccessibilityEvent(AccessibilityEvent event) {



//        AccessibilityNodeInfo source = event.getSource();
//        if (source == null) {
//            return;
//        }
//        final int eventType = event.getEventType();
//        String sourceText = null;
//        switch (eventType) {
//            case AccessibilityEvent.TYPE_VIEW_CLICKED:
//                sourceText = "Clicked on ";
//                break;
//            case AccessibilityEvent.TYPE_VIEW_FOCUSED:
//                sourceText = "Focused on";
//                break;
//            case AccessibilityEvent.TYPE_VIEW_TEXT_TRAVERSED_AT_MOVEMENT_GRANULARITY:
//                sourceText = "Text on ";
//                break;
//        }
//
//        String toastText = sourceText + event.getText();
//        Toast.makeText(getApplicationContext(), toastText, Toast.LENGTH_LONG).show();




//        final int eventType = event.getEventType();
//        String eventText = null;
//        switch (eventType) {
//            case AccessibilityEvent.TYPE_VIEW_CLICKED:
//                eventText = "Clicked on ";
//                break;
//            case AccessibilityEvent.TYPE_VIEW_FOCUSED:
//                eventText = "Focused on";
//                break;
//        }
//
//        eventText = eventText + event.getContentDescription();
//        Toast.makeText(getApplicationContext(), eventText, Toast.LENGTH_LONG).show();



//        AccessibilityNodeInfo source = event.getSource();
//        if (source == null) {
//            return;
//        }
//
//        Log.i("Event", event.toString());
//        Log.i("Source", source.toString());
    }



    @Override public void onInterrupt() {
    }
}

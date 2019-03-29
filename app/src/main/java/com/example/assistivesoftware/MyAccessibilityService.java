package com.example.assistivesoftware;

import android.accessibilityservice.AccessibilityService;
import android.accessibilityservice.AccessibilityServiceInfo;
import android.annotation.SuppressLint;
import android.annotation.TargetApi;
import android.graphics.PixelFormat;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.WindowManager;
import android.view.accessibility.AccessibilityEvent;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.Toast;

public class MyAccessibilityService extends AccessibilityService {



    FrameLayout ll;

    TTSEngine tts = null;

    @SuppressLint("ClickableViewAccessibility")
    @TargetApi(24)
    @Override
    protected void onServiceConnected() {

        //creating a new tts engine
        //initialising the tts
        tts = new TTSEngine();
        tts.init(this);


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
        info.flags |= AccessibilityServiceInfo.FLAG_INCLUDE_NOT_IMPORTANT_VIEWS;
        //info.flags = AccessibilityServiceInfo.FLAG_REQUEST_TOUCH_EXPLORATION_MODE;
        //info.flags = AccessibilityServiceInfo.FLAG_REQUEST_ACCESSIBILITY_BUTTON;
        // We are keeping the timeout to 0 as we don’t need any delay or to pause our accessibility events
        info.notificationTimeout = 0;
        this.setServiceInfo(info);


        //////////////// FLOATING WINDOW ////////////////
        final WindowManager wm = (WindowManager) getSystemService(WINDOW_SERVICE);
        ll = new FrameLayout(this);
        final WindowManager.LayoutParams lp = new WindowManager.LayoutParams();
        lp.type = WindowManager.LayoutParams.TYPE_ACCESSIBILITY_OVERLAY;
        lp.format = PixelFormat.TRANSLUCENT;
        lp.flags |= WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE;
        lp.width = WindowManager.LayoutParams.WRAP_CONTENT;
        lp.height = WindowManager.LayoutParams.WRAP_CONTENT;
        lp.gravity = Gravity.TOP;

        lp.x = 0;
        lp.y = 0;

        LayoutInflater inflater = LayoutInflater.from(this);
        inflater.inflate(R.layout.floating_window, ll);
        wm.addView(ll, lp);


        ll.setOnTouchListener(new View.OnTouchListener() {

            private WindowManager.LayoutParams updatedParams = lp;
            int x , y;
            float touchX , touchY;

            @Override
            public boolean onTouch(View arg0, MotionEvent event) {

                switch (event.getAction()) {
                    case MotionEvent.ACTION_DOWN:

                        x = updatedParams.x;
                        y = updatedParams.y;

                        touchX = event.getRawX();
                        touchY = event.getRawY();

                        break;

                    case MotionEvent.ACTION_MOVE:

                        updatedParams.x = (int) (x + (event.getRawX() - touchX));
                        updatedParams.y = (int) (y + (event.getRawY() - touchY));

                        wm.updateViewLayout(ll, updatedParams);

                    default:

                        break;

                }

                return false;

            }

        });

        //configure exit button
        Button btnExit = ll.findViewById(R.id.btnExit);
        btnExit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                wm.removeView(ll);
                disableSelf();
            }
        });

    }



    @TargetApi(16)
    @Override
    public void onAccessibilityEvent(AccessibilityEvent event) {

        //AccessibilityNodeInfo source = event.getSource();
        final int eventType = event.getEventType();
        String eventText = null;
        switch (eventType) {
            case AccessibilityEvent.TYPE_VIEW_CLICKED:
                eventText = "Clicked on ";
                break;
            case AccessibilityEvent.TYPE_VIEW_FOCUSED:
                eventText = "Focused on";
                break;

//            case AccessibilityEvent.TYPE_WINDOW_CONTENT_CHANGED:
//                eventText = source.getText().toString();
//                break;
        }

        eventText = eventText + event.getText().toString();
        Toast.makeText(getApplicationContext(), eventText, Toast.LENGTH_LONG).show();

        //add text to tts queue
        tts.addTalk(eventText);

    }

    @Override
    public void onInterrupt() {

    }
}

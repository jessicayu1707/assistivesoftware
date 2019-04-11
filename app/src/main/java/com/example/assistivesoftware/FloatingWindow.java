package com.example.assistivesoftware;

import android.annotation.SuppressLint;
import android.annotation.TargetApi;
import android.app.Service;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.PixelFormat;
import android.os.IBinder;
import android.view.Gravity;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.view.WindowManager.LayoutParams;
import android.widget.Button;
import android.widget.LinearLayout;

public class FloatingWindow extends Service {

    private WindowManager wm;
    private LinearLayout ll;
    private Button exit, play;

    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @SuppressLint("ClickableViewAccessibility")
    @Override
    @TargetApi(26)
    public void onCreate() {
        super.onCreate();
        //creating new window manager and linear layout
        //adding stop and play button
        wm = (WindowManager) getSystemService(WINDOW_SERVICE);
        ll = new LinearLayout(this);
        exit = new Button(this);
        play = new Button(this);

        //set orientation of linear layout to vertical
        ll.setOrientation(LinearLayout.VERTICAL);


        //parameters for the buttons
        ViewGroup.LayoutParams btnParams = new ViewGroup.LayoutParams(ViewGroup.LayoutParams.FILL_PARENT,
                                                                      ViewGroup.LayoutParams.WRAP_CONTENT);

        //setting button text and params
        exit.setText("Exit");
        exit.setLayoutParams(btnParams);
        play.setText("Play");
        play.setLayoutParams(btnParams);

        //parameter for linear layout
        LinearLayout.LayoutParams llParams = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT,
                                                                           LinearLayout.LayoutParams.MATCH_PARENT);

        ll.setBackgroundColor(Color.argb(70, 115, 205, 255));
        ll.setLayoutParams(llParams);

        final LayoutParams params = new LayoutParams(300, 300,
                                     LayoutParams.TYPE_APPLICATION_OVERLAY,
                                     LayoutParams.FLAG_NOT_FOCUSABLE,
                                     PixelFormat.TRANSLUCENT);
        params.x = 0;
        params.y = 0;
        params.gravity = Gravity.CENTER;


        ll.addView(exit);
        ll.addView(play);
        wm.addView(ll, params);

        ll.setOnTouchListener(new View.OnTouchListener() {

            private WindowManager.LayoutParams updatedParams = params;
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

        //removes floating window when you press stop
        exit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                wm.removeView(ll);
                stopSelf();
            }
        });

        play.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startService(new Intent(FloatingWindow.this, MyAccessibilityService.class));
            }
        });


    }
}

//Some code referenced from Youtube, Stack Overflow, Documentation, etc.

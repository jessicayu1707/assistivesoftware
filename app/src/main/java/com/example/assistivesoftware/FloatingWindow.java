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
    private Button stop;
    //@androidx.annotation.Nullable
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
        wm = (WindowManager) getSystemService(WINDOW_SERVICE);
        ll = new LinearLayout(this);
        stop = new Button(this);

        ViewGroup.LayoutParams btnParams = new ViewGroup.LayoutParams(ViewGroup.LayoutParams.FILL_PARENT,
                                                                      ViewGroup.LayoutParams.WRAP_CONTENT);
        stop.setText("Stop");
        stop.setLayoutParams(btnParams);



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

        ll.addView(stop);
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

        stop.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                wm.removeView(ll);
                stopSelf();
            }
        });


    }
}

package com.example.assistivesoftware;

import android.app.Service;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.PixelFormat;
import android.os.IBinder;
import android.view.Gravity;
import android.view.WindowManager;
import android.view.WindowManager.LayoutParams;
import android.widget.LinearLayout;

public class FloatingWindow extends Service {

    private WindowManager wm;
    private LinearLayout ll;
    //@androidx.annotation.Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        //creating new window manager and linear layout
        wm = (WindowManager) getSystemService(WINDOW_SERVICE);

        ll = new LinearLayout(this);
        LinearLayout.LayoutParams llParams = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT,
                                                                           LinearLayout.LayoutParams.MATCH_PARENT);
        ll.setBackgroundColor(Color.argb(50, 115, 205, 255));
        ll.setLayoutParams(llParams);

        LayoutParams params = new LayoutParams(300, 300,
                                     LayoutParams.TYPE_APPLICATION_OVERLAY,
                                     LayoutParams.FLAG_NOT_FOCUSABLE,
                                     PixelFormat.TRANSLUCENT);
        params.x = 0;
        params.y = 0;
        params.gravity = Gravity.CENTER;

        wm.addView(ll, params);


    }
}

package com.iglesias.c.appgym.Ui;


import android.content.Context;
import android.os.Handler;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

public class CollapseWindow {
    Handler collapseNotificationHandler;
    public void collapseNow(final Boolean currentFocus, final Context context) {

        // Initialize 'collapseNotificationHandler'
        if (collapseNotificationHandler == null) {
            collapseNotificationHandler = new Handler();
        }

        // Post a Runnable with some delay - currently set to 300 ms
        collapseNotificationHandler.postDelayed(new Runnable() {

            @Override
            public void run() {

                // Use reflection to trigger a method from 'StatusBarManager'

                Object statusBarService = context.getSystemService("statusbar");
                Class<?> statusBarManager = null;

                try {
                    statusBarManager = Class.forName("android.app.StatusBarManager");
                } catch (ClassNotFoundException e) {
                    e.printStackTrace();
                }

                Method collapseStatusBar = null;

                try {
                    collapseStatusBar = statusBarManager .getMethod("collapsePanels");
                } catch (NoSuchMethodException e) {
                    e.printStackTrace();
                }

                collapseStatusBar.setAccessible(true);

                try {
                    collapseStatusBar.invoke(statusBarService);
                } catch (IllegalArgumentException e) {
                    e.printStackTrace();
                } catch (IllegalAccessException e) {
                    e.printStackTrace();
                } catch (InvocationTargetException e) {
                    e.printStackTrace();
                }

                // Check if the window focus has been returned
                // If it hasn't been returned, post this Runnable again
                // Currently, the delay is 100 ms. You can change this
                // value to suit your needs.
                if (!currentFocus) {
                    collapseNotificationHandler.postDelayed(this, 100L);
                }

            }
        }, 300L);
    }
}

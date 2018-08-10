package com.iglesias.c.appgym.Ui;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.view.Window;

import com.iglesias.c.appgym.Utils.ConstantsPreferences;

/**
 */

public class BaseActivity extends AppCompatActivity {
    private static final String TAG = BaseActivity.class.getSimpleName();
    public KioskMode kioskMode;
    protected View mDecorView;
    private boolean rotated = false;
    private SharedPreferences preferences;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        kioskMode = KioskMode.getKioskMode();
        mDecorView = getWindow().getDecorView();
        hideSystemUI();
        getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_HIDE_NAVIGATION);

        preferences = getApplicationContext().getSharedPreferences(ConstantsPreferences.NAME_PREFERENCE_CONFIG, Context.MODE_PRIVATE);
        rotated = preferences.getBoolean("rotated", false);
        if(rotated){
            setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);
        } else {
            setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_REVERSE_LANDSCAPE);
        }
    }

    @Override
    protected void onUserLeaveHint() {
        super.onUserLeaveHint();
        //move current activity to front if required
        Log.i(TAG, "onUserLeaveHint()...");
        kioskMode.moveTaskToFront(this);
    }
    @Override
    public void onResume(){
        super.onResume();
    }

    private void hideSystemUI() {
        mDecorView.setSystemUiVisibility(
                View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                        | View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
                        | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                        | View.SYSTEM_UI_FLAG_HIDE_NAVIGATION
                        | View.SYSTEM_UI_FLAG_FULLSCREEN
                        | View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY);
    }
}
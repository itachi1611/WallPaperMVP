package com.shinro.wallpaper.ui.splash;

import android.os.Bundle;
import android.os.Handler;

import com.shinro.wallpaper.R;
import com.shinro.wallpaper.bases.BaseActivityFullScreen;
import com.shinro.wallpaper.ui.photo.PhotoActivity;

import static com.shinro.wallpaper.ultis.Constants.SPLASH_TIME_OUT;

public class SplashActivity extends BaseActivityFullScreen implements SplashContract.View { //TODO: DON'T FORGET TO ADD THIS ACTIVITY TO THE MANIFEST FILE!!!

    private SplashContract.Presenter mPresenter = new SplashPresenter(this);    // Presenter

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        hideStatusBar();
        setContentView(R.layout.activity_splash);  //TODO: create the layout and add it here
        initView();
        onNavigateTime();
    }

    private void initView() {

    }

    private void onNavigateTime() {
        new Handler().postDelayed(() -> {
            navigateActivity(PhotoActivity.class);
            finish();
        },SPLASH_TIME_OUT);
    }

}

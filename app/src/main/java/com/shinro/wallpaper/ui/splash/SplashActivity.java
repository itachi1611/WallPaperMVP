package com.shinro.wallpaper.ui.splash;

import android.os.Bundle;
import android.os.Handler;

import com.shinro.wallpaper.R;
import com.shinro.wallpaper.bases.BaseActivity;
import com.shinro.wallpaper.ui.photo.photo_grid.PhotoGridActivity;

import static com.shinro.wallpaper.ultis.Constants.SPLASH_TIME_OUT;

public class SplashActivity extends BaseActivity implements SplashContract.View { //TODO: DON'T FORGET TO ADD THIS ACTIVITY TO THE MANIFEST FILE!!!

    private SplashContract.Presenter mPresenter = new SplashPresenter(this);    // Presenter

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        hideStatusBar();
        setContentView(R.layout.activity_splash);  //TODO: create the layout and add it here
        onNavigateTime();
    }

    private void onNavigateTime() {
        new Handler().postDelayed(() -> {
            navigateActivity(PhotoGridActivity.class);
            finish();
        },SPLASH_TIME_OUT);
    }

}

package com.shinro.wallpaper.ui.photo;

import android.os.Bundle;

import com.shinro.wallpaper.R;
import com.shinro.wallpaper.bases.BaseActivity;
import com.shinro.wallpaper.ultis.AppLogger;

import butterknife.ButterKnife;

public class PhotoActivity extends BaseActivity implements PhotoContract.View { //TODO: DON'T FORGET TO ADD THIS ACTIVITY TO THE MANIFEST FILE!!!



    private PhotoContract.Presenter mPresenter = new PhotoPresenter(this);    // Presenter

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_photo);  //TODO: create the layout and add it here
        initView();
    }

    private void initView() {
        ButterKnife.bind(this);
    }

    @Override
    public void onFetchFavouriteImageListSuccess() {

    }

    @Override
    public void onFetchFavouriteImageListError(Throwable e) {
        AppLogger.e(e);
    }
}

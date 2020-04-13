package com.shinro.wallpaper.ui.photo;

import com.shinro.wallpaper.ultis.Constants;

public class PhotoPresenter extends Constants implements PhotoContract.Presenter {

    private PhotoContract.View mView;

    public PhotoPresenter(PhotoContract.View mView) {
        this.mView = mView;
    }

}

package com.shinro.wallpaper.ui.photo_detail;

public class PhotoDetailPresenter implements PhotoDetailContract.Presenter {

    private PhotoDetailContract.View mView;

    public PhotoDetailPresenter(PhotoDetailContract.View mView) {
        this.mView = mView;
    }

}

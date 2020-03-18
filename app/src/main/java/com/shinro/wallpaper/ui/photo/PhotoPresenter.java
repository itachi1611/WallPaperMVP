package com.shinro.wallpaper.ui.photo;

public class PhotoPresenter implements PhotoContract.Presenter {

    private PhotoContract.View mView;

    public PhotoPresenter(PhotoContract.View mView) {
        this.mView = mView;
    }

    @Override
    public void onFetchFavouriteImageList() {

    }
}

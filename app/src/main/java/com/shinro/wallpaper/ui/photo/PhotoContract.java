package com.shinro.wallpaper.ui.photo;

public interface PhotoContract {

    interface View {
        void onFetchFavouriteImageListSuccess();
        void onFetchFavouriteImageListError(Throwable e);
    }

    interface Presenter {
        void onFetchFavouriteImageList();
    }

}

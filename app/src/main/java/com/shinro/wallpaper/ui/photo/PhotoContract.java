package com.shinro.wallpaper.ui.photo;

import com.shinro.wallpaper.models.Photo;

import java.util.List;

public interface PhotoContract {

    interface View {
        void onFetchFavouriteImageListSuccess(List<Photo> mPhotos);
        void onFetchFavouriteImageListError(Throwable e);
        void onRefreshFavouriteImageListSuccess(List<Photo> mPhotos);
        void onRefreshFavouriteImageListError(Throwable e);
    }

    interface Presenter {
        void onFetchFavouriteImageList(String p);
        void onRefreshFavouriteImageList(String p);
    }

}

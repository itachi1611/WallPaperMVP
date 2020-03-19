package com.shinro.wallpaper.ui.photo;

import com.shinro.wallpaper.models.Photo;

import java.util.List;

public interface PhotoContract {

    interface View {
        void onFetchFavouriteImageListSuccess(List<Photo> mPhotos, int action);
        void onFetchFavouriteImageListError(Throwable e);
    }

    interface Presenter {
        void onFetchFavouriteImageList(int p, int action);
    }

}

package com.shinro.wallpaper.ui.photo.grid_view;

import com.shinro.wallpaper.models.Photo;

import java.util.List;

public interface GridViewContract {

    interface View {
        void onFetchFavouriteImageListSuccess(List<Photo> mPhotos);
        void onFetchFavouriteImageListError(Throwable e);
        void onRefreshFavouriteImageListSuccess(List<Photo> mPhotos);
        void onRefreshFavouriteImageListError(Throwable e);
        void onLoadMoreFavouriteImageListSuccess(List<Photo> mPhotos);
        void onLoadMoreFavouriteImageListError(Throwable e);
    }

    interface Presenter {
        void onFetchFavouriteImageList(String p);
        void onRefreshFavouriteImageList(String p);
        void onLoadMoreFavouriteImageList(String p);
    }

}

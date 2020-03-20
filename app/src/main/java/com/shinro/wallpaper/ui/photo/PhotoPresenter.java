package com.shinro.wallpaper.ui.photo;

import android.util.Log;

import com.shinro.wallpaper.api.ApiUtil;
import com.shinro.wallpaper.models.FlickrFavorites;
import com.shinro.wallpaper.models.Photo;
import com.shinro.wallpaper.ultis.Constants;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

public class PhotoPresenter extends Constants implements PhotoContract.Presenter {

    private PhotoContract.View mView;

    public PhotoPresenter(PhotoContract.View mView) {
        this.mView = mView;
    }

    @Override
    public void onFetchFavouriteImageList(String p) {
        ApiUtil.getFavoritesImageList(false, null)
                .onFetchFlickrFavoritesImageList(FLICKR_METHOD, FLICKR_API_KEY, FLICKR_USER_ID, null, null, FLICKR_OPTION, FLICKR_PER_PAGE, p, FLICKR_FORMAT, FLICKR_NO_JSON_CALLBACK)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .subscribe(new Observer<FlickrFavorites>() {
                    @Override
                    public void onSubscribe(Disposable d) {

                    }

                    @Override
                    public void onNext(FlickrFavorites flickrFavorites) {
                        Log.d("NamNT", String.valueOf(flickrFavorites.getPhotos().getPhoto().size()));
                        mView.onFetchFavouriteImageListSuccess(onConvertData(flickrFavorites));
                    }

                    @Override
                    public void onError(Throwable e) {
                        mView.onFetchFavouriteImageListError(e);
                    }

                    @Override
                    public void onComplete() {

                    }
                });
    }

    @Override
    public void onRefreshFavouriteImageList(String p) {
        ApiUtil.getFavoritesImageList(false, null)
                .onFetchFlickrFavoritesImageList(FLICKR_METHOD, FLICKR_API_KEY, FLICKR_USER_ID, null, null, FLICKR_OPTION, FLICKR_PER_PAGE, p, FLICKR_FORMAT, FLICKR_NO_JSON_CALLBACK)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .subscribe(new Observer<FlickrFavorites>() {
                    @Override
                    public void onSubscribe(Disposable d) {

                    }

                    @Override
                    public void onNext(FlickrFavorites flickrFavorites) {
                        Log.d("NamNT", String.valueOf(flickrFavorites.getPhotos().getPhoto().size()));
                        mView.onRefreshFavouriteImageListSuccess(onConvertData(flickrFavorites));
                    }

                    @Override
                    public void onError(Throwable e) {
                        mView.onRefreshFavouriteImageListError(e);
                    }

                    @Override
                    public void onComplete() {

                    }
                });
    }

    private List<Photo> onConvertData(FlickrFavorites favorites) {
        List<Photo> photos = new ArrayList<>();
        if(favorites != null) {
            photos.addAll(favorites.getPhotos().getPhoto());
            return photos;
        }
        return null;
    }

}

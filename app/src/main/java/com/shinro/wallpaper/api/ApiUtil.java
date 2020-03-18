package com.shinro.wallpaper.api;

import com.shinro.wallpaper.ultis.Constants;

public class ApiUtil extends Constants {

    public static FlickrService getFavoritesImageList(boolean isAuthorization, String token) {
        return RetrofitClient.getClient(BASE_URL, isAuthorization, token).create(FlickrService.class);
    }

}

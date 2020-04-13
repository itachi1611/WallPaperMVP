package com.shinro.wallpaper.ui.photo.list_view;

public class ListViewPresenter implements ListViewContract.Presenter {

    private ListViewContract.View mView;

    public ListViewPresenter(ListViewContract.View mView) {
        this.mView = mView;
    }

}

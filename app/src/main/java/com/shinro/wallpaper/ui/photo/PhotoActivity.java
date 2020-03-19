package com.shinro.wallpaper.ui.photo;

import android.os.Bundle;

import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.StaggeredGridLayoutManager;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.shinro.wallpaper.R;
import com.shinro.wallpaper.adapters.FlickrFavoritesImageStaggeredRecycleViewAdapter;
import com.shinro.wallpaper.bases.BaseActivity;
import com.shinro.wallpaper.models.Photo;
import com.shinro.wallpaper.ultis.AppLogger;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class PhotoActivity extends BaseActivity implements PhotoContract.View { //TODO: DON'T FORGET TO ADD THIS ACTIVITY TO THE MANIFEST FILE!!!

    @BindView(R.id.swipeContainer)
    SwipeRefreshLayout swipeContainer;

    @BindView(R.id.rvImageList)
    RecyclerView rvImageList;

    private FlickrFavoritesImageStaggeredRecycleViewAdapter adapter;
    private StaggeredGridLayoutManager layoutManager;

    //Declare and initial value
    private int page = 1;

    //Declare list photo
    private List<Photo> photos;

    private PhotoContract.Presenter mPresenter = new PhotoPresenter(this);    // Presenter

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //Hide status bar
        hideStatusBar();
        setContentView(R.layout.activity_photo);  //TODO: create the layout and add it here

        initView();
    }

    private void initView() {
        ButterKnife.bind(this);
    }

    @Override
    public void onFetchFavouriteImageListSuccess() {

    }

    @Override
    public void onFetchFavouriteImageListError(Throwable e) {
        AppLogger.e(e);
    }
}

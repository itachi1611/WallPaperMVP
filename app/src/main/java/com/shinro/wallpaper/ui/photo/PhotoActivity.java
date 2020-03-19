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
import com.shinro.wallpaper.ultis.RecyclerViewUtils.EndlessRecyclerViewScrollListener;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

import static com.shinro.wallpaper.ultis.Constants.NUM_COLUMNS;

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

        //Init RecyclerView
        initRecyclerView();

        //Fetch image data from Flickr API
        /**
         *  0 -> Load new data
         *  1 -> Load more data
         */
        onFetchFlickrImageData(page, 0);

        //Handle swipe action to refresh data
        onSwipeRefreshData();

        //Handle Load more event
        onScrollToLoadMore();

    }

    private void initView() {
        ButterKnife.bind(this);
        photos = new ArrayList<>();
    }

    private void initRecyclerView() {
        layoutManager = new StaggeredGridLayoutManager(NUM_COLUMNS, 1);
        rvImageList.setLayoutManager(layoutManager);
    }

    private void onFetchFlickrImageData(int p, int action) {
        mPresenter.onFetchFavouriteImageList(p, action);
    }

    private void onSwipeRefreshData() {
        swipeContainer.setOnRefreshListener(() -> {
            PhotoActivity.this.page = 1;
            photos.clear();
            onFetchFlickrImageData(PhotoActivity.this.page, 1);
            onScrollToLoadMore();
        });
    }

    private void onScrollToLoadMore() {
        rvImageList.addOnScrollListener(new EndlessRecyclerViewScrollListener(layoutManager) {
            @Override
            public void onLoadMore(int page, int totalItemsCount, RecyclerView view) {
                PhotoActivity.this.page++;
                onShowLoading();
                onFetchFlickrImageData(PhotoActivity.this.page++, 1);
            }
        });
    }

    private void onLoadDataToRecyclerView() {
        adapter = new FlickrFavoritesImageStaggeredRecycleViewAdapter(photos, PhotoActivity.this);
        rvImageList.setAdapter(adapter);
    }

    @Override
    public void onFetchFavouriteImageListSuccess(List<Photo> mPhotos, int action) {
        if(action == 0) {
            onHideLoading();
            photos.addAll(mPhotos);
            swipeContainer.setRefreshing(false);
            onLoadDataToRecyclerView();
        } else if(action == 1) {
            onHideLoading();
            photos.addAll(mPhotos);
            swipeContainer.setRefreshing(false);
            adapter.notifyDataSetChanged();
        }
    }

    @Override
    public void onFetchFavouriteImageListError(Throwable e) {
        onHideLoading();
        AppLogger.e(e);
    }
}

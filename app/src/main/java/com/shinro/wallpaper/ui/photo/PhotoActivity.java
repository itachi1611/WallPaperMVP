package com.shinro.wallpaper.ui.photo;

import android.os.Bundle;
import android.os.Handler;

import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.DividerItemDecoration;
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
        onFetchFlickrImageData(page);

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
        rvImageList.setItemAnimator(new DefaultItemAnimator());
        rvImageList.addItemDecoration(new DividerItemDecoration(PhotoActivity.this, DividerItemDecoration.VERTICAL));
        rvImageList.setHasFixedSize(true);
    }

    private void onFetchFlickrImageData(int p) {
        mPresenter.onFetchFavouriteImageList(String.valueOf(p));
    }

    private void onRefreshFlickrImageData(int p) {
        mPresenter.onRefreshFavouriteImageList(String.valueOf(p));
    }

    private void onLoadMoreFlickrImageData(int p) {
        mPresenter.onLoadMoreFavouriteImageList(String.valueOf(p));
    }

    private void onSwipeRefreshData() {
        swipeContainer.setOnRefreshListener(() -> {
            page = 1;
            onRefreshFlickrImageData(page);
            handleSwipeRefreshAction();
            //onScrollToLoadMore();
        });
    }

    private void onScrollToLoadMore() {
        rvImageList.addOnScrollListener(new EndlessRecyclerViewScrollListener(layoutManager) {
            @Override
            public void onLoadMore(int page, int totalItemsCount, RecyclerView view) {
                page++;
                onShowLoading();
                onLoadMoreFlickrImageData(page++);
            }
        });
    }

    private void onLoadDataToRecyclerView() {
        if(adapter != null ) {
            adapter = null;
        }
        if(adapter == null) {
            adapter = new FlickrFavoritesImageStaggeredRecycleViewAdapter(photos);
            rvImageList.getRecycledViewPool().clear();
            rvImageList.setAdapter(adapter);
        }
    }

    private void handleSwipeRefreshAction() {
        new Handler().postDelayed(() -> {
            swipeContainer.setRefreshing(false);
        }, 150);
    }

    @Override
    public void onFetchFavouriteImageListSuccess(List<Photo> mPhotos) {
        onHideLoading();
        photos.clear();
        if(mPhotos != null) {
            photos.addAll(mPhotos);
        }
        onLoadDataToRecyclerView();
    }

    @Override
    public void onFetchFavouriteImageListError(Throwable e) {
        onHideLoading();
        AppLogger.e(e);
    }

    @Override
    public void onRefreshFavouriteImageListSuccess(List<Photo> mPhotos) {
        onHideLoading();
        photos.clear();
        if(mPhotos != null) {
            photos.addAll(mPhotos);
        }
        //onLoadDataToRecyclerView();
        adapter.notifyDataSetChanged();
    }

    @Override
    public void onRefreshFavouriteImageListError(Throwable e) {
        onHideLoading();
        AppLogger.e(e);
    }

    @Override
    public void onLoadMoreFavouriteImageListSuccess(List<Photo> mPhotos) {
        onHideLoading();
        if(mPhotos != null) {
            photos.addAll(mPhotos);
        }
        adapter.notifyDataSetChanged();
    }

    @Override
    public void onLoadMoreFavouriteImageListError(Throwable e) {
        onHideLoading();
        AppLogger.e(e);
    }
}

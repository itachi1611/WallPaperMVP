package com.shinro.wallpaper.ui.photo.photo_grid;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.Menu;
import android.view.MenuItem;

import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.StaggeredGridLayoutManager;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.shinro.wallpaper.R;
import com.shinro.wallpaper.adapters.FlickrFavoritesImageStaggeredRecycleViewAdapter;
import com.shinro.wallpaper.bases.BaseActivity;
import com.shinro.wallpaper.models.Photo;
import com.shinro.wallpaper.ui.photo.about.AboutActivity;
import com.shinro.wallpaper.ui.photo.photo_card.PhotoCardActivity;
import com.shinro.wallpaper.ultis.AppLogger;
import com.shinro.wallpaper.ultis.RecyclerViewUtils.EndlessRecyclerViewScrollListener;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

import static com.shinro.wallpaper.ultis.Constants.NUM_COLUMNS;

public class PhotoGridActivity extends BaseActivity implements PhotoGridContract.View { //TODO: DON'T FORGET TO ADD THIS ACTIVITY TO THE MANIFEST FILE!!!

    @BindView(R.id.swipeContainer)
    SwipeRefreshLayout swipeContainer;

    @BindView(R.id.rvImageGrid)
    RecyclerView rvImageGrid;

    @BindView(R.id.bottom_navigation)
    BottomNavigationView navigationView;

    private FlickrFavoritesImageStaggeredRecycleViewAdapter adapter;
    private StaggeredGridLayoutManager layoutManager;

    private Unbinder unbinder;

    //Declare and initial value
    private int page = 1;

    //Declare list photo
    private List<Photo> photos;

    private PhotoGridContract.Presenter mPresenter = new PhotoGridPresenter(this);    // Presenter

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //Hide status bar
        hideStatusBar();

        setContentView(R.layout.activity_grid_photo);  //TODO: create the layout and add it here

        initView();

        navigationView.setOnNavigationItemSelectedListener(navListener);

        //Init RecyclerView
        initRecyclerView();

        //Handle swipe action to refresh data
        onSwipeRefreshData();

        //Handle Load more event
        onScrollToLoadMore();
    }

    private void initView() {
        unbinder = ButterKnife.bind(this);
        photos = new ArrayList<>();
    }

    @Override
    protected void onStart() {
        super.onStart();
        updateBottomNavigationBarState();
    }

    private void updateBottomNavigationBarState(){
        int actionId = R.id.item_grid;
        selectBottomNavigationBarItem(actionId);
    }

    void selectBottomNavigationBarItem(int itemId) {
        MenuItem item = navigationView.getMenu().findItem(itemId);
        item.setChecked(true);
    }

    @Override
    protected void onPause() {
        super.onPause();
        overridePendingTransition(0, 0);
    }

    private BottomNavigationView.OnNavigationItemSelectedListener navListener = menuItem -> {
        navigationView.postDelayed(() -> {
            switch (menuItem.getItemId()) {
                case R.id.item_grid:
                    navigateActivity(PhotoGridActivity.class, Intent.FLAG_ACTIVITY_CLEAR_TOP);
                    break;
                case R.id.item_list:
                    navigateActivity(PhotoCardActivity.class, Intent.FLAG_ACTIVITY_CLEAR_TOP);
                    break;
                case R.id.item_about:
                    navigateActivity(AboutActivity.class, Intent.FLAG_ACTIVITY_CLEAR_TOP);
                    break;
            }
            finish();
        }, 200);
        return true;
    };

    @Override
    protected void onResume() {
        super.onResume();
        //Fetch image data from Flickr API
        onFetchFlickrImageData(page);
    }

    private void initRecyclerView() {
        layoutManager = new StaggeredGridLayoutManager(NUM_COLUMNS, 1);
        rvImageGrid.setLayoutManager(layoutManager);
        rvImageGrid.setItemAnimator(new DefaultItemAnimator());
        rvImageGrid.addItemDecoration(new DividerItemDecoration(this, DividerItemDecoration.VERTICAL));
        rvImageGrid.setHasFixedSize(true);
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
        rvImageGrid.addOnScrollListener(new EndlessRecyclerViewScrollListener(layoutManager) {
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
            //rvImageGrid.getRecycledViewPool().clear();
            rvImageGrid.setAdapter(adapter);
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

    @Override
    protected void onDestroy() {
        super.onDestroy();
        unbinder.unbind();
    }

}

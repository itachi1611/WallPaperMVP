package com.shinro.wallpaper.ui.photo.photo_card;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ImageView;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.shinro.wallpaper.R;
import com.shinro.wallpaper.adapters.RecyclerViewImageCardAdapter;
import com.shinro.wallpaper.bases.BaseActivity;
import com.shinro.wallpaper.helpers.FoxyScaleHelper;
import com.shinro.wallpaper.models.Photo;
import com.shinro.wallpaper.ui.customs.FoxyRecyclerView;
import com.shinro.wallpaper.ui.customs.FoxySwipeRefreshLayout;
import com.shinro.wallpaper.ui.photo.about.AboutActivity;
import com.shinro.wallpaper.ui.photo.photo_grid.PhotoGridActivity;
import com.shinro.wallpaper.ultis.AppLogger;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

public class PhotoCardActivity extends BaseActivity implements PhotoCardContract.View { //TODO: DON'T FORGET TO ADD THIS ACTIVITY TO THE MANIFEST FILE!!!

    @BindView(R.id.swipeLayout)
    FoxySwipeRefreshLayout foxySwipeRefreshLayout;

    @BindView(R.id.rvList)
    FoxyRecyclerView foxyRecyclerView;

    @BindView(R.id.ivBlurView)
    ImageView mBlurView;

    @BindView(R.id.bottom_navigation)
    BottomNavigationView navigationView;

    private List<Photo> photos;

    private FoxyScaleHelper foxyScaleHelper;
    private RecyclerViewImageCardAdapter adapter;
    private LinearLayoutManager layoutManager;

    private Unbinder unbinder;

    private Runnable mBlurRunnable;

    //Declare and initial value
    private int page = 1;
    private int mLastPos = -1;

    private PhotoCardContract.Presenter mPresenter = new PhotoCardPresenter(this);    // Presenter

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //Hide status bar
        hideStatusBar();

        setContentView(R.layout.activity_card_photo);  //TODO: create the layout and add it here
        initView();

        navigationView.setOnNavigationItemSelectedListener(navListener);

        //Init RecyclerView
        //initRecyclerView();

        //Handle swipe action to refresh data
        //onSwipeRefreshData();

        //Handle Load more event
        //onScrollToLoadMore();
    }

    private void initView() {
        unbinder = ButterKnife.bind(this);
        photos = new ArrayList<>();

        foxySwipeRefreshLayout.setColorSchemeResources(
                android.R.color.holo_blue_bright,
                android.R.color.holo_green_light,
                android.R.color.holo_orange_light,
                android.R.color.holo_red_light);
    }

    @Override
    protected void onStart() {
        super.onStart();
        updateBottomNavigationBarState();
    }

    private void updateBottomNavigationBarState(){
        int actionId = R.id.item_list;
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
                    navigateActivity(PhotoGridActivity.class, Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_SINGLE_TOP);
                    break;
                case R.id.item_list:
                    navigateActivity(PhotoCardActivity.class, Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_SINGLE_TOP);
                    break;
                case R.id.item_about:
                    navigateActivity(AboutActivity.class, Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_SINGLE_TOP);
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
        if(!( this instanceof PhotoCardActivity)) {
            onFetchFlickrImageData(page);
        }

    }

    private void initRecyclerView() {
        layoutManager = new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false);
        foxyRecyclerView.setLayoutManager(layoutManager);
        foxyRecyclerView.setHasFixedSize(true);
        adapter = new RecyclerViewImageCardAdapter(photos);
        foxyRecyclerView.setAdapter(adapter);
        foxyScaleHelper = new FoxyScaleHelper();
        foxyScaleHelper.setFirstItemPos(0);
        foxyScaleHelper.attachToRecyclerView(foxyRecyclerView);
        initBlurBackground();
    }

    private void initBlurBackground() {
        foxyRecyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);
                if (newState == RecyclerView.SCROLL_STATE_IDLE) {
                }
            }
        });

    }

//    private void onSwipeRefreshData() {
//        foxySwipeRefreshLayout.setOnRefreshListener(() -> {
//            page = 1;
//            onRefreshFlickrImageData(page);
//            handleSwipeRefreshAction();
//            //onScrollToLoadMore();
//        });
//    }

//    private void onScrollToLoadMore() {
//        foxyRecyclerView.addOnScrollListener(new EndlessRecyclerViewScrollListener(layoutManager) {
//            @Override
//            public void onLoadMore(int page, int totalItemsCount, RecyclerView view) {
//                page++;
//                onShowLoading();
//                onLoadMoreFlickrImageData(page++);
//            }
//        });
//    }

//    private void onLoadDataToRecyclerView() {
//        if(adapter != null ) {
//            adapter = null;
//        }
//        if(adapter == null) {
//            adapter = new RecyclerViewImageCardAdapter(mPhotos);
//            //rvImageGrid.getRecycledViewPool().clear();
//            foxyRecyclerView.setAdapter(adapter);
//        }
//    }

//    private void handleSwipeRefreshAction() {
//        new Handler().postDelayed(() -> {
//            foxySwipeRefreshLayout.setRefreshing(false);
//            foxyScaleHelper.setCurrentItem(foxyScaleHelper.getCurrentItem(), true);
//            adapter.setPhotoList(mPhotos);
//            adapter.notifyDataSetChanged();
//        }, 150);
//    }

    @Override
    public void onFetchFavouriteImageListSuccess(List<Photo> mPhotos) {
        onHideLoading();
        photos.clear();
        if(mPhotos != null) {
            photos.addAll(mPhotos);
        }
        //onLoadDataToRecyclerView();

        initRecyclerView();
    }

    @Override
    public void onFetchFavouriteImageListError(Throwable e) {
        onHideLoading();
        AppLogger.e(e);
    }

    @Override
    public void onRefreshFavouriteImageListSuccess(List<Photo> mPhotos) {
//        onHideLoading();
//        mPhotos.clear();
//        if(mPhotos != null) {
//            mPhotos.addAll(mPhotos);
//        }
//        //onLoadDataToRecyclerView();
//        adapter.notifyDataSetChanged();
    }

    @Override
    public void onRefreshFavouriteImageListError(Throwable e) {
//        onHideLoading();
//        AppLogger.e(e);
    }

    @Override
    public void onLoadMoreFavouriteImageListSuccess(List<Photo> mPhotos) {
//        onHideLoading();
//        if(mPhotos != null) {
//            mPhotos.addAll(mPhotos);
//        }
//        adapter.notifyDataSetChanged();
    }

    @Override
    public void onLoadMoreFavouriteImageListError(Throwable e) {
//        onHideLoading();
//        AppLogger.e(e);
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

    @Override
    protected void onDestroy() {
        super.onDestroy();
        unbinder.unbind();
    }

}

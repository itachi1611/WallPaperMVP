package com.shinro.wallpaper.ui.photo.grid_view;

import android.os.Bundle;
import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.StaggeredGridLayoutManager;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.shinro.wallpaper.R;
import com.shinro.wallpaper.adapters.FlickrFavoritesImageStaggeredRecycleViewAdapter;
import com.shinro.wallpaper.bases.BaseFragment;
import com.shinro.wallpaper.models.Photo;
import com.shinro.wallpaper.ultis.AppLogger;
import com.shinro.wallpaper.ultis.RecyclerViewUtils.EndlessRecyclerViewScrollListener;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

import static com.shinro.wallpaper.ultis.Constants.NUM_COLUMNS;

public class GridViewFragment extends BaseFragment implements GridViewContract.View {

    @BindView(R.id.swipeContainer)
    SwipeRefreshLayout swipeContainer;

    @BindView(R.id.rvImageGrid)
    RecyclerView rvImageGrid;

    private FlickrFavoritesImageStaggeredRecycleViewAdapter adapter;
    private StaggeredGridLayoutManager layoutManager;

    private Unbinder unbinder;

    //Declare and initial value
    private int page = 1;

    //Declare list photo
    private List<Photo> photos;

    private GridViewContract.Presenter mPresenter = new GridViewPresenter(this);   // Presenter

    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        //TODO: Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_view_grid, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        initView(view);

        //Init RecyclerView
        initRecyclerView();

        //Handle swipe action to refresh data
        onSwipeRefreshData();

        //Handle Load more event
        onScrollToLoadMore();
    }

    private void initView(@NonNull View view) {
        unbinder = ButterKnife.bind(this, view);
        photos = new ArrayList<>();
    }

    @Override
    public void onResume() {
        super.onResume();

        //Fetch image data from Flickr API
        //onFetchFlickrImageData(page);
    }

    private void initRecyclerView() {
        layoutManager = new StaggeredGridLayoutManager(NUM_COLUMNS, 1);
        rvImageGrid.setLayoutManager(layoutManager);
        rvImageGrid.setItemAnimator(new DefaultItemAnimator());
        rvImageGrid.addItemDecoration(new DividerItemDecoration(getContext(), DividerItemDecoration.VERTICAL));
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
            rvImageGrid.getRecycledViewPool().clear();
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
    public void onDestroy() {
        super.onDestroy();
        unbinder.unbind();
    }

}

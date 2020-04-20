package com.shinro.wallpaper.ui.photo.list_view;

import android.os.Bundle;
import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.shinro.wallpaper.R;
import com.shinro.wallpaper.adapters.RecyclerViewImageCardAdapter;
import com.shinro.wallpaper.bases.BaseFragment;
import com.shinro.wallpaper.helpers.FoxyScaleHelper;
import com.shinro.wallpaper.models.Photo;
import com.shinro.wallpaper.ui.customs.FoxyRecyclerView;
import com.shinro.wallpaper.ui.customs.FoxySwipeRefreshLayout;
import com.shinro.wallpaper.ultis.AppLogger;
import com.shinro.wallpaper.ultis.RecyclerViewUtils.EndlessRecyclerViewScrollListener;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

public class ListViewFragment extends BaseFragment implements ListViewContract.View {

    @BindView(R.id.swipeLayout)
    FoxySwipeRefreshLayout foxySwipeRefreshLayout;

    @BindView(R.id.rvList)
    FoxyRecyclerView foxyRecyclerView;

    private List<Photo> mPhotos;

    private FoxyScaleHelper foxyScaleHelper;
    private RecyclerViewImageCardAdapter adapter;
    private LinearLayoutManager layoutManager;

    private Unbinder unbinder;

    //Declare and initial value
    private int page = 1;

    private ListViewContract.Presenter mPresenter = new ListViewPresenter(this);   // Presenter

    public ListViewFragment() {}

    public static ListViewFragment newInstance() {
        ListViewFragment fragment = new ListViewFragment();
        return fragment;
    }

    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        //TODO: Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_view_list, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        initView(view);

        //Init RecyclerView
        initRecyclerView();

        //Handle swipe action to refresh data
        //onSwipeRefreshData();

        //Handle Load more event
        //onScrollToLoadMore();
    }

    @Override
    public void onResume() {
        super.onResume();
        //Fetch image data from Flickr API
        onFetchFlickrImageData(page);
    }

    private void initView(@NonNull View view) {
        unbinder = ButterKnife.bind(this, view);
        mPhotos = new ArrayList<>();

        foxySwipeRefreshLayout.setColorSchemeResources(
                android.R.color.holo_blue_bright,
                android.R.color.holo_green_light,
                android.R.color.holo_orange_light,
                android.R.color.holo_red_light);
    }

    private void initRecyclerView() {
        layoutManager = new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL, false);
        foxyRecyclerView.setLayoutManager(layoutManager);
        foxyRecyclerView.setHasFixedSize(true);
        adapter = new RecyclerViewImageCardAdapter(mPhotos);
        foxyRecyclerView.setAdapter(adapter);
        foxyScaleHelper = new FoxyScaleHelper();
        foxyScaleHelper.setFirstItemPos(0);
        foxyScaleHelper.attachToRecyclerView(foxyRecyclerView);
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
        mPhotos.clear();
        if(mPhotos != null) {
            mPhotos.addAll(mPhotos);
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
    public void onDestroy() {
        super.onDestroy();
        unbinder.unbind();
    }

}

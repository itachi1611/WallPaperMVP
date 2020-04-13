package com.shinro.wallpaper.ui.photo_detail;

import android.os.Bundle;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.shinro.wallpaper.R;
import com.shinro.wallpaper.adapters.RecyclerViewImageCardAdapter;
import com.shinro.wallpaper.bases.BaseActivity;
import com.shinro.wallpaper.ui.customs.FoxyRecyclerView;
import com.shinro.wallpaper.helpers.FoxyScaleHelper;
import com.shinro.wallpaper.ui.customs.FoxySwipeRefreshLayout;
import com.shinro.wallpaper.models.Photo;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

public class PhotoDetailActivity extends BaseActivity implements PhotoDetailContract.View, SwipeRefreshLayout.OnRefreshListener { //TODO: DON'T FORGET TO ADD THIS ACTIVITY TO THE MANIFEST FILE!!!

    @BindView(R.id.swipeLayout)
    FoxySwipeRefreshLayout foxySwipeRefreshLayout;

    @BindView(R.id.rvList)
    FoxyRecyclerView foxyRecyclerView;

    private List<Photo> mPhotos;

    private FoxyScaleHelper foxyScaleHelper;
    private RecyclerViewImageCardAdapter adapter;

    private Unbinder unbinder;

    private PhotoDetailContract.Presenter mPresenter = new PhotoDetailPresenter(this);    // Presenter

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        hideStatusBar();
        setContentView(R.layout.activity_detail_photo);  //TODO: create the layout and add it here

        initView();

        initRecyclerView();
    }

    @Override
    protected void onResume() {
        super.onResume();
        initData();
    }

    private void initView() {
        unbinder = ButterKnife.bind(this);

        mPhotos = new ArrayList<>();

        foxySwipeRefreshLayout.setOnRefreshListener(this);

        foxySwipeRefreshLayout.setColorSchemeResources(
                android.R.color.holo_blue_bright,
                android.R.color.holo_green_light,
                android.R.color.holo_orange_light,
                android.R.color.holo_red_light);
    }

    private void initData() {
    }

    private void initRecyclerView() {
        LinearLayoutManager layoutManager = new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false);
        foxyRecyclerView.setLayoutManager(layoutManager);
        foxyRecyclerView.setHasFixedSize(true);
        foxyScaleHelper = new FoxyScaleHelper();
        foxyScaleHelper.setFirstItemPos(1000);
        foxyScaleHelper.attachToRecyclerView(foxyRecyclerView);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        unbinder.unbind();
    }

    @Override
    public void onRefresh() {

    }
}

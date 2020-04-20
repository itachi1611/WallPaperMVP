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





    private Unbinder unbinder;

    private PhotoDetailContract.Presenter mPresenter = new PhotoDetailPresenter(this);    // Presenter

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        hideStatusBar();
        setContentView(R.layout.activity_detail_photo);  //TODO: create the layout and add it here

        initView();
    }

    @Override
    protected void onResume() {
        super.onResume();
        initData();
    }

    private void initView() {
        unbinder = ButterKnife.bind(this);
    }

    private void initData() {
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

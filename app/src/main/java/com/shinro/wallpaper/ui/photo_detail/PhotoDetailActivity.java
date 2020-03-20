package com.shinro.wallpaper.ui.photo_detail;

import android.os.Bundle;
import android.widget.RelativeLayout;

import androidx.viewpager2.widget.ViewPager2;

import com.getbase.floatingactionbutton.FloatingActionsMenu;
import com.shinro.wallpaper.R;
import com.shinro.wallpaper.bases.BaseActivity;

import butterknife.BindView;
import butterknife.ButterKnife;

public class PhotoDetailActivity extends BaseActivity implements PhotoDetailContract.View { //TODO: DON'T FORGET TO ADD THIS ACTIVITY TO THE MANIFEST FILE!!!

    @BindView(R.id.rlContainer)
    RelativeLayout rlContainer;

    @BindView(R.id.vpImageDetail)
    ViewPager2 vpImageDetail;

    @BindView(R.id.multiple_actions)
    FloatingActionsMenu multipleActions;

    private PhotoDetailContract.Presenter mPresenter = new PhotoDetailPresenter(this);    // Presenter

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        hideStatusBar();
        setContentView(R.layout.activity_detail_photo);  //TODO: create the layout and add it here
        initView();
    }

    private void initView() {
        ButterKnife.bind(this);
    }

}

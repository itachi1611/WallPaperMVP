package com.shinro.wallpaper.ui.photo;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.shinro.wallpaper.R;
import com.shinro.wallpaper.bases.BaseActivity;
import com.shinro.wallpaper.ui.photo.about.AboutFragment;
import com.shinro.wallpaper.ui.photo.grid_view.GridViewFragment;
import com.shinro.wallpaper.ui.photo.list_view.ListViewFragment;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

public class PhotoActivity extends BaseActivity implements PhotoContract.View { //TODO: DON'T FORGET TO ADD THIS ACTIVITY TO THE MANIFEST FILE!!!

    @BindView(R.id.bottom_navigation)
    BottomNavigationView navigationView;

    private Unbinder unbinder;

    private PhotoContract.Presenter mPresenter = new PhotoPresenter(this);    // Presenter

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //Hide status bar
        hideStatusBar();
        setContentView(R.layout.activity_photo);  //TODO: create the layout and add it here

        initView();

        initFirstFragment();
    }

    private void initView() {
        unbinder = ButterKnife.bind(this);
        navigationView.setOnNavigationItemSelectedListener(navListener);
    }

    private void initFirstFragment() {
        Fragment fragment = new GridViewFragment();
        loadFragmentToContainer(R.id.photoContainer, fragment);
    }

    private BottomNavigationView.OnNavigationItemSelectedListener navListener = menuItem -> {
        Fragment fragment = null;
        switch (menuItem.getItemId()) {
            case R.id.item_grid:
                fragment = new GridViewFragment();
                break;
            case R.id.item_list:
                fragment = new ListViewFragment();
                break;
            case R.id.item_about:
                fragment = new AboutFragment();
                break;
            default:
                break;
        }
        loadFragmentToContainer(R.id.photoContainer, fragment);
        return true;
    };

    @Override
    protected void onDestroy() {
        super.onDestroy();
        unbinder.unbind();
    }

}

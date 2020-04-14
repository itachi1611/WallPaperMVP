package com.shinro.wallpaper.ui.photo;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

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

        //Check last state
        if(savedInstanceState != null) {

        }

        setContentView(R.layout.activity_photo);  //TODO: create the layout and add it here

        initView();

        navigationView.setOnNavigationItemSelectedListener(navListener);

        initFirstFragment();
    }

    private void initView() {
        unbinder = ButterKnife.bind(this);
    }

    private void initFirstFragment() {
        Fragment fragment = GridViewFragment.newInstance();
        loadFragmentToContainer(R.id.photoContainer, fragment, null, true, true);
    }

    private BottomNavigationView.OnNavigationItemSelectedListener navListener = menuItem -> {
        Fragment fragment;
        switch (menuItem.getItemId()) {
            case R.id.item_grid:
                fragment = GridViewFragment.newInstance();
                loadFragmentToContainer(R.id.photoContainer, fragment, null, true, true);
                return true;
            case R.id.item_list:
                fragment = ListViewFragment.newInstance();
                loadFragmentToContainer(R.id.photoContainer, fragment, null, true, true);
                return true;
            case R.id.item_about:
                fragment = AboutFragment.newInstance();
                loadFragmentToContainer(R.id.photoContainer, fragment, null, true, true);
                return true;
        }
        return false;
    };

    @Override
    protected void onDestroy() {
        super.onDestroy();
        unbinder.unbind();
    }

}

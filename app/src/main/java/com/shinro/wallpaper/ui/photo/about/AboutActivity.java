package com.shinro.wallpaper.ui.photo.about;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;

import androidx.coordinatorlayout.widget.CoordinatorLayout;
import androidx.core.widget.NestedScrollView;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.shinro.wallpaper.R;
import com.shinro.wallpaper.bases.BaseActivity;
import com.shinro.wallpaper.ui.photo.photo_card.PhotoCardActivity;
import com.shinro.wallpaper.ui.photo.photo_grid.PhotoGridActivity;
import com.shinro.wallpaper.ultis.CommonUtils;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;
import mehdi.sakout.aboutpage.AboutPage;
import mehdi.sakout.aboutpage.Element;

public class AboutActivity extends BaseActivity implements AboutContract.View {

    @BindView(R.id.bottom_navigation)
    BottomNavigationView navigationView;

    private Unbinder unbinder;

    private AboutContract.Presenter mPresenter = new AboutPresenter(this);   // Presenter

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //Hide status bar
        hideStatusBar();

        CommonUtils.simulateDayNight(/* DAY */ 0, this);

        Element adsElement = CommonUtils.createAboutElement("Advertise with us", null, null, null, null, null);

        View aboutPage = new AboutPage(this)
            .isRTL(false)
            .setDescription(getString(R.string.about_description))
            .setImage(R.mipmap.ic_launcher_round)
            .addItem(new Element().setTitle("Version 1.0 alpha 1"))
            .addItem(adsElement)
            .addGroup("Connect with us")
            .addEmail("")
            .addWebsite("")
            .addFacebook("")
            .addTwitter("")
            .addYoutube("")
            .addPlayStore("")
            .addInstagram("")
            .addGitHub("")
            .addItem(CommonUtils.getCopyRightsElement(this))
            .create();

        LayoutInflater inflater = LayoutInflater.from(this);
        CoordinatorLayout root = (CoordinatorLayout) inflater.inflate(R.layout.activity_about, null, false);

        NestedScrollView scrollView = root.findViewById(R.id.scrollContainer);

        scrollView.addView(aboutPage);

        setContentView(root);

        initViews();

        navigationView.setOnNavigationItemSelectedListener(navListener);
    }

    private void initViews() {
        unbinder = ButterKnife.bind(this);
    }

    @Override
    protected void onStart() {
        super.onStart();
        updateBottomNavigationBarState();
    }

    private void updateBottomNavigationBarState(){
        int actionId = R.id.item_about;
        selectBottomNavigationBarItem(actionId);
    }

    private void selectBottomNavigationBarItem(int itemId) {
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
                default:
                    break;
            }
            finish();
        }, 200);
        return false;
    };

    @Override
    protected void onDestroy() {
        super.onDestroy();
        unbinder.unbind();
    }

}

package com.shinro.wallpaper.ui.photo;
import android.os.Bundle;
import com.shinro.wallpaper.R;
import com.shinro.wallpaper.bases.BaseActivity;

public class PhotoActivity extends BaseActivity { //TODO: DON'T FORGET TO ADD THIS ACTIVITY TO THE MANIFEST FILE!!!
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_photo);  //TODO: create the layout and add it here
    }
}

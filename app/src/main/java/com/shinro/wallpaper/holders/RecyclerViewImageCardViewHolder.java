package com.shinro.wallpaper.holders;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.pm.PackageManager;
import android.os.Build;
import android.view.View;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Priority;
import com.getbase.floatingactionbutton.FloatingActionButton;
import com.getbase.floatingactionbutton.FloatingActionsMenu;
import com.shinro.wallpaper.R;
import com.shinro.wallpaper.models.Photo;
import com.shinro.wallpaper.ultis.ImageViewUtils;

import java.io.IOException;

import butterknife.BindView;
import butterknife.ButterKnife;

import static com.shinro.wallpaper.ultis.Constants.MY_PERMISSIONS_REQUEST_WRITE_EXTERNAL_STORAGE;

public class RecyclerViewImageCardViewHolder extends RecyclerView.ViewHolder {

    @BindView(R.id.ivItem)
    public ImageView imageView;

    @BindView(R.id.multiple_actions)
    FloatingActionsMenu multipleActions;

    public View itemView;

    private Photo mPhoto;

    private Context context;

    private String [] link = new String[10];

    public RecyclerViewImageCardViewHolder(@NonNull View itemView) {
        super(itemView);
        this.itemView = itemView;
        ButterKnife.bind(this, itemView);
    }

    public void onBindData(Photo photo) {
        if(photo != null) {
            mPhoto = photo;
        }
        String image_url;
        context = itemView.getContext();

        //Init default image to show
        if(photo.getUrlO() != null) {
            image_url = photo.getUrlO();
        } else {
            image_url = photo.getUrlL();
        }
        ImageViewUtils.loadImage(context, imageView, image_url, Priority.NORMAL);

        //Get link for download buttons
        //getPhotoUrl(photo);

        //Init download buttons base on download link
        //initDownloadButton(link);

    }

//    private void getPhotoUrl(Photo photo) {
//        link[0] = photo.getUrlO();
//        link[1] = photo.getUrlL();
//        link[2] = photo.getUrlC();
//        link[3] = photo.getUrlZ();
//        link[4] = photo.getUrlM();
//    }

//    private void initDownloadButton(final String [] url) {
//        for(int i = 0;i < url.length;i++){
//            if(url[i] != null){
//                FloatingActionButton floatingActionButton = new FloatingActionButton(context);
//                switch (i){
//                    case 0:
//                        floatingActionButton.setTitle(mPhoto.getWidthO() + " x " + mPhoto.getHeightO());
//                        break;
//                    case 1:
//                        floatingActionButton.setTitle(mPhoto.getWidthL() + " x " + mPhoto.getHeightL());
//                        break;
//                    case 2:
//                        floatingActionButton.setTitle(mPhoto.getWidthC() + " x " + mPhoto.getHeightC());
//                        break;
//                    case 3:
//                        floatingActionButton.setTitle(mPhoto.getWidthZ() + " x " + mPhoto.getHeightZ());
//                        break;
//                    case 4:
//                        floatingActionButton.setTitle(mPhoto.getWidthM() + " x " + mPhoto.getHeightM());
//                        break;
//                }
//                floatingActionButton.setIcon(R.drawable.ic_file_download);
//                floatingActionButton.setColorNormalResId(R.color.blue_semi_transparent);
//                floatingActionButton.setColorPressedResId(R.color.blue_semi_transparent_pressed);
//                floatingActionButton.setStrokeVisible(false);
//                final int j = i;
//                floatingActionButton.setOnClickListener(new View.OnClickListener() {
//                    @Override
//                    public void onClick(View v) {
//                        try {
//                            onCheckPermission(url[j].trim());
//                        } catch (IOException e) {
//                            e.printStackTrace();
//                        }
//                    }
//                });
//                multipleActions.addButton(floatingActionButton);
//            }
//        }
//    }

//    private void onCheckPermission(String mUrl) throws IOException {
//        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.M){
//            if (ContextCompat.checkSelfPermission(context, Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
//                // Permission is not granted
//                // Should we show an explanation?
//                if (ActivityCompat.shouldShowRequestPermissionRationale((Activity) context, Manifest.permission.WRITE_EXTERNAL_STORAGE)) {
//                    // Show an explanation to the user *asynchronously* -- don't block
//                    // this thread waiting for the user's response! After the user
//                    // sees the explanation, try again to request the permission.
//                } else {
//                    // No explanation needed; request the permission
//                    ActivityCompat.requestPermissions((Activity) context, new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE},
//                            MY_PERMISSIONS_REQUEST_WRITE_EXTERNAL_STORAGE);
//                }
//            } else {
//                // Permission has already been granted
//                //downloadPhotoFromApi(mUrl);
//            }
//        }else{
//            //downloadPhotoFromApi(mUrl);
//        }
//    }

}

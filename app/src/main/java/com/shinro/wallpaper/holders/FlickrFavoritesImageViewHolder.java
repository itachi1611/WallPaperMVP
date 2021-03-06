package com.shinro.wallpaper.holders;

import android.content.Context;
import android.content.Intent;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Priority;
import com.shinro.wallpaper.R;
import com.shinro.wallpaper.models.Photo;
import com.shinro.wallpaper.ui.photo_detail.PhotoDetailActivity;
import com.shinro.wallpaper.ultis.ImageViewUtils;

import butterknife.BindView;
import butterknife.ButterKnife;

public class FlickrFavoritesImageViewHolder extends RecyclerView.ViewHolder {

    @BindView(R.id.constrainContainer)
    public ConstraintLayout constrainContainer;

    @BindView(R.id.imageView_widget)
    public ImageView imageViewWidget;

    @BindView(R.id.tvView)
    public TextView tvView;

    private View itemView;

    public FlickrFavoritesImageViewHolder(@NonNull View itemView) {
        super(itemView);
        this.itemView = itemView;
        ButterKnife.bind(this, itemView);
    }

    public void onBindData(Photo photo) {
        String image_url;
        Context context = itemView.getContext();

        if(photo.getUrlO() != null) {
            image_url = photo.getUrlO();
        } else {
            image_url = photo.getUrlL();
        }

        ImageViewUtils.loadImage(context, imageViewWidget, image_url, Priority.NORMAL);

        tvView.setText(photo.getViews().trim());

        imageViewWidget.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, PhotoDetailActivity.class);
                context.startActivity(intent);
            }
        });
    }

}

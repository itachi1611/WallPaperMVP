package com.shinro.wallpaper.adapters;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.constraintlayout.widget.ConstraintSet;
import androidx.recyclerview.widget.DiffUtil;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Priority;
import com.shinro.wallpaper.R;
import com.shinro.wallpaper.holders.FlickrFavoritesImageViewHolder;
import com.shinro.wallpaper.models.Photo;
import com.shinro.wallpaper.ui.photo_detail.PhotoDetailActivity;
import com.shinro.wallpaper.ultis.DiffCallBack.FlickrFavoriteDiffCallBack;
import com.shinro.wallpaper.ultis.ImageViewUtils;

import java.util.List;

public class FlickrFavoritesImageStaggeredRecycleViewAdapter extends RecyclerView.Adapter<FlickrFavoritesImageViewHolder> {

    private List<Photo> mImageLists;
    private Context mContext;

    //Image ratio
    private ConstraintSet constraintSet = new ConstraintSet();

    public ConstraintSet getConstraintSet() {
        return constraintSet;
    }

    public FlickrFavoritesImageStaggeredRecycleViewAdapter(List<Photo> mImageLists, Context mContext) {
        this.mImageLists = mImageLists;
        this.mContext = mContext;
    }

    @NonNull
    @Override
    public FlickrFavoritesImageViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new FlickrFavoritesImageViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.item_flickr_photo, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull FlickrFavoritesImageViewHolder holder, int position) {
        Photo photo = mImageLists.get(position);
        String image_url;

        if(!photo.getUrlO().isEmpty() && photo.getUrlO() != null) {
            image_url = photo.getUrlO();
        } else {
            image_url = photo.getUrlL();
        }

        ImageViewUtils.loadImage(mContext, holder.imageViewWidget, image_url, Priority.HIGH);

        holder.tvView.setText(photo.getViews().trim());

        holder.imageViewWidget.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(mContext, PhotoDetailActivity.class);
                mContext.startActivity(intent);
            }
        });

        setDiffRatio(photo, holder.constrainContainer, holder.imageViewWidget);
    }

    @Override
    public void onBindViewHolder(@NonNull FlickrFavoritesImageViewHolder holder, int position, @NonNull List<Object> payloads) {
        String id;

        if(payloads.isEmpty()) {
            super.onBindViewHolder(holder, position, payloads);
            return;
        } else {
            Bundle bundle = (Bundle) payloads.get(0);
            for(String k : bundle.keySet()) {
                if(k.equals("id")) {
                    id = bundle.getString(k);
                }
            }
        }
    }

    @Override
    public int getItemCount() {
        return (mImageLists != null) ? mImageLists.size() : 0;
    }

    public List<Photo> getFlickrFavoritesImageList() {
        return mImageLists;
    }

    private void setDiffRatio(Photo photo, ConstraintLayout constraintLayout, ImageView imageView) {
        int width, height;
        if(!photo.getUrlO().isEmpty() && photo.getUrlO() != null) {
            width = Integer.parseInt(photo.getWidthO());
            height = Integer.parseInt(photo.getHeightO());
        } else {
            width = Integer.parseInt(photo.getWidthL());
            height = Integer.parseInt(photo.getHeightL());
        }
        String ratio = String.format("%d:%d", width, height);
        constraintSet.clone(constraintLayout);
        constraintSet.setDimensionRatio(imageView.getId(),ratio);
        constraintSet.applyTo(constraintLayout);
    }

    public void setFlickrFavoritesImageList(List<Photo> photos) {
        FlickrFavoriteDiffCallBack diffCallBack = new FlickrFavoriteDiffCallBack(photos, mImageLists);
        DiffUtil.DiffResult diffResult = DiffUtil.calculateDiff(diffCallBack);
        diffResult.dispatchUpdatesTo(this);
        mImageLists.clear();
        mImageLists.addAll(photos);
    }

}

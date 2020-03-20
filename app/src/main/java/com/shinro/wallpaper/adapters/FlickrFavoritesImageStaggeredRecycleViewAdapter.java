package com.shinro.wallpaper.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.constraintlayout.widget.ConstraintSet;
import androidx.recyclerview.widget.RecyclerView;

import com.shinro.wallpaper.R;
import com.shinro.wallpaper.holders.FlickrFavoritesImageViewHolder;
import com.shinro.wallpaper.models.Photo;

import java.util.List;

public class FlickrFavoritesImageStaggeredRecycleViewAdapter extends RecyclerView.Adapter<FlickrFavoritesImageViewHolder> {

    private List<Photo> mImageLists;

    //Image ratio
    private ConstraintSet constraintSet = new ConstraintSet();

    public ConstraintSet getConstraintSet() {
        return constraintSet;
    }

    public FlickrFavoritesImageStaggeredRecycleViewAdapter(List<Photo> mImageLists) {
        this.mImageLists = mImageLists;
    }

    @NonNull
    @Override
    public FlickrFavoritesImageViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new FlickrFavoritesImageViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.item_flickr_photo, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull FlickrFavoritesImageViewHolder holder, int position) {
        Photo photo = mImageLists.get(position);
        holder.onBindData(photo);
        setDiffRatio(photo, holder.constrainContainer, holder.imageViewWidget);
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
        if(photo.getUrlO() != null) {
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
        this.mImageLists.clear();
        this.mImageLists = photos;
        notifyDataSetChanged();
    }

}

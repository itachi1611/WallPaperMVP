package com.shinro.wallpaper.adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.shinro.wallpaper.R;
import com.shinro.wallpaper.helpers.FoxyAdapterHelper;
import com.shinro.wallpaper.holders.RecyclerViewImageCardViewHolder;
import com.shinro.wallpaper.models.Photo;

import java.util.List;

public class RecyclerViewImageCardAdapter extends RecyclerView.Adapter<RecyclerViewImageCardViewHolder> {

    private List<Photo> mPhotos;
    private FoxyAdapterHelper mFoxyAdapterHelper = new FoxyAdapterHelper();

    public RecyclerViewImageCardAdapter(List<Photo> list) {
        this.mPhotos = list;
    }

    public List<Photo> getPhotoList() {
        return mPhotos;
    }

    public void setPhotoList(List<Photo> list) {
        mPhotos = list;
    }

    @NonNull
    @Override
    public RecyclerViewImageCardViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_flickr_card_photo, parent, false);
        mFoxyAdapterHelper.onCreateViewHolder(parent, view);
        return new RecyclerViewImageCardViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerViewImageCardViewHolder holder, final int position) {
        mFoxyAdapterHelper.onBindViewHolder(holder.itemView, position, getItemCount());
        Photo photo = mPhotos.get(position);
        holder.onBindData(photo);
    }

    @Override
    public int getItemCount() {
        return (mPhotos != null) ? mPhotos.size() : 0;
    }
}

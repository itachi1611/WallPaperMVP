package com.shinro.wallpaper.holders;

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.RecyclerView;

import com.shinro.wallpaper.R;

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

}

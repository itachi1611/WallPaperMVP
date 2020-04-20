package com.shinro.wallpaper.helpers;

import android.content.Context;
import android.view.View;
import android.view.ViewTreeObserver;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.LinearSnapHelper;
import androidx.recyclerview.widget.RecyclerView;

import com.shinro.wallpaper.ui.customs.FoxyRecyclerView;
import com.shinro.wallpaper.ultis.ScreenUtil;

public class FoxyScaleHelper {

    private FoxyRecyclerView mRecyclerView;
    private Context mContext;

    private float mScale = 0.9f;
    private int mPagePadding = FoxyAdapterHelper.sPagePadding;
    private int mShowLeftCardWidth = FoxyAdapterHelper.sShowLeftCardWidth;

    private int mCardWidth;
    private int mOnePageWidth;
    private int mCardGalleryWidth;

    private int mFirstItemPos;
    private int mCurrentItemOffset;

    private CardLinearSnapHelper mLinearSnapHelper = new CardLinearSnapHelper();
    private int mLastPos;

    public void attachToRecyclerView(final FoxyRecyclerView mRecyclerView) {
        if (mRecyclerView == null) {
            return;
        }
        this.mRecyclerView = mRecyclerView;
        mContext = mRecyclerView.getContext();
        mRecyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);
                // Log.e("TAG", "RecyclerView.OnScrollListener onScrollStateChanged");
                if (newState == RecyclerView.SCROLL_STATE_IDLE) {
                    mLinearSnapHelper.mNoNeedToScroll = getCurrentItem() == 0 ||
                            getCurrentItem() == mRecyclerView.getAdapter().getItemCount() - 2;
                    if (mLinearSnapHelper.finalSnapDistance[0] == 0
                            && mLinearSnapHelper.finalSnapDistance[1] == 0) {
                        mCurrentItemOffset = 0;
                        mLastPos = getCurrentItem();
                        mRecyclerView.dispatchOnPageSelected(mLastPos);
                    }
                } else {
                    mLinearSnapHelper.mNoNeedToScroll = false;
                }
            }

            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                //Log.e("TAG", String.format("onScrolled dx=%s, dy=%s", dx, dy));
                super.onScrolled(recyclerView, dx, dy);
                mCurrentItemOffset += dx;
                onScrolledChangedCallback();
            }
        });
        initWidth();
        mLinearSnapHelper.attachToRecyclerView(mRecyclerView);
    }

    private void initWidth() {
        mRecyclerView.getViewTreeObserver().addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
            @Override
            public void onGlobalLayout() {
                mRecyclerView.getViewTreeObserver().removeGlobalOnLayoutListener(this);
                mCardGalleryWidth = mRecyclerView.getWidth();
                mCardWidth = mCardGalleryWidth - ScreenUtil.dip2px(mContext, 2 * (mPagePadding + mShowLeftCardWidth));
                mOnePageWidth = mCardWidth;
                scrollToPosition(mFirstItemPos);
            }
        });
    }

    public void setCurrentItem(int item) {
        setCurrentItem(item,false);
    }

    public void setCurrentItem(int item, boolean smoothScroll) {
        if (mRecyclerView == null) {
            return;
        }
        if (smoothScroll) {
            mRecyclerView.smoothScrollToPosition(item);
        }else {
            scrollToPosition(item);
        }
    }

    public void scrollToPosition(int pos) {
        if (mRecyclerView == null) {
            return;
        }
        ((LinearLayoutManager) mRecyclerView.getLayoutManager()).scrollToPositionWithOffset(pos, ScreenUtil.dip2px(mContext, mPagePadding + mShowLeftCardWidth));
        mCurrentItemOffset = 0;
        mLastPos = pos;
        mRecyclerView.dispatchOnPageSelected(mLastPos);
        //onScrolledChangedCallback();
        mRecyclerView.post(() -> onScrolledChangedCallback());
    }

    public void setFirstItemPos(int firstItemPos) {
        this.mFirstItemPos = firstItemPos;
    }


    private void onScrolledChangedCallback() {
        if (mOnePageWidth == 0) {
            return;
        }
        int currentItemPos = getCurrentItem();
        int offset = mCurrentItemOffset - (currentItemPos - mLastPos) * mOnePageWidth;
        float percent = (float) Math.max(Math.abs(offset) * 1.0 / mOnePageWidth, 0.0001);

        //Log.e("TAG",String.format("offset=%s, percent=%s", offset, percent));
        View leftView = null;
        View currentView;
        View rightView = null;
        if (currentItemPos > 0) {
            leftView = mRecyclerView.getLayoutManager().findViewByPosition(currentItemPos - 1);
        }
        currentView = mRecyclerView.getLayoutManager().findViewByPosition(currentItemPos);
        if (currentItemPos < mRecyclerView.getAdapter().getItemCount() - 1) {
            rightView = mRecyclerView.getLayoutManager().findViewByPosition(currentItemPos + 1);
        }

        if (leftView != null) {
            // y = (1 - mScale)x + mScale
            leftView.setScaleY((1 - mScale) * percent + mScale);
        }
        if (currentView != null) {
            // y = (mScale - 1)x + 1
            currentView.setScaleY((mScale - 1) * percent + 1);
        }
        if (rightView != null) {
            // y = (1 - mScale)x + mScale
            rightView.setScaleY((1 - mScale) * percent + mScale);
        }
    }

    public int getCurrentItem() {
        //return ((LinearLayoutManager) mRecyclerView.getLayoutManager()).findFirstVisibleItemPosition();
        return mRecyclerView.getLayoutManager().getPosition(mLinearSnapHelper.findSnapView(mRecyclerView.getLayoutManager()));
    }

    public void setScale(float scale) {
        mScale = scale;
    }

    public void setPagePadding(int pagePadding) {
        mPagePadding = pagePadding;
    }

    public void setShowLeftCardWidth(int showLeftCardWidth) {
        mShowLeftCardWidth = showLeftCardWidth;
    }

    private static class CardLinearSnapHelper extends LinearSnapHelper {
        public boolean mNoNeedToScroll = false;
        public int[] finalSnapDistance = {0, 0};

        @Override
        public int[] calculateDistanceToFinalSnap(@NonNull RecyclerView.LayoutManager layoutManager, @NonNull View targetView) {
            //Log.e("TAG", "calculateDistanceToFinalSnap");
            if (mNoNeedToScroll) {
                finalSnapDistance[0] = 0;
                finalSnapDistance[1] = 0;
            } else {
                finalSnapDistance = super.calculateDistanceToFinalSnap(layoutManager, targetView);
            }
            return finalSnapDistance;
        }
    }

}

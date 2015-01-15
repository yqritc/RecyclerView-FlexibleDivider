package com.yqritc.recyclerviewflexibledivider;

import android.content.Context;
import android.graphics.Rect;
import android.support.v7.widget.RecyclerView;
import android.view.View;

/**
 * Created by yqritc on 2015/01/15.
 */
public class HorizontalDividerItemDecoration extends FlexibleDividerDecoration {

    private MarginProvider mMarginProvider;

    private HorizontalDividerItemDecoration(Builder builder) {
        super(builder);
        mMarginProvider = builder.mMarginProvider;
    }

    @Override
    protected void getDividerOffset(Rect outRect) {
        outRect.top = getDividerSize();
    }

    @Override
    protected int getDividerSize() {
        return mDivider.getIntrinsicHeight();
    }

    @Override
    protected Rect getDividerBound(int position, RecyclerView parent, View child) {
        Rect bounds = new Rect(0, 0, 0, 0);
        RecyclerView.LayoutParams params = (RecyclerView.LayoutParams) child.getLayoutParams();
        bounds.left = parent.getPaddingLeft() +
                mMarginProvider.dividerLeftMargin(position, parent);
        bounds.right = parent.getWidth() - parent.getPaddingRight() -
                mMarginProvider.dividerRightMargin(position, parent);
        if (mIsPaintMode) {
            bounds.top = child.getTop() - params.topMargin;
            bounds.bottom = bounds.top;
        } else {
            int dividerSize = mSizeProvider.dividerSize(position, parent);
            bounds.top = child.getTop() - params.topMargin - dividerSize / 2;
            bounds.bottom = bounds.top + dividerSize;
        }
        return bounds;
    }

    public interface MarginProvider {

        public int dividerLeftMargin(int position, RecyclerView parent);

        public int dividerRightMargin(int position, RecyclerView parent);
    }


    public static class Builder extends FlexibleDividerDecoration.Builder<Builder> {

        private MarginProvider mMarginProvider = new MarginProvider() {
            @Override
            public int dividerLeftMargin(int position, RecyclerView parent) {
                return 0;
            }

            @Override
            public int dividerRightMargin(int position, RecyclerView parent) {
                return 0;
            }
        };

        public Builder(Context context) {
            super(context);
        }

        public Builder marginProvider(MarginProvider provider) {
            mMarginProvider = provider;
            return this;
        }

        public HorizontalDividerItemDecoration build() {
            return new HorizontalDividerItemDecoration(this);
        }
    }
}
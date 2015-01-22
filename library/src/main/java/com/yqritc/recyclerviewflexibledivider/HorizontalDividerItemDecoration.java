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

        if (mDividerType == DividerType.DRAWABLE) {
            int dividerSize = mSizeProvider.dividerSize(position, parent);
            bounds.top = child.getBottom() + params.topMargin - dividerSize / 2;
            bounds.bottom = bounds.top + dividerSize;
        } else {
            bounds.top = child.getBottom() + params.topMargin;
            bounds.bottom = bounds.top;
        }

        return bounds;
    }

    public interface MarginProvider {

        /**
         * Returns left margin of divider.
         *
         * @param position Divider position
         * @param parent   RecyclerView
         * @return left margin
         */
        public int dividerLeftMargin(int position, RecyclerView parent);

        /**
         * Returns right margin of divider.
         *
         * @param position Divider position
         * @param parent   RecyclerView
         * @return right margin
         */
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

        public Builder margin(final int leftMargin, final int rightMargin) {
            return marginProvider(new MarginProvider() {
                @Override
                public int dividerLeftMargin(int position, RecyclerView parent) {
                    return leftMargin;
                }

                @Override
                public int dividerRightMargin(int position, RecyclerView parent) {
                    return rightMargin;
                }
            });
        }

        public Builder marginProvider(MarginProvider provider) {
            mMarginProvider = provider;
            return this;
        }

        public HorizontalDividerItemDecoration build() {
            checkBuilderParams();
            return new HorizontalDividerItemDecoration(this);
        }
    }
}
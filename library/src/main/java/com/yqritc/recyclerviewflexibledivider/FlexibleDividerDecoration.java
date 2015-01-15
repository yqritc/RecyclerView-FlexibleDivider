package com.yqritc.recyclerviewflexibledivider;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.drawable.Drawable;
import android.support.v7.widget.RecyclerView;
import android.view.View;

/**
 * Created by yqritc on 2015/01/08.
 */
public abstract class FlexibleDividerDecoration extends RecyclerView.ItemDecoration {

    private static final int DEFAULT_SIZE = 2;
    private static final int[] ATTRS = new int[]{
            android.R.attr.listDivider
    };

    protected boolean mIsPaintMode;
    protected Drawable mDivider;
    protected Paint mPaint;
    protected VisibilityProvider mVisibilityProvider;
    protected ColorProvider mColorProvider;
    protected SizeProvider mSizeProvider;

    protected FlexibleDividerDecoration(Builder builder) {
        mColorProvider = builder.mColorProvider;
        mIsPaintMode = mColorProvider != null;
        if (mIsPaintMode) {
            mPaint = new Paint();
        } else {
            TypedArray a = builder.mContext.obtainStyledAttributes(ATTRS);
            mDivider = a.getDrawable(0);
            a.recycle();
        }

        mSizeProvider = builder.mSizeProvider;
        if (mSizeProvider == null) {
            mSizeProvider = new SizeProvider() {
                @Override
                public int dividerSize(int position, RecyclerView parent) {
                    if (mIsPaintMode) {
                        return DEFAULT_SIZE;
                    } else {
                        return getDividerSize();
                    }
                }
            };

        }

        mVisibilityProvider = builder.mVisibilityProvider;
    }

    @Override
    public void onDrawOver(Canvas c, RecyclerView parent, RecyclerView.State state) {
        int childCount = parent.getChildCount();
        for (int i = 1; i < childCount; i++) {
            View child = parent.getChildAt(i);
            int childPosition = parent.getChildPosition(child);
            if (mVisibilityProvider.shouldHideDivider(childPosition, parent)) {
                continue;
            }

            Rect bounds = getDividerBound(childPosition, parent, child);
            if (mIsPaintMode) {
                mPaint.setColor(mColorProvider.dividerColor(childPosition, parent));
                mPaint.setStrokeWidth(mSizeProvider.dividerSize(childPosition, parent));
                c.drawLine(bounds.left, bounds.top, bounds.right, bounds.bottom, mPaint);
            } else {
                mDivider.setBounds(bounds);
                mDivider.draw(c);
            }
        }
    }

    @Override
    public void getItemOffsets(Rect outRect, View view, RecyclerView parent,
            RecyclerView.State state) {
        super.getItemOffsets(outRect, view, parent, state);
        if (mIsPaintMode) {
            return;
        }
        getDividerOffset(outRect);
    }

    abstract void getDividerOffset(Rect outRect);

    abstract int getDividerSize();

    abstract Rect getDividerBound(int position, RecyclerView parent, View child);

    public interface VisibilityProvider {

        /**
         * Returns true if divider drawn over the top line of the cell at position should be
         * hidden.
         * The range of position value is "1" to "item size - 1".
         *
         * @param position Target position of the cell.
         * @param parent   RecyclerView
         * @return true if the divider at position should be hidden
         */
        public boolean shouldHideDivider(int position, RecyclerView parent);
    }

    public interface ColorProvider {

        public int dividerColor(int position, RecyclerView parent);
    }

    public interface SizeProvider {

        public int dividerSize(int position, RecyclerView parent);
    }

    public static class Builder<T extends Builder> {

        private Context mContext;
        private ColorProvider mColorProvider;
        private SizeProvider mSizeProvider;
        private VisibilityProvider mVisibilityProvider = new VisibilityProvider() {
            @Override
            public boolean shouldHideDivider(int position, RecyclerView parent) {
                return false;
            }
        };

        public Builder(Context context) {
            mContext = context;
        }

        public T colorProvider(ColorProvider provider) {
            mColorProvider = provider;
            return (T) this;
        }

        public T sizeProvider(SizeProvider provider) {
            mSizeProvider = provider;
            return (T) this;
        }

        public T visibilityProvider(VisibilityProvider provider) {
            mVisibilityProvider = provider;
            return (T) this;
        }
    }
}
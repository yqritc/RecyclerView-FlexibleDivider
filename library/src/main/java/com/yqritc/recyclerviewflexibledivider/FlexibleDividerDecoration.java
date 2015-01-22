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

    protected enum DividerType {
        DRAWABLE, PAINT, COLOR
    }

    protected DividerType mDividerType = DividerType.DRAWABLE;
    protected Drawable mDivider;
    protected VisibilityProvider mVisibilityProvider;
    protected PaintProvider mPaintProvider;
    protected ColorProvider mColorProvider;
    protected SizeProvider mSizeProvider;
    private Paint mPaint;

    protected FlexibleDividerDecoration(Builder builder) {
        if (builder.mPaintProvider != null) {
            mDividerType = DividerType.PAINT;
            mPaintProvider = builder.mPaintProvider;
        } else if (builder.mColorProvider != null) {
            mDividerType = DividerType.COLOR;
            mColorProvider = builder.mColorProvider;
            mPaint = new Paint();
            setSizeProvider(builder);
        } else {
            mDividerType = DividerType.DRAWABLE;
            TypedArray a = builder.mContext.obtainStyledAttributes(ATTRS);
            mDivider = a.getDrawable(0);
            a.recycle();
            setSizeProvider(builder);
        }

        mVisibilityProvider = builder.mVisibilityProvider;
    }

    private void setSizeProvider(Builder builder) {
        mSizeProvider = builder.mSizeProvider;
        if (mSizeProvider == null) {
            mSizeProvider = new SizeProvider() {
                @Override
                public int dividerSize(int position, RecyclerView parent) {
                    if (mDividerType == DividerType.COLOR) {
                        return DEFAULT_SIZE;
                    } else {
                        return getDividerSize();
                    }
                }
            };

        }
    }

    @Override
    public void onDrawOver(Canvas c, RecyclerView parent, RecyclerView.State state) {
        int childCount = parent.getChildCount();
        for (int i = 0; i < childCount - 1; i++) {
            View child = parent.getChildAt(i);
            int childPosition = parent.getChildPosition(child);
            if (mVisibilityProvider.shouldHideDivider(childPosition, parent)) {
                continue;
            }

            Rect bounds = getDividerBound(childPosition, parent, child);
            switch (mDividerType) {
                case DRAWABLE:
                    mDivider.setBounds(bounds);
                    mDivider.draw(c);
                    break;
                case PAINT:
                    mPaint = mPaintProvider.dividerPaint(childPosition, parent);
                    c.drawLine(bounds.left, bounds.top, bounds.right, bounds.bottom, mPaint);
                    break;
                case COLOR:
                    mPaint.setColor(mColorProvider.dividerColor(childPosition, parent));
                    mPaint.setStrokeWidth(mSizeProvider.dividerSize(childPosition, parent));
                    c.drawLine(bounds.left, bounds.top, bounds.right, bounds.bottom, mPaint);
                    break;
            }
        }
    }

    @Override
    public void getItemOffsets(Rect outRect, View view, RecyclerView parent,
            RecyclerView.State state) {
        super.getItemOffsets(outRect, view, parent, state);
        if (mDividerType != DividerType.DRAWABLE) {
            return;
        }
        getDividerOffset(outRect);
    }

    abstract void getDividerOffset(Rect outRect);

    abstract int getDividerSize();

    abstract Rect getDividerBound(int position, RecyclerView parent, View child);

    public interface VisibilityProvider {

        /**
         * Returns true if divider should be hidden.
         *
         * @param position Divider position
         * @param parent   RecyclerView
         * @return True if the divider at position should be hidden
         */
        public boolean shouldHideDivider(int position, RecyclerView parent);
    }

    public interface PaintProvider {

        public Paint dividerPaint(int position, RecyclerView parent);
    }

    public interface ColorProvider {

        /**
         * Returns {@link android.graphics.Color} value of divider
         *
         * @param position Divider position
         * @param parent   RecyclerView
         * @return Color value
         */
        public int dividerColor(int position, RecyclerView parent);
    }

    public interface SizeProvider {

        /**
         * Returns size value of divider.
         * Height for horizontal divider, width for vertical divider
         *
         * @param position Divider position
         * @param parent   RecyclerView
         * @return Size of divider
         */
        public int dividerSize(int position, RecyclerView parent);
    }

    public static class Builder<T extends Builder> {

        private Context mContext;
        private PaintProvider mPaintProvider;
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

        public T paint(final Paint paint) {
            return paintProvider(new PaintProvider() {
                @Override
                public Paint dividerPaint(int position, RecyclerView parent) {
                    return paint;
                }
            });
        }

        public T paintProvider(PaintProvider provider) {
            mPaintProvider = provider;
            return (T) this;
        }

        public T color(final int color) {
            return colorProvider(new ColorProvider() {
                @Override
                public int dividerColor(int position, RecyclerView parent) {
                    return color;
                }
            });
        }

        public T colorProvider(ColorProvider provider) {
            mColorProvider = provider;
            return (T) this;
        }

        public T size(final int size) {
            return sizeProvider(new SizeProvider() {
                @Override
                public int dividerSize(int position, RecyclerView parent) {
                    return size;
                }
            });
        }

        public T sizeProvider(SizeProvider provider) {
            mSizeProvider = provider;
            return (T) this;
        }

        public T visibilityProvider(VisibilityProvider provider) {
            mVisibilityProvider = provider;
            return (T) this;
        }

        protected void checkBuilderParams() {
            if (mPaintProvider != null) {
                if (mColorProvider != null) {
                    throw new IllegalArgumentException(
                            "Use setColor method of Paint class to specify line color. Do not provider ColorProvider if you set PaintProvider.");
                }
                if (mSizeProvider != null) {
                    throw new IllegalArgumentException(
                            "Use setStrokeWidth method of Paint class to specify line size. Do not provider SizeProvider if you set PaintProvider.");
                }
            }
        }
    }
}
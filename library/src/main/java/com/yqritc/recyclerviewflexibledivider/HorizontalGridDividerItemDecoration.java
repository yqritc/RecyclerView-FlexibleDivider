package com.yqritc.recyclerviewflexibledivider;

import android.content.Context;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;

/**
 * Created by yqritc on 2015/01/15.
 */
public class HorizontalGridDividerItemDecoration extends HorizontalDividerItemDecoration {

    protected HorizontalGridDividerItemDecoration(Builder builder) {
        super(builder);
    }

    @Override
    protected int getChildCount(RecyclerView parent) {
        int lastDividerOffset = getLastDividerOffset(parent);
        return mShowLastDivider ? parent.getChildCount() : parent.getChildCount() - lastDividerOffset;
    }

    /**
     * Returns offset for how many views we don't have to draw a divider for,
     * for LinearLayoutManager it is as simple as not drawing the last child divider,
     * but for a GridLayoutManager it needs to take the span count for the last items into account
     * until we use the span count configured for the grid.
     *
     * @param parent RecyclerView
     * @return offset for how many views we don't have to draw a divider or 1 if its a LinearLayoutManager
     */
    private int getLastDividerOffset(RecyclerView parent) {
        if (parent.getLayoutManager() instanceof GridLayoutManager) {
            GridLayoutManager gridLayoutManager = (GridLayoutManager) parent.getLayoutManager();
            int spanCount = gridLayoutManager.getSpanCount();
            int itemCount = parent.getAdapter().getItemCount();
            int lastRowItems = itemCount % spanCount;
            int lastItemIndex = itemCount - 1;
            GridLayoutManager.SpanSizeLookup spanSizeLookup = gridLayoutManager.getSpanSizeLookup();
            int lastRowSpanSize = 0;
            for (int i = lastItemIndex; i > 0; i--) {
                lastRowSpanSize += spanSizeLookup.getSpanSize(i);
                if (lastRowSpanSize >= spanCount || i == lastItemIndex - lastRowItems) {
                    return itemCount - i;
                }
            }
        }
        return 1;
    }

    @Override
    protected boolean wasDividerAlreadyDrawnForPosition(RecyclerView parent, int position, int lastChildPosition) {
        if (parent.getLayoutManager() instanceof GridLayoutManager) {
            GridLayoutManager gridLayoutManager = (GridLayoutManager) parent.getLayoutManager();
            int spanCount = gridLayoutManager.getSpanCount();
            GridLayoutManager.SpanSizeLookup spanSizeLockup = gridLayoutManager.getSpanSizeLookup();
            if (spanSizeLockup.getSpanGroupIndex(position, spanCount) == spanSizeLockup.getSpanGroupIndex(lastChildPosition, spanCount) && lastChildPosition != -1) {
                return true;
            }
        }
        return false;
    }

    public static class Builder extends HorizontalDividerItemDecoration.Builder {

        public Builder(Context context) {
            super(context);
        }

        public HorizontalGridDividerItemDecoration build() {
            checkBuilderParams();
            return new HorizontalGridDividerItemDecoration(this);
        }
    }
}
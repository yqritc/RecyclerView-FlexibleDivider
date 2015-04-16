package com.yqritc.recyclerviewflexibledivider.sample;

import com.yqritc.recyclerviewflexibledivider.FlexibleDividerDecoration;
import com.yqritc.recyclerviewflexibledivider.HorizontalDividerItemDecoration;

import android.content.Context;
import android.graphics.Color;
import android.graphics.DashPathEffect;
import android.graphics.Paint;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

/**
 * Created by yqritc on 2015/01/08.
 */
public class ComplexAdapter extends RecyclerView.Adapter<ComplexAdapter.ViewHolder> implements
        FlexibleDividerDecoration.PaintProvider,
//        FlexibleDividerDecoration.SizeProvider,
//        FlexibleDividerDecoration.ColorProvider,
        FlexibleDividerDecoration.VisibilityProvider,
        HorizontalDividerItemDecoration.MarginProvider {

    private static final int ITEM_SIZE = 20;

    private LayoutInflater mLayoutInflater;

    public ComplexAdapter(Context context) {
        super();
        mLayoutInflater = LayoutInflater.from(context);
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = mLayoutInflater.inflate(R.layout.layout_sample_item, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        holder.mSampleText.setText(String.valueOf(position));
    }

    @Override
    public int getItemCount() {
        return ITEM_SIZE;
    }

    @Override
    public Paint dividerPaint(int position, RecyclerView parent) {
        Paint paint = new Paint();
        switch (position % 10) {
            case 0:
                paint.setColor(Color.RED);
                paint.setStrokeWidth(30);
                break;
            case 1:
                paint.setColor(Color.MAGENTA);
                paint.setStrokeWidth(10);
                break;
            default:
                if (position % 2 == 0) {
                    paint.setColor(Color.BLUE);
                    paint.setAntiAlias(true);
                    paint.setPathEffect(new DashPathEffect(new float[]{25.0f, 25.0f}, 0));
                } else {
                    paint.setColor(Color.GREEN);

                }
                paint.setStrokeWidth(2 + position);
                break;
        }

        return paint;
    }

//    @Override
//    public int dividerColor(int position, RecyclerView parent) {
//        switch (position % 10) {
//            case 0:
//                return Color.RED;
//            case 1:
//                return Color.MAGENTA;
//            default:
//                if (position % 2 == 0) {
//                    return Color.BLUE;
//                } else {
//                    return Color.GREEN;
//                }
//        }
//    }
//
//    @Override
//    public int dividerSize(int position, RecyclerView parent) {
//        switch (position % 10) {
//            case 0:
//                return 30;
//            case 1:
//                return 10;
//            default:
//                return 2+position;
//        }
//    }

    @Override
    public boolean shouldHideDivider(int position, RecyclerView parent) {
        if (position == 14 || position == 15) {
            return true;
        }
        return false;
    }

    @Override
    public int dividerLeftMargin(int position, RecyclerView parent) {
        if (position < 10) {
            return position * 20;
        } else {
            return (20 - position) * 20;
        }
    }

    @Override
    public int dividerRightMargin(int position, RecyclerView parent) {
        if (position < 10) {
            return position * 20 + 20;
        } else {
            return (20 - position) * 20 + 20;
        }
    }

    static class ViewHolder extends RecyclerView.ViewHolder {

        TextView mSampleText;

        public ViewHolder(View view) {
            super(view);
            mSampleText = (TextView) view.findViewById(R.id.text_sample);
        }
    }
}

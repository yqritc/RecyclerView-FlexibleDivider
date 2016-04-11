package com.yqritc.recyclerviewflexibledivider.sample;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.DashPathEffect;
import android.graphics.Paint;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.FrameLayout;

import com.yqritc.recyclerviewflexibledivider.FlexibleDividerDecoration;
import com.yqritc.recyclerviewflexibledivider.HorizontalDividerItemDecoration;
import com.yqritc.recyclerviewflexibledivider.VerticalDividerItemDecoration;

public class AllLinearActivity extends AppCompatActivity {

    private SimpleAdapter adapter;

    private FrameLayout frameLayout;

    private FlexibleDividerDecoration current;

    private int manager = 0;
    private int way = 0;
    private int reverse = 0;
    private int decorator = 0;

    private boolean getReverse() {
        if (reverse == 0) {
            return false;
        } else {
            return true;
        }
    }

    private int getWay() {
        if (way == 0) {
            return RecyclerView.VERTICAL;
        } else {
            return RecyclerView.HORIZONTAL;
        }
    }

    private FlexibleDividerDecoration getDecorator() {
        switch (decorator) {
            case 0:
                if(way == 1) {
                    return new VerticalDividerItemDecoration.Builder(this).reverse(getReverse()).build();
                } else {
                    return new HorizontalDividerItemDecoration.Builder(this).reverse(getReverse()).build();
                }
            case 1:
                Paint paint = new Paint();
                paint.setStrokeWidth(5);
                paint.setColor(Color.BLUE);
                paint.setAntiAlias(true);
                paint.setPathEffect(new DashPathEffect(new float[]{25.0f, 25.0f}, 0));
                if(way == 1) {
                    new VerticalDividerItemDecoration.Builder(this)
                            .paint(paint)
                            .showLastDivider()
                            .reverse(getReverse())
                            .build();
                } else {
                    new HorizontalDividerItemDecoration.Builder(this)
                            .paint(paint)
                            .showLastDivider()
                            .reverse(getReverse())
                            .build();
                }
            case 2:
                if(way == 1) {
                    return new VerticalDividerItemDecoration.Builder(this)
                            .drawable(R.drawable.sample)
                            .size(15)
                            .reverse(getReverse())
                            .build();
                } else {
                    return new HorizontalDividerItemDecoration.Builder(this)
                            .drawable(R.drawable.sample)
                            .size(15)
                            .reverse(getReverse())
                            .build();
                }
            default:
                if(way == 1) {
                    return new VerticalDividerItemDecoration.Builder(this).reverse(getReverse()).build();
                } else {
                    return new HorizontalDividerItemDecoration.Builder(this).reverse(getReverse()).build();
                }
        }
    }

    public static void startActivity(Activity activity) {
        Intent intent = new Intent(activity, AllLinearActivity.class);
        activity.startActivity(intent);
    }

    private void reload() {
        frameLayout.removeAllViews();

        RecyclerView recyclerView = new RecyclerView(getBaseContext());

        FrameLayout.LayoutParams lp = new FrameLayout.LayoutParams(FrameLayout.LayoutParams.MATCH_PARENT, FrameLayout.LayoutParams.MATCH_PARENT);

        frameLayout.addView(recyclerView, lp);


        current = getDecorator();

        if (manager == 0) {
            LinearLayoutManager manager = new LinearLayoutManager(this, getWay(), getReverse());
            recyclerView.setLayoutManager(manager);
            recyclerView.setAdapter(adapter);
            recyclerView.addItemDecoration(current);
        } else {
            GridLayoutManager gridLayoutManager = new GridLayoutManager(this, 2, getWay(), getReverse());
            recyclerView.setLayoutManager(gridLayoutManager);
            recyclerView.setAdapter(adapter);
            recyclerView.addItemDecoration(getDecorator());
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_all_linear);

        frameLayout = (FrameLayout) findViewById(R.id.main_recyclerview);

        adapter = new SimpleAdapter(this);

        reload();

        Button linear = (Button) findViewById(R.id.linear);
        linear.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                manager = 0;
                reload();
            }
        });

        Button grid = (Button) findViewById(R.id.grid);
        grid.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                manager = 1;
                reload();
            }
        });

        Button vertical = (Button) findViewById(R.id.vertical);
        vertical.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                way = 0;
                reload();
            }
        });

        Button horizontal = (Button) findViewById(R.id.horizontal);
        horizontal.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                way = 1;
                reload();
            }
        });

        Button normal = (Button) findViewById(R.id.normal);
        normal.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                reverse = 0;
                reload();
            }
        });

        Button reverseBtn = (Button) findViewById(R.id.reverse);
        reverseBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                reverse = 1;
                reload();
            }
        });

        Button simple = (Button) findViewById(R.id.simple);
        simple.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                decorator = 0;
                reload();
            }
        });

        Button paint = (Button) findViewById(R.id.paint);
        paint.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                decorator = 1;
                reload();
            }
        });

        Button drawable = (Button) findViewById(R.id.drawable);
        drawable.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                decorator = 2;
                reload();
            }
        });
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_sample, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_simple:
                SimpleActivity.startActivity(this);
                return true;
            case R.id.action_paint:
                PaintActivity.startActivity(this);
                return true;
            case R.id.action_drawable:
                DrawableActivity.startActivity(this);
                return true;
            case R.id.action_complex:
                ComplexActivity.startActivity(this);
                return true;
            case R.id.action_simple_grid:
                SimpleGridActivity.startActivity(this);
                return true;
            case R.id.action_all_linear:
                AllLinearActivity.startActivity(this);
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }
}
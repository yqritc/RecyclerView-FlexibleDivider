package com.yqritc.recyclerviewflexibledivider.sample;

import com.yqritc.recyclerviewflexibledivider.HorizontalDividerItemDecoration;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.OrientationHelper;
import android.support.v7.widget.RecyclerView;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;


public class ComplexActivity extends AppCompatActivity {

    public static void startActivity(Activity activity) {
        Intent intent = new Intent(activity, ComplexActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        activity.startActivity(intent);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sample);

        RecyclerView recyclerView = (RecyclerView) findViewById(R.id.main_recyclerview);
        // Workaround for dash path effect
        // https://code.google.com/p/android/issues/detail?id=29944
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.HONEYCOMB) {
            recyclerView.setLayerType(View.LAYER_TYPE_SOFTWARE, null);
        }

        ComplexAdapter adapter = new ComplexAdapter(this);
        LinearLayoutManager manager = new LinearLayoutManager(this);
        manager.setOrientation(OrientationHelper.VERTICAL);
        recyclerView.setLayoutManager(manager);
        recyclerView.setAdapter(adapter);
        recyclerView.addItemDecoration(new HorizontalDividerItemDecoration.Builder(this)
                .paintProvider(adapter)
                .visibilityProvider(adapter)
                .marginProvider(adapter)
                .build());
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
            default:
                return super.onOptionsItemSelected(item);
        }
    }
}

package com.yqritc.recyclerviewflexibledivider.sample;

import com.yqritc.recyclerviewflexibledivider.FlexibleDividerDecoration;
import com.yqritc.recyclerviewflexibledivider.HorizontalDividerItemDecoration;

import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.OrientationHelper;
import android.support.v7.widget.RecyclerView;
import android.view.Menu;
import android.view.MenuItem;


public class SampleActivity extends ActionBarActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sample);

        SampleAdapter adapter = new SampleAdapter(this);
        LinearLayoutManager manager = new LinearLayoutManager(this);
        manager.setOrientation(OrientationHelper.VERTICAL);
        RecyclerView recyclerView = (RecyclerView) findViewById(R.id.main_recyclerview);
        recyclerView.setLayoutManager(manager);
        recyclerView.setAdapter(adapter);
        recyclerView.addItemDecoration(
                new HorizontalDividerItemDecoration.Builder(this)
                        .sizeProvider(new FlexibleDividerDecoration.SizeProvider() {
                            @Override
                            public int dividerSize(int position, RecyclerView parent) {
                                return position%5 * 10;
                            }
                        })
//                        .colorProvider(new FlexibleDividerDecoration.ColorProvider() {
//                            @Override
//                            public int dividerColor(int position, RecyclerView parent) {
//                                return position%2==0 ? Color.BLUE : Color.RED;
//                            }
//                        })
//                        .visibilityProvider(new FlexibleDividerDecoration.VisibilityProvider() {
//                            @Override
//                            public boolean shouldHideDivider(int position, RecyclerView parent) {
//                                return false;
//                            }
//                        })
//                        .marginProvider(new HorizontalDividerItemDecoration.MarginProvider() {
//                            @Override
//                            public int dividerLeftMargin(int position, RecyclerView parent) {
//                                return 0;
//                            }
//
//                            @Override
//                            public int dividerRightMargin(int position, RecyclerView parent) {
//                                return 0;
//                            }
//                        })
                        .build());
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_sample, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}

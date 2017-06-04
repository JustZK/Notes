package com.example.zk.notes.slidingpanelayout;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.example.zk.notes.R;
import com.example.zk.notes.util.LogUtil;

import java.util.ArrayList;

public class SlidingPaneLayoutActivity extends Activity {
    private static final String TAG = "SlidingPaneLayoutActivity";
    private RecyclerView sliding_pane_layout_rlv;
    private ArrayList<String> list;
    private SlidingPaneLayoutActivityListAdapter listAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sliding_pane_layout);
        init();
    }

    private void init(){
        sliding_pane_layout_rlv = (RecyclerView) findViewById(R.id.sliding_pane_layout_rlv);
        list = new ArrayList<>();
        list.add(this.getResources().getString(R.string.sliding_pane_layout_one));
        list.add(this.getResources().getString(R.string.sliding_pane_layout_two));
        list.add(this.getResources().getString(R.string.sliding_pane_layout_three));
        listAdapter = new SlidingPaneLayoutActivityListAdapter(this, list);
        sliding_pane_layout_rlv.setAdapter(listAdapter);
        sliding_pane_layout_rlv.setLayoutManager(new LinearLayoutManager(this));
        sliding_pane_layout_rlv.setItemAnimator(new DefaultItemAnimator());
        listAdapter.setOnItemClickListener(onItemClickListener);

    }

    private SlidingPaneLayoutActivityListAdapter.OnItemClickListener onItemClickListener = new SlidingPaneLayoutActivityListAdapter.OnItemClickListener() {
        @Override
        public void onItemClick(View view, int position) {
            LogUtil.d(TAG,"onItemClick");
            if (list.get(position).equals(SlidingPaneLayoutActivity.this.getResources().getString(R.string.sliding_pane_layout_one))){
                Intent intent = new Intent(SlidingPaneLayoutActivity.this, SlidingPaneLayoutActivityOne.class);
                startActivity(intent);
            }else if (list.get(position).equals(SlidingPaneLayoutActivity.this.getResources().getString(R.string.sliding_pane_layout_two))){
                Intent intent = new Intent(SlidingPaneLayoutActivity.this, SlidingPaneLayoutActivityTwo.class);
                startActivity(intent);
            }else if (list.get(position).equals(SlidingPaneLayoutActivity.this.getResources().getString(R.string.sliding_pane_layout_three))){
                Intent intent = new Intent(SlidingPaneLayoutActivity.this, SlidingPaneLayoutActivityThree.class);
                startActivity(intent);
            }

        }

        @Override
        public void onItemLongClick(View view, int position) {
            LogUtil.d(TAG,"onItemLongClick");
        }
    };
}

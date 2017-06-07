package com.example.zk.notes.drawable;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.example.zk.notes.R;
import com.example.zk.notes.slidingpanelayout.BaseSlideCloseActivity;
import com.example.zk.notes.util.LogUtil;

import java.util.ArrayList;

public class DrawableActivity extends BaseSlideCloseActivity {

    private static final String TAG = "DrawableActivity";
    private RecyclerView drawable_rlv;
    private ArrayList<String> list;
    private DrawableActivityListAdapter listAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_drawable);

        init();
    }

    private void init(){
        drawable_rlv = (RecyclerView) findViewById(R.id.drawable_rlv);
        list = new ArrayList<>();
        list.add(this.getResources().getString(R.string.drawable_one));
        list.add(this.getResources().getString(R.string.drawable_two));
        list.add(this.getResources().getString(R.string.drawable_three));
        listAdapter = new DrawableActivityListAdapter(this, list);
        drawable_rlv.setAdapter(listAdapter);
        drawable_rlv.setLayoutManager(new LinearLayoutManager(this));
        drawable_rlv.setItemAnimator(new DefaultItemAnimator());
        listAdapter.setOnItemClickListener(onItemClickListener);

    }

    private DrawableActivityListAdapter.OnItemClickListener onItemClickListener = new DrawableActivityListAdapter.OnItemClickListener() {
        @Override
        public void onItemClick(View view, int position) {
            LogUtil.d(TAG,"onItemClick");
            if (list.get(position).equals(DrawableActivity.this.getResources().getString(R.string.drawable_one))){
                Intent intent = new Intent(DrawableActivity.this, DrawableActivityOne.class);
                startActivity(intent);
            }else if (list.get(position).equals(DrawableActivity.this.getResources().getString(R.string.drawable_two))){
                Intent intent = new Intent(DrawableActivity.this, DrawableActivityTwo.class);
                startActivity(intent);
            }else if (list.get(position).equals(DrawableActivity.this.getResources().getString(R.string.drawable_three))){
                Intent intent = new Intent(DrawableActivity.this, DrawableActivityThree.class);
                startActivity(intent);
            }

        }

        @Override
        public void onItemLongClick(View view, int position) {
            LogUtil.d(TAG,"onItemLongClick");
        }
    };
}

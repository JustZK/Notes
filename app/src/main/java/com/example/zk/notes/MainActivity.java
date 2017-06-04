package com.example.zk.notes;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.example.zk.notes.login.LoginActivity;
import com.example.zk.notes.player.MediaPlayerActivity;
import com.example.zk.notes.slidingpanelayout.SlidingPaneLayoutActivity;
import com.example.zk.notes.util.LogUtil;

import java.util.ArrayList;

public class  MainActivity extends Activity {
    private static final String TAG = "MainActivity";
    private RecyclerView main_rlv;
    private ArrayList<String> list;
    private MainActivityListAdapter listAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        init();

    }

    private void init(){
        main_rlv = (RecyclerView) findViewById(R.id.main_rlv);
        list = new ArrayList<>();
        list.add(this.getResources().getString(R.string.login_like_qq));
        list.add(this.getResources().getString(R.string.customize_drawable));
        list.add(this.getResources().getString(R.string.media_player));
        list.add(getResources().getString(R.string.sliding_pane_layout));
        listAdapter = new MainActivityListAdapter(this, list);
        main_rlv.setAdapter(listAdapter);
        main_rlv.setLayoutManager(new LinearLayoutManager(this));
        main_rlv.setItemAnimator(new DefaultItemAnimator());
        listAdapter.setOnItemClickListener(onItemClickListener);

    }

    private MainActivityListAdapter.OnItemClickListener onItemClickListener = new MainActivityListAdapter.OnItemClickListener() {
        @Override
        public void onItemClick(View view, int position) {
            LogUtil.d(TAG,"onItemClick");
            if (list.get(position).equals(MainActivity.this.getResources().getString(R.string.login_like_qq))){
                Intent intent = new Intent(MainActivity.this, LoginActivity.class);
                startActivity(intent);
            }else if (list.get(position).equals(MainActivity.this.getResources().getString(R.string.customize_drawable))){

            }else if (list.get(position).equals(MainActivity.this.getResources().getString(R.string.media_player))){
                Intent intent = new Intent(MainActivity.this, MediaPlayerActivity.class);
                startActivity(intent);
            }else if (list.get(position).equals(MainActivity.this.getResources().getString(R.string.sliding_pane_layout))){
                Intent intent = new Intent(MainActivity.this, SlidingPaneLayoutActivity.class);
                startActivity(intent);
            }

        }

        @Override
        public void onItemLongClick(View view, int position) {
            LogUtil.d(TAG,"onItemLongClick");
        }
    };
}

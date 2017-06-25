package com.example.zk.notes.animation;

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

public class AnimationActivity extends BaseSlideCloseActivity {

    private static final String TAG = "AnimationActivity";
    private RecyclerView animation_rlv;
    private ArrayList<String> list;
    private AnimationActivityAdapter listAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_animation);

        init();
    }

    private void init(){
        animation_rlv = (RecyclerView) findViewById(R.id.animation_rlv);
        list = new ArrayList<>();
        list.add(this.getResources().getString(R.string.animation_one));
        list.add(this.getResources().getString(R.string.animation_two));
        list.add(this.getResources().getString(R.string.animation_three));
        list.add(this.getResources().getString(R.string.animation_four));
        list.add(this.getResources().getString(R.string.animation_five));
        listAdapter = new AnimationActivityAdapter(this, list);
        animation_rlv.setAdapter(listAdapter);
        animation_rlv.setLayoutManager(new LinearLayoutManager(this));
        animation_rlv.setItemAnimator(new DefaultItemAnimator());
        listAdapter.setOnItemClickListener(onItemClickListener);

    }

    private AnimationActivityAdapter.OnItemClickListener onItemClickListener = new AnimationActivityAdapter.OnItemClickListener() {
        @Override
        public void onItemClick(View view, int position) {
            LogUtil.d(TAG,"onItemClick");
            if (list.get(position).equals(AnimationActivity.this.getResources().getString(R.string.animation_one))){
                Intent intent = new Intent(AnimationActivity.this, AnimationActivityOne.class);
                startActivity(intent);
            }else if (list.get(position).equals(AnimationActivity.this.getResources().getString(R.string.animation_two))){
                Intent intent = new Intent(AnimationActivity.this, AnimationActivityTwo.class);
                startActivity(intent);
            }else if (list.get(position).equals(AnimationActivity.this.getResources().getString(R.string.animation_three))){
                Intent intent = new Intent(AnimationActivity.this, AnimationActivityThree.class);
                startActivity(intent);
            }else if (list.get(position).equals(AnimationActivity.this.getResources().getString(R.string.animation_four))){
                Intent intent = new Intent(AnimationActivity.this, AnimationActivityFour.class);
                startActivity(intent);
            } else if (list.get(position).equals(AnimationActivity.this.getResources().getString(R.string.animation_five))){
                Intent intent = new Intent(AnimationActivity.this, AnimationActivityFive.class);
                startActivity(intent);
            }

        }

        @Override
        public void onItemLongClick(View view, int position) {
            LogUtil.d(TAG,"onItemLongClick");
        }
    };
}

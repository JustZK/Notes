package com.example.zk.notes;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.example.zk.notes.animation.AnimationActivity;
import com.example.zk.notes.dialog.DialogPlusActiviry;
import com.example.zk.notes.drawable.DrawableActivity;
import com.example.zk.notes.keyboard.KeyboardActivity;
import com.example.zk.notes.login.LoginActivity;
import com.example.zk.notes.nfc.NFCActivity;
import com.example.zk.notes.player.MediaPlayerActivity;
import com.example.zk.notes.service.DemoIntentService;
import com.example.zk.notes.service.DemoPushService;
import com.example.zk.notes.slidingpanelayout.SlidingPaneLayoutActivity;
import com.example.zk.notes.util.LogUtil;
import com.igexin.sdk.PushManager;

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
        list.add(getResources().getString(R.string.keyboard));
        list.add(getResources().getString(R.string.animation));
        list.add(getResources().getString(R.string.nfc));
        list.add(getResources().getString(R.string.dialog_plus));
        listAdapter = new MainActivityListAdapter(this, list);
        main_rlv.setAdapter(listAdapter);
        main_rlv.setLayoutManager(new LinearLayoutManager(this));
        main_rlv.setItemAnimator(new DefaultItemAnimator());
        listAdapter.setOnItemClickListener(onItemClickListener);

        // com.getui.demo.DemoPushService 为第三方自定义推送服务
        PushManager.getInstance().initialize(this.getApplicationContext(), DemoPushService.class);
        // com.getui.demo.DemoIntentService 为第三方自定义的推送服务事件接收类
        PushManager.getInstance().registerPushIntentService(this.getApplicationContext(), DemoIntentService.class);

    }

    private MainActivityListAdapter.OnItemClickListener onItemClickListener = new MainActivityListAdapter.OnItemClickListener() {
        @Override
        public void onItemClick(View view, int position) {
            LogUtil.d(TAG,"onItemClick");
            if (list.get(position).equals(MainActivity.this.getResources().getString(R.string.login_like_qq))){
                Intent intent = new Intent(MainActivity.this, LoginActivity.class);
                startActivity(intent);
            }else if (list.get(position).equals(MainActivity.this.getResources().getString(R.string.customize_drawable))){
                Intent intent = new Intent(MainActivity.this, DrawableActivity.class);
                startActivity(intent);
            }else if (list.get(position).equals(MainActivity.this.getResources().getString(R.string.media_player))){
                Intent intent = new Intent(MainActivity.this, MediaPlayerActivity.class);
                startActivity(intent);
            }else if (list.get(position).equals(MainActivity.this.getResources().getString(R.string.sliding_pane_layout))){
                Intent intent = new Intent(MainActivity.this, SlidingPaneLayoutActivity.class);
                startActivity(intent);
            }else if (list.get(position).equals(MainActivity.this.getResources().getString(R.string.keyboard))){
                Intent intent = new Intent(MainActivity.this, KeyboardActivity.class);
                startActivity(intent);
            }else if (list.get(position).equals(MainActivity.this.getResources().getString(R.string.animation))){
                Intent intent = new Intent(MainActivity.this, AnimationActivity.class);
                startActivity(intent);
            }else if (list.get(position).equals(MainActivity.this.getResources().getString(R.string.nfc))){
                Intent intent = new Intent(MainActivity.this, NFCActivity.class);
                startActivity(intent);
            }else if (list.get(position).equals(MainActivity.this.getResources().getString(R.string.dialog_plus))){
                Intent intent = new Intent(MainActivity.this, DialogPlusActiviry.class);
                startActivity(intent);
            }

        }

        @Override
        public void onItemLongClick(View view, int position) {
            LogUtil.d(TAG,"onItemLongClick");
        }
    };
}

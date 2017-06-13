package com.example.zk.notes.drawable;

import android.graphics.drawable.ClipDrawable;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.Spinner;

import com.example.zk.notes.R;
import com.example.zk.notes.slidingpanelayout.BaseSlideCloseActivity;

public class DrawableActivityFour extends BaseSlideCloseActivity {
    private final static String TAG = "DrawableActivityFour";
    private ImageView drawable_four_iv_clip;
    private Drawable drawable;
    private ArrayAdapter<String> clipAdapter;
    private Spinner drawable_four_sp_clip;
    private String[] clipDescArray = { "水平百分十", "水平百分三十", "水平百分五十", "水平百分七十", "水平百分百", "垂直百分十", "垂直百分三十", "垂直百分五十",
            "垂直百分七十", "垂直百分百", };
    private int[] clipArray = { 10, 30, 50, 70, 100, 10, 30, 50, 70, 100 };
    private int ratio;
    private ClipDrawable clip;
    private ClipDrawable clip_drawable;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_drawable_four);

        init();
    }

    private void init(){
        drawable_four_iv_clip = (ImageView) findViewById(R.id.drawable_four_iv_clip);
        drawable_four_sp_clip = (Spinner) findViewById(R.id.drawable_four_sp_clip);
        drawable = getResources().getDrawable(R.drawable.xiaohuangren);

        clipAdapter = new ArrayAdapter<>(this, R.layout.item_text, clipDescArray);
        drawable_four_sp_clip.setPrompt("请选择裁剪幅度");
        drawable_four_sp_clip.setAdapter(clipAdapter);
        drawable_four_sp_clip.setOnItemSelectedListener(mOnItemSelectedListener);
        drawable_four_sp_clip.setSelection(0);


    }

    private AdapterView.OnItemSelectedListener mOnItemSelectedListener = new AdapterView.OnItemSelectedListener() {
        @Override
        public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
            ratio = clipArray[position];
            clip = new ClipDrawable(drawable, Gravity.CENTER,
                    position < 5 ? ClipDrawable.HORIZONTAL : ClipDrawable.VERTICAL);
            drawable_four_iv_clip.setImageDrawable(clip);
            clip_drawable = (ClipDrawable) drawable_four_iv_clip.getDrawable();
            clip_drawable.setLevel(ratio * 100);

        }

        @Override
        public void onNothingSelected(AdapterView<?> parent) {

        }
    };
}

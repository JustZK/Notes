package com.example.zk.notes.drawable;

import android.graphics.drawable.Drawable;
import android.graphics.drawable.ScaleDrawable;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.Spinner;

import com.example.zk.notes.R;
import com.example.zk.notes.slidingpanelayout.BaseSlideCloseActivity;

public class DrawableActivitySix extends BaseSlideCloseActivity {
    private Spinner drawable_six_sp_scale;
    private ImageView drawable_six_iv_scale;
    private Drawable drawable;
    private float[] scaleArray = { 0.5f, 0.4f, 0.25f, 0.1f, 0.0f };
    private String[] scaleDescArray = {
            "二分之一", "十分之六", "四分之三", "十分之九", "原始尺寸"
    };
    private ArrayAdapter<String> scaleAdapter;
    private float ratio;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_drawable_six);

        init();
    }

    private void init(){
        drawable_six_iv_scale = (ImageView) findViewById(R.id.drawable_six_iv_scale);
        drawable_six_sp_scale = (Spinner) findViewById(R.id.drawable_six_sp_scale);
        drawable = getResources().getDrawable(R.drawable.xiaohuangren);
        drawable.setLevel(10000);

        scaleAdapter = new ArrayAdapter<>(this, R.layout.item_text, scaleDescArray);
        drawable_six_sp_scale.setPrompt("请选择缩小比例");
        drawable_six_sp_scale.setAdapter(scaleAdapter);
        drawable_six_sp_scale.setOnItemSelectedListener(mOnItemSelectedListener);
        drawable_six_sp_scale.setSelection(0);
    }

    private AdapterView.OnItemSelectedListener mOnItemSelectedListener = new AdapterView.OnItemSelectedListener() {
        @Override
        public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
            ratio = scaleArray[position];
            ScaleDrawable scale = new ScaleDrawable(drawable,
                    Gravity.CENTER, ratio, ratio);
            drawable_six_iv_scale.setImageDrawable(scale);
        }

        @Override
        public void onNothingSelected(AdapterView<?> parent) {

        }
    };
}

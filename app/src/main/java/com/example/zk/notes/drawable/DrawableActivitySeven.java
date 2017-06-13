package com.example.zk.notes.drawable;

import android.graphics.drawable.Drawable;
import android.graphics.drawable.RotateDrawable;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.Spinner;

import com.example.zk.notes.R;
import com.example.zk.notes.slidingpanelayout.BaseSlideCloseActivity;

public class DrawableActivitySeven extends BaseSlideCloseActivity {
    private Spinner drawable_seven_sp_scale;
    private ImageView drawable_seven_iv_scale;
    private Drawable drawable;
    private String[] rotateDescArray = {"0度", "45度", "90度", "135度", "180度", "225度", "270度", "315度", "360度"};
    private float[] rotateArray = { 0.0f, 0.125f, 0.25f, 0.375f, 0.5f, 0.625f, 0.75f, 0.875f, 1.0f };
    private ArrayAdapter<String> rotateAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_drawable_seven);

        init();
    }

    private void init(){
        drawable_seven_iv_scale = (ImageView) findViewById(R.id.drawable_seven_iv_scale);
        drawable_seven_sp_scale = (Spinner) findViewById(R.id.drawable_seven_sp_scale);
        drawable = getResources().getDrawable(R.drawable.xiaohuangren);
        drawable.setLevel(10000);

        rotateAdapter = new ArrayAdapter<>(this, R.layout.item_text, rotateDescArray);
        drawable_seven_sp_scale.setPrompt("请选择旋转角度");
        drawable_seven_sp_scale.setAdapter(rotateAdapter);
        drawable_seven_sp_scale.setOnItemSelectedListener(mOnItemSelectedListener);
        drawable_seven_sp_scale.setSelection(0);
    }

    private AdapterView.OnItemSelectedListener mOnItemSelectedListener = new AdapterView.OnItemSelectedListener() {
        @Override
        public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
            float ratio = rotateArray[position];
            RotateDrawable rotate = (RotateDrawable) drawable_seven_iv_scale.getDrawable();
            rotate.setLevel((int)(10000*ratio));
        }

        @Override
        public void onNothingSelected(AdapterView<?> parent) {

        }
    };
}

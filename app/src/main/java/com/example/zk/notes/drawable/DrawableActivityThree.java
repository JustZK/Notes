package com.example.zk.notes.drawable;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

import com.example.zk.notes.R;
import com.example.zk.notes.slidingpanelayout.BaseSlideCloseActivity;

public class DrawableActivityThree extends BaseSlideCloseActivity {

    private static final String TAG = "DrawableActivityThree";
    private ImageView drawable_three_iv_alpha;
    private Bitmap mBitmap;
    private AlphaDrawable drawable;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_drawable_three);

        init();
    }

    private void init(){
        drawable_three_iv_alpha = (ImageView) findViewById(R.id.drawable_three_iv_alpha);

        mBitmap = BitmapFactory.decodeResource(getResources(), R.drawable.xiaohuangren);
        drawable_three_iv_alpha.setImageDrawable(new BitmapDrawable(getResources(), mBitmap));

        findViewById(R.id.drawable_three_btn_alpha_start).setOnClickListener(mOnClickListener);



    }

    private View.OnClickListener mOnClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            switch (v.getId()){
                case R.id.drawable_three_btn_alpha_start:
                    drawable = new AlphaDrawable(getResources(), mBitmap);
                    drawable.setPeriod(2);
                    drawable_three_iv_alpha.setImageDrawable(drawable);
                    break;
                default:
                    break;
            }
        }
    };
}

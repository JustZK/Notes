package com.example.zk.notes.drawable;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.zk.notes.R;
import com.example.zk.notes.slidingpanelayout.BaseSlideCloseActivity;

public class DrawableActivityOne extends BaseSlideCloseActivity {

    private static final String TAG = "DrawableActivityOne";
    private TextView drawable_one_shape_tv;
    private ImageView drawable_one_shape_iv;
    private Bitmap mBitmap;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_drawable_one);

        init();
    }

    private void init(){
        drawable_one_shape_iv = (ImageView) findViewById(R.id.drawable_one_shape_iv);
        drawable_one_shape_tv = (TextView) findViewById(R.id.drawable_one_shape_tv);

        mBitmap = BitmapFactory.decodeResource(getResources(), R.drawable.xiaohuangren);

        findViewById(R.id.drawable_one_circle_btn).setOnClickListener(mOnClickListener);
        findViewById(R.id.drawable_one_oval_btn).setOnClickListener(mOnClickListener);
        findViewById(R.id.drawable_one_round_btn).setOnClickListener(mOnClickListener);
    }

    private View.OnClickListener mOnClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            switch (v.getId()){
                case R.id.drawable_one_circle_btn:
                    drawable_one_shape_tv.setText("下面是圆形修饰后的图片");
                    drawable_one_shape_iv.setImageDrawable(new CircleDrawable(getResources(), mBitmap));
                    break;
                case R.id.drawable_one_oval_btn:
                    drawable_one_shape_tv.setText("下面是椭圆形修饰后的图片");
                    drawable_one_shape_iv.setImageDrawable(new OvalDrawable(getResources(), mBitmap));
                    break;
                case R.id.drawable_one_round_btn:
                    drawable_one_shape_tv.setText("下面是圆角矩形修饰后的图片");
                    RoundDrawable drawable = new RoundDrawable(getResources(), mBitmap);
                    drawable.setCornerRadius(MetricsUtil.dip2px(DrawableActivityOne.this, 40));
                    drawable_one_shape_iv.setImageDrawable(drawable);
                    break;
                default:
                    break;
            }
        }
    };
}

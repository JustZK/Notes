package com.example.zk.notes.animation;

import android.graphics.drawable.AnimationDrawable;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.zk.notes.R;
import com.example.zk.notes.animation.GifImage.GifFrame;
import com.example.zk.notes.slidingpanelayout.BaseSlideCloseActivity;

import java.io.InputStream;

public class AnimationActivityFour extends BaseSlideCloseActivity {
    private ImageView animation_four_iv;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_animation_four);

        init();
    }

    private void init(){
        animation_four_iv = (ImageView) findViewById(R.id.animation_four_iv);

        findViewById(R.id.animation_four_btn1).setOnClickListener(mOnClickListener);
        findViewById(R.id.animation_four_btn2).setOnClickListener(mOnClickListener);
    }

    private View.OnClickListener mOnClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            switch (v.getId()){
                case R.id.animation_four_btn1:
                    showFrameAnimation();
                    break;
                case R.id.animation_four_btn2:
                    showGifAnimation(R.drawable.gif);
                    break;
                default:
                    break;
            }
        }
    };

    private Drawable getDraw(int id) {
        return getResources().getDrawable(id);
    }

    private void showFrameAnimation() {
        //帧动画需要把每帧图片加入AnimationDrawable队列
        AnimationDrawable animationList = new AnimationDrawable();
        animationList.addFrame(getDraw(R.drawable.flow_p1), 50);
        animationList.addFrame(getDraw(R.drawable.flow_p2), 50);
        animationList.addFrame(getDraw(R.drawable.flow_p3), 50);
        animationList.addFrame(getDraw(R.drawable.flow_p4), 50);
        animationList.addFrame(getDraw(R.drawable.flow_p5), 50);
        animationList.addFrame(getDraw(R.drawable.flow_p6), 50);
        animationList.addFrame(getDraw(R.drawable.flow_p7), 50);
        animationList.addFrame(getDraw(R.drawable.flow_p8), 50);
        //setOneShot为true表示只播放一次，为false表示循环播放
        animationList.setOneShot(false);
        animation_four_iv.setImageDrawable(animationList);
        animationList.start();
    }

    private void showGifAnimation(int resid) {
        InputStream is = getResources().openRawResource(resid);
        GifImage gifImage = new GifImage();
        int code = gifImage.read(is);
        if (code == GifImage.STATUS_OK) {
            GifFrame[] frameList = gifImage.getFrames();
            AnimationDrawable gifList = new AnimationDrawable();
            for (int i=0; i<frameList.length; i++) {
                //BitmapDrawable用于把Bitmap格式转换为Drawable格式
                BitmapDrawable bitmapDrawable = new BitmapDrawable(getResources(), frameList[i].image);
                gifList.addFrame(bitmapDrawable, frameList[i].delay);
            }
            gifList.setOneShot(false);
            animation_four_iv.setImageDrawable(gifList);
            gifList.start();
        } else if (code == GifImage.STATUS_FORMAT_ERROR) {
            Toast.makeText(this, "该图片不是gif格式", Toast.LENGTH_LONG).show();
        } else {
            Toast.makeText(this, "gif图片读取失败:"+code, Toast.LENGTH_LONG).show();
        }
    }
}

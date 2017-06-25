package com.example.zk.notes.animation;

import android.graphics.drawable.Drawable;
import android.graphics.drawable.TransitionDrawable;
import android.os.Bundle;
import android.view.View;
import android.view.animation.AlphaAnimation;
import android.widget.ImageView;

import com.example.zk.notes.R;
import com.example.zk.notes.slidingpanelayout.BaseSlideCloseActivity;

public class AnimationActivityTwo extends BaseSlideCloseActivity {

    private ImageView animation_two_iv;
    private AlphaAnimation alphaAnimation;
    private TransitionDrawable transitionDrawable;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_animation_two);
        init();
    }

    private void init(){
        animation_two_iv = (ImageView) findViewById(R.id.animation_two_iv);
        findViewById(R.id.animation_two_btn1).setOnClickListener(mOnClickListener);
        findViewById(R.id.animation_two_btn2).setOnClickListener(mOnClickListener);
        //一开始先设置透明，这样图片不会显示，等点击按钮时再显示
        animation_two_iv.setAlpha(0.0f);
        alphaAnimation = new AlphaAnimation(0.0f, 1.0f);
        alphaAnimation.setDuration(3000);    //深浅动画持续时间
        alphaAnimation.setFillAfter(true);   //动画结束时保持结束的画面

    }

    private View.OnClickListener mOnClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            switch (v.getId()){
                case R.id.animation_two_btn1:
                    animation_two_iv.setImageResource(R.drawable.xiaohuangren);
                    animation_two_iv.setAlpha(1.0f);
                    animation_two_iv.setAnimation(alphaAnimation);
                    alphaAnimation.start();
                    break;
                case R.id.animation_two_btn2:
                    //淡入淡出动画需要先设置一个Drawable数组，用于变换图片
                    Drawable[] drawableArray = {
                            getResources().getDrawable(R.drawable.xiaohuangren),
                            getResources().getDrawable(R.drawable.fennvdexiaoniao)
                    };
                    transitionDrawable = new TransitionDrawable(drawableArray);
                    animation_two_iv.setImageDrawable(transitionDrawable);
                    transitionDrawable.startTransition(3000);
                    break;
                default:
                    break;
            }
        }
    };
}

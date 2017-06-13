package com.example.zk.notes.drawable;

import android.animation.Animator;
import android.animation.ObjectAnimator;
import android.graphics.drawable.ClipDrawable;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.widget.ImageView;

import com.example.zk.notes.R;
import com.example.zk.notes.slidingpanelayout.BaseSlideCloseActivity;

public class DrawableActivityFive extends BaseSlideCloseActivity {
    private ImageView drawable_five_iv_anim;
    private Drawable drawable;
    private int mOriention;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_drawable_five);

        init();
    }

    private void init(){
        drawable_five_iv_anim = (ImageView) findViewById(R.id.drawable_five_iv_anim);
        drawable = getResources().getDrawable(R.drawable.xiaohuangren);
        findViewById(R.id.drawable_five_anim_horizontal).setOnClickListener(mOnClickListener);
        findViewById(R.id.drawable_five_anim_vertical).setOnClickListener(mOnClickListener);
    }

    private void playAnimation(int oriention, int from, int to) {
        mOriention = oriention;
        ClipDrawable clip = new ClipDrawable(drawable, Gravity.CENTER, oriention);
        drawable_five_iv_anim.setImageDrawable(clip);
        ClipDrawable anim_drawable = (ClipDrawable) drawable_five_iv_anim.getDrawable();
        ObjectAnimator anim = ObjectAnimator.ofInt(anim_drawable, "level", from, to);
        anim.setDuration(3000);
        if (from > 0) {
            anim.addListener(mAnimatorListener);
        }
        anim.start();
    }

    private View.OnClickListener mOnClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            switch (v.getId()){
                case R.id.drawable_five_anim_horizontal:
                    playAnimation(ClipDrawable.HORIZONTAL, 10000, 0);
                    break;
                case R.id.drawable_five_anim_vertical:
                    playAnimation(ClipDrawable.VERTICAL, 10000, 0);
                    break;
                default:
                    break;
            }
        }
    };

    private Animator.AnimatorListener mAnimatorListener = new Animator.AnimatorListener() {
        @Override
        public void onAnimationStart(Animator animation) {

        }

        @Override
        public void onAnimationEnd(Animator animation) {
            playAnimation(mOriention, 0, 10000);
        }

        @Override
        public void onAnimationCancel(Animator animation) {

        }

        @Override
        public void onAnimationRepeat(Animator animation) {

        }
    };
}

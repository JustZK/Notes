  package com.example.zk.notes.animation;

  import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;

import com.example.zk.notes.R;
import com.example.zk.notes.slidingpanelayout.BaseSlideCloseActivity;

  public class AnimationActivityOne extends BaseSlideCloseActivity {

    private LinearLayout animation_one_layout;
    private CicleAnimation cicleAnimation;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_animation_one);

        init();
    }

    private void init(){
        findViewById(R.id.animation_one_btn).setOnClickListener(mOnClickListener);
        animation_one_layout = (LinearLayout) findViewById(R.id.animation_one_layout);
        cicleAnimation = new CicleAnimation(this);
        animation_one_layout.addView(cicleAnimation);
        cicleAnimation.render();
    }

    private View.OnClickListener mOnClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            switch (v.getId()){
                case R.id.animation_one_btn:
                    cicleAnimation.refresh();
                    break;
                default:
                    break;
            }
        }
    };
}

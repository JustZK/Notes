package com.example.zk.notes.animation;

import android.os.Bundle;
import android.view.animation.Animation;
import android.widget.ImageView;

import com.example.zk.notes.R;
import com.example.zk.notes.slidingpanelayout.BaseSlideCloseActivity;

public class AnimationActivityThree extends BaseSlideCloseActivity {

    private ImageView animation_three_iv;
    private SwingAnimation swingAnimation;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_animation_three);

        init();
    }

    private void init(){
        animation_three_iv = (ImageView) findViewById(R.id.animation_three_iv);
        //参数取值说明：中间度数、摆到左侧的度数、摆到右侧的度数、圆心X坐标类型、圆心X坐标相对比例、圆心Y坐标类型、圆心Y坐标相对比例
        //坐标类型有三种：ABSOLUTE 绝对坐标，RELATIVE_TO_SELF 相对自身的坐标，RELATIVE_TO_PARENT 相对上级视图的坐标
        //X坐标相对比例，为0时表示左边顶点，为1表示右边顶点，为0.5表示水平中心点
        //Y坐标相对比例，为0时表示上边顶点，为1表示下边顶点，为0.5表示垂直中心点
        swingAnimation = new SwingAnimation(
                0f, 60f, -60f, Animation.RELATIVE_TO_SELF, 0.5f, Animation.RELATIVE_TO_SELF, 0.0f);
        swingAnimation.setDuration(4000);     //动画持续时间
        swingAnimation.setRepeatCount(-1);     //动画重播次数
        swingAnimation.setFillAfter(false);  //是否保持动画结束画面
        swingAnimation.setStartOffset(500);   //动画播放延迟
        animation_three_iv.startAnimation(swingAnimation);
    }
}

package com.example.zk.notes.slidingpanelayout;

import android.app.Activity;
import android.os.Bundle;
import android.support.v4.widget.SlidingPaneLayout;
import android.view.View;

import com.example.zk.notes.R;

public class SlidingPaneLayoutActivityTwo extends Activity implements SlidingPaneLayout.PanelSlideListener{

    private SlidingPaneLayout sliding_pane_layout_two_layout;
    private SlidingPaneLayoutFragmentLeft slidingPaneLayoutFragmentLeft;
    private SlidingPaneLayoutFragmentRight slidingPaneLayoutFragmentRight;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sliding_pane_layout_two);

        init();
    }

    private void init(){
        sliding_pane_layout_two_layout = (SlidingPaneLayout) findViewById(R.id.sliding_pane_layout_two_layout);
        slidingPaneLayoutFragmentLeft = (SlidingPaneLayoutFragmentLeft) getFragmentManager().findFragmentById(R.id.sliding_pane_layout_two_fragment_left);
        slidingPaneLayoutFragmentRight = (SlidingPaneLayoutFragmentRight) getFragmentManager().findFragmentById(R.id.sliding_pane_layout_two_fragment_right);

        sliding_pane_layout_two_layout.setPanelSlideListener(this);

    }

    @Override
    public void onPanelSlide(View panel, float slideOffset) {

    }

    @Override
    public void onPanelOpened(View panel) {

    }

    @Override
    public void onPanelClosed(View panel) {

    }
}

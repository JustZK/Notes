package com.example.zk.notes.drawable;

import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.drawable.BitmapDrawable;
import android.os.Handler;

public class AlphaDrawable extends BitmapDrawable {
	
	private Handler mHandler = new Handler();
	private int mPeriod = 5;
	private int mCount = 100;
	private int mGap = 0;

	public AlphaDrawable(Resources res, Bitmap bitmap) {
		super(res, bitmap);
	}

	public void setPeriod(int period) {
		mPeriod = period;
	}

	public int getPeriod() {
		return mPeriod;
	}

	@Override  
	public void draw(Canvas canvas) {
		super.draw(canvas);
		if (mGap == 0) {
			mGap = mPeriod*1000 / mCount;
		}
		mHandler.postDelayed(mRefresh, mGap);
	}
	
	private Runnable mRefresh = new Runnable() {

		@Override
		public void run() {
			mCount--;
			if (mCount >= 0) {
				setAlpha((int) (255 * (100-mCount)/100.0));
			}
		}
		
	};

}

package com.example.zk.notes.drawable;

import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapShader;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.RectF;
import android.graphics.Shader.TileMode;
import android.graphics.drawable.BitmapDrawable;

/**
 * 圆角矩形修饰图片
 */
public class RoundDrawable extends BitmapDrawable {

	private Paint mPaint;
	private RectF mRect;
	private int mCornerRadius = 10;
	
	public RoundDrawable(Resources res, Bitmap bitmap) {
		super(res, bitmap);
		BitmapShader bitmapShader = new BitmapShader(bitmap,
				TileMode.CLAMP, TileMode.CLAMP);
		mPaint = new Paint();
		mPaint.setAntiAlias(true);  //抗锯齿
		mPaint.setShader(bitmapShader);
	}

	public void setCornerRadius(int corner_radius) {
		mCornerRadius = corner_radius;
	}
	
	public int getCornerRadius() {
		return mCornerRadius;
	}
	
	@Override
	public void setBounds(int left, int top, int right, int bottom) {
		super.setBounds(left, top, right, bottom);
		mRect = new RectF(left, top, right, bottom);
	}

	@Override
	public void draw(Canvas canvas) {
		canvas.drawRoundRect(mRect, mCornerRadius, mCornerRadius, mPaint);
	}
	
}

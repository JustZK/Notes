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
 * 椭圆形修饰图片
 */
public class OvalDrawable extends BitmapDrawable {
	
	private Paint mPaint;

	public OvalDrawable(Resources res, Bitmap bitmap) {
		super(res, bitmap);
		BitmapShader bitmapShader = new BitmapShader(bitmap,
				TileMode.CLAMP, TileMode.CLAMP);
		mPaint = new Paint();
		mPaint.setAntiAlias(true);  //抗锯齿
		mPaint.setShader(bitmapShader);
	}

	@Override
	public void draw(Canvas canvas) {
		int width = getBitmap().getWidth();
		int height = getBitmap().getHeight();
		RectF oval = new RectF(0, 0, width, height);
		canvas.drawOval(oval, mPaint);
	}
	
}

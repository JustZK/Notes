package com.example.zk.notes.drawable;

import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapShader;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Shader.TileMode;
import android.graphics.drawable.BitmapDrawable;

/**
 * 圆形修饰图片
 */
public class CircleDrawable extends BitmapDrawable {

	private Paint mPaint;

	public CircleDrawable(Resources res, Bitmap bitmap) {
		super(res, bitmap);
		BitmapShader bitmapShader = new BitmapShader(bitmap,
				TileMode.CLAMP, TileMode.CLAMP);
		mPaint = new Paint();
		mPaint.setAntiAlias(true);
		mPaint.setShader(bitmapShader);
	}

	@Override
	public void draw(Canvas canvas) {
		int width = getBitmap().getWidth();
		int height = getBitmap().getHeight();
		int radius = Math.min(width, height) / 2;
		int x_pos = (width>radius+radius)?width/2:radius;
		int y_pos = (height>radius+radius)?height/2:radius;
		canvas.drawCircle(x_pos, y_pos, radius, mPaint);
	}
	
}

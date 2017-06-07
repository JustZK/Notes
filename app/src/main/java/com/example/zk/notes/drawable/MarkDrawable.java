package com.example.zk.notes.drawable;

import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Paint.FontMetrics;
import android.graphics.Point;
import android.graphics.drawable.BitmapDrawable;

public class MarkDrawable extends BitmapDrawable {
	private static final String TAG = "MarkDrawable";
	
	private Paint mPaint;
	private String mText;
	private int mTextColor;
	private float mTextSize;
	private int mTextAlign;
	
	public static int ALIGN_TOP = 1;
	public static int ALIGN_CENTER = 2;
	public static int ALIGN_BOTTOM = 3;

	public MarkDrawable(Resources res, Bitmap bitmap) {
		super(res, bitmap);
		mTextColor = Color.GREEN;
		mTextSize = 40f;
		mTextAlign = ALIGN_CENTER;
	}

	public void setTextColor(int text_color) {
		mTextColor = text_color;
	}

	public int getTextColor() {
		return mTextColor;
	}

	public void setTextSize(float text_size) {
		mTextSize = text_size;
	}

	public float getTextSize() {
		return mTextSize;
	}

	public void setTextAlign(int text_align) {
		mTextAlign = text_align;
	}

	public int getTextAlign() {
		return mTextAlign;
	}

	public void setText(String text) {
	    mText = text;
	    mPaint = new Paint();
	    mPaint.setAntiAlias(true);
	    mPaint.setColor(mTextColor);
	    mPaint.setTextSize(mTextSize);
	    mPaint.setTextAlign(Paint.Align.CENTER);
	}
	
	@Override  
	public void draw(Canvas canvas) {
		super.draw(canvas);
		if (mPaint != null) {
			Point point = getFontScope(mText, mTextSize);
			int width = getBitmap().getWidth();
			int height = getBitmap().getHeight();
			int x_pos = (width>point.x)?(width/2):0;
			int y_pos = 0;
			if (mTextAlign == ALIGN_TOP) {
				y_pos = point.y;
			} else if (mTextAlign == ALIGN_CENTER) {
				y_pos = height / 2;
			} else if (mTextAlign == ALIGN_BOTTOM) {
				y_pos = height - point.y/2;
			}
		    canvas.drawText(mText, x_pos, y_pos, mPaint);
		}
	}
	
	//根据字体大小获得文字宽度和高度
	private Point getFontScope(String text, float size) {
		Point point = new Point();
	    Paint paint = new Paint();
	    paint.setTextSize(size);
	    FontMetrics fm = paint.getFontMetrics();
	    point.x = (int) paint.measureText(text, 0, text.length());
	    point.y = (int) Math.ceil(fm.descent - fm.ascent);
		return point;
	}
	
}

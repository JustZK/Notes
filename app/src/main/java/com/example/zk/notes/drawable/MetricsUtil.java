package com.example.zk.notes.drawable;

import android.app.Activity;
import android.content.Context;
import android.graphics.Point;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.Display;
import android.view.View;
import android.view.ViewGroup;
import android.view.View.MeasureSpec;
import android.view.ViewGroup.LayoutParams;
import android.view.WindowManager;
import android.widget.LinearLayout;

public class MetricsUtil {
	private final static String TAG = "MetricsUtil";

	public static int getWidth(Activity act) {
		DisplayMetrics mDisplayMetrics = new DisplayMetrics();
		act.getWindowManager().getDefaultDisplay().getMetrics(mDisplayMetrics);
		return mDisplayMetrics.widthPixels;
	}
	
	public static int getHeight(Activity act) {
		DisplayMetrics mDisplayMetrics = new DisplayMetrics();
		act.getWindowManager().getDefaultDisplay().getMetrics(mDisplayMetrics);
		return mDisplayMetrics.heightPixels;
	}

	public static Point getSize(Activity act) {
		Display display = act.getWindowManager().getDefaultDisplay();
		Point size = new Point();
		display.getSize(size);
		return size;
	}

	public static Point getSize(Context ctx) {
		WindowManager wm = (WindowManager) ctx.getSystemService(Context.WINDOW_SERVICE);
		Display display = wm.getDefaultDisplay();
		Point size = new Point();
		display.getSize(size);
		return size;
	}

	public static Point getSizeNew(Context ctx) {
		WindowManager wm = (WindowManager) ctx.getSystemService(Context.WINDOW_SERVICE);
		DisplayMetrics dm = new DisplayMetrics();
		wm.getDefaultDisplay().getMetrics(dm);
		Point size = new Point();
		size.x = dm.widthPixels;
		size.y = dm.heightPixels;
		return size;
	}
	
	public static float getRealHeight(Activity act, int resid) {
		LinearLayout llayout = (LinearLayout) act.findViewById(resid);
		return getRealHeight(llayout);
	}
	
	public static float getRealHeight(View parent, int resid) {
		LinearLayout llayout = (LinearLayout) parent.findViewById(resid);
		return getRealHeight(llayout);
	}
	
	public static float getRealHeight(View child) {
		float realHeight = 0;

		LinearLayout llayout = (LinearLayout) child;
		LayoutParams params = llayout.getLayoutParams();
		if (params == null) {
			params = new LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT);
		}
		//i1=0表示MeasureSpec.UNSPECIFIED
		int i1 = ViewGroup.getChildMeasureSpec(0, 0, params.width);
		int i2 = params.height;
		int i3;
		if (i2 > 0) {
			i3 = MeasureSpec.makeMeasureSpec(i2, MeasureSpec.EXACTLY);
		} else {
			i3 = MeasureSpec.makeMeasureSpec(0, MeasureSpec.UNSPECIFIED);
		}
			llayout.measure(i1, i3);
			Log.d(TAG, "MeasureSpec.UNSPECIFIED="+MeasureSpec.UNSPECIFIED
					+",MeasureSpec.EXACTLY="+MeasureSpec.EXACTLY
					+",MeasureSpec.AT_MOST="+MeasureSpec.AT_MOST);
		realHeight = llayout.getMeasuredHeight();
			
		return realHeight;
	}

	/**
	 * 根据手机的分辨率从 dp 的单位 转成为 px(像素)  。加0.5的目的是四舍五入
	 */
	public static int dip2px(Context context, float dpValue) {
        final float scale = context.getResources().getDisplayMetrics().density;
        return (int) (dpValue * scale + 0.5f);
    }

	/**
	 * 根据手机的分辨率从 px(像素) 的单位 转成为 dp 。加0.5的目的是四舍五入
	 */
	public static int px2dip(Context context, float pxValue) {
        final float scale = context.getResources().getDisplayMetrics().density;
        return (int) (pxValue / scale + 0.5f);
    }

}


package com.common.cache.test.view;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Point;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.Scroller;

import com.common.cache.App;
import com.common.uitl.LogUtil;
import com.common.uitl.Tool;

public class RelayoutLayoutOnLayout extends RelativeLayout {

	private Context mcontext;
	private Scroller mScroller;

	public RelayoutLayoutOnLayout(Context context, AttributeSet attrs) {
		super(context, attrs);
		mcontext = context;
		mScroller = new Scroller(context);
	}

	private int count = 0;

	@Override
	protected void onLayout(boolean changed, int l, int t, int r, int b) {
		super.onLayout(changed, l, t, r, b);
		Point p = Tool.getDisplayMetrics(mcontext);
		View childView = getChildAt(count);
		count++;
		l = p.x * count;
		childView.layout(l, t, r, b);
	}

	@Override
	protected void onDraw(Canvas canvas) {

		super.onDraw(canvas);
	}

	
	@Override
	public boolean onTouchEvent(MotionEvent event) {
		 mScroller.startScroll(0, 0, -30, -30, 50);
		return true;
	}

	@Override
	/** 
	 * Called by a parent to request that a child update its values for mScrollX 
	 * and mScrollY if necessary. This will typically be done if the child is 
	 * animating a scroll using a {@link android.widget.Scroller Scroller} 
	 * object. 
	 */
	public void computeScroll() {
		if (mScroller.computeScrollOffset())// 如果mScroller没有调用startScroll，这里将会返回false。
		{
			// 因为调用computeScroll函数的是MyLinearLayout实例，
			// 所以调用scrollTo移动的将是该实例的孩子，也就是MyButton实例
			scrollTo(mScroller.getCurrX(), 0);
			// 继续让系统重绘
			getChildAt(0).invalidate();
		}
	}
}

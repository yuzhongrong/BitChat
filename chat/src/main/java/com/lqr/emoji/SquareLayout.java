package com.lqr.emoji;

import android.annotation.SuppressLint;
import android.content.Context;
import android.util.AttributeSet;
import android.widget.LinearLayout;

/*****
 * 控制图片等宽等高
 * @author Binzu
 *
 */
@SuppressLint("NewApi")
public class SquareLayout extends LinearLayout {

	public SquareLayout(Context context, AttributeSet attrs, int defStyle) {
		super(context, attrs, defStyle);
	}
	public SquareLayout(Context context, AttributeSet attrs) {
		super(context, attrs);
	}
	public SquareLayout(Context context) {
		super(context);
	}
	@Override
	protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
		setMeasuredDimension(getDefaultSize(0, widthMeasureSpec), getDefaultSize(0, heightMeasureSpec));
        // Children are just made to fill our space.
        int childWidthSize = getMeasuredWidth();
        @SuppressWarnings("unused")
		int childHeightSize = getMeasuredHeight();
        //高度和宽度一样
        heightMeasureSpec = MeasureSpec.makeMeasureSpec(childWidthSize, MeasureSpec.EXACTLY);
        widthMeasureSpec =  MeasureSpec.makeMeasureSpec(childWidthSize, MeasureSpec.EXACTLY);
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
	}
}

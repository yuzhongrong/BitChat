package com.lqr.emoji;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.GridView;

/**
 * Created by shaco on 16/5/7.
 */
public class NoScroolGridView extends GridView {

    public NoScroolGridView(Context context) {
        super(context);
    }

    public NoScroolGridView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public NoScroolGridView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
    }

    //不出现滚动条
    @Override
    public void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {

        int expandSpec = MeasureSpec.makeMeasureSpec(Integer.MAX_VALUE >> 2, MeasureSpec.AT_MOST);
        super.onMeasure(widthMeasureSpec, expandSpec);
    }
}


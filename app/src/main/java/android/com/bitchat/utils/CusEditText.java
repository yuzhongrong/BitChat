package android.com.bitchat.utils;

import android.content.Context;
import android.graphics.Rect;
import android.util.AttributeSet;

/**
 * Created by jack on 2018/6/29.
 */

public class CusEditText extends android.support.v7.widget.AppCompatEditText {
    public CusEditText(Context context) {
        this(context,null);
    }

    public CusEditText(Context context, AttributeSet attrs) {
        super(context, attrs);
    }


    @Override
    protected void onFocusChanged(boolean focused, int direction, Rect previouslyFocusedRect) {
        super.onFocusChanged(focused, direction, previouslyFocusedRect);

        String hint = null;
        if(focused){
            hint =getHint().toString();
            setTag(hint);
            setHint("");
        }else{
            hint = getTag().toString();
            setHint(hint);
        }
    }
}

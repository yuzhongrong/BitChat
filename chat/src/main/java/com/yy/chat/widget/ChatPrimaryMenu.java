package com.yy.chat.widget;

import android.content.Context;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.jakewharton.rxbinding2.widget.RxTextView;
import com.yy.chat.R;


/**
 *
 * @author yiyang
 */
public class ChatPrimaryMenu extends LinearLayout implements View.OnClickListener {
    private ImageView mIvPicture;
    private EditText mEtContent;
    private TextView mBtnSend;

    public ChatPrimaryMenu(Context context) {
        super(context);
        init(context);
    }

    public ChatPrimaryMenu(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    private void init(Context context) {
        LayoutInflater.from(context).inflate(R.layout.chat_paimary_menu, this);
        mIvPicture = (ImageView) findViewById(R.id.iv_picture);
        mIvPicture.setOnClickListener(this);
        mEtContent = (EditText) findViewById(R.id.et_content);
        mBtnSend = (TextView) findViewById(R.id.btn_send);
        mBtnSend.setOnClickListener(this);

        RxTextView.textChanges(mEtContent)
                .subscribe(s -> {
                    if (!TextUtils.isEmpty(s)) {
                        mBtnSend.setEnabled(true);
                    } else {
                        mBtnSend.setEnabled(false);
                    }
                });

    }

    public EditText getEditText(){
        return mEtContent;
    }

    @Override
    public void onClick(View v) {
        if(mListener==null)return;
        int id = v.getId();
        if(id == R.id.iv_picture){
            mListener.onSendPicClicked();
        }else if(id == R.id.btn_send){
            mListener.onSendBtnClicked(mEtContent.getText().toString().trim());
            mEtContent.setText("");
        }
    }


    private ChatPrimaryMenuListener mListener;

    public interface ChatPrimaryMenuListener{
        /**
         * when send button clicked
         * @param content
         */
        void onSendBtnClicked(String content);
        /**
         * 点击了发送图片按钮
         */
        void onSendPicClicked();

    }
    public void setChatPrimaryMenuListener(ChatPrimaryMenuListener listener){
        mListener = listener;
    }


}

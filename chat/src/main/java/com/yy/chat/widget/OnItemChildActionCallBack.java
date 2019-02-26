package com.yy.chat.widget;

import android.view.View;

import com.yy.chat.bean.ChatMessage;

/**
 *
 * @author yiyang
 */
public interface OnItemChildActionCallBack {
    void onResendClick(ChatMessage message);

    void onBubbleClick(ChatMessage message, int position,View view);

    void onUserAvatarClick(ChatMessage message);
    void onBubbleLongClick(ChatMessage message,View view);
    void onRootClick(ChatMessage message);
}

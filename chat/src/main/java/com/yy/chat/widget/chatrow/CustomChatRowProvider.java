package com.yy.chat.widget.chatrow;

/**
 *
 * @author yiyang
 */
public interface CustomChatRowProvider {
    /**
     * 根据给定message返回chat row
     * @return
     */
    ChatRow getCustomChatRow(int itemViewType);
}

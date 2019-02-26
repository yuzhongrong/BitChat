package com.yy.chat.bean;

import java.util.Comparator;

public class DateSort implements Comparator<ChatMessage> {
    @Override
    public int compare(ChatMessage o1, ChatMessage o2) {
        return (int) (o1.getTimeStamp() - o2.getTimeStamp());
    }
}

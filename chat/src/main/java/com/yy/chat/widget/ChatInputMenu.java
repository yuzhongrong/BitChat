package com.yy.chat.widget;

import android.content.Context;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.widget.FrameLayout;
import android.widget.LinearLayout;

import com.yy.chat.R;


/**
 *
 * @author yiyang
 */
public class ChatInputMenu extends LinearLayout {

    private FrameLayout primaryMenuContainer;
    private boolean inited;
    private LayoutInflater layoutInflater;
    private ChatPrimaryMenu chatPrimaryMenu;
    private Context mContext;

    public ChatInputMenu(Context context) {
        this(context, null);
    }

    public ChatInputMenu(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init(context);
    }

    private void init(Context context) {
        mContext = context;
        layoutInflater = LayoutInflater.from(context);
        layoutInflater.inflate(R.layout.chat_input_menu, this);
        primaryMenuContainer = (FrameLayout) findViewById(R.id.primary_menu_container);

        init();
    }

    private void init() {
        if(inited){
            return;
        }
        if(chatPrimaryMenu == null){
            chatPrimaryMenu = new ChatPrimaryMenu(mContext);
        }
        primaryMenuContainer.addView(chatPrimaryMenu);

        inited = true;
    }

    public void setChatPrimaryMenuListener(ChatPrimaryMenu.ChatPrimaryMenuListener listener){
        if(!inited)return;
        chatPrimaryMenu.setChatPrimaryMenuListener(listener);
    }

    public ChatPrimaryMenu getChatPrimaryMenu(){
        return chatPrimaryMenu;
    }
}

package com.yy.chat.widget.chatrow;

import android.content.Context;
import android.graphics.drawable.AnimationDrawable;
import android.support.annotation.LayoutRes;
import android.text.TextUtils;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.cjwsc.idcm.Utils.GlideUtil;
import com.orhanobut.logger.Logger;
import com.yy.chat.R;
import com.yy.chat.bean.ChatMessage;
import com.yy.chat.bean.Friend;
import com.yy.chat.bean.MemberInfo;
import com.yy.chat.db.DBManager;

import org.simple.eventbus.EventBus;


/**
 * @author yiyang
 */
public class ChatRowRedEnvelope extends ChatRow {


    private final int mSize;

    private ImageView progress_bar;
    private TextView mTvNick;
    private boolean mIsReceive;

    public ChatRowRedEnvelope(Context context, @LayoutRes int res) {
        super(context, res);
        mSize = mContext.getResources().getDimensionPixelSize(R.dimen.dp200);
        mIsReceive = res == R.layout.row_img_recv;
    }

    @Override
    protected void onFindViewById() {
        progress_bar = (ImageView) mView.findViewById(R.id.progress_bar);
        mTvNick = mView.findViewById(R.id.tv_userid);
    }

    @Override
    public void onSetUpView(ChatMessage chatMessage) {
        if (chatMessage.getType() == 1 && mIsReceive) {//群聊接收信息展示昵称
            mTvNick.setVisibility(VISIBLE);
            if (!TextUtils.isEmpty(chatMessage.getNickname())) {
                String nickName = chatMessage.getNickname();
                mTvNick.setText(nickName);
            } else {
                String nickName = "";
                MemberInfo memberInfo = DBManager.getMemberInfo(chatMessage.getUserid(), chatMessage.getFrom());
                if (memberInfo != null) {
                    if (!TextUtils.isEmpty(memberInfo.getMember_nick())) {
                        nickName = memberInfo.getMember_nick();
                    }
                }
                mTvNick.setText(nickName);
            }
        }
    }
}

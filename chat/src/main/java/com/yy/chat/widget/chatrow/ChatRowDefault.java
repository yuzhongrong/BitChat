package com.yy.chat.widget.chatrow;

import android.content.Context;
import android.support.annotation.LayoutRes;
import android.text.TextUtils;
import android.text.style.ImageSpan;
import android.widget.TextView;

import com.lqr.emoji.MoonUtils;
import com.yy.chat.R;
import com.yy.chat.bean.ChatMessage;
import com.yy.chat.bean.MemberInfo;
import com.yy.chat.db.DBManager;


/**
 * @author yiyang
 */
public class ChatRowDefault extends ChatRow {


    private TextView mTvContent;
    private TextView mTvNick;
    private boolean mIsReceive;

    public ChatRowDefault(Context context, @LayoutRes int res) {
        super(context, res);
        mIsReceive = res == R.layout.row_text_recv;
    }

    @Override
    protected void onFindViewById() {
        mTvContent = mView.findViewById(R.id.tv_chatcontent);
//        if (mIsReceive) {
        mTvNick = mView.findViewById(R.id.tv_userid);
//        }
    }

    @Override
    public void onSetUpView(ChatMessage chatMessage) {
        //  mTvContent.setText(textMessageBody.getText());
        if (chatMessage != null) {
            //可识别表情+文字
//            MoonUtils.identifyFaceExpression(mContext, mTvContent, chatMessage.getBody(), ImageSpan.ALIGN_BOTTOM);

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
}

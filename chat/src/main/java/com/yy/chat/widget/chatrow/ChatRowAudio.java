package com.yy.chat.widget.chatrow;

import android.content.Context;
import android.graphics.drawable.AnimationDrawable;
import android.support.annotation.LayoutRes;
import android.text.TextUtils;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.cjwsc.idcm.Utils.UIUtils;
import com.cjwsc.idcm.base.application.BaseApplication;
import com.yy.chat.R;
import com.yy.chat.bean.Friend;
import com.yy.chat.bean.MemberInfo;
import com.yy.chat.contasts.AppConst;
import com.yy.chat.bean.ChatMessage;
import com.yy.chat.db.DBManager;


/**
 * @author yiyang
 */
public class ChatRowAudio extends ChatRow {


    private RelativeLayout bubble;
    private TextView duration;
    private TextView mTvNick;
    private boolean mIsReceive;
    private TextView tv_poit;
    private ImageView imageView;

    public ChatRowAudio(Context context, @LayoutRes int res) {
        super(context, res);
        mIsReceive = res == R.layout.row_audio_recv;
    }

    @Override
    protected void onFindViewById() {
        imageView = mView.findViewById(R.id.tv_chatcontent);
        bubble = mView.findViewById(R.id.bubble);
        tv_poit = mView.findViewById(R.id.tv_poit);
        duration = mView.findViewById(R.id.duration);
//        if (mIsReceive) {
        mTvNick = mView.findViewById(R.id.tv_userid);
//        }

    }

    @Override
    public void onSetUpView(ChatMessage message) {
        int increment = (int) (UIUtils.getDisplayWidth() / 3 / AppConst.DEFAULT_MAX_AUDIO_RECORD_TIME_SECOND * message.getAudiotime());
        ViewGroup.LayoutParams params = bubble.getLayoutParams();
        int leng = UIUtils.dip2Px(BaseApplication.getContext().getResources().getDimensionPixelOffset(R.dimen.dp40)) + UIUtils.dip2Px(increment);
        params.width = UIUtils.px2dip(leng);
        bubble.setLayoutParams(params);
        duration.setText(message.getAudiotime() + "");

        if (position == mAdapter.getSelectedPosition()) {
            AnimationDrawable animation = (AnimationDrawable) imageView.getBackground();
            animation.start();
        } else {
            if (!message.isDirction())
                imageView.setBackground(getResources().getDrawable(R.drawable.audio_animation_left_list));
            else
                imageView.setBackground(getResources().getDrawable(R.drawable.audio_animation_right_list));
        }
        if (!message.isDirction())
            if (message.isPlay()) {
                tv_poit.setVisibility(INVISIBLE);
            } else {
                tv_poit.setVisibility(VISIBLE);
            }
        if (message.getType() == 1 && mIsReceive) {//群聊接收信息展示昵称
            mTvNick.setVisibility(VISIBLE);
            if (!TextUtils.isEmpty(message.getNickname())) {
                String nickName = message.getNickname();
                mTvNick.setText(nickName);
            } else {
                String nickName = "";
                MemberInfo memberInfo = DBManager.getMemberInfo(message.getUserid(), message.getFrom());
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

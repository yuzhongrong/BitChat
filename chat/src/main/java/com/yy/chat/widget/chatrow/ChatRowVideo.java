package com.yy.chat.widget.chatrow;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Matrix;
import android.graphics.drawable.AnimationDrawable;
import android.media.MediaMetadataRetriever;
import android.support.annotation.LayoutRes;
import android.text.TextUtils;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.cjwsc.idcm.Utils.GlideUtil;
import com.yy.chat.R;
import com.yy.chat.bean.ChatMessage;
import com.yy.chat.bean.Friend;
import com.yy.chat.bean.MemberInfo;
import com.yy.chat.db.DBManager;


/**
 * @author yiyang
 */
public class ChatRowVideo extends ChatRow {


    private final int mSize;
    private ImageView imageView;
    private ImageView progress_bar;
    private TextView mTvNick;

    public ChatRowVideo(Context context, @LayoutRes int res) {
        super(context, res);
        mSize = mContext.getResources().getDimensionPixelSize(R.dimen.dp200);
    }

    @Override
    protected void onFindViewById() {
        imageView = mView.findViewById(R.id.image);
        progress_bar = (ImageView) mView.findViewById(R.id.progress_bar);
//        if (mIsReceive) {
        mTvNick = mView.findViewById(R.id.tv_userid);
//        }
    }

    @Override
    public void onSetUpView(ChatMessage chatMessage) {


        String path = chatMessage.getBody();
        if (!TextUtils.isEmpty(chatMessage.getThumbImg())&&!chatMessage.isDirction()) {
            path = chatMessage.getThumbImg();
        }
        if (!path.equals(imageView.getTag(imageView.getId()))) {
            if (!chatMessage.isDirction()) {
                progress_bar.setVisibility(VISIBLE);
                AnimationDrawable animationDrawable = (AnimationDrawable) progress_bar.getDrawable();
                animationDrawable.start();
            }
            int width = mContext.getResources().getDimensionPixelOffset(R.dimen.dp150);
            int height = mContext.getResources().getDimensionPixelOffset(R.dimen.dp120);
            GlideUtil.loadImageViewContentWithSizeFixRatio(mContext, path, 4 / 3, 18 / 9, width, height, imageView, (RelativeLayout.LayoutParams) imageView.getLayoutParams(), new GlideUtil.back() {
                @Override
                public void success() {
                    progress_bar.setVisibility(GONE);
                }

                @Override
                public void failed() {
                    progress_bar.setVisibility(GONE);
                }
            });

            imageView.setTag(imageView.getId(), path);
        }
        if (chatMessage.getType() == 1 && !chatMessage.isDirction()) {//群聊接收信息展示昵称
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

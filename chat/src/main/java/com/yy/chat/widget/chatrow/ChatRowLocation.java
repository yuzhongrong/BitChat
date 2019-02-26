package com.yy.chat.widget.chatrow;

import android.content.Context;
import android.graphics.drawable.AnimationDrawable;
import android.support.annotation.LayoutRes;
import android.text.TextUtils;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.baidu.mapapi.map.BaiduMap;
import com.baidu.mapapi.map.MapStatus;
import com.baidu.mapapi.map.MapStatusUpdateFactory;
import com.baidu.mapapi.map.MapView;
import com.baidu.mapapi.map.MyLocationConfiguration;
import com.baidu.mapapi.map.MyLocationData;
import com.baidu.mapapi.map.UiSettings;
import com.baidu.mapapi.model.LatLng;
import com.cjwsc.idcm.Utils.GlideUtil;
import com.cjwsc.idcm.Utils.ToastUtil;
import com.makeramen.roundedimageview.RoundedImageView;
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
public class ChatRowLocation extends ChatRow {


    private final int mSize;
    private ImageView progress_bar;
    private TextView mTvNick;
    private RoundedImageView imageView;


    public ChatRowLocation(Context context, @LayoutRes int res) {
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
        String path=chatMessage.getImg();
        if(chatMessage.isDirction()){
            path=chatMessage.getThumbImg();
        }
        if (!path.equals(imageView.getTag(imageView.getId()))) {
            if (!chatMessage.isDirction()) {
                progress_bar.setVisibility(VISIBLE);
                AnimationDrawable animationDrawable = (AnimationDrawable) progress_bar.getDrawable();
                animationDrawable.start();
            }
            int width = mContext.getResources().getDimensionPixelOffset(R.dimen.dp200);
            int height = mContext.getResources().getDimensionPixelOffset(R.dimen.dp120);
            GlideUtil.loadImageViewContentWithSizeFixRatio(mContext, path, 18 / 9 / 1, 18 / 9, width, height, imageView, (RelativeLayout.LayoutParams) imageView.getLayoutParams(), new GlideUtil.back() {
                @Override
                public void success() {
                    progress_bar.setVisibility(GONE);
                    EventBus.getDefault().post(true, "ACTION_MESSAGE_REFRESH");
                }

                @Override
                public void failed() {
                    progress_bar.setVisibility(GONE);
                    EventBus.getDefault().post(true, "ACTION_MESSAGE_REFRESH");
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

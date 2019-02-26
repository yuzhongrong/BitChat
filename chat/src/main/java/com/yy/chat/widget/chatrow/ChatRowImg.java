package com.yy.chat.widget.chatrow;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.drawable.AnimationDrawable;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.os.RecoverySystem;
import android.support.annotation.LayoutRes;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.util.Log;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.DataSource;
import com.bumptech.glide.load.engine.GlideException;
import com.bumptech.glide.request.RequestListener;
import com.bumptech.glide.request.target.SimpleTarget;
import com.bumptech.glide.request.target.Target;
import com.bumptech.glide.request.transition.Transition;
import com.cjwsc.idcm.Utils.GlideApp;
import com.cjwsc.idcm.Utils.GlideUtil;
import com.cjwsc.idcm.Utils.UIUtils;
import com.orhanobut.logger.Logger;
import com.yy.chat.bean.ChatMessage;
import com.yy.chat.R;
import com.yy.chat.bean.Friend;
import com.yy.chat.bean.MemberInfo;
import com.yy.chat.db.DBManager;

import org.simple.eventbus.EventBus;

import static com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions.withCrossFade;


/**
 * @author yiyang
 */
public class ChatRowImg extends ChatRow {


    private final int mSize;
    private ImageView mTvContent;
    private ImageView imageView;
    private ImageView gifView;
    private ImageView progress_bar;
    private TextView mTvNick;
    private boolean mIsReceive;

    public ChatRowImg(Context context, @LayoutRes int res) {
        super(context, res);
        mSize = mContext.getResources().getDimensionPixelSize(R.dimen.dp200);
        mIsReceive = res == R.layout.row_img_recv;
    }

    @Override
    protected void onFindViewById() {
        imageView = mView.findViewById(R.id.image);
        progress_bar = (ImageView) mView.findViewById(R.id.progress_bar);
        gifView = mView.findViewById(R.id.image2);
//        if (mIsReceive) {
        mTvNick = mView.findViewById(R.id.tv_userid);
//        }
    }

    @Override
    public void onSetUpView(ChatMessage chatMessage) {


        String path = chatMessage.getBody();
        if (path.endsWith(".gif")) {
            gifView.setVisibility(VISIBLE);
            imageView.setVisibility(GONE);
            mTvContent = gifView;
            Logger.d("-----path=" + path);
        } else {
            gifView.setVisibility(GONE);
            imageView.setVisibility(VISIBLE);
            mTvContent = imageView;
        }
//        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
//            mTvContent.setTransitionName(chatMessage.getBody());
//        }

        if (!path.equals(mTvContent.getTag(mTvContent.getId()))) {
            if (!chatMessage.isDirction()) {
                progress_bar.setVisibility(VISIBLE);
                AnimationDrawable animationDrawable = (AnimationDrawable) progress_bar.getDrawable();
                animationDrawable.start();
            }
            int width = mContext.getResources().getDimensionPixelOffset(R.dimen.dp150);
            int height = mContext.getResources().getDimensionPixelOffset(R.dimen.dp120);
            GlideUtil.loadImageViewContentWithSizeFixRatio(mContext, path, 4 / 3, 18 / 9, width, height, mTvContent, (RelativeLayout.LayoutParams) mTvContent.getLayoutParams(), new GlideUtil.back() {
                @Override
                public void success() {
                    progress_bar.setVisibility(GONE);
                    EventBus.getDefault().post(true,"ACTION_MESSAGE_REFRESH");
                }

                @Override
                public void failed() {
                    progress_bar.setVisibility(GONE);
                    EventBus.getDefault().post(true,"ACTION_MESSAGE_REFRESH");
                }
            });
            mTvContent.setTag(mTvContent.getId(), path);
        }
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

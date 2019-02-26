package com.yy.chat.widget.chatrow;

import android.content.Context;
import android.graphics.drawable.AnimationDrawable;
import android.support.annotation.LayoutRes;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.cjwsc.idcm.Utils.GlideApp;
import com.cjwsc.idcm.Utils.LoginUtils;
import com.cjwsc.idcm.Utils.TimeUtils;
import com.cjwsc.idcm.base.application.BaseApplication;
import com.yy.chat.R;
import com.yy.chat.bean.ChatMessage;
import com.yy.chat.bean.Friend;
import com.yy.chat.db.DBManager;
import com.yy.chat.message.MessageListAdapter;

import org.litepal.crud.callback.UpdateOrDeleteCallback;


/**
 * @author yiyang
 */
public abstract class ChatRow extends LinearLayout {

    private LinearLayout ll_root;
    private ImageView mIvAvatar;
    private View mLayoutBubble;
    private View mMsgStatus;
    private ImageView mProgressBar;
    private AnimationDrawable animationDrawable;
    private View mLayoutLoding;
    private View mPrecentage;
    protected int position;
    protected MessageListAdapter mAdapter;

    public interface ChatRowActionCallback {
        void onRootClick(ChatMessage message);

        void onResendClick(ChatMessage message);

        void onBubbleClick(ChatMessage message);

        void onBubbleLongClick(ChatMessage message,View view);

        void onUserAvatarClick(ChatMessage message);
    }

    protected ChatRowActionCallback mCallback;

    //    protected  ChatMessage mMessage;
    protected final Context mContext;
    protected final View mView;
    protected ChatMessage mMessage;

    public ChatRow(Context context, @LayoutRes int res) {
        super(context);
        mContext = context;
        mView = LayoutInflater.from(mContext).inflate(res, this);
        mView.setLayoutParams(new RecyclerView.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT));

        initView();
//        mMessage = message;
//        mBody = (BODY) message.getBody();
    }

    private void initView() {
        ll_root = (LinearLayout) findViewById(R.id.ll_root);
        mIvAvatar = (ImageView) findViewById(R.id.iv_userhead);
        mLayoutBubble = findViewById(R.id.bubble);
        mMsgStatus = findViewById(R.id.msg_status);
        mProgressBar = findViewById(R.id.progress_bar);
        mPrecentage = findViewById(R.id.percentage);
        mLayoutLoding = findViewById(R.id.ll_loading);
        onFindViewById();
    }

    public void setUpView(ChatMessage message, int pos, MessageListAdapter adapter, ChatRowActionCallback callback) {
        mMessage = message;
        position = pos;
        mAdapter = adapter;

        mCallback = callback;
        setBaseView();
        onSetUpView(message);
    }

    private void setBaseView() {
        refreshStatus();

        TextView timestamp = (TextView) findViewById(R.id.timestamp);
        if (timestamp != null) {
            if (position == 0) {
                timestamp.setText(TimeUtils.getNewChatTime(mMessage.getTimeStamp()));
                timestamp.setVisibility(View.VISIBLE);
            } else {
                // 两条消息时间离得如果稍长，显示时间
                ChatMessage prevMessage = mAdapter.getItem(position - 1);
                if (prevMessage != null && isCloseEnough(mMessage.getTimeStamp(), prevMessage.getTimeStamp())) {
                    timestamp.setVisibility(View.GONE);
                } else {
                    timestamp.setText(TimeUtils.getNewChatTime(mMessage.getTimeStamp()));
                    timestamp.setVisibility(View.VISIBLE);
                }
            }
        }

        if (mIvAvatar != null && mContext != null) {
            if (mMessage.isDirction() && LoginUtils.getLoginBean() != null) {
                GlideApp.with(mContext).load(LoginUtils.getLoginBean(BaseApplication.getContext()).getAvatar()).into(mIvAvatar);
            } else {
                if (!TextUtils.isEmpty(mMessage.getHead())) {
                    GlideApp.with(mContext).load(mMessage.getHead()).into(mIvAvatar);
                } else {
                    GlideApp.with(mContext).load(R.drawable.em_default_avatar).into(mIvAvatar);
                }
            }

        }
        setListener();
    }

    public static boolean isCloseEnough(long var0, long var2) {
        long var4 = var0 - var2;
        if (var4 < 0L) {
            var4 = -var4;
        }
//3分钟
        return var4 < 3 * 60 * 1000L;
    }

    private void refreshStatus() {
        //消息状态
        int status = mMessage.getStatus();
        if (ChatMessage.Status.INPROGRESS.ordinal() == status) {
            if (mMsgStatus != null)
                mMsgStatus.setVisibility(GONE);
            if (mProgressBar != null) {
                animationDrawable = (AnimationDrawable) mProgressBar.getDrawable();
                mProgressBar.setVisibility(VISIBLE);
                if (animationDrawable != null) {
                    animationDrawable.start();
                }
            }
            if (mLayoutLoding != null)
                mLayoutLoding.setVisibility(VISIBLE);
            if (mPrecentage != null)
                mPrecentage.setVisibility(GONE);
        } else if (ChatMessage.Status.FAIL.ordinal() == status) {
            if (mMsgStatus != null)
                mMsgStatus.setVisibility(VISIBLE);
            if (mProgressBar != null) {
                mProgressBar.setVisibility(GONE);
                if (animationDrawable != null) {
                    animationDrawable.stop();
                }
            }

            if (mLayoutLoding != null)
                mLayoutLoding.setVisibility(GONE);
            if (mPrecentage != null)
                mPrecentage.setVisibility(GONE);
        } else {
            if (mMsgStatus != null)
                mMsgStatus.setVisibility(GONE);
            if (mProgressBar != null) {
                mProgressBar.setVisibility(GONE);
                if (animationDrawable != null) {
                    animationDrawable.stop();
                }
            }
            if (mLayoutLoding != null)
                mLayoutLoding.setVisibility(GONE);
            if (mPrecentage != null)
                mPrecentage.setVisibility(GONE);
        }
    }

    private void setListener() {
        if (mCallback == null) return;
        if (mIvAvatar != null) {
            mIvAvatar.setOnClickListener(new OnClickListener() {
                @Override
                public void onClick(View v) {
                    mCallback.onUserAvatarClick(mMessage);
                }
            });
        }
        if (mMsgStatus != null) {
            mMsgStatus.setOnClickListener(new OnClickListener() {
                @Override
                public void onClick(View v) {
                    mCallback.onResendClick(mMessage);
                }
            });
        }
        if (mLayoutBubble != null) {
            mLayoutBubble.setOnClickListener(new OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (mMessage.getMsgType() == ChatMessage.Type.VOICE.ordinal()) {
                        if (!mMessage.isDirction())
                            DBManager.update(mMessage, new UpdateOrDeleteCallback() {
                                @Override
                                public void onFinish(int i) {
                                    findViewById(R.id.tv_poit).setVisibility(GONE);
                                }
                            });
                    }
                    mCallback.onBubbleClick(mMessage);
                }
            });
        }
        if (mLayoutBubble != null) {
            mLayoutBubble.setOnLongClickListener(new OnLongClickListener() {
                @Override
                public boolean onLongClick(View v) {
                    mCallback.onBubbleLongClick(mMessage,mLayoutBubble);
                    return false;
                }
            });
        }
        if (ll_root != null) {
            ll_root.setOnClickListener(new OnClickListener() {
                @Override
                public void onClick(View v) {
                    mCallback.onRootClick(mMessage);
                }
            });
        }
    }

    public void onViewUpdate() {
        refreshStatus();
    }

    protected abstract void onFindViewById();


    public abstract void onSetUpView(ChatMessage message);

}

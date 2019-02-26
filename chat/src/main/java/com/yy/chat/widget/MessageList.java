package com.yy.chat.widget;

import android.content.Context;
import android.os.Build;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SimpleItemAnimator;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;


import com.orhanobut.logger.Logger;
import com.yy.chat.bean.ChatMessage;
import com.yy.chat.bean.DateSort;
import com.yy.chat.message.MessageListAdapter;
import com.yy.chat.message.OnMessageChangeListener;
import com.yy.chat.R;
import com.yy.chat.widget.chatrow.CustomChatRowProvider;

/**
 * @author yiyang
 */
public class MessageList extends FrameLayout implements OnMessageChangeListener {
    private RecyclerView mRecyclerView;
    private List<ChatMessage> mMessages;
    private LinearLayoutManager mLayoutManager;
    private MessageListAdapter mAdapter;
    private Context mContext;
    private boolean move = true;

    public MessageList(@NonNull Context context) {
        this(context, null);
    }

    public MessageList(@NonNull Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init(context);
    }

    public RecyclerView getRecycleView() {
        return mRecyclerView;
    }

    public boolean isMove() {
        return move;
    }

    public void setMove(boolean move) {
        this.move = move;
    }

    private void init(Context context) {

        this.mContext = context;
        LayoutInflater.from(context).inflate(R.layout.chat_message_list, this);
        mRecyclerView = (RecyclerView) findViewById(R.id.recycler_view);
        if (mRecyclerView != null) {
            RecyclerView.ItemAnimator animator = mRecyclerView.getItemAnimator();
            if (animator instanceof SimpleItemAnimator) {
                ((SimpleItemAnimator) animator).setSupportsChangeAnimations(false);
            }
        }
        mMessages = new ArrayList<>();
        mAdapter = new MessageListAdapter(mMessages);
        mAdapter.setHasStableIds(true);
        mLayoutManager = new LinearLayoutManager(context);
        mRecyclerView.setLayoutManager(mLayoutManager);
        mRecyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {

            int lastPosition = -1;

            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);

            }

            @Override
            public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);
                if (newState == RecyclerView.SCROLL_STATE_IDLE) {
                    RecyclerView.LayoutManager layoutManager = recyclerView.getLayoutManager();
                    if (layoutManager instanceof LinearLayoutManager) {
                        lastPosition = ((LinearLayoutManager) layoutManager).findLastVisibleItemPosition();
                    }

                    //时判断界面显示的最后item的position是否等于itemCount总数-1也就是最后一个item的position
                    //如果相等则说明已经滑动到最后了
                    if (lastPosition == recyclerView.getLayoutManager().getItemCount() - 1) {
                        move = true;
                    } else {
                        move = false;
                    }

                } else {
                    move = false;
                }

            }
        });
        mAdapter.bindToRecyclerView(mRecyclerView);
    }

    public void setChatRowProvider(CustomChatRowProvider chatRowProvider) {
        mAdapter.setChatRowProvider(chatRowProvider);
    }


    public void setData(List<ChatMessage> messages) {
        mMessages.clear();
        mMessages.addAll(messages);
        Collections.sort(mMessages, new DateSort());
        mAdapter.notifyDataSetChanged();
        refreshSelectLast();
    }

    public void addData(ChatMessage message) {
        for (ChatMessage chatMessage : mMessages) {
            if (message.getMsgid()!=null&&message.getMsgid().equals(chatMessage.getMsgid()))
                return;
        }
        mMessages.add(message);
        Collections.sort(mMessages, new DateSort());
        mAdapter.notifyDataSetChanged();
//        mRecyclerView.postDelayed(new Runnable() {
//            @Override
//            public void run() {
//                refreshSelectLast();
//            }
//        }, 50);
//        if(message!=null){
//            message.handleMsg(mContext, this);
//        }
    }


    public void addFristData(List<ChatMessage> datas) {
        mMessages.addAll(0, datas);
        Collections.sort(mMessages, new DateSort());
        mAdapter.notifyDataSetChanged();
//        mRecyclerView.postDelayed(new Runnable() {
//            @Override
//            public void run() {
//                refreshSelectLast();
//
//            }
//        }, 50);
    }

    public void addData(List<ChatMessage> messages) {
        mMessages.addAll(messages);
        Collections.sort(mMessages, new DateSort());
        mAdapter.notifyDataSetChanged();
        mRecyclerView.postDelayed(new Runnable() {
            @Override
            public void run() {
                refreshSelectLast();

            }
        }, 50);
    }

    public List<ChatMessage> getData() {
        return mAdapter.getData();
    }

    public MessageListAdapter getAdapter() {
        return mAdapter;
    }

    @Override
    public void onChanged() {
        mAdapter.notifyDataSetChanged();
    }

    public void refreshSelectLast() {
        refreshSelectLast(false);
    }


    public void refreshTop() {
        refreshSelectTop(false);
    }


    public void refreshSelectTop(boolean isSmooth) {
        if (mMessages.size() < 1)
            return;
        if (isSmooth)
            mRecyclerView.smoothScrollToPosition(0);
        else
            mLayoutManager.scrollToPositionWithOffset(0, 0);
    }


    public void refreshSelectLast(boolean isSmooth) {
        if (mMessages.size() < 1)
            return;
        if (isSmooth) {
            mRecyclerView.smoothScrollToPosition(mMessages.size() - 1);
        } else
            mLayoutManager.scrollToPositionWithOffset(mMessages.size() - 1, 0);
    }


    public void setItemClickListener(OnItemChildActionCallBack callback) {
        mAdapter.setOnItemChildActionCallBack(callback);
    }

    @Override
    protected void onDetachedFromWindow() {
        super.onDetachedFromWindow();
    }

    public void clearAll() {

        if (mMessages != null && mMessages.size() > 0) {
            mMessages.clear();
            mAdapter.notifyDataSetChanged();

        }

    }

}

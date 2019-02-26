package com.yy.chat.message;

import android.support.annotation.Nullable;
import android.view.View;
import android.view.ViewGroup;

import com.chad.library.adapter.base.BaseMultiItemQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.orhanobut.logger.Logger;
import com.yy.chat.R;
import com.yy.chat.bean.ChatMessage;
import com.yy.chat.widget.OnItemChildActionCallBack;
import com.yy.chat.widget.chatrow.ChatRow;
import com.yy.chat.widget.chatrow.ChatRowAudio;
import com.yy.chat.widget.chatrow.ChatRowDefault;
import com.yy.chat.widget.chatrow.ChatRowHint;
import com.yy.chat.widget.chatrow.ChatRowImg;
import com.yy.chat.widget.chatrow.ChatRowLocation;
import com.yy.chat.widget.chatrow.ChatRowRedEnvelope;
import com.yy.chat.widget.chatrow.ChatRowText;
import com.yy.chat.widget.chatrow.ChatRowTransferAccounts;
import com.yy.chat.widget.chatrow.ChatRowVideo;
import com.yy.chat.widget.chatrow.CustomChatRowProvider;

import java.util.List;


/**
 * @author yiyang
 */
public class MessageListAdapter extends BaseMultiItemQuickAdapter<ChatMessage, MessageListAdapter.MessageHolder> {
    private static final int MESSAGE_TYPE_RECV_TXT = 0;
    private static final int MESSAGE_TYPE_SENT_TXT = 1;
    private static final int MESSAGE_TYPE_SENT_IMAGE = 2;
    private static final int MESSAGE_TYPE_RECV_IMAGE = 3;
    private static final int MESSAGE_TYPE_SENT_VOICE = 4;
    private static final int MESSAGE_TYPE_RECV_VOICE = 5;
    private static final int MESSAGE_TYPE_SENT_LOCATION = 6;
    private static final int MESSAGE_TYPE_RECV_LOCATION = 7;
    private static final int MESSAGE_TYPE_SENT_VIDEO = 8;
    private static final int MESSAGE_TYPE_RECV_VIDEO = 9;
    private static final int MESSAGE_TYPE_SENT_FILE = 10;
    private static final int MESSAGE_TYPE_RECV_FILE = 11;
    private static final int MESSAGE_TYPE_HINT = 12;
    private static final int MESSAGE_TYPE_SENT_REDENVELOPE = 13;
    private static final int MESSAGE_TYPE_RECV_REDENVELOPE = 14;
    private static final int MESSAGE_TYPE_SENT_GROUPINVITAINO = 15;
    private static final int MESSAGE_TYPE_RECV_GROUPINVITAINO = 16;
    private static final int MESSAGE_TYPE_SENT_TRANSFER_ACCOUNTS = 17;
    private static final int MESSAGE_TYPE_RECV_TRANSFER_ACCOUNTS = 18;
    private static final int MESSAGE_TYPE_DEFAULT = 19;
    private CustomChatRowProvider mChatRowProvider;
    private int selectedPosition = -5;

    public int getSelectedPosition() {
        return selectedPosition;
    }

    public void setSelectedPosition(int selectedPosition) {
        this.selectedPosition = selectedPosition;
    }

    public void setChatRowProvider(CustomChatRowProvider chatRowProvider) {
        mChatRowProvider = chatRowProvider;
    }

    @Override
    public long getItemId(int position) {
        return super.getItemId(position);
    }

    public MessageListAdapter(@Nullable List<ChatMessage> data) {
        super(data);
    }

    @Override
    protected int getDefItemViewType(int position) {
        ChatMessage chatMessage = getItem(position);
        if (chatMessage == null) {
            return MESSAGE_TYPE_DEFAULT;
        }
        if (chatMessage.getMsgType() == ChatMessage.Type.TEXT.ordinal()) {
            return chatMessage.isDirction() ? MESSAGE_TYPE_SENT_TXT : MESSAGE_TYPE_RECV_TXT;
        }
        if (chatMessage.getMsgType() == ChatMessage.Type.IMAGE.ordinal()) {
            return chatMessage.isDirction() ? MESSAGE_TYPE_SENT_IMAGE : MESSAGE_TYPE_RECV_IMAGE;
        }
        if (chatMessage.getMsgType() == ChatMessage.Type.VOICE.ordinal()) {
            return chatMessage.isDirction() ? MESSAGE_TYPE_SENT_VOICE : MESSAGE_TYPE_RECV_VOICE;
        }
        if (chatMessage.getMsgType() == ChatMessage.Type.HINT.ordinal()) {
            return MESSAGE_TYPE_HINT;
        }
        if (chatMessage.getMsgType() == ChatMessage.Type.VIDEO.ordinal()) {
            return chatMessage.isDirction() ? MESSAGE_TYPE_SENT_VIDEO : MESSAGE_TYPE_RECV_VIDEO;
        }
        if (chatMessage.getMsgType() == ChatMessage.Type.LOCATION.ordinal()) {
            return chatMessage.isDirction() ? MESSAGE_TYPE_SENT_LOCATION : MESSAGE_TYPE_RECV_LOCATION;
        }
        if (chatMessage.getMsgType() == ChatMessage.Type.RED_ENVELOPE.ordinal()) {
            return chatMessage.isDirction() ? MESSAGE_TYPE_SENT_REDENVELOPE : MESSAGE_TYPE_RECV_REDENVELOPE;
        }
        if (chatMessage.getMsgType() == ChatMessage.Type.GROUP_INVITATION.ordinal()) {
            return chatMessage.isDirction() ? MESSAGE_TYPE_SENT_GROUPINVITAINO : MESSAGE_TYPE_RECV_GROUPINVITAINO;
        }
        if (chatMessage.getMsgType() == ChatMessage.Type.TRANSFER_ACCOUNTS.ordinal()) {
            return chatMessage.isDirction() ? MESSAGE_TYPE_SENT_TRANSFER_ACCOUNTS : MESSAGE_TYPE_RECV_TRANSFER_ACCOUNTS;
        }
        return MESSAGE_TYPE_DEFAULT;
    }

    @Nullable
    @Override
    public ChatMessage getItem(int position) {

        return super.getItem(position);
    }

    @Override
    protected MessageHolder onCreateDefViewHolder(ViewGroup parent, int viewType) {
        ChatRow chatRow = null;
        switch (viewType) {
            case MESSAGE_TYPE_SENT_GROUPINVITAINO:
            case MESSAGE_TYPE_SENT_TXT:
                chatRow = new ChatRowText(mContext, R.layout.row_text_send);
                break;
            case MESSAGE_TYPE_RECV_GROUPINVITAINO:
            case MESSAGE_TYPE_RECV_TXT:
                chatRow = new ChatRowText(mContext, R.layout.row_text_recv);
                break;
            case MESSAGE_TYPE_SENT_IMAGE:
                chatRow = new ChatRowImg(mContext, R.layout.row_img_send);
                break;
            case MESSAGE_TYPE_RECV_IMAGE:
                chatRow = new ChatRowImg(mContext, R.layout.row_img_recv);
                break;
            case MESSAGE_TYPE_SENT_VOICE:
                chatRow = new ChatRowAudio(mContext, R.layout.row_audio_send);
                break;
            case MESSAGE_TYPE_RECV_VOICE:
                chatRow = new ChatRowAudio(mContext, R.layout.row_audio_recv);
                break;
            case MESSAGE_TYPE_HINT:
                chatRow = new ChatRowHint(mContext, R.layout.row_hint);
                break;
            case MESSAGE_TYPE_SENT_VIDEO:
                chatRow = new ChatRowVideo(mContext, R.layout.row_video_send);
                break;
            case MESSAGE_TYPE_RECV_VIDEO:
                chatRow = new ChatRowVideo(mContext, R.layout.row_video_recv);
                break;
            case MESSAGE_TYPE_SENT_LOCATION:
                chatRow = new ChatRowLocation(mContext, R.layout.row_location_send);
                break;
            case MESSAGE_TYPE_RECV_LOCATION:
                chatRow = new ChatRowLocation(mContext, R.layout.row_location_recv);
                break;
            case MESSAGE_TYPE_SENT_REDENVELOPE:
                chatRow = new ChatRowRedEnvelope(mContext, R.layout.row_redenvelope_send);
                break;
            case MESSAGE_TYPE_RECV_REDENVELOPE:
                chatRow = new ChatRowRedEnvelope(mContext, R.layout.row_redenvelope_recv);
                break;
            case MESSAGE_TYPE_DEFAULT:
                chatRow = new ChatRowDefault(mContext, R.layout.row_default_recv);
                break;
            case MESSAGE_TYPE_SENT_TRANSFER_ACCOUNTS:
                chatRow = new ChatRowTransferAccounts(mContext, R.layout.row_transfer_accounts_send);
                break;
            case MESSAGE_TYPE_RECV_TRANSFER_ACCOUNTS:
                chatRow = new ChatRowTransferAccounts(mContext, R.layout.row_transfer_accounts_recv);
                break;

        }
        if (chatRow == null) {
            return null;
        }
        return new MessageHolder(chatRow);
    }

    class MessageHolder extends BaseViewHolder {

        private ChatMessage mMessage;

        MessageHolder(ChatRow view) {
            super(view);
        }

        void setUpView(ChatMessage message, ChatRow.ChatRowActionCallback callback) {
            mMessage = message;
            ViewGroup.MarginLayoutParams layoutParams = (ViewGroup.MarginLayoutParams) itemView.getLayoutParams();
            if (getAdapterPosition() == getData().size() - 1) {
                int paddingBottom = mContext.getResources().getDimensionPixelSize(R.dimen.dp10);
                if (layoutParams.bottomMargin != paddingBottom) {
                    layoutParams.bottomMargin = paddingBottom;
                    itemView.setLayoutParams(layoutParams);
                }
            } else {
                if (layoutParams.bottomMargin != 0) {
                    layoutParams.bottomMargin = 0;
                    itemView.setLayoutParams(layoutParams);
                }
            }
            getChatRow().setUpView(message, getAdapterPosition(), MessageListAdapter.this, callback);
            handleMessage();
        }

        private void handleMessage() {
            int status = mMessage.getStatus();
            if (status == ChatMessage.Status.CREATE.ordinal()) {
                if (status == ChatMessage.Status.INPROGRESS.ordinal()) return;
                mMessage.handleMsg(mContext, mMessage, MessageListAdapter.this);
            }
        }

        protected ChatRow getChatRow() {
            return (ChatRow) itemView;
        }
    }

    private OnItemChildActionCallBack mOnItemChildActionCallBack;

    public void setOnItemChildActionCallBack(OnItemChildActionCallBack callback) {
        mOnItemChildActionCallBack = callback;
    }

    @Override
    protected void convert(MessageHolder helper, ChatMessage item) {
        helper.setUpView(item, new ChatRow.ChatRowActionCallback() {
            @Override
            public void onRootClick(ChatMessage message) {
                if (mOnItemChildActionCallBack != null)
                    mOnItemChildActionCallBack.onRootClick(message);
            }

            @Override
            public void onResendClick(ChatMessage message) {
                if (mOnItemChildActionCallBack != null)
                    mOnItemChildActionCallBack.onResendClick(message);
            }

            @Override
            public void onBubbleClick(ChatMessage message) {
                if (mOnItemChildActionCallBack != null)
                    mOnItemChildActionCallBack.onBubbleClick(message, helper.getAdapterPosition(), helper.itemView);
            }

            @Override
            public void onBubbleLongClick(ChatMessage message, View view) {
                if (mOnItemChildActionCallBack != null)
                    mOnItemChildActionCallBack.onBubbleLongClick(message, view);
            }

            @Override
            public void onUserAvatarClick(ChatMessage message) {
                if (mOnItemChildActionCallBack != null)
                    mOnItemChildActionCallBack.onUserAvatarClick(message);
            }
        });
    }
}

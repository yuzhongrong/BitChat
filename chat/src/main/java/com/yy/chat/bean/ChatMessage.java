package com.yy.chat.bean;

import android.content.ContentResolver;
import android.content.Context;
import android.text.TextUtils;

import com.chad.library.adapter.base.entity.MultiItemEntity;
import com.vector.update_app.utils.DrawableUtil;
import com.yy.chat.R;
import com.yy.chat.db.DBManager;
import com.yy.chat.message.MessageListAdapter;
import com.yy.chat.utils.DrawableUtils;

import org.litepal.annotation.Column;
import org.litepal.crud.LitePalSupport;
import org.litepal.crud.callback.UpdateOrDeleteCallback;
import org.simple.eventbus.EventBus;

import java.io.Serializable;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

/**
 * @author yiyang
 */

public class ChatMessage extends LitePalSupport implements MultiItemEntity, Serializable {
    private int id;
    private String msgid;
    private String uid;
    private String fromID;//来源
    private String body;//消息体
    private String img;//暂存网络大图
    private String thumbImg;//网络小图
    private int status;
    private int type;//消息类型
    private int audiotime;
    private long timeStamp;
    private int msgType;
    private boolean dirction;
    private String userid;
    private int noRead;//未读数量
    private boolean isPlay;
    private boolean isOrig; //原图发送
    private String url;
    private String head;
    private String nickname;

    public ChatMessage() {

    }

    public ChatMessage(String body, Type type) {
        this.body = body;
        this.msgType = type.ordinal();
        this.timeStamp = System.currentTimeMillis();
        this.dirction = true;
    }

    public ChatMessage(String body, boolean isOrig) {
        this.body = body;
        this.msgType = Type.IMAGE.ordinal();
        this.isOrig = isOrig;
        this.timeStamp = System.currentTimeMillis();
        this.dirction = true;
    }

    public ChatMessage(String body, int audiotime, Type type) {
        this.audiotime = audiotime;
        this.body = body;
        this.msgType = type.ordinal();
        this.timeStamp = System.currentTimeMillis();
        this.dirction = true;
    }

    public String getUserid() {
        return userid;
    }

    public void setUserid(String userid) {
        this.userid = userid;
    }




    public String getHead() {

        try{
            Friend friend = null;
            if (type == 0) {
                friend = DBManager.getFriendsById(uid, fromID);
            } else {
                if (!TextUtils.isEmpty(userid))
                    friend = DBManager.getFriendsById(uid, userid);
            }
            if (friend != null) {
                head = friend.getPortraitUri();
            }

        }catch (Exception e){
            //调试没有数据的时候
            head= DrawableUtils.getResourcesUri(R.drawable.em_default_avatar);

        }

        return head;
    }

    public void setHead(String head) {
        this.head = head;
    }

    public String getNickname() {
        Friend friend = null;
        if (type == 0) {
            friend = DBManager.getFriendsById(uid, fromID);
        } else {
            if (!TextUtils.isEmpty(userid))
                friend = DBManager.getFriendsById(uid, userid);
            if (friend != null && friend.getName().equals(friend.getRemarck())) {
                MemberInfo memberInfo = DBManager.getMemberInfo(userid, fromID);
                if (memberInfo != null) {
                    if (!TextUtils.isEmpty(memberInfo.getMember_nick())) {
                        return memberInfo.getMember_nick();
                    }
                }
            }
        }
        if (friend != null) {
            nickname = friend.getRemarck();
        }
        return nickname;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    public int getNoRead() {
        noRead = DBManager.getNoRead(uid, fromID);
        return noRead;
    }

    public String getImg() {
        return img;
    }

    public void setImg(String img) {
        this.img = img;
    }

    public boolean isOrig() {
        return isOrig;
    }

    public void setOrig(boolean orig) {
        isOrig = orig;
    }

    public String getThumbImg() {
        return thumbImg;
    }

    public void setThumbImg(String thumbImg) {
        this.thumbImg = thumbImg;
    }

    public void setNoRead(int noRead) {
        this.noRead = noRead;
    }

    public boolean isPlay() {
        return isPlay;
    }

    public void setPlay(boolean play) {
        isPlay = play;
    }


    public String getUid() {
        return uid;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }

    public String getFrom() {
        return fromID;
    }

    public void setFrom(String from) {
        this.fromID = from;
    }

    public String getBody() {
        return body;
    }

    public void setBody(String body) {
        this.body = body;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getMsgid() {
        return msgid;
    }

    public void setMsgid(String msgid) {
        this.msgid = msgid;
    }

    public HandleThreadMessage getHandlerMessage() {
        return handlerMessage;
    }


    public boolean isDirction() {
        return dirction;
    }

    public void setDirction(boolean dirction) {
        this.dirction = dirction;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public int getAudiotime() {
        return audiotime;
    }

    public void setAudiotime(int audiotime) {
        this.audiotime = audiotime;
    }

    public long getTimeStamp() {
        return timeStamp;
    }

    public void setTimeStamp(long timeStamp) {
        this.timeStamp = timeStamp;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public int getStatus() {
        return status;
    }

    public int getMsgType() {
        return msgType;
    }

    public void setMsgType(int msgType) {
        this.msgType = msgType;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    @Override
    public int getItemType() {
        return msgType;
    }

    public void handleMsg(Context context, ChatMessage chatMessage, MessageListAdapter messageListAdapter) {
        if (!isDirction())//修改接收消息方向问题
            return;
        if (handlerMessage == null) {
            return;
        }
        setStatus(Status.INPROGRESS.ordinal());
        SendMsgCallBack sendMsgCallBack = new SendMsgCallBack() {
            @Override
            public void onSuccess() {
                setStatus(Status.SUCCESS.ordinal());
                sendEvent();
                messageListAdapter.notifyDataSetChanged();
            }

            @Override
            public void onFailed() {
                setStatus(Status.FAIL.ordinal());
                sendEvent();
                messageListAdapter.notifyDataSetChanged();
            }
        };
        sendMsg(context, chatMessage, sendMsgCallBack);
    }

    public interface SendMsgCallBack {
        void onSuccess();

        void onFailed();
    }

    public void sendEvent() {
        DBManager.update(this, new UpdateOrDeleteCallback() {
            @Override
            public void onFinish(int i) {
                EventBus.getDefault().post(this);
            }
        });
    }

    //发送文字消息
    private void sendMsg(Context context, ChatMessage chatMessage, final SendMsgCallBack callBack) {
        /**不同人做的事情可能不同所以这里要用抽象**/
        handlerMessage.handlerMessage(context, chatMessage, callBack);

    }

    /**
     * 不同客户不同处理，成功了调用
     **/

    private HandleThreadMessage handlerMessage;

    public ChatMessage setHandlerMessage(HandleThreadMessage handlerMessage) {
        this.handlerMessage = handlerMessage;
        return this;
    }

    public interface HandleThreadMessage {
        void handlerMessage(Context context, ChatMessage chatMessage, final SendMsgCallBack callBack);
    }

    public enum Type {
        TEXT, IMAGE, VOICE, HINT, LOCATION, VIDEO, FILE, FRIEND_ADD, FRIEND_AGREE, RED_ENVELOPE, GROUP_INVITATION, TRANSFER_ACCOUNTS
    }

    public enum Status {
        CREATE, INPROGRESS, SUCCESS, FAIL, NOREAD, VOICE
    }

}

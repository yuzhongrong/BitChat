package com.yy.chat.bean;

import android.text.TextUtils;

import com.cjwsc.idcm.Utils.pwd.PwdCheckUtil;
import com.yy.chat.utils.PinyinUtils;

import org.litepal.crud.LitePalSupport;

import java.io.Serializable;

/**
 * @创建者 CSDN_LQR
 * @描述 朋友表(用户信息表)
 */
public class Session extends LitePalSupport implements Serializable {

    private String sessionid;
    private String newmessage;
    private long updatetime;
    private int unreadmsgcount;
    private int type;


    public Session(String sessionid,int type, String newmessage, long updatetime) {
        this.sessionid = sessionid;
        this.newmessage = newmessage;
        this.updatetime = updatetime;
    }



    public String getSessionid() {
        return sessionid;
    }

    public Session setSessionid(String sessionid) {
        this.sessionid = sessionid;
        return this;
    }

    public String getNewmessage() {
        return newmessage;
    }

    public Session setNewmessage(String newmessage) {
        this.newmessage = newmessage;
        return this;
    }

    public long getUpdatetime() {
        return updatetime;
    }

    public Session setUpdatetime(long updatetime) {

        this.updatetime = updatetime;
        return this;
    }

    public int getUnreadmsgcount() {
        return unreadmsgcount;
    }

    public Session setUnreadmsgcount(int unreadmsgcount) {

        this.unreadmsgcount = unreadmsgcount;
        return this;
    }

    public int getType() {
        return type;
    }

    public Session setType(int type) {
        this.type = type;
        return this;
    }

}
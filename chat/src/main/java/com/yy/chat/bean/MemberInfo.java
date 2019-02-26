package com.yy.chat.bean;

import com.cjwsc.idcm.Utils.LoginUtils;
import com.cjwsc.idcm.base.application.BaseApplication;

import org.litepal.crud.LitePalSupport;

import java.io.Serializable;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;

/**
 * 群组成员信息
 */
public class MemberInfo extends LitePalSupport implements Serializable {

      private int member_id; //成员自增id
    private String member_JID; //成员ID
    private String member_nick; //成员所在群的群昵称
    private int member_position; //成员所在群的群职位(0.群主 1.管理员 2.群成员)
    private String member_joinDate; //成员所在群加群日期
    private String group_id; //群组编号
    private String group_nick; //群组名称
    private String userId = LoginUtils.getLoginBean(BaseApplication.getContext()).getMobile();

    public String getUserId() {
        return userId;
    }

    public int getMember_id() {
        return member_id;
    }

    public void setMember_id(int member_id) {
        this.member_id = member_id;
    }

    public String getMember_JID() {
        return member_JID;
    }

    public void setMember_JID(String member_JID) {
        this.member_JID = member_JID;
    }

    public String getMember_nick() {
//        try {
//            member_nick=URLDecoder.decode(member_nick,"UTF-8");
//        } catch (UnsupportedEncodingException e) {
//            e.printStackTrace();
//        }
        return member_nick;
    }

    public void setMember_nick(String member_nick) {
        this.member_nick = member_nick;
    }

    public int getMember_position() {
        return member_position;
    }

    public void setMember_position(int member_position) {
        this.member_position = member_position;
    }

    public String getMember_joinDate() {
        return member_joinDate;
    }

    public void setMember_joinDate(String member_joinDate) {
        this.member_joinDate = member_joinDate;
    }

    public String getGroup_id() {
        return group_id;
    }

    public void setGroup_id(String group_id) {
        this.group_id = group_id;
    }

    public String getGroup_nick() {
        return group_nick;
    }

    public void setGroup_nick(String group_nick) {
        this.group_nick = group_nick;
    }

}

package com.yy.chat.bean;

import android.os.Build;
import android.support.annotation.RequiresApi;

import com.cjwsc.idcm.Utils.LoginUtils;
import com.cjwsc.idcm.base.application.BaseApplication;
import com.yy.chat.utils.PinyinUtils;

import org.litepal.crud.LitePalSupport;

import java.io.Serializable;
import java.util.Objects;

/**
 * 群信息
 */
public class GroupInfo extends LitePalSupport implements Serializable{
    private int id;//群组自增id
    private String group_id;//群组编号
    private String group_createJID;//创建人JID
    private String group_createDate;//群组创建时间
    private String group_nick;//群名
    private String groupNameSpelling;//群名对应的拼音
    private String group_photo;//群头像url
    private String group_act;//群公告
    private int groupMemberCount;//群成员个数
    private MemberInfo[] listGroupMembers;//群成员信息数组
    private String userId = LoginUtils.getLoginBean(BaseApplication.getContext()).getMobile();

    public String getUserId() {
        return userId;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getGroup_id() {
        return group_id;
    }

    public void setGroup_id(String group_id) {
        this.group_id = group_id;
    }

    public String getGroup_createJID() {
        return group_createJID;
    }

    public void setGroup_createJID(String group_createJID) {
        this.group_createJID = group_createJID;
    }

    public String getGroup_createDate() {
        return group_createDate;
    }

    public void setGroup_createDate(String group_createDate) {
        this.group_createDate = group_createDate;
    }

    public String getGroup_nick() {
        return group_nick;
    }

    public void setGroup_nick(String group_nick) {
        setGroupNameSpelling(PinyinUtils.getPinyin(group_nick));
        this.group_nick = group_nick;
    }

    public String getGroup_photo() {
        return group_photo;
    }

    public void setGroup_photo(String group_photo) {
        this.group_photo = group_photo;
    }

    public String getGroup_act() {
        return group_act;
    }

    public void setGroup_act(String group_act) {
        this.group_act = group_act;
    }

    public MemberInfo[] getListGroupMembers() {
        return listGroupMembers;
    }

    public void setListGroupMembers(MemberInfo[] listGroupMembers) {
        this.listGroupMembers = listGroupMembers;
    }

    public int getGroupMemberCount() {
        return groupMemberCount;
    }

    public void setGroupMemberCount(int groupMemberCount) {
        this.groupMemberCount = groupMemberCount;
    }

    public String getGroupNameSpelling() {
        return groupNameSpelling;
    }

    public void setGroupNameSpelling(String groupNameSpelling) {
        this.groupNameSpelling = groupNameSpelling;
    }

    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        GroupInfo groupInfo = (GroupInfo) o;
        return Objects.equals(group_id, groupInfo.group_id);
    }

    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    @Override
    public int hashCode() {

        return Objects.hash(group_id);
    }
}

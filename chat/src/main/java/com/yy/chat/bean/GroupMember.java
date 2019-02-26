package com.yy.chat.bean;

import com.yy.chat.db.DBManager;

import org.litepal.crud.LitePalSupport;

/**
 * @创建者 CSDN_LQR
 * @描述 群成员
 */
public class GroupMember extends LitePalSupport {

    private String userId;
    private String groupId;
    private String groupName;
    private String groupNameSpelling;
    private String groupPortrait;
    private Friend friend;
    private GroupInfo groupInfo;

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getGroupId() {
        return groupId;
    }

    public void setGroupId(String groupId) {
        this.groupId = groupId;
    }

    public Friend getFriend() {
        this.friend = DBManager.getFriendsById(userId);
        return friend;
    }

    public void setFriend(Friend friend) {
        this.friend = friend;
    }

    public String getGroupName() {
        return groupName;
    }

    public void setGroupName(String groupName) {
        this.groupName = groupName;
    }

    public String getGroupNameSpelling() {
        return groupNameSpelling;
    }

    public void setGroupNameSpelling(String groupNameSpelling) {
        this.groupNameSpelling = groupNameSpelling;
    }

    public String getGroupPortrait() {
        return groupPortrait;
    }

    public void setGroupPortrait(String groupPortrait) {
        this.groupPortrait = groupPortrait;
    }

    public GroupInfo getGroupInfo() {
        this.groupInfo = DBManager.getGroupInfo(groupId);
        return groupInfo;
    }

    public void setGroupInfo(GroupInfo groupInfo) {
        this.groupInfo = groupInfo;
    }
}

package com.yy.chat.bean;

import android.text.TextUtils;

import com.cjwsc.idcm.Utils.LoginUtils;
import com.cjwsc.idcm.Utils.pwd.PwdCheckUtil;
import com.cjwsc.idcm.base.application.BaseApplication;
import com.yy.chat.utils.PinyinUtils;

import org.litepal.crud.LitePalSupport;

import java.io.Serializable;

/**
 * @创建者 CSDN_LQR
 * @描述 朋友表(用户信息表)
 */
public class Friend extends LitePalSupport implements Serializable {

    private String userId;
    private String name;
    private String portraitUri;
    private String displayName;
    private String region;
    private String phoneNumber;
    private String address;
    private String status;
    private Long timestamp;
    private String letters;
    private String nameSpelling;
    private String remarck;
    private String displayNameSpelling;
    private String uid;
    private String sex;
    private String msg;
    private String groupMemberNick;
    private int read;   //加好友红点
    private int isFriend;//0为朋友 1为普通用户
    private int isRequest;//对方请求加好友
    private String vvNum;//vv号

    public void setUid(String uid) {
        this.uid = uid;
    }

    public String getUid() {
        return uid;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public String getSex() {
        return sex;
    }

    public void setSex(String sex) {
        this.sex = sex;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getRemarck() {
        return remarck;
    }

    public void setRemarck(String remarck) {
        this.remarck = remarck;
        setDisplayNameSpelling(PinyinUtils.getPinyin(remarck));
    }

    public int getIsFriend() {
        return isFriend;
    }

    public void setIsFriend(int isFriend) {
        this.isFriend = isFriend;
    }

    public int getIsRequest() {
        return isRequest;
    }

    public void setIsRequest(int isRequest) {
        this.isRequest = isRequest;
    }

    public int getRead() {
        return read;
    }

    public void setRead(int read) {
        this.read = read;
    }

    public Friend() {

    }

    public Friend(String userId, String name, String portraitUri) {
        this.userId = userId;
        this.name = name;
        this.portraitUri = portraitUri;
        this.displayName = name;
        String displayNameSpelling = PinyinUtils.getPinyin(name);
        this.nameSpelling = displayNameSpelling;
        this.displayNameSpelling = PwdCheckUtil.isLetter(displayNameSpelling) ? displayNameSpelling : "#";
    }


    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
        this.displayName = name;
        String displayNameSpelling = PinyinUtils.getPinyin(name);
        this.nameSpelling = displayNameSpelling;
        this.displayNameSpelling = PwdCheckUtil.isLetter(displayNameSpelling) ? displayNameSpelling : "#";
    }

    public String getPortraitUri() {
        return portraitUri;
    }

    public void setPortraitUri(String portraitUri) {
        this.portraitUri = portraitUri;
    }

    public String getDisplayName() {
        return displayName;
    }

    public void setDisplayName(String displayName) {
        this.displayName = displayName;
    }

    public String getRegion() {
        return region;
    }

    public void setRegion(String region) {
        this.region = region;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public Long getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(Long timestamp) {
        this.timestamp = timestamp;
    }

    public String getLetters() {
        return letters;
    }

    public void setLetters(String letters) {
        this.letters = letters;
    }

    public String getNameSpelling() {
        return nameSpelling;
    }

    public void setNameSpelling(String nameSpelling) {
        this.nameSpelling = nameSpelling;
    }

    public String getDisplayNameSpelling() {
        return displayNameSpelling;
    }

    public void setDisplayNameSpelling(String displayNameSpelling) {
        this.displayNameSpelling = displayNameSpelling;
    }

    public boolean isExitsDisplayName() {
        return !TextUtils.isEmpty(displayName);
    }

    public String getGroupMemberNick() {
        return groupMemberNick;
    }

    public void setGroupMemberNick(String groupMemberNick) {
        this.groupMemberNick = groupMemberNick;
    }

    public String getVvNum() {
        return vvNum;
    }

    public void setVvNum(String vvNum) {
        this.vvNum = vvNum;
    }
}
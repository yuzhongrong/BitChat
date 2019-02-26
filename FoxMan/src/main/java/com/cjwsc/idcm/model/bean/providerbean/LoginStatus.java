package com.cjwsc.idcm.model.bean.providerbean;

import com.cjwsc.idcm.Utils.AESUtil;
import com.cjwsc.idcm.Utils.LoginUtils;
import com.cjwsc.idcm.base.application.BaseApplication;

import java.io.Serializable;

/**
 * 作者：hxy
 * 电话：13891436532
 * 邮箱：hua.xiangyang@shuweikeji.com
 * 版本号：1.0
 * 类描述：FoxIDCW com.cjwsc.idcm.model.bean.providerbean ${CLASS_NAME}
 * 备注消息：
 * 修改时间：2018/3/15 14:37
 **/

public class LoginStatus implements Serializable {
    /**
     * id (integer, optional): 自增ID ,
     * guid (string, optional): 唯一编码 ,
     * user_name (string, optional): 用户名 ,
     * mobile (string, optional): 手机号码 ,
     * email (string, optional): 电子邮箱 ,
     * telphone (string, optional): 电话 ,
     * Ticket (string, optional): Ticket ,
     * language_url (string, optional): 语言包标记 ,
     * local_currency (integer, optional): 用户设置的本地货币 id ,
     * payPasswordFlag (boolean, optional): 支付密码是否设置 ,
     * local_currency_name (string, optional): 用户设置的本地货币名称 ,
     * login_time (string, optional): 最后登录时间 ,
     * create_time (string, optional): 注册时间 ,
     * device_id (string, optional): 用于标识设备的唯一id，目前是在注册的时候服务器端使用guid生成
     */
    private int id;
    private String guid;
    private String user_name;
    private String mobile;
    private String email;
    private String telphone;
    private String Ticket;
    private String language_url;
    private int local_currency;
    private boolean payPasswordFlag;
    private String local_currency_name;
    private int posMain;
    private String savePayPass;
    private String login_time;
    private String create_time;
    private String client;
    private String device_id;
    private String avatar;
    private String price;
    private String sex;
    private String address;
    private boolean autoAddFriend=true;
    private boolean SettingMsgNotification = true;
    private boolean SettingMsgDetail = true;
    private boolean SettingMsgSound = true;
    private boolean SettingMsgVibrate = true;
    private boolean SettingMsgSpeaker = true;
    private String token;
    private String vvNum;//vv号
    private int vvStatus;//vv号的状态 0:未修改过 1：已修改过

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
        LoginUtils.putLoginBean(this);
    }

    private String nickname;

    public String getCity() {
        return city;
    }

    public LoginStatus setCity(String city) {
        this.city = city;
        LoginUtils.putLoginBean(this);
        return this;
    }

    private String city;


    private String pwd;

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
        LoginUtils.putLoginBean(this);
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
        LoginUtils.putLoginBean(this);
    }

    public String getGuid() {
        return guid;
    }

    public void setGuid(String guid) {
        this.guid = guid;
        LoginUtils.putLoginBean(this);
    }

    public String getUser_name() {
        return user_name;
    }

    public void setUser_name(String user_name) {
        this.user_name = user_name;
        LoginUtils.putLoginBean(this);
    }

    public String getMobile() {
        return mobile;
    }

    public LoginStatus setMobile(String mobile) {
        this.mobile = mobile;
        LoginUtils.putLoginBean(this);
        return this;
    }

    public boolean isAutoAddFriend() {
        return autoAddFriend;
    }

    public void setAutoAddFriend(boolean autoAddFriend) {
        this.autoAddFriend = autoAddFriend;
        LoginUtils.putLoginBean(this);
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
        LoginUtils.putLoginBean(this);
    }

    public String getTelphone() {
        return telphone;
    }

    public void setTelphone(String telphone) {
        this.telphone = telphone;
        LoginUtils.putLoginBean(this);
    }

    public String getTicket() {
        return Ticket;
    }

    public void setTicket(String ticket) {
        Ticket = ticket;
        LoginUtils.putLoginBean(this);
    }

    public String getLanguage_url() {
        return language_url;
    }

    public void setLanguage_url(String language_url) {
        this.language_url = language_url;
        LoginUtils.putLoginBean(this);
    }

    public int getLocal_currency() {
        return local_currency;
    }

    public void setLocal_currency(int local_currency) {
        this.local_currency = local_currency;
        LoginUtils.putLoginBean(this);
    }

    public boolean isPayPasswordFlag() {
        return payPasswordFlag;
    }

    public void setPayPasswordFlag(boolean payPasswordFlag) {
        this.payPasswordFlag = payPasswordFlag;
        LoginUtils.putLoginBean(this);
    }

    public String getLocal_currency_name() {
        return local_currency_name;
    }

    public void setLocal_currency_name(String local_currency_name) {
        this.local_currency_name = local_currency_name;
        LoginUtils.putLoginBean(this);
    }

    public int getPosMain() {
        return posMain;
    }

    public void setPosMain(int posMain) {
        this.posMain = posMain;
        LoginUtils.putLoginBean(this);
    }

    public String getSavePayPass() {
        return savePayPass;
    }

    public void setSavePayPass(String savePayPass) {
        this.savePayPass = savePayPass;
        LoginUtils.putLoginBean(this);
    }

    public String getLogin_time() {
        return login_time;
    }

    public void setLogin_time(String login_time) {
        this.login_time = login_time;
        LoginUtils.putLoginBean(this);
    }

    public String getCreate_time() {
        return create_time;
    }

    public void setCreate_time(String create_time) {
        this.create_time = create_time;
        LoginUtils.putLoginBean(this);
    }

    public String getClient() {
        return client;
    }

    public void setClient(String client) {
        this.client = client;
        LoginUtils.putLoginBean(this);
    }

    public String getDevice_id() {
        return device_id;
    }

    public void setDevice_id(String device_id) {
        this.device_id = device_id;
        LoginUtils.putLoginBean(this);
    }


    public String getPwd() {
        return pwd;
    }


    public String getDecodePwd() {

        try {
            return new AESUtil().decrypt(this.pwd);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "";

    }

    public LoginStatus setPwd(String pwd) {
        this.pwd = pwd;
        LoginUtils.putLoginBean(this);
        return this;
    }

    public String getAvatar() {
        return avatar;
    }

    public LoginStatus setAvatar(String avatar) {
        this.avatar = avatar;
        LoginUtils.putLoginBean(this);
        return this;
    }

    public String getNickname() {
        return nickname;
    }

    public LoginStatus setNickname(String nickname) {
        this.nickname = nickname;
        LoginUtils.putLoginBean(this);
        return this;
    }

    public String getSex() {
        return sex;
    }

    public void setSex(String sex) {
        this.sex = sex;
        LoginUtils.putLoginBean(this);
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
        LoginUtils.putLoginBean(this);
    }

    public boolean isSettingMsgNotification() {
        return SettingMsgNotification;
    }

    public void setSettingMsgNotification(boolean settingMsgNotification) {
        SettingMsgNotification = settingMsgNotification;
        LoginUtils.putLoginBean(this);
    }

    public boolean isSettingMsgDetail() {
        return SettingMsgDetail;
    }

    public void setSettingMsgDetail(boolean settingMsgDetail) {
        SettingMsgDetail = settingMsgDetail;
        LoginUtils.putLoginBean(this);
    }

    public boolean isSettingMsgSound() {
        return SettingMsgSound;
    }

    public void setSettingMsgSound(boolean settingMsgSound) {
        SettingMsgSound = settingMsgSound;
        LoginUtils.putLoginBean(this);
    }

    public boolean isSettingMsgVibrate() {
        return SettingMsgVibrate;
    }

    public void setSettingMsgVibrate(boolean settingMsgVibrate) {
        SettingMsgVibrate = settingMsgVibrate;
        LoginUtils.putLoginBean(this);
    }

    public boolean isSettingMsgSpeaker() {
        return SettingMsgSpeaker;
    }

    public void setSettingMsgSpeaker(boolean settingMsgSpeaker) {
        SettingMsgSpeaker = settingMsgSpeaker;
        LoginUtils.putLoginBean(this);
    }

    public String getVvNum() {
        return vvNum;
    }

    public void setVvNum(String vvNum) {
        this.vvNum = vvNum;
        LoginUtils.putLoginBean(this);
    }

    public int getVvStatus() {
        return vvStatus;
    }

    public void setVvStatus(int vvStatus) {
        this.vvStatus = vvStatus;
        LoginUtils.putLoginBean(this);
    }

    @Override
    public String toString() {
        return "LoginStatus{" +
                "id=" + id +
                ", guid='" + guid + '\'' +
                ", user_name='" + user_name + '\'' +
                ", mobile='" + mobile + '\'' +
                ", email='" + email + '\'' +
                ", telphone='" + telphone + '\'' +
                ", Ticket='" + Ticket + '\'' +
                ", language_url='" + language_url + '\'' +
                ", local_currency=" + local_currency +
                ", payPasswordFlag=" + payPasswordFlag +
                ", local_currency_name='" + local_currency_name + '\'' +
                ", posMain=" + posMain +
                ", savePayPass='" + savePayPass + '\'' +
                ", login_time='" + login_time + '\'' +
                ",  create_time='" + create_time + '\'' +
                ", client='" + client + '\'' +
                ", device_id='" + device_id + '\'' +
                '}';
    }


}

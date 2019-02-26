package com.cjwsc.idcm.Utils;

import android.content.Context;

import com.cjwsc.idcm.base.application.BaseApplication;
import com.cjwsc.idcm.constant.AcacheKeys;
import com.cjwsc.idcm.model.bean.providerbean.LoginStatus;

/**
 * 作者：hxy
 * 电话：13891436532
 * 邮箱：hua.xiangyang@shuweikeji.com
 * 版本号：1.0
 * 类描述：FoxIDCW com.cjwsc.idcm.Utils ${CLASS_NAME}
 * 备注消息：
 * 修改时间：2018/3/15 16:27
 **/

public final class LoginUtils {

    public static LoginStatus getLoginBean(Context context) {
        return (LoginStatus) ACacheUtil.get(context).getAsObject(AcacheKeys.loginbean);
    }

    public static LoginStatus getLoginBean() {
        Context context = BaseApplication.getContext();
        return (LoginStatus) ACacheUtil.get(context).getAsObject(AcacheKeys.loginbean);
    }

    public static LoginStatus getLastLoginBean() {
        Context context = BaseApplication.getContext();
        return (LoginStatus) ACacheUtil.get(context).getAsObject(AcacheKeys.lastLoginbean);
    }

    public static void clear() {
        Context context = BaseApplication.getContext();
        ACacheUtil.get(context).put(AcacheKeys.lastLoginbean, getLoginBean(context));
        ACacheUtil.get(context).remove(AcacheKeys.loginbean);
    }

    public static void clearLast() {
        Context context = BaseApplication.getContext();
        ACacheUtil.get(context).remove(AcacheKeys.lastLoginbean);
        ACacheUtil.get(context).remove(AcacheKeys.loginbean);
    }

    public static void putLoginBean(LoginStatus mLoginStatus) {
        Context context = BaseApplication.getContext();
        ACacheUtil.get(context).put(AcacheKeys.loginbean, mLoginStatus);//当前登录用户
        ACacheUtil.get(context).put(AcacheKeys.lastLoginbean, mLoginStatus);//退出登录最后一个用户
        putLoginBean(context, mLoginStatus.getMobile(), mLoginStatus);//缓存历史记录
    }

    public static LoginStatus hasLoginBean(String key) {
        Context context = BaseApplication.getContext();
        return (LoginStatus) ACacheUtil.get(context).getAsObject(key);
    }

    public static void putLoginBean(Context context, String key, LoginStatus mLoginStatus) {
        ACacheUtil.get(context).put(key, mLoginStatus);
    }

    public static LoginStatus getLoginBean(Context context, String phone) {
        return (LoginStatus) ACacheUtil.get(context).getAsObject(phone);
    }

    public static String getAppToken(Context context) {
        LoginStatus bean = getLoginBean(context);
        return bean == null ? "" : bean.getTicket();
    }
}

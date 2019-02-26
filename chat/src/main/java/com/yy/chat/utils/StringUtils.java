package com.yy.chat.utils;

import android.content.Context;
import android.content.Intent;
import android.text.Spannable;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.TextPaint;
import android.text.TextUtils;
import android.text.style.ClickableSpan;
import android.view.View;

import com.cjwsc.idcm.Utils.LoginUtils;
import com.cjwsc.idcm.Utils.ToastUtil;
import com.cjwsc.idcm.base.application.BaseApplication;
import com.google.gson.JsonObject;
import com.orhanobut.logger.Logger;
import com.yy.chat.bean.Friend;
import com.yy.chat.db.DBManager;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class StringUtils {

    public static List<Friend> getList(Context context, String body) {

        List<Friend> friends = new ArrayList<>();
        if(TextUtils.isEmpty(body)){
            return friends;
        }
        try {
            JSONArray jsonArray = new JSONArray(body);

            Friend friend = null;
            JSONObject jsonObject;
            for (int i = 0; i < jsonArray.length(); i++) {
                jsonObject = jsonArray.getJSONObject(i);
                if (jsonObject.has("userid")) {
                    friend = DBManager.getFriendsById(jsonObject.getString("userid"));
                    if (friend == null) {
                        friend = new Friend();
                        friend.setUserId(jsonObject.getString("userid"));
                        if (jsonObject.has("nickname")) {
                            friend.setName(jsonObject.getString("nickname"));
                            friend.setRemarck(jsonObject.getString("nickname"));
                        }
                        friend.setUid(LoginUtils.getLoginBean(context).getMobile());
                        friend.setIsFriend(1);
                        DBManager.addNewFriend(friend, null);
                    }
                }
                friends.add(friend);
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return friends;
    }

    public static SpannableString getClickableSpan(final Context context, String body) {
        return getClickableSpan(context, body, true);
    }

    public static SpannableString getClickableSpan(final Context context, String body, boolean click) {
        List<Friend> list = getList(context, body);
        //用ImageSpan替换文本
        SpannableString spanableInfo = null;
        if (list != null && list.size() > 0) {
            String s = "";
            for (int i = 0; i < list.size(); i++) {
                Friend zanResponse = list.get(i);
                if (list.size() > 1) {
                    if (i == 0) {
                        if (!zanResponse.getUserId().equals(LoginUtils.getLoginBean().getMobile()))
                            s += zanResponse.getRemarck() + "邀请";
                        else
                            s += "你邀请";
                    } else {
                        if (!zanResponse.getUserId().equals(LoginUtils.getLoginBean().getMobile()))
                            s += zanResponse.getRemarck() + "、";
                        else
                            s += "你、";
                    }
                } else {
                    if (!zanResponse.getUserId().equals(LoginUtils.getLoginBean().getMobile()))
                        s += zanResponse.getRemarck() ;
                    else
                        s += "你";
                }
            }
            if (list.size() > 1) {
                s = s.substring(0, s.length() - 1);
            }
            s += "加入群聊";
            spanableInfo = new SpannableString(s);
            spanableInfo.setSpan(null, 0, 0, Spannable.SPAN_INCLUSIVE_EXCLUSIVE);
            for (int i = 0; i < list.size(); i++) {
                final Friend zanResponse = list.get(i);
                String s1 = zanResponse.getRemarck();
                int start = s.indexOf(s1);
                int end = s.indexOf(s1) + s1.length();
                if (!zanResponse.getUserId().equals(LoginUtils.getLoginBean().getMobile()) && click) {
                    spanableInfo.setSpan(new Clickable(new View.OnClickListener() {
                                @Override
                                public void onClick(View v) {
                                    Logger.d("------onClick----------");
                                    Intent intent = new Intent();
                                    intent.setClassName(BaseApplication.getContext().getApplicationInfo().packageName, "com.android.foxim.activitys.AddFriendSendNewsActivity");
                                    intent.addCategory("android.intent.category.userinfo");
                                    intent.setFlags(intent.FLAG_ACTIVITY_NEW_TASK);
                                    intent.putExtra("friend", (Serializable) zanResponse);
                                    context.startActivity(intent);
                                }
                            }), start, end,
                            Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
                }
            }
        }
        return spanableInfo;
    }

    /**
     * 内部类，用于截获点击富文本后的事件
     *
     * @author pro
     */
    public static class Clickable extends ClickableSpan implements View.OnClickListener {
        private final View.OnClickListener mListener;

        public Clickable(View.OnClickListener l) {
            mListener = l;
        }

        @Override
        public void onClick(View v) {
            mListener.onClick(v);
        }

        @Override
        public void updateDrawState(TextPaint ds) {
            ds.setColor(ds.linkColor);
            ds.setUnderlineText(false);    //去除超链接的下划线
        }
    }
}

package com.yy.chat.widget;


import com.cjwsc.idcm.Utils.pwd.PwdCheckUtil;
import com.yy.chat.bean.Friend;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

/**
 * @创建者 CSDN_LQR
 * @描述 根据拼音进行排序整理的工具类
 */
public class SortUtils {

    public static void sortContacts(List<Friend> list) {
        // Collections.sort(list);//排序后由于#号排在上面，故得把#号的部分集合移动到集合的最下面
        Collections.sort(list, new Comparator<Friend>() {
            @Override
            public int compare(Friend o1, Friend o2) {
                return o1.getDisplayNameSpelling().compareToIgnoreCase(o2.getDisplayNameSpelling());

            }
        });
        List<Friend> specialList = new ArrayList<>();
        for (int i = 0; i < list.size(); i++) {
            //将属于#号的集合分离开来
            String str = "";
            if (list.get(i).getDisplayNameSpelling().length() > 0) {
                str = list.get(i).getDisplayNameSpelling().substring(0, 1);//拿到第一个字母
            }else{
                str = "#";
            }
            if (!PwdCheckUtil.isLetter(str)) str = "#";//如果不是26位字母的 那么赋值# 代表无法识别
            if (str.equalsIgnoreCase("#")) {
                specialList.add(list.get(i));
            }
        }

        if (specialList.size() != 0) {
            list.removeAll(specialList);//先移出掉顶部的#号部分
            list.addAll(list.size(), specialList);//将#号的集合添加到集合底部
        }

    }
}

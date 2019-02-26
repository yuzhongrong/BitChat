package com.yy.chat.db;

import android.content.ContentValues;
import android.text.TextUtils;

import com.cjwsc.idcm.Utils.LogUtil;
import com.cjwsc.idcm.Utils.LoginUtils;
import com.cjwsc.idcm.base.application.BaseApplication;
import com.yy.chat.bean.ChatMessage;
import com.yy.chat.bean.Friend;
import com.yy.chat.bean.GroupInfo;
import com.yy.chat.bean.MemberInfo;
import com.yy.chat.bean.Session;

import org.litepal.LitePal;
import org.litepal.crud.callback.FindMultiCallback;
import org.litepal.crud.callback.SaveCallback;
import org.litepal.crud.callback.UpdateOrDeleteCallback;
import org.simple.eventbus.EventBus;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.Flowable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.functions.Function;
import io.reactivex.schedulers.Schedulers;

public class BitchatDBManager {


    //插入一条聊天消息
    public static void msgInsert(ChatMessage m, SaveCallback callback) {
        if (m != null) {
            if (m.getMsgType() == ChatMessage.Type.FRIEND_ADD.ordinal() || m.getMsgType() == ChatMessage.Type.FRIEND_AGREE.ordinal()) {
                return;
            }
            insert(m, callback);
        }
    }

    private static void insert(ChatMessage m, SaveCallback callback) {
        String uid = LoginUtils.getLoginBean(BaseApplication.getContext()).getMobile();
        if (m.getType() == 0 && m.getMsgType() == ChatMessage.Type.HINT.ordinal()) {//单聊的通过加好友信息只能有一条
            m.saveOrUpdateAsync("uid=? and fromID=? and msgType=?", uid, m.getFrom(), ChatMessage.Type.HINT.ordinal() + "").listen(callback);
        } else {
            m.saveOrUpdateAsync("uid=? and msgid=?", uid, m.getMsgid()).listen(callback);
        }
    }

    public static void getMsg(String from, int type, FindMultiCallback callback) {
        LitePal.where("uid=? and fromID = ? and msgType=?", LoginUtils.getLoginBean(BaseApplication.getContext()).getMobile()
                , from, type + "")
                .findAsync(ChatMessage.class).listen(callback);

    }

    public static void getMsgVoice(String from, int type, FindMultiCallback callback) {
        LitePal.where("uid=? and fromID = ? and msgType=? and dirction=?", LoginUtils.getLoginBean(BaseApplication.getContext()).getMobile()
                , from, type + "", "0")
                .findAsync(ChatMessage.class).listen(callback);

    }

    /**
     * 查询 指定ID的message是否存在
     */
    public static boolean queryMessageIsExist(String stanzaId) {
        return !LitePal.where("uid =? and msgid =?", LoginUtils.getLoginBean(BaseApplication.getContext()).getMobile(), stanzaId).find(ChatMessage.class).isEmpty();
    }

    public static boolean queryMessageIsExist(ChatMessage stanzaId) {
        return !LitePal.where("uid =? and fromID =? and msgType=?", LoginUtils.getLoginBean(BaseApplication.getContext()).getMobile(), stanzaId.getFrom(), stanzaId.getMsgType() + "").find(ChatMessage.class).isEmpty();
    }

    //获取所有未读信息
    public static void getAllNoRead(FindMultiCallback callback) {
        LitePal.where("uid=? and status = ?", LoginUtils.getLoginBean(BaseApplication.getContext()).getMobile(), ChatMessage.Status.NOREAD.ordinal() + "").findAsync(ChatMessage.class).listen(callback);
    }

    public static void update(String from, String status) {
        String uid = LoginUtils.getLoginBean(BaseApplication.getContext()).getMobile();
        ContentValues contentValues = new ContentValues();
        contentValues.put("status", ChatMessage.Status.SUCCESS.ordinal());
        LitePal.updateAllAsync(ChatMessage.class, contentValues, "uid=? and fromID=? and status = ?", uid, from, status).listen(new UpdateOrDeleteCallback() {
            @Override
            public void onFinish(int i) {
                EventBus.getDefault().post(new ChatMessage());
            }
        });
    }

    public static void update(ChatMessage chatMessage, UpdateOrDeleteCallback callback) {
        chatMessage.updateAllAsync("uid=? and msgId=?", chatMessage.getUid(), chatMessage.getMsgid()).listen(callback);
    }


    public static void msgSelect(String from, String to, String id, int page, FindMultiCallback callback) {
        if (TextUtils.isEmpty(id)) {
            try {
                LitePal.where("uid=? and fromID=?", from, to).order("timeStamp desc").limit(page).findAsync(ChatMessage.class).listen(callback);
            } catch (Exception e) {
                e.printStackTrace();
            }
        } else {
            LitePal.where("uid=? and fromID=? and timeStamp < ?", from, to, id).order("timeStamp desc").limit(page).findAsync(ChatMessage.class).listen(callback);
        }
    }

    public static void getConversations(String uid, FindMultiCallback callback) {
//        Cursor cursor= LitePal.findBySQL("select * from (select * from ChatMessage where uid=" + uid + " order by timeStamp) c GROUP BY c.fromID");
        LitePal.where(" uid = ?  GROUP BY fromID", uid).select().order("timeStamp desc").findAsync(ChatMessage.class).listen(callback);
    }

    public static int getNoRead(String uid, String from) {
        return LitePal.where("uid=? and fromID=? and status=?", uid, from, ChatMessage.Status.NOREAD.ordinal() + "").count(ChatMessage.class);
    }

    public static void deleteMsgByFrom(String from) {
        LitePal.deleteAllAsync(ChatMessage.class, "uid=? and fromID=?", LoginUtils.getLoginBean(BaseApplication.getContext()).getMobile(), from).listen(new UpdateOrDeleteCallback() {
            @Override
            public void onFinish(int i) {
                EventBus.getDefault().post(new ChatMessage());
                EventBus.getDefault().post("", "ACTION_ADD_NEWFRIEND_UPDATE");
                EventBus.getDefault().post(from, "ACTION_CONTACTS_UPDATE");
            }
        });
    }

    public static void deleteMsgByFrom2(String from) {
        LitePal.deleteAllAsync(ChatMessage.class, "uid=? and fromID=?", LoginUtils.getLoginBean(BaseApplication.getContext()).getMobile(), from).listen(new UpdateOrDeleteCallback() {
            @Override
            public void onFinish(int i) {
                EventBus.getDefault().post(new ChatMessage());
                EventBus.getDefault().post(from, "ACTION_CONTACTS_UPDATE");
            }
        });
    }

    public static Friend getFriendsById(String userid) {
        if (LoginUtils.getLoginBean(BaseApplication.getContext()) == null) {
            return null;
        }
        String uid = LoginUtils.getLoginBean(BaseApplication.getContext()).getMobile();
        return LitePal.where("uid=? and userId=?", uid, userid).findFirst(Friend.class);
    }

    public static Friend getFriendsById(String uid, String userid) {
        return LitePal.where("uid=? and userId=?", uid, userid).findFirst(Friend.class);
    }

    public static void setIsFriend(String userid, UpdateOrDeleteCallback callback) {
        ContentValues contentValues = new ContentValues();
        contentValues.put("isFriend", 0);
        contentValues.put("read", 0);
        LitePal.updateAllAsync(Friend.class, contentValues, "uid=? and userId=?", LoginUtils.getLoginBean(BaseApplication.getContext()).getMobile(), userid).listen(callback);
    }

    public static void setIsFriend(String userid, String uid, UpdateOrDeleteCallback callback) {
        ContentValues contentValues = new ContentValues();
        contentValues.put("isFriend", 0);
        contentValues.put("read", 0);
        LitePal.updateAllAsync(Friend.class, contentValues, "uid=? and userId=?", uid, userid).listen(callback);
    }

    public static void addNewFriend(Friend friend, SaveCallback saveCallback) {
        friend.saveOrUpdateAsync("userId=? and uid=?", friend.getUserId(),
                LoginUtils.getLoginBean(BaseApplication.getContext()).getMobile()).listen(saveCallback);
    }

    public static void addNewFriend(String uid, Friend friend, SaveCallback saveCallback) {
        friend.saveOrUpdateAsync("userId=? and uid=?", friend.getUserId(),
                uid).listen(saveCallback);
    }

    public static Friend getFriends(String userid) {
        return LitePal.where("uid=? and userId=? and isFriend=?", LoginUtils.getLoginBean(BaseApplication.getContext()).getMobile(), userid, "0").findFirst(Friend.class);
    }

    public static void getAllFriends1(FindMultiCallback callback) {
        if (LoginUtils.getLoginBean(BaseApplication.getContext()) != null)
            LitePal.where("uid=? and isRequest=? and read=? GROUP BY userId", LoginUtils.getLoginBean(BaseApplication.getContext()).getMobile(), "1", "1").findAsync(Friend.class).listen(callback);
    }

    /**
     * 获取所有好友
     **/

    public static Flowable<List<Friend>> getAllFriends() {
        return Flowable.just(LitePal.findAll(Friend.class))
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());


    }


    /**判断好友是否存在**/
    public static boolean isexitFriend(String address){
//       int count =LitePal.where("userId=?",address).count(Friend.class);
//       if(count>0)
//           return true;
//         else
//           return false;

       return LitePal.isExist(Friend.class,"userId=?",address);
    }


    /**session is exit**/


    public static boolean isexitSession(String sessionid){

        return LitePal.isExist(Session.class,"sessionid=?",sessionid);

    }



    /**get session list**/

    public static Flowable<List<Session>> getAllSession() {
        return Flowable.just(LitePal.findAll(Session.class))
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());


    }


    /**get all unread session message count**/

    public static int getAllUnreadFromSession(){
       return LitePal.sum(Session.class,"unreadmsgcount",Integer.class);

    }



    public static Session getOneSession(String sessionid){

        List<Session> sessions=LitePal.where("sessionid=?",sessionid).find(Session.class);
        if(sessions.size()==0){
            return null;
        }else{
            return sessions.get(0);
        }



    }


    public static Friend getRemarkBySessionId(String sessionid){

       return LitePal.where("userId=?",sessionid).findFirst(Friend.class);


    }




    //获取请求的好友可能是好友可能不是好友
    public static void getRequestFriend(FindMultiCallback findCallback) {
        LitePal.where("uid=? and isRequest=? GROUP BY userId", LoginUtils.getLoginBean(BaseApplication.getContext()).getMobile(), "1").order("timestamp desc").findAsync(Friend.class).listen(findCallback);
    }

    //删除好友 没有增加删除会话框和聊天信息
    public static void deleteFriend(String jid, UpdateOrDeleteCallback callback) {
        LogUtil.e("*******delete friend userid=" + jid);
        String userid = LoginUtils.getLoginBean(BaseApplication.getContext()).getMobile();
        ContentValues contentValues = new ContentValues();
        contentValues.put("isFriend", 1);
        contentValues.put("isRequest", 0);
        LitePal.updateAllAsync(Friend.class, contentValues, "userId=? and uid=?", jid, userid).listen(callback);
    }

    public static void setFriendRemarck(Friend friend, UpdateOrDeleteCallback callback) {
        friend.updateAllAsync("userId=? and uid=?", friend.getUserId(), friend.getUserId()).listen(callback);
    }

    public static GroupInfo getGroupInfo(String groupId) {
        if (LoginUtils.getLoginBean(BaseApplication.getContext()) == null) {
            return null;
        }
        return LitePal.where("userId=? and group_id=?", LoginUtils.getLoginBean(BaseApplication.getContext()).getMobile(),
                groupId).findFirst(GroupInfo.class);
    }

    public static void getAllGroupInfo(FindMultiCallback callback) {
        LitePal.where("userId=?", LoginUtils.getLoginBean(BaseApplication.getContext()).getMobile())
                .findAsync(GroupInfo.class).listen(callback);
    }

    public static void addOrUpdateGroupInfo(GroupInfo groupInfo, SaveCallback callback) {
        groupInfo.saveOrUpdateAsync("userId=? and group_id=?", LoginUtils.getLoginBean(BaseApplication.getContext()).getMobile(),
                groupInfo.getGroup_id()).listen(callback);
    }

    //解散群聊
    public static void dissolveGroup(String groupId) {
        LitePal.deleteAllAsync(GroupInfo.class, "group_id=?", groupId).listen(new UpdateOrDeleteCallback() {
            @Override
            public void onFinish(int i) {
                LitePal.deleteAllAsync(ChatMessage.class,
                        "fromID=?", groupId).listen(new UpdateOrDeleteCallback() {
                    @Override
                    public void onFinish(int i) {
                        EventBus.getDefault().post(new ChatMessage());
                    }
                });
            }
        });
        LitePal.deleteAllAsync(MemberInfo.class, "group_id=?", groupId).listen(null);
    }

    //本人退出群聊
    public static void quitGroup(String groupId) {
        LitePal.deleteAllAsync(GroupInfo.class, "userId=? and group_id=?", LoginUtils.getLoginBean(BaseApplication.getContext()).getMobile(),
                groupId).listen(new UpdateOrDeleteCallback() {
            @Override
            public void onFinish(int i) {
                deleteMsgByFrom2(groupId);
            }
        });
        LitePal.deleteAllAsync(MemberInfo.class, "userId=? and group_id=?",
                LoginUtils.getLoginBean(BaseApplication.getContext()).getMobile(), groupId).listen(null);
    }

    public static void deleteMsgAndMember(String groupId) {
        deleteMsgByFrom2(groupId);
        LitePal.deleteAllAsync(MemberInfo.class, "userId=? and group_id=?",
                LoginUtils.getLoginBean(BaseApplication.getContext()).getMobile(), groupId).listen(null);
    }


    public static void addOrUpdateMemberInfo(MemberInfo memberInfo, SaveCallback callback) {
        memberInfo.saveOrUpdateAsync("userId=? and group_id=? and member_JID=? ", LoginUtils.getLoginBean(BaseApplication.getContext()).getMobile(),
                memberInfo.getGroup_id(), memberInfo.getMember_JID()).listen(callback);
    }

    public static MemberInfo getMemberInfo(String userId, String groupId) {
        return LitePal.where("userId=? and member_JID=? and group_id=?", LoginUtils.getLoginBean(BaseApplication.getContext()).getMobile(),
                userId, groupId).findFirst(MemberInfo.class);
    }

    public static void getAllMemberInfo(FindMultiCallback callback) {
        LitePal.where("userId=?", LoginUtils.getLoginBean(BaseApplication.getContext()).getMobile())
                .findAsync(MemberInfo.class).listen(callback);
    }

    public static void getAllMemberInfoByGroup(String groupId, FindMultiCallback callback) {
        LitePal.where("group_id=? and userId=?", groupId, LoginUtils.getLoginBean(BaseApplication.getContext()).getMobile())
                .findAsync(MemberInfo.class).listen(callback);
    }

    public static void updateAllMemberInfoByGroup(String groupId, List<MemberInfo> memberInfos) {
        LitePal.deleteAllAsync(MemberInfo.class, "group_id=? and userId=?", groupId,
                LoginUtils.getLoginBean(BaseApplication.getContext()).getMobile()).listen(new UpdateOrDeleteCallback() {
            @Override
            public void onFinish(int i) {
                LitePal.saveAllAsync(memberInfos).listen(null);
            }
        });

    }

    public static void setReadFriend() {
        ContentValues contentValues = new ContentValues();
        contentValues.put("read", 0);
        LitePal.updateAllAsync(Friend.class, contentValues, "uid=?", LoginUtils.getLoginBean(BaseApplication.getContext()).getMobile()).listen(new UpdateOrDeleteCallback() {
            @Override
            public void onFinish(int i) {
                EventBus.getDefault().post("", "ACTION_ADDFRIEND_NOTIFY");
            }
        });
    }

    /**
     * 根据字眼查询联系人匹配+群组匹配+聊天记录匹配
     **/
    public static Flowable<List<Object>> getAllMatch(String str) {

        //群组查找暂时没做
        return Flowable.just(new ArrayList<>())
                .observeOn(Schedulers.io())
                .map(new Function<ArrayList<Object>, ArrayList<Object>>() {

                    @Override
                    public ArrayList<Object> apply(ArrayList<Object> objects) throws Exception {
                        /**联系人匹配**/
                        List<Friend> friends = findContactsByFriend(str);
                        for (Friend item : friends) objects.add(item);

                        /**群成员列表匹配 这个由于设计问题 这边无法通过正规做法实现因为member_nick初始化都是为null无法匹配**/
//                        List<GroupInfo> datas=  DBManager.getSelfGroups();
//                        int count=getMemberCount();
//
//                        for (GroupInfo item:datas){
//                            List<MemberInfo> memberInfos= DBManager.getContactByGroupMembers(str, item.getGroup_id());
//                            if(memberInfos.size()>0){
//                                item.setListGroupMembers((MemberInfo[]) memberInfos.toArray());
//                                objects.add(item);
//                            }
//
//                        }
                        return objects;
                    }
                });

    }

    /**
     * 查询文字匹配的联系人
     */
    public static List<Friend> findContactsByFriend(String querystr) {
        String uid = LoginUtils.getLoginBean(BaseApplication.getContext()).getMobile();
        return LitePal.where("(uid=? and isFriend=? and userId!=?) and (remarck like ? or name like ? or userId like ? or vvNum like ?)", uid
                , "0", uid, "%" + querystr + "%", "%" + querystr + "%", querystr + "%", "%" + querystr + "%").find(Friend.class);

    }
}

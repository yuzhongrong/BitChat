package android.com.bitchat.controler;

import android.com.bitchat.bean.EventAddfriend;
import android.com.bitchat.utils.MessageUtils;

import com.hwangjr.rxbus.RxBus;
import com.orhanobut.logger.Logger;
import com.yy.chat.bean.ChatMessage;
import com.yy.chat.bean.Friend;
import com.yy.chat.bean.Session;
import com.yy.chat.db.BitchatDBManager;

import org.litepal.crud.callback.SaveCallback;
import org.simple.eventbus.EventBus;

public class GsSocketManager {



    static  {
        System.loadLibrary("bitchatAPI"); //引入包名
        System.loadLibrary("bitchat"); //引入包名
        System.loadLibrary("gcommon"); //引入包名
    }


    private static GsSocketManager instance;

    public static GsSocketManager getInstance(){
        synchronized (GsSocketManager.class){

            if(instance==null){
                instance=new GsSocketManager();
                return instance;
            }

            return instance;

        }

    }






    public  native  String helloBitchat();

    public  native  int gchatlogout();

    public  native  int gchatlogin(String ip, int port, String publickey, String privatekey,String address);

    public  native String gchatsearch(String address);

    public  native boolean gchatgenkey(String path);

    public  native String gchatconnectfriend(String id);

    public  native int gchatchat(String handle,String content);


    public static void gchatcallback(String msg,int len,int type,String handlername,String address,String pulickey) {
        /*you can do anything here as you like*/
        Logger.d("----onMessage()------>"+msg);

        //handler add friend


        //构造接收消息
        ChatMessage chatMessage= MessageUtils.Companion.makeReceiveMsg(address,type,msg);

        //保存消息到本地消息列表数据库
        chatMessage.saveAsync().listen(new SaveCallback() {
            @Override
            public void onFinish(boolean b) {
                if(b){

                    if(!chatMessage.getBody().startsWith("remark:")){
                        RxBus.get().post(chatMessage);
                    }

                }
            }
        });

        //create session
        if(!chatMessage.getBody().startsWith("remark:")){

            Session session;
            if(BitchatDBManager.isexitSession(chatMessage.getFrom())){
                session= BitchatDBManager.getOneSession(chatMessage.getFrom());
                session.setSessionid(chatMessage.getFrom())
                .setType(type)
                .setNewmessage(chatMessage.getBody())
                .setUpdatetime(chatMessage.getTimeStamp());


            }else{
                session=new Session(chatMessage.getFrom(),type,chatMessage.getBody(),chatMessage.getTimeStamp());
            }
            session.setUnreadmsgcount(session.getUnreadmsgcount()+1);
            session.saveOrUpdateAsync("sessionid=?",session.getSessionid()).listen(new SaveCallback() {
                @Override
                public void onFinish(boolean b) {

                    RxBus.get().post("ACTION_UPDATE_SESSION",session.getSessionid());
                }
            });

        }




        //接收到消息的时候直接把发送这个消息的人加成好友
        if(!BitchatDBManager.isexitFriend(address)){
            Friend friend= new Friend(address, address,"");
            friend.setIsFriend(0);
            int index=chatMessage.getBody().indexOf(":");
            friend.setRemarck(chatMessage.getBody().substring(index+1));
            friend.saveOrUpdateAsync("userId=?",address).listen(new SaveCallback() {
                @Override
                public void onFinish(boolean b) {
                    if(b){
                        RxBus.get().post("ACTION_UPDATE_ADD_FRIEND","");
                    }

                }
            });

        }







        //更新本地会话列表

     //   Session session=new Session(address,msg,System.currentTimeMillis());


    }



    //  public  native String gchat_connect_friend(String id);
   // public  native String gchat_chat(byte[] bytes);
   // public  native String gchat_quit_chat();

}
